package com.ldx.javaSpringBoot.aspect;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)//运行时生效
public @interface ServiceAnnotation {
    String value() default "bbb";
}
