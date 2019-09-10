package com.linkkou.gson.typefactory.impl;

import com.google.gson.*;
import com.linkkou.gson.typefactory.GsonEnum;

import java.lang.reflect.*;

/**
 * 枚举转换
 *
 * @author lk
 * @version 1.0
 * @date 2019/9/2 14:33
 */
public class GsonEnumTypeAdapter<E> implements JsonSerializer<E> {

    public GsonEnumTypeAdapter() {
    }

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        JsonPrimitive jsonElement = null;
        if (src instanceof GsonEnum) {
            try {
                Constructor<JsonPrimitive> constructor = JsonPrimitive.class.getDeclaredConstructor(Object.class);
                constructor.setAccessible(true);
                jsonElement = constructor.newInstance(((GsonEnum) src).serialize());
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
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
