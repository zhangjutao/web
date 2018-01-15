package com.gooalgene.iqgs.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.dna.service.DNAGenStructureService;
import com.gooalgene.iqgs.dao.DNAGenBaseInfoDao;
import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.DNAGenStructure;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.gooalgene.common.constant.CommonConstant.DEFAULTRESULTVIEW;

@Service
public class FPKMService implements InitializingBean {

    @Autowired
    private FPKMDao fpkmDao;

    @Autowired
    private DNAGenBaseInfoDao dnaGenBaseInfoDao;

    @Autowired
    private DNAGenStructureService dnaGenStructureService;

    @Autowired
    private CacheManager manager;

    private Cache cache;

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = manager.getCache("advanceSearch");
    }

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
        Page<AdvanceSearchResultView> page = new Page<>(pageNo, pageSize, false);
        List<Integer> properGeneIdList = null;
        if (baseInfo != null) {
            String geneIdOrName = baseInfo.getGeneId();  //用户输入的geneId，这里需要调正则匹配服务
            //先判断是根据ID/name查询还是根据function查询，拿到基因ID集合
            if (geneIdOrName != null && !geneIdOrName.trim().equals("")){  //如果用户输入为geneFunction，这里不存在geneId
                boolean isGeneId = GeneRegexpService.isGeneId(geneIdOrName);
                if (isGeneId) {
                    List<String> matchedGene = GeneRegexpService.interpretGeneInput(geneIdOrName);  //基因匹配结果
                    if (matchedGene != null && matchedGene.size() > 0) {
                        //先取第一个匹配到的值
                        baseInfo.setGeneId(matchedGene.get(0));
                        baseInfo.setGeneOldId(matchedGene.get(0));
                    }
                } else {
                    baseInfo.setGeneId(null);
                    baseInfo.setGeneName(geneIdOrName);
                }
            }
            //如果是function，那么ID/name均为null，直接拿到从前台传过来的DNAGenBaseInfo即可，这里获取到符合条件的基因ID集合
            properGeneIdList = dnaGenBaseInfoDao.findProperGeneId(baseInfo);
            int total = properGeneIdList.size();
            page.setTotal(total);
            int end = pageNo * pageSize;
            end = end < total ? end : total;  //防止数组越界
            List<AdvanceSearchResultView> advanceSearchResultViews =
                    fpkmDao.fetchFirstHundredGene(condition, selectSnp, selectIndel, selectQTL, properGeneIdList.subList((pageNo - 1) * pageSize, end));
            page.addAll(advanceSearchResultViews);
            return new PageInfo<>(page);
        } else if (genStructure != null){
            //先从基因结构表中查找符合该种查询条件的基因总个数，返回基因结构ID，然后通过结构ID到高级搜索中搜索
            List<Integer> properGeneStructureIdList = dnaGenStructureService.getGeneStructureId(genStructure.getChromosome(), genStructure.getStart(), genStructure.getEnd());
            // todo 高级搜索混杂在这里会有问题，total不一定是那么多
            int total = properGeneStructureIdList.size();
            page.setTotal(total);
            int end = pageNo * pageSize;
            end = end < total ? end : total;
            List<AdvanceSearchResultView> advanceSearchResultViews =
                    fpkmDao.fetchFirstHundredGeneInGeneStructure(condition, selectSnp, selectIndel, selectQTL, properGeneStructureIdList.subList((pageNo - 1) * pageSize, end));
            page.addAll(advanceSearchResultViews);
            return new PageInfo<>(page);
        }
        //QTL查询高级搜索
        List<AdvanceSearchResultView> searchResult =
                fpkmDao.findGeneThroughGeneExpressionCondition(condition, selectSnp, selectIndel, firstHierarchyQtlId, selectQTL, null, null);
        return new PageInfo<>(searchResult);
    }

    public boolean checkExistSNP(int fpkmId, String snpConsequenceType){
        return fpkmDao.checkExistSNP(fpkmId, snpConsequenceType);
    }
}
