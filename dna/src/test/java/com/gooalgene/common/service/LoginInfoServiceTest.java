package com.gooalgene.common.service;

import com.gooalgene.common.authority.LoginInfo;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by liuyan on 2017/11/6.
 */

public class LoginInfoServiceTest {

    private final static Logger logger= LoggerFactory.getLogger(LoginInfoServiceTest.class);

    @Autowired
    public  LoginInfoService loginInfoService;

    @Test
    public void testInsertLoginInfo() throws Exception {
        LoginInfo loginInfo=new LoginInfo();
        loginInfo.setId(44);
        loginInfo.setLoginTime(new Date());
        loginInfo.setLogoutTime(new Date());
        logger.warn("userid", loginInfo.getId());
        logger.warn("logintime",loginInfo.getLoginTime());
        System.out.println(loginInfoService);
        loginInfoService.insertLoginInfo(loginInfo);

    }

    @Test
    public void testGetLoginInfoById() throws Exception {

    }

    @Test
    public void testGetAllLoginInfo() throws Exception {

    }
}