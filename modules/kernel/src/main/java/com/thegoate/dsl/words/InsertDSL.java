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

package com.thegoate.dsl.words;

import com.thegoate.Goate;
import com.thegoate.annotations.GoateDescription;
import com.thegoate.dsl.DSL;
import com.thegoate.dsl.GoateDSL;

/**
 * NOT YET SUPPORTED!!!
 * Returns the value of object after inserting the given values.
 * Created by gtque on 8/10/2017.
 */
@GoateDSL(word = "insert")
@GoateDescription(description = "NOT YET SUPPORTED!!! Returns the object inserted with corresponding data from the goate collection." +
        "\n The default is to treat the object as a string, but it will try to find the appropriate insert utility and only use insert string as a default.",
    parameters = {"The object to have things inserted into, this may be a string or a call to another dsl.",
    "The data to insert into the object, should have to fields in it: data and mapping (mapping is optional)"})
public class InsertDSL extends DSL {
    public InsertDSL(Object value) {
        super(value);
    }

    @Override
    public Object evaluate(Goate data) {
        return null;//new Insert(get(1,data)).with(data);
    }
}
