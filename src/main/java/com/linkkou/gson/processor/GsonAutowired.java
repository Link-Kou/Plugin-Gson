package com.linkkou.gson.processor;

import com.linkkou.gson.GsonBuild;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lk
 * @version 1.0
 * @date 2019/9/2 19:09
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface GsonAutowired {

    /**
     * 分组
     *
     * @return
     */
    String group() default "";

}
