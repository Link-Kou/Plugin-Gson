package com.linkkou.gson.typefactory.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DecimalFormat;

/**
 * Double 精度控制
 *
 * @author lk
 * @version 1.0
 * @date 2019/9/2 14:33
 */
public class FloatTypeAdapter<E> implements JsonSerializer<E> {

    public FloatTypeAdapter() {
    }

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        //null不需要判断，gson默认已经排除null
        if (src instanceof Float) {
            Float value = (Float) src;
            DecimalFormat format = new DecimalFormat("##0.00");
            if (value.isNaN()) {
                String temp = format.format(0);
                return new JsonPrimitive(temp); // Convert NaN to zero
            }
            String temp = format.format(src);
            return new JsonPrimitive(temp);
        }
        return null;
    }

}
