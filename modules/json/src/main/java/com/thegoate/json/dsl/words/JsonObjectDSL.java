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

package com.thegoate.json.dsl.words;

import com.thegoate.Goate;
import com.thegoate.annotations.GoateDescription;
import com.thegoate.dsl.DSL;
import com.thegoate.dsl.GoateDSL;
import org.json.JSONObject;

/**
 * Returns a json object.
 * Created by gtque on 4/21/2017.
 */
@GoateDSL(word = "json object")
@GoateDescription(description = "Returns a json object based on the given string representation",
        parameters = {"The string representation of the json object, null, empty or missing produces an empty json object."})
public class JsonObjectDSL extends DSL {
    public JsonObjectDSL(Object value) {
        super(value);
    }

    public static Object jsonObject(String jsonString){
        return jsonObject(jsonString, new Goate());
    }

    public static Object jsonObject(String jsonString, Goate data){
        return new JsonObjectDSL("json object::"+jsonString).evaluate(data);
    }
    @Override
    public Object evaluate(Goate data) {
        String def = ""+get(1,data);
        if(def.equals("null")||def.isEmpty()){
            def = "{}";
        }
        return new JSONObject(def);
    }
}
