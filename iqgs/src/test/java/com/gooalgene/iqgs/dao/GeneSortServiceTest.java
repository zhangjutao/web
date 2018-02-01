package com.gooalgene.iqgs.dao;

import com.github.pagehelper.PageInfo;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.iqgs.entity.Tissue;
import com.gooalgene.iqgs.entity.sort.SortRequestParam;
import com.gooalgene.iqgs.entity.sort.SortedResult;
import com.gooalgene.iqgs.service.sort.GeneSortViewService;
import com.gooalgene.iqgs.web.SortController;
import com.gooalgene.utils.ResultUtil;
import junit.framework.TestCase;
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
public class GeneSortServiceTest extends TestCase {
    @Autowired
    private GeneSortViewService geneSortViewService;

    @Autowired
    private SortController sortController;

    @Test
    public void testFindViewByGeneId(){
        Tissue tissue = new Tissue();
        tissue.setPod(23.0);
        tissue.setCotyledon(11d);
        PageInfo<SortedResult> sortedResults = geneSortViewService.findViewByGeneId(Arrays.asList("Glyma.04G197300", "Glyma.01G182600", "Glyma.02G036200", "Glyma.13G319500"),
                tissue, 19, 1, 10);
        assertNotNull(sortedResults);
    }

    @Test
    public void testCopyGeneId() {
        Tissue tissue = new Tissue();
        tissue.setPod(23.0);
        tissue.setCotyledon(11d);
        PageInfo<SortedResult> sortedResults = geneSortViewService.findViewByGeneId(Arrays.asList("Glyma.04G197300", "Glyma.01G182600", "Glyma.02G036200", "Glyma.13G319500"),
                tissue, 19, 1, 10);
        SortRequestParam sortRequestParam = new SortRequestParam();
        sortRequestParam.setGeneIdList(Arrays.asList("Glyma.04G197300", "Glyma.01G182600", "Glyma.02G036200", "Glyma.13G319500"));
        sortRequestParam.setTissue(tissue);
        sortRequestParam.setTraitCategoryId(19);
        ResultVO<List<String>> resultUtil = sortController.copyOrderedGeneId(sortRequestParam);
        assertNotNull(sortedResults);
    }

    @Test
    public void testFindSortedView(){
        Tissue tissue = new Tissue();
        tissue.setPod(23.0);
        tissue.setCotyledon(11d);
        PageInfo<SortedResult> sortedResults = geneSortViewService.findSortedView(Arrays.asList("Glyma.04G197300", "Glyma.01G182600", "Glyma.02G036200", "Glyma.13G319500"),
                tissue, 19, 1, 10);
        assertNotNull(sortedResults);
    }
}
