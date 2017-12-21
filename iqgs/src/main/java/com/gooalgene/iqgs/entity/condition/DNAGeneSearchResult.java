package com.gooalgene.iqgs.entity.condition;

import com.gooalgene.iqgs.entity.DNAGenBaseInfo;

/**
 * iqgs查询返回列表对应实体类
 * @author crabime
 */
public class DNAGeneSearchResult extends DNAGenBaseInfo {
    // 是否存在非同义突变，默认为false
    private boolean existsSNP = false;

    public boolean isExistsSNP() {
        return existsSNP;
    }

    public void setExistsSNP(boolean existsSNP) {
        this.existsSNP = existsSNP;
    }
}
