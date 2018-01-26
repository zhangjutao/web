package com.gooalgene.iqgs.dao;

import com.gooalgene.iqgs.entity.sort.IndelScore;
import com.gooalgene.iqgs.entity.sort.SnpScore;
import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;
import com.gooalgene.iqgs.service.FPKMService;
import com.gooalgene.iqgs.service.sort.SortService;
import com.gooalgene.iqgs.service.sort.SortServiceImpl;
import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class GeneSortDaoTest extends TestCase {

    @Autowired
    private GeneSortDao geneSortDao;
    @Autowired
    private FPKMService fpkmService;

    @Autowired
    private SortServiceImpl sortService;

    @Test
    public void testFindViewByGeneId() throws IllegalAccessException {
        String[] geneIds = new String[]{"Glyma.04G197300", "Glyma.01G182600","Glyma.02G036200","Glyma.13G319500"};
        String fields="cotyledon,pod";
        if(fields!=null&&fields!=""){
            //fields=sortService.getPrefixBeforeFields(fields);
        }
        List<String> qtlNamesByTrait = geneSortDao.getQtlNamesByTrait(19);
        List<SortedSearchResultView> views = geneSortDao.findViewByGeneId(Arrays.asList(geneIds),fields);
        /*for (SortedSearchResultView view:views){
            sortService.calculateScoreOfFpkm(view);
            sortService.calculateScoreOfQtl(view);
            sortService.calculateScoreOfSnpAndIndel(view);
        }
        double v = views.get(0).getScore() - views.get(1).getScore();
        System.out.println(v);*/
        List<SortedSearchResultView> sort = sortService.sort(views,qtlNamesByTrait);
        System.out.println(sort);
    }

}
