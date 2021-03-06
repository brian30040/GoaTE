/*
 * Copyright (c) 2017. Eric Angeli
 *
 *  Permission is hereby granted, free of charge,
 *  to any person obtaining a copy of this software
 *  and associated documentation files (the "Software"),
 *  to deal in the Software without restriction,
 *  including without limitation the rights to use, copy,
 *  modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit
 *  persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission
 *  notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 *  AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 *  DEALINGS IN THE SOFTWARE.
 */
package com.thegoate.barn;

import com.thegoate.Goate;
import com.thegoate.barn.data.BarnDataLoader;
import com.thegoate.barn.staff.StepsExecutor;
import com.thegoate.expect.ExpectEvaluator;
import com.thegoate.expect.ExpectationThreadBuilder;
import com.thegoate.testng.TestNGEngine;
import org.testng.SkipException;

import static org.testng.Assert.assertTrue;

/**
 * The base barn class for running complete data driven tests.
 * Created by Eric Angeli on 5/22/2017.
 */
public class Barn extends TestNGEngine {
    protected String label = "barn";//default label, all tests in the test case directory belong to this group.
    protected String baseTestCaseDir = "testcases";
    protected ExpectEvaluator ev = null;
    Throwable preFailure = null;

    public Barn() {
        super();
        includeClassMethodInName = false;
    }

    public Barn(Goate data) {
        super(data);
        includeClassMethodInName = false;
    }

    @Override
    public void defineDataLoaders() {
        runData.put("dl##", new BarnDataLoader().testCaseDirectory(baseTestCaseDir).defaultGroup(label));
    }

    public void setup() {
        LOG.debug("Barn Setup", "----running setup----");
        try {
            steps("setup");
        } catch (Throwable t) {
            preFailure = t;
        }
        LOG.debug("Barn Setup", "----finished setup----");
    }

    public void cleanup() {
        LOG.debug("Cleanup", "----running cleanup----");
        try {
            steps("cleanup");
            evaluatePostconditions();
        } catch (Throwable t) {
            LOG.warn(getTestName(), "Cleanup had a failure: " + t.getMessage(), t);
            throw t;
        }
        LOG.debug("Cleanup", "----finished cleanup----");
    }

    protected void steps(String step) {
        if (data.size() > 0) {
            StepsExecutor dosteps = new StepsExecutor(data).notOrdered();
            String steps = "" + data.get(step, "[]");
            data.put(step + "_result##", dosteps.doSteps(steps));
        }
    }

    public void execute() {
        setup();
        if (data.size() == 0) {
            LOG.skip(getTestName(), "Skipping test because there is nothing to execute.");
            throw new SkipException("No run data.");
        }
        if (preFailure != null) {
            LOG.skip(getTestName(), "Skipping test because pre-steps failed: " + preFailure.getMessage());
            throw new SkipException(preFailure.getMessage());
        } else {
            try {
                //the status of preconditions is only printed if one of the preconditions fails.
                LOG.debug(getTestName(), "----checking preconditions----");
                evaluatePreconditions();
                ev = null;//reset to null so that if the test throws an exception
                //the checks for preconditions don't get printed.
                try {
                    LOG.debug(getTestName(), "----starting execution----");
                    StepsExecutor steps = new StepsExecutor(data).notOrdered();
                    data.put("_goate_result", steps.doSteps(null).get("0"));
                } catch (Throwable t) {
                    LOG.fatal(getTestName(), "Encountered a problem executing the test: " + t.getMessage(), t);
                    throw t;
                } finally {
                    LOG.debug(getTestName(), "----finished execution----");
                    LOG.debug(getTestName(), "----evaluating expectations----");
                }
                evaluateExpectations();
            } catch (Throwable t) {
                throw t;
            } finally {
                LOG.debug(getTestName(), "----finished expectations----");
                cleanup();
            }
        }
    }

    protected void evaluateExpectations() {
        evaluate("expect", false);
    }

    protected void evaluatePreconditions() {
        evaluate("preconditions", true);
    }

    protected void evaluatePostconditions() {
        evaluate("postconditions", false);
    }

    protected void evaluate(String stage, boolean clearAfterRunning) {
        etb = new ExpectationThreadBuilder(new Goate().put("parent", data).merge(data, false));
        etb.expect(data.filter(stage + "\\."));
        evalTimeout(Long.parseLong("" + data.get(stage + "_timeout", data.get("timeout_expect", 500L))));
        evalPeriod(Long.parseLong("" + data.get(stage + "_period", data.get("period_expect", 50L))));
        evaluate(clearAfterRunning);
    }
}
