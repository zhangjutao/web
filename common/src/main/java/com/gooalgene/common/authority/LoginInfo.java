package com.gooalgene.common.authority;

import java.util.Date;

/**
 * Created by liuyan on 2017/11/6.
 */
public class LoginInfo {
    private int id;
    private Date loginTime;
    private Date logoutTime;

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
