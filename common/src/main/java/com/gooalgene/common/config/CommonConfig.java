package com.gooalgene.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

/**
 * create by Administrator on2018/2/9 0009
 */
@Configuration
public class CommonConfig {

    @Bean
    public RandomValueStringGenerator generator(){
        return new RandomValueStringGenerator();
    }

}
