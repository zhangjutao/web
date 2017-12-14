package com.gooalgene.common;

import com.gooalgene.common.service.ConfigService;
import com.gooalgene.entity.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
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

    @Autowired
    private SqlSessionFactoryBean sqlSessionFactoryBean;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String displayName = event.getApplicationContext().getDisplayName();
        // 只监听一个应用启动事件
        if (!displayName.equals("Root WebApplicationContext")){
            return;
        }
        List<Configuration> configurations = configService.getAllConfig();
        Cache cache = cacheManager.getCache("config");
        for (Configuration configuration : configurations){
            cache.putIfAbsent(configuration.getKey(), configuration.getValue());
        }
        try {
            Collection<String> mappedStatementNames = sqlSessionFactoryBean.getObject().getConfiguration().getMappedStatementNames();
            Iterator<String> iterator = mappedStatementNames.iterator();
            while (iterator.hasNext()){
                String mappedStatement = iterator.next();
                logger.info("扫描文件名：" + mappedStatement);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("spring启动时获取mybatis配置文件发生异常", e.getCause());
        }
        logger.debug("配置目前有" + configurations.size() + "个配置");
    }
}
