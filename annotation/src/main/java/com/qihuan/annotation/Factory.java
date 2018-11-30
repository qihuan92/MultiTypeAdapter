package com.qihuan.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Factory
 *
 * @author qi
 * @date 2018/11/30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Factory {
}
