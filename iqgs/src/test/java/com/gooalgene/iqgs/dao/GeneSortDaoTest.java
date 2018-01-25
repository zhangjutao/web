package com.gooalgene.iqgs.dao;

import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.sort.IndelScore;
import com.gooalgene.iqgs.entity.sort.QtlScore;
import com.gooalgene.iqgs.entity.sort.SnpScore;
import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;
import com.gooalgene.iqgs.service.FPKMService;
import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.security.provider.Sun;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class GeneSortDaoTest extends TestCase {

    @Autowired
    private GeneSortDao geneSortDao;
    @Autowired
    private FPKMService fpkmService;


    @Test
    public void testFindViewByGeneId() throws IllegalAccessException {
        String[] geneIds = new String[]{"Glyma.02G036200", "Glyma.13G319500"};
        String fields="seed,pod";
        fields=getPrefixBeforeFields(fields);
        List<SortedSearchResultView> views = geneSortDao.findViewByGeneId(Arrays.asList(geneIds),fields,19);
        //Map<String, Integer> stringIntegerMap = fpkmService.calculateScoreOfFpkm(Lists.<GeneFPKM>newArrayList(view.getFpkm()));
        for (SortedSearchResultView view:views){
            fpkmService.calculateScoreOfFpkm(view);
            calculateScoreOfQtl(view);
            calculateScoreOfSnpAndIndel(view);
        }
        System.out.println(1);
        /*Set<QtlScore> allQtl = view.getAllQtl();
        for(QtlScore qtlScore:allQtl){
            for(Map.Entry entry:stringIntegerMap.entrySet()){
                if(qtlScore.getGeneId().equals(entry.getKey())){
                    Integer value = (Integer) entry.getValue()+10;
                    stringIntegerMap.put((String) entry.getKey(),value);
                }
            }
        }
        Set<SnpScore> snpConsequenceType = view.getSnpConsequenceType();
        for (SnpScore snpScore:snpConsequenceType){
            for(Map.Entry entry:stringIntegerMap.entrySet()){
                if(snpScore.getGeneId().equals(entry.getKey())){
                    if(snpScore.getScore()==null){
                        snpScore.setScore(0);
                    }
                    Integer value = (Integer) entry.getValue()+snpScore.getScore()/snpConsequenceType.size();*//*snpScore.getCount()*//*
                    stringIntegerMap.put((String) entry.getKey(),value);
                }
            }
        }
        Set<IndelScore> indelConsequenceType = view.getIndelConsequenceType();
        for (IndelScore indelScore:indelConsequenceType){
            for(Map.Entry entry:stringIntegerMap.entrySet()){
                if(indelScore.getGeneId().equals(entry.getKey())){
                    if(indelScore.getScore()==null){
                        indelScore.setScore(0);
                    }
                    Integer value = (Integer) entry.getValue()+indelScore.getScore()/indelConsequenceType.size();*//*snpScore.getCount()*//*
                    stringIntegerMap.put((String) entry.getKey(),value);
                }
            }
        }*/

        /*assertEquals(16.73, view.getFpkm().getPod());
        assertEquals(18.8, view.getFpkm().getShootMeristem());
        assertEquals(7, view.getSnpConsequenceType().size());
        assertEquals(3, view.getIndelConsequenceType().size());
        assertEquals(12, view.getAllQtl().size());
        assertEquals("Chr02", view.getChromosome());
        assertEquals("3334180bp-3337009bp:-", view.getLocation());*/
    }

    private String getPrefixBeforeFields(String fields){
        String[] strings=fields.split(",");
        List<String> strings2= Lists.newArrayList();
        for (String str:strings){
            strings2.add("a."+str);
        }
        return StringUtils.join(strings2.toArray(),",");
    }

    private SortedSearchResultView calculateScoreOfQtl(SortedSearchResultView view){
        view.setScore(view.getScore()+view.getAllQtl().size()*10);
        return view;
    }
    private SortedSearchResultView calculateScoreOfSnpAndIndel(SortedSearchResultView view){
        Integer oldScore=view.getScore();
        Integer sum=0;
        for(SnpScore snpScore:view.getSnpConsequenceType()){
            Integer score = snpScore.getScore();
            if(score==null){
                score=0;
            }
            sum+=(score);/*snpScore.getCount()*/
        }
        sum=sum/(view.getSnpConsequenceType().size());
        Integer sum2=0;
        for(IndelScore indelScore:view.getIndelConsequenceType()){
            Integer score = indelScore.getScore();
            if(score==null){
                score=0;
            }
            sum2+=(score);/*indelScore.getCount()*/
        }
        sum2=sum2/(view.getIndelConsequenceType().size());
        view.setScore(oldScore+sum+sum2);
        return view;
    }
}
