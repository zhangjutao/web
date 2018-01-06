package com.gooalgene.iqgs.service;

import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FPKMService {

    @Autowired
    private FPKMDao fpkmDao;

    /**
     * 根据用户选择的基因表达量、SNP、INDEL，筛选出对应基因
     * @param condition 基因表达量
     * @param selectSnp 选择的SNP name集合
     * @param selectIndel 选择的INDEL name集合
     * @return 符合条件基因ID集合
     */
    public List<String> findProperGeneUnderSampleRun(List<GeneExpressionConditionEntity> condition,
                                                     List<String> selectSnp,
                                                     List<String> selectIndel,
                                                     List<Integer> selectQTL){
        return fpkmDao.findGeneThroughGeneExpressionCondition(condition, selectSnp, selectIndel, selectQTL);
    }
}
