package com.gooalgene.qtl.dao;

import com.gooalgene.entity.Associatedgenes;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by crabime on 12/17/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class AssociatedGenesDaoTest extends TestCase {

    @Autowired
    private AssociatedgenesDao associatedgenesDao;

    @Test
    public void testGetByNameAndVersion(){
        assertNotNull(associatedgenesDao);
    }

    @Test
    public void testFindAssociatedGeneByGeneId(){
        List<Associatedgenes> associatedGenes = associatedgenesDao.findAssociatedGeneByGeneId(224);
        assertEquals(3, associatedGenes.size());
    }
}
