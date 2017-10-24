package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

/**
 * qtlName和version唯一，插入数据时 需要进行判断。
 * <p/>
 * Created by ShiYun on 2017/7/6 0006.
 */
public class Associatedgenes extends DataEntity<Associatedgenes> {

    private String qtlName;

    private String version;

    private String associatedGenes;

    public String getQtlName() {
        return qtlName;
    }

    public void setQtlName(String qtlName) {
        this.qtlName = qtlName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAssociatedGenes() {
        return associatedGenes;
    }

    public void setAssociatedGenes(String associatedGenes) {
        this.associatedGenes = associatedGenes;
    }
}
