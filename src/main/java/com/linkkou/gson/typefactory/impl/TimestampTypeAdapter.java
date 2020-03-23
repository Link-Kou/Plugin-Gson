/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.linkkou.gson.typefactory.impl;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 拷贝GSON源代码
 * 改用为Java8的写法
 */
public final class TimestampTypeAdapter extends TypeAdapter<Timestamp> {


    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TimestampTypeAdapter() {
    }

    public TimestampTypeAdapter(DateTimeFormatter format) {
        dateTimeFormatter = format;
    }


    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        // we use a runtime check to make sure the 'T's equal
        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            return typeToken.getRawType() == Timestamp.class ? (TypeAdapter<T>) new TimestampTypeAdapter() : null;
        }
    };

    @Override
    public Timestamp read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        Instant instant = LocalDateTime.parse(in.nextString(), this.dateTimeFormatter).atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return new Timestamp(date.getTime());
    }

    @Override
    public void write(JsonWriter out, Timestamp value) throws IOException {
        out.value(value == null ? null : this.dateTimeFormatter.format(value.toInstant()));
    }
}
