package com.linkkou.gson.typefactory.impl;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author lk
 * @version 1.0
 * @date 2020/4/29 14:59
 */
public class BigIntegerTypeAdapter extends TypeAdapter<BigInteger> {

    @Override
    public void write(JsonWriter out, BigInteger value) throws IOException {
        out.value(value);
    }

    @Override
    public BigInteger read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            return new BigInteger(in.nextString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
