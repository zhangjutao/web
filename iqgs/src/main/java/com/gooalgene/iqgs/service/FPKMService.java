package com.gooalgene.iqgs.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

import java.util.List;

import static com.gooalgene.common.constant.CommonConstant.DEFAULTRESULTVIEW;

@Service
public class FPKMService implements InitializingBean {

    @Autowired
    private FPKMDao fpkmDao;

    @Autowired
    private DNAGenBaseInfoDao dnaGenBaseInfoDao;

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
        if (baseInfo != null) {
            String geneIdOrName = baseInfo.getGeneId();  //用户输入的geneId，这里需要调正则匹配服务
            boolean commonGene = GeneRegexpService.isCommonGeneIdOrName(geneIdOrName);  //如果是通用的基因ID或者name，直接从缓存中拿已存的前100条数据
            if (commonGene){
                // todo 解决分页问题
                Cache.ValueWrapper valueWrapper = cache.get(DEFAULTRESULTVIEW);
                List<AdvanceSearchResultView> defaultSearchResultViews = null;
                if (valueWrapper == null){
                    List<Integer> frontHundredGene = dnaGenBaseInfoDao.getFrontHundredGene();
                    defaultSearchResultViews = fpkmDao.fetchFirstHundredGene(condition, selectSnp, selectIndel, selectQTL, frontHundredGene);
                    cache.putIfAbsent(DEFAULTRESULTVIEW, defaultSearchResultViews);
                }else {
                    defaultSearchResultViews = (List<AdvanceSearchResultView>) valueWrapper.get();
                }
                Page<AdvanceSearchResultView> page = new Page<>(pageNo, pageSize, false);
                page.addAll(defaultSearchResultViews);
                page.setTotal(56044);
                PageInfo<AdvanceSearchResultView> resultPageInfo = new PageInfo<>(page);
                return resultPageInfo;
            }else {
                List<Integer> frontHundredGene = dnaGenBaseInfoDao.getFrontHundredGene();
                List<AdvanceSearchResultView> defaultSearchResultViews = fpkmDao.fetchFirstHundredGene(condition, selectSnp, selectIndel, selectQTL, frontHundredGene);
                cache.putIfAbsent(DEFAULTRESULTVIEW, defaultSearchResultViews);
            }
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
        }
        PageHelper.startPage(pageNo, pageSize);
        List<AdvanceSearchResultView> searchResult =
                fpkmDao.findGeneThroughGeneExpressionCondition(condition, selectSnp, selectIndel, firstHierarchyQtlId, selectQTL, baseInfo, genStructure);
        return new PageInfo<>(searchResult);
    }

    public boolean checkExistSNP(int fpkmId, String snpConsequenceType){
        return fpkmDao.checkExistSNP(fpkmId, snpConsequenceType);
    }
}
