package com.gooalgene.common.authority;

import java.util.Date;

/**
 * Created by liuyan on 2017/11/6.
 */
public class LoginInfo {
    private int id;
    private Integer userId;
    private Date loginTime;
    private Date logoutTime;

    public LoginInfo(Integer userId,Date loginTime,Date logoutTime){
        this.userId=userId;
        this.loginTime=loginTime;
        this.logoutTime=logoutTime;
    }
    public LoginInfo(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }
}
