package com.yja.myapp.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Auth {
    // 역할 --> 일반, 골드, 관리자, 판매관리자
//    public String role();
    public boolean require() default true;
}


