package com.util.util.poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel文件名
 *
 * @dept 上海软件研发中心
 * @author HaoXin.Liu
 * @date 2019/4/3 17:11
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelFileName {
    String value() default "" ;
}
