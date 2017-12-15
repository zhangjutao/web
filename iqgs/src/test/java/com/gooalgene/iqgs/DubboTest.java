package com.gooalgene.iqgs;

import com.gooalgene.primer.interfaces.DubboService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class DubboTest extends TestCase {

    @Autowired
    private DubboService dubboService;

    @Test
    public void testDubbo(){
        String result = dubboService.say();
        System.out.println(result);
    }
}
