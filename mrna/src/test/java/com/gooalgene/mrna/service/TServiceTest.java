package com.gooalgene.mrna.service;

import com.gooalgene.mrna.vo.GResultVo;
import com.gooalgene.mrna.vo.GenResult;
import com.gooalgene.utils.JsonUtils;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class TServiceTest extends TestCase {
    @Autowired
    private TService tService;

    @Autowired
    private StudyService studyService;

    @Test
    public void testGenerateData(){
        String[] gens = studyService.queryGenesForFirst();
        GenResult genResult = tService.generateData(gens);
        String json = JsonUtils.Bean2Json(genResult);
        System.out.println(json);
    }

    /**
     * 获取任意基因FPKM值
     * 12月份需求只需要大组织的FPKM值>30即可
     */
    @Test
    public void testGenerateDataInRandomGene(){
        String[] genes = new String[1];
        genes[0] = "Glyma.08G267800";
        GenResult genResult = tService.generateData(genes);
        String result = JsonUtils.Bean2Json(genResult);
        System.out.println(result);
        List<GResultVo> cate = genResult.getCate();
        for (Iterator<GResultVo> iterator = cate.listIterator(); iterator.hasNext();){
            GResultVo gene = iterator.next();
            List<Double> fpkm = gene.getValues();
            if (gene.getLevel() != 0 || fpkm.get(0) < 30){
                iterator.remove();
            }
        }
        genResult.setCate(cate);
        System.out.println(JsonUtils.Bean2Json(genResult));
    }

    @Test
    public void testQueryClassifyByFather(){
        List<String> result = tService.queryClassifyByFather("seed_All");
        for (String r : result){
            System.out.println(r);
        }
    }
}
