package com.gooalgene.entity;
import lombok.Data;

import java.util.Map;

/*
* ali短信对象，需要传入：username，phone,
* */
public class AliMessage {
    private User user;
    private String username;
    private String managerPhone;

    private Map templateParam;

    private String signName;

    private String templateCode;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public Map getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(Map templateParam) {
        this.templateParam = templateParam;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
}
