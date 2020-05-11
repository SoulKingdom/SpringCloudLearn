package com.util.util.poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Excel标题中文名
 *
 * @dept 上海软件研发中心
 * @author HaoXin.Liu
 * @date 2019/4/3 16:07
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ChineseName {
    /**
     *  Excel名称
     */
    String value();
}
