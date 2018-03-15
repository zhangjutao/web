package com.gooalgene.common.service;

import com.gooalgene.common.authority.LoginInfo;
import com.gooalgene.common.authority.User;
import com.gooalgene.common.dao.LoginInfoDao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by liuyan on 2017/11/6.
 */

@Service
public class LoginInfoService {

    @Autowired
    private LoginInfoDao loginInfoDao;

    @Autowired
    private UserService userService;

    /**
     * 记录用户登录信息
     */
    public void insertLoginInfo(LoginInfo loginInfo){
        int userid=loginInfo.getUserId();
        User user=userService.getUserById(userid);
        int count=user.getLoginCount();
        user.setLoginCount(count+1);
        userService.updateUserLoginCount(user);
        loginInfoDao.insertLoginInfo(loginInfo);
    }

    public ArrayList<LoginInfo> getLoginInfoById(int id){
        return loginInfoDao.getLoginInfoById(id);
    }

    public ArrayList<LoginInfo> getAllLoginInfo() {
        return loginInfoDao.getAllLoginInfo();
    }

}
