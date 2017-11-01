package com.gooalgene.common;

import com.gooalgene.common.service.ConfigService;
import com.gooalgene.entity.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动获取配置文件中配置信息
 * 由于使用的servlet2.5标准，所以使用@WebServlet注解无效，
 * 必须配置在各自模块的配置文件中
 */
@Component
public class ConfigAfterTomcatStartup implements ApplicationListener<ContextRefreshedEvent> {
    private final static Logger logger = LoggerFactory.getLogger(ConfigAfterTomcatStartup.class);
    @Autowired
    private ConfigService configService;
    @Autowired
    private GuavaCacheManager cacheManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Configuration> configurations = configService.getAllConfig();
        Cache cache = cacheManager.getCache("config");
        for (Configuration configuration : configurations){
            cache.putIfAbsent(configuration.getKey(), configuration.getValue());
        }
        logger.info("配置目前有" + configurations.size() + "个配置");
    }
}
