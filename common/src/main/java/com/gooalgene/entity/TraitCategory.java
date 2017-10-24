package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

import javax.persistence.Column;
import java.util.Date;

/**
 * 大类分类实体类（15个大类）
 * Created by ShiYun on 2017/7/6 0006.
 */
public class TraitCategory extends DataEntity<TraitCategory> {


    /**
     * qtl分类名
     */
    private String qtlName;

    /**
     * 中文描述
     */
    private String qtlDesc;

    /**
     * soybase上的给出缩写
     */
    private String qtlOthername;

    /**
     * 入库时间
     */
    @Column(name = "qtl_createtime", columnDefinition = "timestamp")
    private Date qtlCreatetime;


    public String getQtlName() {
        return qtlName;
    }

    public void setQtlName(String qtlName) {
        this.qtlName = qtlName;
    }

    public String getQtlDesc() {
        return qtlDesc;
    }

    public void setQtlDesc(String qtlDesc) {
        this.qtlDesc = qtlDesc;
    }

    public String getQtlOthername() {
        return qtlOthername;
    }

    public void setQtlOthername(String qtlOthername) {
        this.qtlOthername = qtlOthername;
    }

    public Date getQtlCreatetime() {
        return qtlCreatetime;
    }

    public void setQtlCreatetime(Date qtlCreatetime) {
        this.qtlCreatetime = qtlCreatetime;
    }

    @Override
    public String toString() {
        return "TraitCategory{" +
                "qtlName='" + qtlName + '\'' +
                ", qtlDesc='" + qtlDesc + '\'' +
                ", qtlOthername='" + qtlOthername + '\'' +
                ", qtlCreatetime=" + qtlCreatetime +
                '}';
    }
}
