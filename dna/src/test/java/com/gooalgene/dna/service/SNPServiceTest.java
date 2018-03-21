package com.gooalgene.dna.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.common.Page;
import com.gooalgene.dna.entity.DNAGens;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:spring-context-test.xml"))
public class SNPServiceTest extends TestCase {

    @Autowired
    private SNPService snpService;

    @Test
    public void testSearchSNPByGene() throws JsonProcessingException {
        String type = "SNP";
        String[] consequenceTypes = new String[]{"downstream", "exonic_nonsynonymous SNV"};
        String gene = "PT1_10000698";
        Map map = snpService.searchSNPByGene(type, consequenceTypes, gene, new Page<DNAGens>(1, 10));
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(map));
    }
}
