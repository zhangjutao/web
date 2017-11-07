package com.gooalgene.dna.dao;

import com.gooalgene.common.security.JdbcRequestMapBuilder;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Created by crabime on 11/6/17.
 * 测试JdbcRequestMapBuilder
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:spring-security.xml"))
public class JdbcRequestMapBuilderTest extends TestCase {
    @Autowired
    private JdbcRequestMapBuilder builder;

    @Test
    public void testBuildMap(){
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = builder.buildRequestMap();
        assertEquals(3, requestMap.size());
    }
}
