package com.linkkou.gson.typefactory.impl;

import com.google.gson.*;
import com.linkkou.gson.typefactory.GsonEnum;

import java.lang.reflect.*;

/**
 * 输出 枚举转换
 * 由{@link GsonEnumTypeAdapterFactory}使用
 *
 * @author lk
 * @version 1.0
 * @date 2019/9/2 19:09
 */
public class GsonEnumJsonSerializer<E> implements JsonSerializer<E> {

    public GsonEnumJsonSerializer() {

    }

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        JsonPrimitive jsonElement = null;
        if (src instanceof GsonEnum) {
            try {
                final Object serialize = ((GsonEnum) src).serialize();
                JsonPrimitive jsonPrimitive = new JsonPrimitive("");
                final Field value = jsonPrimitive.getClass().getDeclaredField("value");
                value.setAccessible(true);
                value.set(jsonPrimitive, serialize);
                jsonElement = jsonPrimitive;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return jsonElement;
    }

    public E deserialize(JsonElement json, Type type) throws JsonParseException {
        final Class<?>[] interfaces = ((Class) type).getInterfaces();
        for (Class c : interfaces) {
            if (c.equals(GsonEnum.class)) {
                try {
                    GsonEnum[] objs = (GsonEnum[]) ((Class) type).getEnumConstants();
                    if (null != objs && objs.length > 0) {
                        /**
                         * 自定义枚举的实现接口后其内部的枚举对应的都是一个方法
                         * 默认取值任意一个枚举的实现方法就行
                         */
                        return (E) objs[0].deserialize(json);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
