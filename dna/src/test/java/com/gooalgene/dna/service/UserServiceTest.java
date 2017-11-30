package com.gooalgene.dna.service;

import com.gooalgene.common.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:spring-context-test.xml"))
public class UserServiceTest extends TestCase {
    @Autowired
    private UserService userService;

    @Test
    public void testCheckUserExists(){
        boolean exists = userService.exist("crabime");
        assertTrue(exists);
    }

}
