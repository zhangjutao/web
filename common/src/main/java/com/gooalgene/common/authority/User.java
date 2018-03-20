package com.gooalgene.common.authority;

import com.gooalgene.common.DataEntity;

import java.util.Date;

/**
 * Created by liuyan on 2017/11/1.
 */
public class User {
    private String username;// 登录名
    private String email;
    private String password;// 密码
    private String phone;
    private String domains;
    private String university;
    private int enabled;
    private int reset;
    private int status;
    private Date create_time;
    private Date due_time;    //用户试用的截止日期
    private String description;
    private  int uid;        //用户在数据库中的id 值  主要为获取当前记录的id使用
    private int id;
    private int enableDay;
    private int loginCount;

    public User(){

    }
    public User(String username,String password,String email){
        this.username=username;
        this.password=password;
        this.email=email;
    }
    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public int getEnableDay() {
        return enableDay;
    }

    public void setEnableDay(int enableDay) {
        this.enableDay = enableDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDue_time() {
        return due_time;
    }

    public void setDue_time(Date due_time) {
        this.due_time = due_time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getReset() {
        return reset;
    }

    public void setReset(int reset) {
        this.reset = reset;
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDomains() {
        return domains;
    }

    public void setDomains(String domains) {
        this.domains = domains;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }



}
