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
package com.thegoate.utils.compare.tools.string;

import com.thegoate.annotations.IsDefault;
import com.thegoate.utils.compare.CompareUtil;

import static com.thegoate.utils.type.GoateNullCheck.isNull;

/**
 * Checks if a string is lexographically less than or equal to.
 * Created by Eric Angeli on 4/17/2018.
 */
@CompareUtil(operator = "<=", type = String.class)
@IsDefault
public class CompareStringLessThanEqualTo extends CompareStringTool {
    public CompareStringLessThanEqualTo(Object actual) {
        super(actual);
    }

    @Override
    public boolean evaluate() {
        String check = ""+actual;
        String act = "" + expected;
        return isNull(actual)?(isNull(expected)?true:false):(isNull(expected)?false:check.compareTo(act)<=0);
    }
}
