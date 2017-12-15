package com.gooalgene.dna.service;

import com.gooalgene.common.Page;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.entity.SNP;
import junit.framework.TestCase;
import net.sf.json.JSONArray;
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
        String endPos = "500000";
        List<SNP> snps = dnaMongoService.searchIdAndPosInRegin(type, ctype, chr, startPos, endPos, null);
        System.out.println(snps.size());
    }

    @Test
    public void testSearchIdAndPosInclude(){
        String type = "INDEL";
        String ctype = "all";
        String chr = "Chr01";
        String startPos = "0";
        String endPos = "500000";
        List<SNP> snps = dnaMongoService.searchIdAndPosInRegion(type, ctype, chr, startPos, endPos, null);
        System.out.println(snps.size());
    }
    @Test
    public void testSearchInGene(){
        String type = "INDEL";
        String ctype = "all";
        String gene = "Glyma.20G250100";
        Page<DNAGens> page=new Page<>();
        page.setPageSize(87);
        String upsteam="47868688";
        String downsteam="47872967";
        List<SNP> snps = dnaMongoService.searchInGene(type, ctype,gene, upsteam,downsteam, page);
        System.out.println(snps.size());
    }

}
