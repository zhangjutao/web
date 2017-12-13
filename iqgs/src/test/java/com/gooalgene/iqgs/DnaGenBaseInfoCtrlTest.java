package com.gooalgene.iqgs;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.iqgs.dao.DNAGenBaseInfoDao;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.DNAGenFamily;
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

/**
 * DNAGenBaseInfoCtroller相关方法测试
 *
 * @author gaarakseven
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class DnaGenBaseInfoCtrlTest extends TestCase{

    private JsonGenerator jsonGenerator = null;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private DNAGenBaseInfoDao dnaGenBaseInfoService;

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
}
