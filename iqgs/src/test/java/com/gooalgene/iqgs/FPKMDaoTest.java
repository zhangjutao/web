package com.gooalgene.iqgs;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.constant.CommonConstant;
import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.Tissue;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.entity.condition.GeneExpressionCondition;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class FPKMDaoTest extends TestCase {

    @Autowired
    private FPKMDao fpkmDao;

    @Test
    public void testFindGeneThroughGeneExpressionCondition(){
        List<GeneExpressionConditionEntity> list = new ArrayList<>();
        GeneExpressionConditionEntity condition = new GeneExpressionConditionEntity();
        Tissue tissue = new Tissue();
        tissue.setPod(0.0);
        condition.setTissue(tissue);
        condition.setBegin(10.0);
        condition.setEnd(20.0);
        list.add(condition);
        GeneExpressionConditionEntity condition1 = new GeneExpressionConditionEntity();
        Tissue embryo = new Tissue();
        embryo.setEmbryo(0.0);
        condition1.setBegin(40.0);
        condition1.setEnd(50.0);
        condition1.setTissue(embryo);  //这只新的查询条件为胚芽(embryo)
        list.add(condition1);
        List<AdvanceSearchResultView> geneResult = fpkmDao.findGeneThroughGeneExpressionCondition(list, null, null, null, null);
        assertEquals(47, geneResult.size());
        //如果有SNP、INDEL筛选情况下
        List<String> snpConsequenceList = new ArrayList<>();
        snpConsequenceList.add("upstream;downstream");
        snpConsequenceList.add("UTR5");
        List<String> indelConsequenceList = new ArrayList<>();
        indelConsequenceList.add("exonic_frameshift deletion");
        indelConsequenceList.add("splicing");
        geneResult = fpkmDao.findGeneThroughGeneExpressionCondition(list, snpConsequenceList, indelConsequenceList, null, null);
        assertEquals(20, geneResult.size());
        //测试传入高级搜索中QTL ID
        Integer[] associateGeneIdArray = new Integer[]{1453, 1941, 2089};
        geneResult = fpkmDao.findGeneThroughGeneExpressionCondition(list, snpConsequenceList, indelConsequenceList, null, Arrays.asList(associateGeneIdArray));
        assertEquals(2, geneResult.size());
        //测试通过一级搜索筛选出一部分基因，从该基因进行高级搜索进一步筛选
        Integer[] firstHierarchyQtlId = new Integer[]{1926, 2089, 3864};
        geneResult = fpkmDao.findGeneThroughGeneExpressionCondition(list, snpConsequenceList, indelConsequenceList, Arrays.asList(firstHierarchyQtlId), Arrays.asList(associateGeneIdArray));
        assertEquals(2, geneResult.size());
    }

    @Test
    public void testNullGeneExpression(){
        Integer[] associateGeneIdArray = new Integer[]{3861, 3862, 3866};
        PageHelper.startPage(1, 10);
        List<AdvanceSearchResultView> geneResult = fpkmDao.findGeneThroughGeneExpressionCondition(null, null, null, null, Arrays.asList(associateGeneIdArray));
        assertEquals(10, geneResult.size());
        assertEquals(1, ((Page)geneResult).getPageNum());
        assertEquals(188, ((Page)geneResult).getTotal());
        PageInfo<AdvanceSearchResultView> pageInfo = new PageInfo<>(geneResult);
        assertEquals(188, pageInfo.getTotal());
        assertEquals(10, pageInfo.getPageSize());
    }

    @Test
    public void testCheckExistSNP(){
        boolean result = fpkmDao.checkExistSNP(5, CommonConstant.EXONIC_NONSYNONYMOUSE);
        assertTrue(result);
    }

    @Test
    public void testDoubleNullCompare(){
        Double a = null;
        try {
            assertFalse("a不大于十", a > 10);
            fail();
        }catch (NullPointerException e){

        }
    }

}
