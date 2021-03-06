/*
 * Copyright (c) 2019.
 * nicekkong JE Foundation
 */

/******************************************************
 * Project Name : my-rest-api
 * File Name    : .java
 * Author       : nicekkong@gmail.com
 * Create Date  : 2019-06-01 00:10
 * Description  : 
 ******************************************************/

package com.nicekkong.myrestapi.common;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)  // annotation을 붙인 것을 어디까지 가져갈 것인가??
public @interface TestDescription {

    String value();
}
