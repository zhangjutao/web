package com.gooalgene.iqgs;

import com.gooalgene.iqgs.dao.RegularityNetworkDao;
import com.gooalgene.iqgs.entity.RegularityLink;
import com.gooalgene.iqgs.entity.RegularityNode;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Test
    public void testRegularityNodeEquals(){
        RegularityNode node = new RegularityNode("Glyma.04G131800", 1);
        RegularityNode node2 = new RegularityNode("Glyma.04G131800", 1);
        assertTrue(node.equals(node2));
        Set<RegularityNode> set = new HashSet<>();
        set.add(node);
        set.add(node2);
        assertEquals(1, set.size());
    }
}
