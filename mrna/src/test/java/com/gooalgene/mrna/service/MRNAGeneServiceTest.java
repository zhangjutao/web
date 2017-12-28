package com.gooalgene.mrna.service;

import com.gooalgene.common.dao.MrnaGensDao;
import com.gooalgene.entity.MrnaGens;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class MRNAGeneServiceTest extends TestCase {
    @Autowired
    private MrnaGensDao mrnaGensDao;

    @Test
    public void testFindMRNAGeneByGeneId(){
        String geneId = "Glyma.01G000200";
        MrnaGens mrnaGene = mrnaGensDao.findMRNAGeneByGeneId(geneId);
        assertEquals("LOC102661143", mrnaGene.getGeneName());
    }
}
