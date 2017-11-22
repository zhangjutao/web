package com.gooalgene.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * spring security在单元测试中会影响某一个service的测试
 * component-scan直接扫描com.gooalgene包导致部分不需要使用的class文件也被扫描了，
 * 如：{@link com.gooalgene.common.handler.AuthenticationSuccessHandlerImpl}
 * 这里可以在该类上使用该注解，避免运行单元测试时出现spring security需要注入的类找不到的情况
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestExcluded {
}
