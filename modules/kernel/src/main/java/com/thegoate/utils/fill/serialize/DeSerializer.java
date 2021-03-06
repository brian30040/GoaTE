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
package com.thegoate.utils.fill.serialize;

import com.thegoate.Goate;
import com.thegoate.logging.BleatBox;
import com.thegoate.logging.BleatFactory;
import com.thegoate.reflection.GoateReflection;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Remember: GoaTE is intended for testing purposes, as such unsafe
 * methods, reflection, and access may be used or altered. Use in production code at your own risk.
 * Created by Eric Angeli on 6/26/2018.
 */
public class DeSerializer extends Cereal{
    private BleatBox LOG = BleatFactory.getLogger(getClass());
    private Goate data;
    private Class dataSource;
    private Class genericType;

    public <T> T build(Class<T> type){
        Object o = null;
        try {
            o = buildInstance(type);
            if (data == null) {
                LOG.debug("Build Pojo", "The data was null, the pojo will be initialized but empty (unless defaults are set)");
            } else {
                if(dataSource == null){
                    LOG.debug("Build Pojo", "The data source was not specified, defaulting to default");
                    dataSource = DefaultSource.class;
                }
                GoateReflection gr = new GoateReflection();
                Map<String, Field> fields = gr.findFields(type);
                for(Map.Entry<String, Field> field:fields.entrySet()){
                    GoateSource gs = findGoateSource(field.getValue(), dataSource);
                    String fieldKey = field.getKey();
                    boolean flatten = false;
                    if(gs!=null){
                        fieldKey = gs.key();
                        flatten = gs.flatten();
                    }
                    Object value = data.get(fieldKey);
                    boolean acc = field.getValue().isAccessible();
                    field.getValue().setAccessible(true);
                    try {
                        if(value!=null
                                ||data.filter(fieldKey+"\\.").size()>0
                                ||data.getStrict(fieldKey)!=null
                                ||field.getValue().getType().getAnnotation(GoatePojo.class)!=null) {
                            Goate d = new Goate().merge(data,false);
                            if(!flatten){
                                d = data.filter(fieldKey.replace("##", "[0-9]*")).scrubKeys(fieldKey+"\\.");
                            } else {
                                d = data.filterStrict(fieldKey.replace("##","[0-9]*"));
                            }
                            field.getValue().set(o, new Cast(d, dataSource).container(o).field(field.getValue()).cast(value,field.getValue().getType()));
                        }
                    } catch (Exception e) {
                        LOG.error("Build Pojo", "Failed to set field: " + e.getMessage(), e);
                    }
                    field.getValue().setAccessible(acc);
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            LOG.error("Build Pojo", "Failed to build the pojo: " + e.getMessage(), e);
        }
        return (T)o;
    }

    private Object buildInstance(Class type) throws IllegalAccessException, InstantiationException {
        if(type==null){
            LOG.error("Build Pojo", "Can't build the pojo if you don't tell me what to build.");
            throw new RuntimeException("The pojo class was not specified.");
        }
        Object o = type.newInstance();
        if(o instanceof TypeT){
            ((TypeT)o).setGoateType(genericType);
        }
        return o;
    }

    public DeSerializer data(Goate data){
        this.data = data;
        return this;
    }

    public DeSerializer T(Class type){
        return genericType(type);
    }
    public DeSerializer genericType(Class type){
        this.genericType = type;
        return this;
    }

    public DeSerializer data(Map<String, ?> data){
        return data(new Goate(data));
    }

    public DeSerializer from(Class dataSource){
        this.dataSource = dataSource;
        return this;
    }
}
