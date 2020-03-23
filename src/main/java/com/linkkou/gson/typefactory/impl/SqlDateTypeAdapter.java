package com.linkkou.gson.typefactory.impl;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author lk
 * @version 1.0
 * @date 2020/3/18 14:47
 */
public class SqlDateTypeAdapter extends TypeAdapter<Date> {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SqlDateTypeAdapter() {
    }

    public SqlDateTypeAdapter(DateTimeFormatter format) {
        dateTimeFormatter = format;
    }

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            return typeToken.getRawType() == Date.class ? (TypeAdapter<T>) new SqlDateTypeAdapter() : null;
        }
    };

    @Override
    public synchronized Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        Instant instant = LocalDateTime.parse(in.nextString(), this.dateTimeFormatter).atZone(ZoneId.systemDefault()).toInstant();
        return (Date) Date.from(instant);
    }

    @Override
    public synchronized void write(JsonWriter out, Date value) throws IOException {
        out.value(value == null ? null : this.dateTimeFormatter.format(value.toInstant()));
    }
}
