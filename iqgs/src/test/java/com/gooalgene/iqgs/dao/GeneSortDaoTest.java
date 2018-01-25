package com.gooalgene.iqgs.dao;

import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.sort.IndelScore;
import com.gooalgene.iqgs.entity.sort.QtlScore;
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
import sun.security.provider.Sun;

import java.util.*;

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
        String[] geneIds = new String[]{"Glyma.02G036200", "Glyma.13G319500"};
        String fields="seed,pod";
        fields=sortService.getPrefixBeforeFields(fields);
        List<SortedSearchResultView> views = geneSortDao.findViewByGeneId(Arrays.asList(geneIds),fields,19);
        for (SortedSearchResultView view:views){
            sortService.calculateScoreOfFpkm(view);
            sortService.calculateScoreOfQtl(view);
            sortService.calculateScoreOfSnpAndIndel(view);
        }
        double v = views.get(0).getScore() - views.get(1).getScore();
        System.out.println(v);
    }

}
