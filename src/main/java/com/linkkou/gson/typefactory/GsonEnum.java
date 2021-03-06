package com.linkkou.gson.typefactory;

import com.google.gson.JsonElement;

/**
 * 自定义枚举转换接口
 * 如果要实现枚举的自定义转换就必须实现此接口
 *
 * @author lk
 * @version 1.0
 * @date 2019/9/2 19:09
 */
public interface GsonEnum<E extends Enum<E>> {

    /**
     * 序列化
     *
     * @return
     */
    Object serialize();

    /**
     * 反序列化
     *
     * @param jsonEnum
     * @return
     */
    E deserialize(JsonElement jsonEnum);

    /**
     * Mybatis 序列化
     *
     * @return
     */
    default Integer reverse() {
        return (Integer) this.serialize();
    }

    /**
     * Mybatis 获取到值
     *
     * @param o   值
     * @param <T> 值
     * @return 枚举
     */
    <T> E convert(T o);

}
