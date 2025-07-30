package com.example.dgu.returnwork.global.annotation;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(hidden = true)
public @interface CurrentUser {
    boolean required() default true;
    String errorMessage() default "로그인이 필요합니다.";
}
