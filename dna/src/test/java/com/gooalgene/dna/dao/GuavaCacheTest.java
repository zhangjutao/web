package com.gooalgene.dna.dao;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:spring-context-test.xml"))
public class GuavaCacheTest extends TestCase {
    @Autowired
    private GuavaCacheManager guavaCacheManager;

    @Test
    public void testGetGuavaManager(){
        Cache cache = guavaCacheManager.getCache("config");
        cache.putIfAbsent("hello", "helloValue");
        cache.putIfAbsent("world", "world");
        assertEquals("helloValue", cache.get("hello").get());
        Cache.ValueWrapper absent = cache.get("absent");
        assertNull(absent);
    }
}
