package com.gooalgene.iqgs.entity.condition;

import com.gooalgene.entity.Associatedgenes;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;

import java.util.List;

/**
 * iqgs查询返回列表对应实体类
 * @author crabime
 */
public class DNAGeneSearchResult extends DNAGenBaseInfo {
    /**
     * 是否存在非同义突变，默认为false
     */
    private boolean existsSNP = false;

    /**
     * 关联的所有QTL
     */
    private List<Associatedgenes> associateQTLs;

    public boolean isExistsSNP() {
        return existsSNP;
    }

    public void setExistsSNP(boolean existsSNP) {
        this.existsSNP = existsSNP;
    }

    public List<Associatedgenes> getAssociateQTLs() {
        return associateQTLs;
    }

    public void setAssociateQTLs(List<Associatedgenes> associateQTLs) {
        this.associateQTLs = associateQTLs;
    }
}
