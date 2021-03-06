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
package com.thegoate.utils;

import com.thegoate.Goate;
import com.thegoate.logging.BleatBox;
import com.thegoate.logging.BleatFactory;

/**
 * Base goate util class that implements the isType method.
 * Created by Eric Angeli on 5/19/2017.
 */
public abstract class GoateUtility implements Utility {
    protected final BleatBox LOG = BleatFactory.getLogger(getClass());
    protected Goate takeActionOn;
    protected Object nested;
    protected boolean processNested;
    protected Goate health = new Goate();
    protected Goate data;

    public GoateUtility(Object val) {
        processNested = true;
        init(val);
    }

    protected void init(Object val){
        this.takeActionOn = (Goate)val;
    }

    @Override
    public boolean isType(Object check) {
        return check instanceof Goate;
    }

    @Override
    public GoateUtility setData(Goate data){
        this.data = data;
        return this;
    }

    @Override
    public Goate healthCheck(){
        return health;
    }

    protected abstract Object processNested(Object subContainer);
}
