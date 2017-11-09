package com.gooalgene.dna.service;

import com.gooalgene.common.authority.LoginInfo;
import com.gooalgene.common.authority.User;
import com.gooalgene.common.service.LoginInfoService;
import com.gooalgene.common.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:spring-context.xml"))
public class UserServiceTest extends TestCase {
    @Autowired
    private UserService userService;


    @Test
    public void testCheckUserExists(){
        boolean exists = userService.exist("crabime");
        assertTrue(exists);
    }

}
