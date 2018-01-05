package com.gooalgene.iqgs;

import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.Tissue;
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
        List<String> geneResult = fpkmDao.findGeneThroughGeneExpressionCondition(list);
        assertEquals(47, geneResult.size());
    }

}
