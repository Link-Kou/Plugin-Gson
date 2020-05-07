package com.linkkou.gson.typefactory.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DecimalFormat;

/**
 * 输出
 * Float 精度控制
 * @author lk
 * @version 1.0
 * @date 2019/9/2 19:09
 *
 */
public class FloatJsonSerializer<E> implements JsonSerializer<E> {

    public FloatJsonSerializer() {
    }

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        //null不需要判断，gson默认已经排除null
        if (src instanceof Float) {
            Float value = (Float) src;
            DecimalFormat format = new DecimalFormat("##0.00");
            if (value.isNaN()) {
                String temp = format.format(0);
                // Convert NaN to zero
                return new JsonPrimitive(temp);
            }
            String temp = format.format(src);
            return new JsonPrimitive(temp);
        }
        return null;
    }

}
