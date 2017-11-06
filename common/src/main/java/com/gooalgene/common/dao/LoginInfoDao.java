package com.gooalgene.common.dao;

import com.gooalgene.common.authority.LoginInfo;
import com.gooalgene.common.persistence.MyBatisDao;


import java.util.ArrayList;

/**
 * Created by liuyan on 2017/11/6.
 */
@MyBatisDao
public interface LoginInfoDao {

    //获取特定用户的登录信息
     ArrayList<LoginInfo>getLoginInfoById(int id);

    //获取所有用户的登录信息
     ArrayList<LoginInfo> getAllLoginInfo();
    //插入一条用户的登录信息
     void insertLoginInfo(LoginInfo loginInfo);

}
