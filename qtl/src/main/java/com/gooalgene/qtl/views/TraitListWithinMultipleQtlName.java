package com.gooalgene.qtl.views;

import com.gooalgene.entity.Qtl;

import java.util.List;

/**
 * 一个trait list中包含多个qtl name
 * 数据参见qtl中trait与qtl_name两个字段
 * @author crabime
 */
public class TraitListWithinMultipleQtlName {

    /**
     * 小组织ID(主键)
     */
    private Integer traitListId;

    private Integer qtlId;

    private String traitName;

    private List<Qtl> qtls;

    public Integer getTraitListId() {
        return traitListId;
    }

    public void setTraitListId(Integer traitListId) {
        this.traitListId = traitListId;
    }

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

    public List<Qtl> getQtls() {
        return qtls;
    }

    public void setQtls(List<Qtl> qtls) {
        this.qtls = qtls;
    }
}
