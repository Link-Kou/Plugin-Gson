package com.linkkou.gson.typefactory.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.linkkou.gson.typefactory.GsonEnum;

import java.io.IOException;

/**
 * 枚举转换 TypeAdapter 的工厂类
 *
 * @author lk
 * @version 1.0
 * @date 2019/9/2 14:33
 */
public class GsonEnumTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (!rawType.isEnum()) {
            //没有指定的转换器的时候，返回NULL，GSON会自己适配相关的解析器
            return null;
        } else {
            final Class<?>[] interfaces = rawType.getInterfaces();
            for (Class c : interfaces) {
                if (c.equals(GsonEnum.class)) {

                    return new TypeAdapter<T>() {

                        @Override
                        public void write(JsonWriter out, T value) throws IOException {
                            /**
                             * 如果是枚举就执行自己定义的解析器
                             * {@link GsonEnumTypeAdapter}
                             */
                            final TypeAdapter<T> adapter = (TypeAdapter<T>) gson.getAdapter(GsonEnum.class);
                            adapter.write(out, value);
                        }

                        @Override
                        public T read(JsonReader in) throws IOException {
                            /**
                             * 拷贝源代码的实现方案
                             * {@link com.google.gson.internal.bind.TypeAdapters.ENUM_FACTORY}
                             */
                            JsonElement value = Streams.parse(in);
                            if (value.isJsonNull()) {
                                return null;
                            }
                            return (T) new GsonEnumTypeAdapter().deserialize(value, rawType);
                        }

                    };
                }
            }
            return null;
        }
    }


}
