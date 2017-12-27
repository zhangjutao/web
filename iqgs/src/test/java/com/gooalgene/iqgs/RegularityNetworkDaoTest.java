package com.gooalgene.iqgs;

import com.gooalgene.iqgs.dao.RegularityNetworkDao;
import com.gooalgene.iqgs.entity.RegularityLink;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class RegularityNetworkDaoTest extends TestCase {
    @Autowired
    private RegularityNetworkDao regularityNetworkDao;

    @Test
    public void testFindRelateGene(){
        String geneId = "Glyma.04G131800";
        List<RegularityLink> relateGenes = regularityNetworkDao.findRelateGene(geneId, 1);
        assertEquals(100, relateGenes.size());
        List<RegularityLink> relateGeneWithSecondHierarchy = regularityNetworkDao.findRelateGene(geneId, 2);
        assertEquals(10, relateGeneWithSecondHierarchy.size());
    }
}
