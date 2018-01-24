package com.gooalgene.iqgs;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.common.Page;
import com.gooalgene.common.dao.StudyDao;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.entity.Associatedgenes;
import com.gooalgene.entity.Study;
import com.gooalgene.iqgs.dao.DNAGenBaseInfoDao;
import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.DNAGenFamily;
import com.gooalgene.iqgs.entity.DNAGenSequence;
import com.gooalgene.iqgs.service.DNAGenBaseInfoService;
import com.gooalgene.mrna.entity.ExpressionVo;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    private DNAGenBaseInfoService dnaGenBaseInfoService;

    @Autowired
    private DNAGenBaseInfoDao dnaGenBaseInfoDao;

    @Autowired
    private StudyDao studyDao;

    @Autowired
    private FPKMDao fpkmDao;

    @Autowired
    private MongoTemplate mongoTemplate;

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

    /**
     * 测试iqgs中条件查询接口
     */
    @Test
    public void testFindByConditions() throws IOException {
        DNAGenBaseInfo bean = new DNAGenBaseInfo();
        bean.setGeneName("Gly");
        Page<DNAGenBaseInfo> page = new Page<>(1, 10);
//        bean.setPage(page);
        List<DNAGenBaseInfo> geneResult = dnaGenBaseInfoDao.findByConditions(bean);
        assertEquals(10, geneResult.size());
        // 截取集合中前三个
        List<DNAGenBaseInfo> firstThree = geneResult.subList(0, 3);
        String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(firstThree);
        System.out.println(result);
    }

    @Test
    public void testFindGeneByQTLName(){
        Integer[] ids = {1003, 1005, 1008};
        List<DNAGenBaseInfo> all = dnaGenBaseInfoDao.findGeneInQTLIds(Arrays.asList(ids));
        assertNotNull(all);
        assertEquals(532, all.size());
    }

    /**
     * 测试iqgs中获取基因序列的dao接口
     */
    @Test
    public void testfindGenSequenceByGeneIdDao() throws Exception {
        DNAGenSequence dnaGenSequence =new DNAGenSequence();
        dnaGenSequence.setGeneId("Glyma.01G004900");
        List<DNAGenSequence> dgs=dnaGenBaseInfoDao.findGenSequenceByGeneId(dnaGenSequence);
        for (int i = 0; i < dgs.size(); i++) {
            DNAGenSequence genSequence =  dgs.get(i);
            System.out.println(genSequence.toString());
        }
    }

    /**
     * 测试iqgs中获取基因基本信息接口
     */
    @Test
    public void testfindByGeneIdDao() throws Exception {
        DNAGenBaseInfo bean=new DNAGenBaseInfo();
        bean.setGeneId("Glyma.01G004900");
        DNAGenBaseInfo dna=dnaGenBaseInfoDao.findByGeneId(bean);
        System.out.println(dna.toString());
    }

    /**
     * 测试iqgs中获取基因结构的dao接口
     */
    @Test
    public void testfindGenStructureByTranscriptIdDao() throws IOException {
        DNAGenStructure dnaGenStructure =new DNAGenStructure();
        dnaGenStructure.setTranscriptId("Glyma.01G004900.1");
        List<DNAGenStructure> dgs=dnaGenBaseInfoDao.findGenStructureByTranscriptId(dnaGenStructure);
        for (int i = 0; i < dgs.size(); i++) {
            DNAGenStructure genStructure =  dgs.get(i);
            System.out.println(genStructure.toString());
        }
    }

    /**
     * 测试iqgs中基因表达量排序
     */
    @Test
    public void testqueryStudyByGene() throws Exception {
        //List<String> run = studyDao.findSampleruns();//查询所有的run
        String collectionName="all_gens_fpkm";
        Query query = new Query();
        query.addCriteria(Criteria.where("gene").is("Glyma.01G004900"));
        //query.addCriteria(Criteria.where("samplerun.name").in(run));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "samplerun.value")));//降序
        long total = mongoTemplate.count(query, ExpressionVo.class, collectionName);
        System.out.println(total);
        query.limit(10);//取10条
        query.skip(5);
        //query.limit(15);
        //System.out.println("Query count:" + query.toString());
        List<ExpressionVo> runs = mongoTemplate.find(query, ExpressionVo.class, "all_gens_fpkm");
        //System.out.println(runs.toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < runs.size(); i++) {
            ExpressionVo expressionVo =  runs.get(i);
            String samplerunName=expressionVo.getSamplerun().getName();
            Study study=studyDao.findBySampleRun(samplerunName);
            System.out.println(study.toString());

            Date time = (Date) study.getCreateTime();
            //study.setCreateTime(simpleDateFormat.format(time));
            //System.out.println(study.getCreateTime());
            /*System.out.println(expressionVo.getSamplerun().getValue());
            System.out.println(expressionVo.toString());*/
        }
    }

    @Test
    public void testCheckGeneExists(){
        String geneId = "Glyma.28G267800";  //不存在情况
        Integer result = dnaGenBaseInfoDao.checkGeneExists(geneId);
        assertNull(result);
        geneId = "Glyma.08G267800";
        result = dnaGenBaseInfoDao.checkGeneExists(geneId);
        assertEquals(21973, result.intValue());
        geneId = "Glyma08G36030";
        result = dnaGenBaseInfoDao.checkGeneExists(geneId);
        assertEquals(21973, result.intValue());
    }

    @Test
    public void testCheckGeneExistsInQtlList(){
        int id = 21972;
        List<Integer> qtlList = new ArrayList<>();
        qtlList.add(1);
        qtlList.add(2052);
        qtlList.add(2312);
        boolean exists = dnaGenBaseInfoDao.checkGeneExistsInQtlList(id, qtlList);
        assertTrue(exists);
        qtlList.remove(0);
        exists = dnaGenBaseInfoDao.checkGeneExistsInQtlList(id, qtlList);
        assertFalse(exists);
    }

    @Test
    public void testFindAllQTLNamesByGeneId(){
        String geneId = "Glyma08G36090";
        List<Associatedgenes> allQTLNames = dnaGenBaseInfoDao.findAllAssociatedQTLByGeneId(geneId);
        assertEquals(18, allQTLNames.size());
    }

    @Test
    public void testFindProperGeneId(){
        DNAGenBaseInfo info = new DNAGenBaseInfo();
        info.setGeneId("a");
        assertEquals(56044, dnaGenBaseInfoDao.findProperGeneId(info).size());
        info.setGeneId(null);
        info.setFunctions("sequence");
        assertEquals(163, dnaGenBaseInfoDao.findProperGeneId(info).size());
    }

}
