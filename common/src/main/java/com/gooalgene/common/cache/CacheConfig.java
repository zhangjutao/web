package com.gooalgene.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager initCacheManager(){
        GuavaManager manager = new GuavaManager();
        //最多1000个高级搜索缓存结果
        Cache<Object, Object> advanceSearchCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build();
        GuavaCache guavaCache = new GuavaCache("advanceSearch", advanceSearchCache);
        manager.addGuavaCache(guavaCache);
        //2000个配置缓存
        Cache<Object, Object> configCache = CacheBuilder.newBuilder()
                .maximumSize(2000)
                .build();
        GuavaCache configGuavaCache = new GuavaCache("config", configCache);
        manager.addGuavaCache(configGuavaCache);
        //排序相关的搜索结果列表缓存
        Cache<Object, Object> sortCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build();
        GuavaCache sortedGuavaCache = new GuavaCache("sortCache", sortCache);
        manager.addGuavaCache(sortedGuavaCache);
        return manager;
    }

}
