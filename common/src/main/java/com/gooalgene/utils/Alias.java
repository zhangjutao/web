package com.gooalgene.utils;

import java.lang.annotation.*;

/**
 * 前端传值到后端时如果与后端bean属性不一致时,反射无法为该属性值,这时可以使用alias注解完成前端值之间匹配
 * 如:SampleInfo中有sampleId属性,当前端传入为sampleid,此时可使用如下:
 * <pre>
 * &#064;Alias("sampleid")
 * private String sampleId
 * </pre>
 * 同样可通过ReflectionUtils实现转换
 *
 * Created by crabime on 3/18/18.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Alias {
    /**
     * 前端传入的别名
     */
    String value() default "";
}
