package com.linkkou.gson.typefactory.impl;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author lk
 * @version 1.0
 * @date 2020/4/29 14:59
 */
public class BigDecimalTypeAdapter extends TypeAdapter<BigDecimal> {

    @Override
    public void write(JsonWriter out, BigDecimal bigDecimal) throws IOException {
        out.value(bigDecimal);
    }

    @Override
    public BigDecimal read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            return new BigDecimal(in.nextString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
