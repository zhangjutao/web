package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

import javax.persistence.Column;

/**
 * 小分类实体类
 * Created by ShiYun on 2017/7/6 0006.
 */

public class TraitList extends DataEntity<TraitList> {

    private Integer qtlId;

    private String traitName;

    @Column(name = "trait_createtime", columnDefinition = "timestamp")
    private String traitCreatetime;

    public Integer getQtlId() {
        return qtlId;
    }

    public void setQtlId(Integer qtlId) {
        this.qtlId = qtlId;
    }

    public String getTraitName() {
        return traitName;
    }

    public void setTraitName(String traitName) {
        this.traitName = traitName;
    }

    public String getTraitCreatetime() {
        return traitCreatetime;
    }

    public void setTraitCreatetime(String traitCreatetime) {
        this.traitCreatetime = traitCreatetime;
    }

    @Override
    public String toString() {
        return "TraitList{" +
                "qtlId=" + qtlId +
                ", traitName='" + traitName + '\'' +
                ", traitCreatetime='" + traitCreatetime + '\'' +
                '}';
    }
}
