package com.gooalgene.iqgs.dao;

import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class GeneSortDaoTest extends TestCase {

    @Autowired
    private GeneSortDao geneSortDao;

    @Test
    public void testFindViewByGeneId(){
        String geneId = "Glyma.02G036200";
        SortedSearchResultView view = geneSortDao.findViewByGeneId(geneId);
        assertEquals(16.73, view.getFpkm().getPod());
        assertEquals(18.8, view.getFpkm().getShootMeristem());
        assertEquals(7, view.getSnpConsequenceType().size());
        assertEquals(3, view.getIndelConsequenceType().size());
        assertEquals(12, view.getAllQtl().size());
    }
}
