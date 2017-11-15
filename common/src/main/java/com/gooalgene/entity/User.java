package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by Administrator on 2017/07/08.
 */
public class User extends DataEntity<User> {

    private String username;// 登录名
    private String password;// 密码

    public User(String username,String password){
        this.username=username;
        this.password=password;
    }
    public User(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
