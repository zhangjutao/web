package com.gooalgene.dna.service;

import com.gooalgene.dna.dto.DNAGenStructureDto;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml","classpath:spring-mongodb.xml"}))
public class DNAGensServiceTest extends TestCase {

    @Autowired
    private DNAGensService dnaGensService;

    @Autowired
    private DNAGenStructureService dnaGenStructureService;

    @Test
    public void testDNAGeneServiceNotNullAndGetRegionNotError(){
        assertNotNull(dnaGensService);
        List<String> geneResult = dnaGensService.getByRegion("Chr01", "0", "100000");
        assertEquals(5, geneResult.size());
    }

    @Test
    public void testDNAGeneStructureFindById(){
        String geneId = "Glyma.01G000100";
        List<DNAGenStructureDto> structureResult = dnaGenStructureService.getByGeneId(geneId);
        assertEquals(5, structureResult.size());
    }

    @Test
    public void testGetGeneStructureId(){
        assertEquals(5505, dnaGenStructureService.getGeneStructureId("Chr01", 40000000L, 50000000L).size());
    }
}
