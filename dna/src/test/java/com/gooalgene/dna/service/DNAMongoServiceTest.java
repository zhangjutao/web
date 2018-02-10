package com.gooalgene.dna.service;

import com.gooalgene.common.Page;
import com.gooalgene.common.constant.CommonConstant;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
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

    @Test
    public void testSearchByGene() {
        String[] ctypeList = {"all"};
        Page page = new Page();
        page.setPageSize(10);
        page.setPageNo(2);
        List<SNP> snps = dnaMongoService.searchByGene("SNP", ctypeList, "Glyma.01G000300", page);
        System.out.println(snps);
    }

    @Test
    public void testGetAllConsequenceTypeByGeneId(){
        Set<String> result = dnaMongoService.getAllConsequenceTypeByGeneId("Glyma.10G000100", "SNP");
        assertTrue(result.contains("exonic_nonsynonymous SNV"));
    }

    @Test
    public void testCheckGeneConsequenceType(){
        String geneId = "Glyma.01G182600";
        List<String> consequenceTypeList = new ArrayList<>();
        consequenceTypeList.add("upstream");
        consequenceTypeList.add("downstream");
        boolean result = dnaMongoService.checkGeneConsequenceType(geneId, CommonConstant.SNP, consequenceTypeList);
        assertTrue(result);
    }
}
