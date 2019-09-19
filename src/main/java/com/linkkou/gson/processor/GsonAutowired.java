package com.linkkou.gson.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动注入
 * @author lk
 * @version 1.0
 *
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
