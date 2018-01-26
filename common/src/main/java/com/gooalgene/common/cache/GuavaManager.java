package com.gooalgene.common.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 重写Spring GuavaCacheManager
 * Spring中Cache Manager无法创建多个不同CacheBuilder的Cache，无法满足不同Cache的要求
 * @author Crabime
 */
public class GuavaManager implements CacheManager {
    /**
     * 最多存放十种不同Cache
     */
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(10);

    public GuavaManager() {
    }

    public void addGuavaCache(GuavaCache guavaCache){
        this.cacheMap.put(guavaCache.getName(), guavaCache);
    }

    @Override
    public Cache getCache(String name) {
        return cacheMap.get(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }
}
