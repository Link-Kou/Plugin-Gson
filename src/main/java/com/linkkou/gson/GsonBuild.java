package com.linkkou.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.linkkou.gson.typefactory.impl.*;
import com.linkkou.gson.typefactory.GsonEnum;

import java.sql.Time;

/**
 *
 * @author lk
 * @version 1.0
 * @date 2019/9/2 19:09
 *
 */
public class GsonBuild {

    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            //当Map的key为复杂对象时,需要开启该方法
            .enableComplexMapKeySerialization()
            //当字段值为空或null时，依然对该字段进行转换
            .serializeNulls()
            //防止特殊字符出现乱码
            .disableHtmlEscaping()
            .registerTypeAdapterFactory(new GsonEnumTypeAdapterFactory())
            //不导出实体中没有用@Expose注解的属性
            //.excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(GsonEnum.class, new GsonEnumTypeAdapter<GsonEnum>())
            //.registerTypeAdapter(GsonDateTimestamp.class, new GsonDateTimeStampAdapter<GsonDateTimestamp>())
            //.registerTypeAdapter(GsonDate.class, new GsonDateAdapter<GsonDate>())
            //时间进行格式化
            .registerTypeAdapter(Time.class, new TimeTypeAdapter())
            //double==Double & float==Float 类型精度控制
            .registerTypeAdapter(Double.class, new DoubleTypeAdapter())
            .registerTypeAdapter(Float.class, new DoubleTypeAdapter())
            .create();


    public static Gson getGson() {
        return gson;
    }
}
