package com.gooalgene.dna.service;

import com.gooalgene.dna.entity.SNP;
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
public class DNAMongoServiceTest extends TestCase {
    @Autowired
    private DNAMongoService dnaMongoService;

    @Test
    public void testSearchInRegion(){
        String type = "INDEL";
        String ctype = "all";
        String chr = "Chr01";
        String startPos = "0";
        String endPos = "100000";
        long start = System.currentTimeMillis();
        List<SNP> snps = dnaMongoService.searchInRegin(type, ctype, chr, startPos, endPos, null);
        long end = System.currentTimeMillis();
        System.out.println(snps.size() + "时间为：" + (end - start));
    }

    @Test
    public void testSearchIDAndPos(){
        String type = "INDEL";
        String ctype = "all";
        String chr = "Chr01";
        String startPos = "0";
        String endPos = "1000";
        List<SNP> snps = dnaMongoService.searchInRegin(type, ctype, chr, startPos, endPos, null);
        assertEquals(19, snps.size());
    }
}
