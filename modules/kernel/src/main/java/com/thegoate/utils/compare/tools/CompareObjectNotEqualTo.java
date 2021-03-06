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
package com.thegoate.utils.compare.tools;

import java.util.Objects;

import com.thegoate.HealthMonitor;
import com.thegoate.annotations.IsDefault;
import com.thegoate.reflection.GoateReflection;
import com.thegoate.utils.compare.Compare;
import com.thegoate.utils.compare.CompareTool;
import com.thegoate.utils.compare.CompareUtil;

import static com.thegoate.logging.volume.VolumeKnob.volume;

/**
 * Compares two booleans for equality.
 * Created by Eric Angeli on 5/9/2017.
 */
@CompareUtil(operator = "!=")
@IsDefault
public class CompareObjectNotEqualTo extends CompareObject {

    public CompareObjectNotEqualTo(Object actual) {
        super(actual);
    }

    @Override
    public boolean isType(Object check) {
        return false;
    }

    @Override
    public boolean evaluate() {
        boolean result = false;
        GoateReflection gr = new GoateReflection();
        if (expected != null && (gr.isPrimitive(expected.getClass()) || expected instanceof Number)){
            LOG.debug("isNotEqualTo", "Detected a primitive, will try to compare as that formatted primitive type.");
            Compare comp = new Compare(expected);
            result = comp.compareNumeric(expected instanceof Number).to(actual).using("!=").evaluate();
            health = comp.healthCheck();
        } else {
            result = !(Objects.equals(actual, expected));
        }
        if(actual instanceof HealthMonitor){
            health = (((HealthMonitor) actual).healthCheck());
        } else {
            if(!result) {
                health.put("is equal", "" + volume(actual) + "==" + volume(expected));
            }
        }
        return result;
    }
}
