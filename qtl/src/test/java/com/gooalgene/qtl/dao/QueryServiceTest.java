package com.gooalgene.qtl.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.common.Page;
import com.gooalgene.entity.TraitCategory;
import com.gooalgene.qtl.service.QueryService;
import junit.framework.TestCase;
import net.sf.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * 查询所有qtl大类型
 * @author crabime
 * @since 12/12/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class QueryServiceTest extends TestCase {

    @Autowired
    private QueryService queryService;

    @Autowired
    private TraitCategoryDao traitCategoryDao;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testQueryAllTrait(){
        JSONArray traitJsonArray = queryService.queryAll();
        int resultSize = traitJsonArray.size();
        assertEquals(15, resultSize);
    }

    /**
     * 测试traitCategoryDao接口
     * 获取到所有trait类型
     */
    @Test
    public void testTraitCategoryDaoFindList(){
        TraitCategory traitCategory = new TraitCategory();
        List<TraitCategory> traitCategoryDaoList = traitCategoryDao.findList(traitCategory);
        assertEquals(15, traitCategoryDaoList.size());
    }

    /**
     * 查询该版本的所有染色体与lg(别称)之间的map集合
     */
    @Test
    public void testQueryChrsByVersion(){
        String version = "Gmax_275_v2.0";
        Map result = queryService.queryChrsByVersion(version);
        assertEquals(20, result.size());
        Set<Map.Entry> entrySet = result.entrySet();
        Iterator iterator = entrySet.iterator();

        while (iterator.hasNext()){
            Map.Entry next = (Map.Entry) iterator.next();
            System.out.println(next.getKey() + " ==> " + next.getValue()); //拿到所有染色体chr与lg之间关系
        }
    }

    /**
     * 高级搜索中qtl选项需要的数据
     */
    @Test
    public void testAdvanceSearchForQtlOption(){

    }

    @Test
    public void testQtlSearchByKeywords(){
        String version = "Gmax_275_v2.0";
        String type = "Trait";
        String keywords = "Reaction to Phakopsora pachyrhizi infection";
        Page page = new Page();
        page.setPageNo(0);
        page.setPageSize(10);
        Map result = queryService.qtlSearchbyKeywords(version, type, keywords, page);  //不做分页

    }

    @Test
    public void testQuerySearchByResult() throws JsonProcessingException {
        Map a = queryService.qtlSearchByResult("Glycine_max.V1.0.23.dna.genome", "all", "Seed", "", 1, 20);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        a.values().removeAll(Collections.singleton(null));
        String result = mapper.writeValueAsString(a);
        System.out.println(result);
    }
}
