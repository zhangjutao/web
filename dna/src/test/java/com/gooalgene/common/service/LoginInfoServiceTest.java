package com.gooalgene.common.service;

import com.gooalgene.common.authority.LoginInfo;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by liuyan on 2017/11/6.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:spring-context.xml"))
public class LoginInfoServiceTest extends TestCase {

    @Autowired
    private LoginInfoService loginInfoService;
    @Test
    public void testInsertLoginInfo() throws Exception {
        LoginInfo loginInfo=new LoginInfo();
        loginInfo.setUserId(44);
        loginInfo.setLoginTime(new Date());
        loginInfoService.insertLoginInfo(loginInfo);
    }

}