package com.gooalgene.iqgs;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.common.Page;
import com.gooalgene.iqgs.dao.DNAGenBaseInfoDao;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.DNAGenFamily;
import com.gooalgene.iqgs.entity.DNAGenHomologous;
import com.gooalgene.iqgs.service.DNAGenBaseInfoService;
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

/**
 * DNAGenBaseInfoCtroller相关方法测试
 *
 * @author gaarakseven
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class DnaGenBaseInfoServiceTest extends TestCase{

    private JsonGenerator jsonGenerator = null;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private DNAGenBaseInfoDao dnaGenBaseInfoDao;

    @Autowired
    DNAGenBaseInfoService dnaGenBaseInfoService;

    @Before
    public void setUp(){
        try {
            jsonGenerator = objectMapper.getFactory().createGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试findFamilyByFamilyId方法
     *
     * @throws IOException jackson解析错误，无法输出到输出流中
     */
    @Test
    public void testFindFamilyByFamilyId() throws IOException {
        DNAGenFamily dnaGenFamilies = dnaGenBaseInfoService.findFamilyByFamilyId("ARR-B");
        jsonGenerator.writeObject(dnaGenFamilies);
    }

    @Test
    public void testGetGenHomologousByGeneId() {
        List<DNAGenHomologous> homologous = dnaGenBaseInfoService.getGenHomologousByGeneId("Glyma.01G004900");
        System.out.println(homologous);
    }

    /**
     * 测试iqgs中条件查询接口
     */
    @Test
    public void testFindByConditions() throws IOException {
        DNAGenBaseInfo bean = new DNAGenBaseInfo();
        bean.setGeneName("Gly");
        Page<DNAGenBaseInfo> page = new Page<>(1, 10);
        bean.setPage(page);
        List<DNAGenBaseInfo> geneResult = dnaGenBaseInfoDao.findByConditions(bean);
        assertEquals(10, geneResult.size());
        // 截取集合中前三个
        List<DNAGenBaseInfo> firstThree = geneResult.subList(0, 3);
        String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(firstThree);
        System.out.println(result);
    }
}
