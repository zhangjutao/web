package com.gooalgene.iqgs.dao;

import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;
import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class GeneSortDaoTest extends TestCase {

    @Autowired
    private GeneSortDao geneSortDao;

    @Test
    public void testFindViewByGeneId(){
        String geneId = "Glyma.02G036200";
        String fields="seed,pod";
        fields=getPrefixBeforeFields(fields);
        SortedSearchResultView view = geneSortDao.findViewByGeneId(Arrays.asList(new String[]{geneId}),fields,19);
        assertEquals(16.73, view.getFpkm().getPod());
        assertEquals(18.8, view.getFpkm().getShootMeristem());
        assertEquals(7, view.getSnpConsequenceType().size());
        assertEquals(3, view.getIndelConsequenceType().size());
        assertEquals(12, view.getAllQtl().size());
        assertEquals("Chr02", view.getChromosome());
        assertEquals("3334180bp-3337009bp:-", view.getLocation());
    }

    private String getPrefixBeforeFields(String fields){
        String[] strings=fields.split(",");
        List<String> strings2= Lists.newArrayList();
        for (String str:strings){
            strings2.add("a."+str);
        }
        return StringUtils.join(strings2.toArray(),",");
    }
}
