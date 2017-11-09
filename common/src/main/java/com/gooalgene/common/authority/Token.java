package com.gooalgene.common.authority;

import java.util.Date;

/**
 * Created by liuyan on 2017/11/3.
 */
public class Token {
    private int userid;
    private String token;
    private Date  due_time;
    private int token_status;
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDue_time() {
        return due_time;
    }

    public void setDue_time(Date due_time) {
        this.due_time = due_time;
    }

    public int getToken_status() {
        return token_status;
    }

    public void setToken_status(int token_status) {
        this.token_status = token_status;
    }
}
