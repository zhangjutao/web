package com.gooalgene.mrna.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context.xml"}))
public class ClassifyServiceTest extends TestCase {

    @Autowired
    private ClassifyService classifyService;

    @Test
    public void testQuery(){
        assertNotNull(classifyService);
        classifyService.query();
    }
}
