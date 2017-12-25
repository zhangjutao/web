package com.gooalgene.qtl.views;

import com.gooalgene.entity.TraitList;

import java.util.List;

/**
 * 查询返回的一个trait category，一个trait category对应多个trait_list数据
 * 一个trait_list对应多个qtl name
 * @author crabime
 */
public class TraitCategoryWithinMultipleTraitList {

    /**
     * 组织类型ID(主键)
     */
    private Integer traitCategoryId;

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
     * 改trait category对应的trait_list集合
     */
    private List<TraitList> traitLists;

    public Integer getTraitCategoryId() {
        return traitCategoryId;
    }

    public void setTraitCategoryId(Integer traitCategoryId) {
        this.traitCategoryId = traitCategoryId;
    }

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

    public List<TraitList> getTraitLists() {
        return traitLists;
    }

    public void setTraitLists(List<TraitList> traitLists) {
        this.traitLists = traitLists;
    }
}
