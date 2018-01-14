package com.gooalgene.iqgs.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.DNAGenStructure;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
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
     * @param pageNo 页码
     * @param pageSize 每页条数
     * @return 符合条件基因ID集合
     */
    public PageInfo<AdvanceSearchResultView> findProperGeneUnderSampleRun(List<GeneExpressionConditionEntity> condition,
                                                                      List<String> selectSnp,
                                                                      List<String> selectIndel,
                                                                      List<Integer> firstHierarchyQtlId,
                                                                      List<Integer> selectQTL,
                                                                      DNAGenBaseInfo baseInfo,
                                                                      DNAGenStructure genStructure,
                                                                      int pageNo,
                                                                      int pageSize){
        PageHelper.startPage(pageNo, pageSize);
        List<AdvanceSearchResultView> searchResult =
                fpkmDao.findGeneThroughGeneExpressionCondition(condition, selectSnp, selectIndel, firstHierarchyQtlId, selectQTL, baseInfo, genStructure);
        return new PageInfo<>(searchResult);
    }

    public boolean checkExistSNP(int fpkmId, String snpConsequenceType){
        return fpkmDao.checkExistSNP(fpkmId, snpConsequenceType);
    }
}
