package com.gooalgene.dna.service;

import com.gooalgene.dna.entity.SNP;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml","classpath:spring-mongodb.xml"}))
public class findSampleByIdTest extends TestCase{
    @Autowired
    private SNPService snpService;
    @Autowired
    DNAMongoService dnaMongoService;
    @Test
    public void testFindSNPBySNPId() {
        Map oneSNP = snpService.findSampleById("GlyI001090");
//        assertEquals();
        System.out.println(oneSNP);
    }


    @Test
    public void testGenotypeTransform() {
        SNP snp = dnaMongoService.findDataById("SNP", "Chr01", "GlyS001012353596");
        Map transformSNP = snpService.genotypeTransform(snp, "SNP");
        System.out.println(transformSNP);
    }
}
