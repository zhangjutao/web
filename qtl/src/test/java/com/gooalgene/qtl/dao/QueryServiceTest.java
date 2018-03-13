package com.gooalgene.qtl.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.gooalgene.common.Page;
import com.gooalgene.entity.TraitCategory;
import com.gooalgene.qtl.entity.QtlSearchResult;
import com.gooalgene.qtl.entity.QtlTableEntity;
import com.gooalgene.qtl.service.QueryService;
import com.gooalgene.qtl.service.handler.NullSerializerImpl;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public void testQtlSearchByResult() throws JsonProcessingException {
        QtlTableEntity entity = queryService.qtlSearchByResult("", "All", "Al", null, 7, 10);
        List<QtlSearchResult> data = entity.getData();
        ObjectMapper mapper = new ObjectMapper();
        DefaultSerializerProvider.Impl provider = new DefaultSerializerProvider.Impl();
        provider.setNullValueSerializer(new NullSerializerImpl());
        mapper.setSerializerProvider(provider);
        for (QtlSearchResult result : data){
            String chr = result.getChr();
            if (chr == null){
                System.out.println(mapper.writeValueAsString(result));
            }
        }
        System.out.println("\r\n============\r\n");
        System.out.println(mapper.writeValueAsString(entity));
    }

}
