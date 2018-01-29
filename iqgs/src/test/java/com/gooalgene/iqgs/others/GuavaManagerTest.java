package com.gooalgene.iqgs.others;

import com.gooalgene.common.cache.GuavaManager;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.guava.GuavaCache;

import java.util.concurrent.TimeUnit;

public class GuavaManagerTest extends TestCase {
    private GuavaManager guavaManager;

    @Before
    public void setUp(){
        guavaManager = new GuavaManager();
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                .maximumSize(2000)
                .expireAfterAccess(5, TimeUnit.SECONDS)
                .build();
        GuavaCache guavaCache = new GuavaCache("cache1", cache);
        guavaManager.addGuavaCache(guavaCache);
        Cache<Object, Object> cache1 = CacheBuilder.newBuilder()
                .maximumSize(1)
                .expireAfterAccess(10, TimeUnit.SECONDS)
                .build();
        GuavaCache guavaCache1 = new GuavaCache("cache2", cache1);
        guavaManager.addGuavaCache(guavaCache1);
    }

    @Test
    public void testGuavaManager() throws InterruptedException {
        org.springframework.cache.Cache springCache = guavaManager.getCache("cache1");
        springCache.put("one", 1);
        Thread.sleep(6000);
        assertNull(springCache.get("one"));
    }

    @Test
    public void testMaximumSizeCache() throws InterruptedException {
        org.springframework.cache.Cache springCache = guavaManager.getCache("cache2");
        springCache.put("one", 1);
        springCache.put("two", 2);
        Thread.sleep(6000);
        assertNull(springCache.get("one"));
        assertEquals(2, Integer.parseInt(springCache.get("two").get().toString()));
    }
}
