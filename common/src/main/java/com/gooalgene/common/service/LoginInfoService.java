package com.gooalgene.common.service;

import com.gooalgene.common.authority.LoginInfo;
import com.gooalgene.common.dao.LoginInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin.javascript.navig.Array;

import java.util.ArrayList;

/**
 * Created by liuyan on 2017/11/6.
 */

@Service
public class LoginInfoService {

    @Autowired
    private LoginInfoDao loginInfoDao;

    public void insertLoginInfo(LoginInfo loginInfo){
        loginInfoDao.insertLoginInfo(loginInfo);

    }

    public ArrayList<LoginInfo> getLoginInfoById(int id){
        return loginInfoDao.getLoginInfoById(id);
    }
    public ArrayList<LoginInfo> getAllLoginInfo(){
        return loginInfoDao.getAllLoginInfo();
    }




}
