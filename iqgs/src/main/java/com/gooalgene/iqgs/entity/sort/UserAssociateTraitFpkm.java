package com.gooalgene.iqgs.entity.sort;

import java.util.Date;

public class UserAssociateTraitFpkm {
    private Integer id;

    private String userId;

    private Integer traitCategoryId;

    private String fpkmStr;

    private Date createTime;

    public UserAssociateTraitFpkm(String userId, Integer traitCategoryId, String fpkmStr) {
        this.userId = userId;
        this.traitCategoryId = traitCategoryId;
        this.fpkmStr = fpkmStr;
    }

    public UserAssociateTraitFpkm(String userId, Integer traitCategoryId, String fpkmStr, Date createTime) {
        this.userId = userId;
        this.traitCategoryId = traitCategoryId;
        this.fpkmStr = fpkmStr;
        this.createTime = createTime;
    }

    public UserAssociateTraitFpkm() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTraitCategoryId() {
        return traitCategoryId;
    }

    public void setTraitCategoryId(Integer traitCategoryId) {
        this.traitCategoryId = traitCategoryId;
    }

    public String getFpkmStr() {
        return fpkmStr;
    }

    public void setFpkmStr(String fpkmStr) {
        this.fpkmStr = fpkmStr == null ? null : fpkmStr.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}