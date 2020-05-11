package com.util.util.poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel主键注解标识
 *
 * @dept 上海软件研发中心
 * @author HaoXin.Liu
 * @date 2019/4/3 16:12
 **/
//该注解保留的时机  如果需要使用反射，必须为runtime
@Retention(RetentionPolicy.RUNTIME)
//该注解可以写在哪个位置
@Target(ElementType.FIELD)
public @interface ColumnID {
}
