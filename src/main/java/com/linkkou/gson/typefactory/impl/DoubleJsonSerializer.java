package com.linkkou.gson.typefactory.impl;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.DecimalFormat;

/**
 * 输出
 * Double 精度控制
 * 小数点保留两位
 * @author lk
 * @version 1.0
 * @date 2019/9/2 19:09
 *
 */
public class DoubleJsonSerializer<E> implements JsonSerializer<E> {

    public DoubleJsonSerializer() {
    }

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        //null不需要判断，gson默认已经排除null
        if (src instanceof Double) {
            Double value = (Double) src;
            DecimalFormat format = new DecimalFormat("##0.00");
            if (value.isNaN()) {
                String temp = format.format(0);
                return new JsonPrimitive(temp);
            }
            String temp = format.format(src);
            return new JsonPrimitive(temp);
        }
        return null;
    }

}
