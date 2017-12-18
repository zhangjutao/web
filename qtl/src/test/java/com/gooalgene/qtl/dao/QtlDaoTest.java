package com.gooalgene.qtl.dao;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.entity.Qtl;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * qtl查询相关单元测试代码
 * @author crabime
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class QtlDaoTest extends TestCase {
    @Autowired
    private QtlDao qtlDao;

    private ObjectMapper objectMapper = new ObjectMapper();

    private JsonGenerator jsonGenerator = null;

    @Before
    public void setUp(){
        try {
            jsonGenerator = objectMapper.getFactory().createGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试findAllQtlList方法
     * @throws IOException jackson解析错误，无法输出到输出流中
     */
    @Test
    public void testFindAllQtlList() throws IOException {
        assertNotNull(qtlDao);
        List<Qtl> allQtl = qtlDao.findAllList();
        Qtl qtl = allQtl.get(0);
        jsonGenerator.writeObject(qtl);

    }

    @Test
    public void testFindQTLList(){
        Qtl qtl = new Qtl();
        qtl.setKeywords("pyralid");
        List<Qtl> result = qtlDao.findQTLList(qtl);
        assertEquals(8, result.size());
    }

    /**
     * 测试qtl findByCondition方法
     */
    @Test
    public void testQtlFindByCondition(){
        Qtl qtl = new Qtl();
        qtl.setKeywords("Trait");
        // 外包直接将结果放到map中，很奇怪！不过也可以用Jackson转
        List<Map> result = qtlDao.findByCondition(qtl);
        // 查看第一个值
        Map firstValue = result.get(0);

        try {
            String normalMapResult = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(firstValue);
            System.out.println(normalMapResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindQtlsByName(){
        List<Qtl> qtls = qtlDao.findQtlsByName("daidzein");
        assertNotNull(qtls);
        assertEquals(60, qtls.size());
    }
}
