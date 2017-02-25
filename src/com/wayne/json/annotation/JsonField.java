/*
 * Copyright (C) 2004 - 2017 UCWeb Inc. All Rights Reserved.
 * Description : json转换的注解
 *
 * Creation    : 2017/02/22
 * Author      : weihan.hwh@alibaba-inc.com
 */

package com.wayne.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被注解的成员变量和json的值可以相互转换
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonField {
    String name();

    Class clazz() default BasicClass.class;
}