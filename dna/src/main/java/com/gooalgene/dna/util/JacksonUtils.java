package com.gooalgene.dna.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

public class JacksonUtils {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    /**
     * 将输入JSON字符串转换为对象
     * @param jsonVariable JSON字符串
     * @param <T> 转换结果类型
     * @param targetClass 转换的目标类
     * @return 转换结果
     */
    public static <T> T convertJsonToObject(String jsonVariable, Class<T> targetClass) throws IOException {
        Assert.isTrue(!StringUtils.isEmpty(jsonVariable), "传入JSON字符串为空");
        T t = mapper.readValue(jsonVariable, targetClass);
        return t;
    }

    /**
     * 将输入JSON字符串转换为对象数组
     * @param jsonVariable JSON数组字符串
     * @param <T> 转换结果类型
     * @return 转换集合结果
     */
    public static <T> List<T> convertJsonToArray(String jsonVariable, Class<T> targetClass) throws IOException {
        Assert.isTrue(!StringUtils.isEmpty(jsonVariable), "传入JSON字符串为空");
        // 这里如果使用TypeReference，对泛型支持力度不行
        List<T> list = mapper.readValue(jsonVariable, mapper.getTypeFactory().constructCollectionType(List.class, targetClass));
        return list;
    }
}
