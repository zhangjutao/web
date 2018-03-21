package com.gooalgene.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 根据前台传入的一组参数,转换为目标对象
 *
 * @author crabime
 */
public class FrontEndReflectionUtils {
    private static final Logger logger = LoggerFactory.getLogger(FrontEndReflectionUtils.class);

    /**
     * 针对前台传入的map参数,如群体中任意个SampleInfo属性,如果采用jsonPath通过if-else,会有大量的if-else判断,
     * 这里采用统一的转换规则,将前台传入的map进行与实体bean之间映射,通过反射的方式来为新的实体赋值,如:
     * <pre>
     *     map.put("sampleId", "anyId");
     *     map.put("environment", "heat");
     *     SampleInfo result = constructNewInstance("com.gooalgene.dna.entity.SampleInfo", map);
     * </pre>
     * 通过上面代码转换可实现result sampleInfo中sampleId、environment属性的设值过程,返回一个新的SampleInfo实例
     *
     * 如果前端传入的属性与实体bean属性名不一致,这里可以使用{@link Alias}来完成转换过程,使用如下:
     * <pre>
     *     // 先为目标类属性设置一个Alias属性
     *     &#064;Alias("scientific")
     *     private String scientificName;
     *
     *
     *     map.put("sampleId", "anyId");
     *     map.put("environment", "heat");
     *     map.put("scientific", "astronomy");  // 该scientific最终也会转换为sampleInfo的scientificName值
     *     SampleInfo result = constructNewInstance("com.gooalgene.dna.entity.SampleInfo", map);
     * </pre>
     *
     * @param fullQualifiedName condition中属性对应bean类名全称
     * @param conditions 前端传入的condition条件
     * @param <T> 要转换的目标对象类型
     * @return 转换后的目标对象
     */
    public static <T> T constructNewInstance(String fullQualifiedName, Map<String, ?> conditions) {
        Class<?> sampleClass = null;
        T t = null;
        try {
            sampleClass = Class.forName(fullQualifiedName);
            Constructor<?> constructor = sampleClass.getConstructor();
            t = (T) constructor.newInstance();
            Field[] declaredFields = sampleClass.getDeclaredFields();
            Map<String, Field> reflectionMap = new HashMap<>();
            for (Field field : declaredFields) {
                // 别名也需要计算在内
                if (field.getAnnotation(Alias.class) != null) {
                    Alias aliasAnnotation = field.getAnnotation(Alias.class);
                    String aliasValue = aliasAnnotation.value();
                    reflectionMap.put(aliasValue, field);
                }
                String fieldName = field.getName();
                reflectionMap.put(fieldName, field);
            }
            Set<String> allKey = reflectionMap.keySet();
            Set<? extends Map.Entry<String, ?>> entries = conditions.entrySet();
            for (Map.Entry<String, ?> entry : entries) {
                String key = entry.getKey();
                if (allKey.contains(key)) {
                    Field field = reflectionMap.get(key);
                    field.setAccessible(true);
                    field.set(t, entry.getValue());
                } else {
                    logger.warn(fullQualifiedName + "类中不包含" + key + "属性");
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            logger.error("反射过程出错", e.getCause());
        }
        return t;
    }
}
