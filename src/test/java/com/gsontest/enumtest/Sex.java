package com.gsontest.enumtest;

import com.google.gson.JsonElement;
import com.linkkou.gson.typefactory.GsonEnum;

/**
 * @author lk
 * @version 1.0
 *
 */
public enum Sex implements GsonEnum<Sex> {


    BOY(1),

    GIRL(0);


    private int number;

    Sex(int number) {
        this.number = number;
    }

    /**
     * 编码
     *
     * @return
     */
    @Override
    public Object serialize() {
        return this.number;
    }

    /**
     * 转换
     *
     * @param jsonEnum
     * @return
     */
    @Override
    public Sex deserialize(JsonElement jsonEnum) {
        for (Sex data : Sex.values()) {
            if (data.number == jsonEnum.getAsInt()) {
                return data;
            }
        }
        return BOY;
    }

}
