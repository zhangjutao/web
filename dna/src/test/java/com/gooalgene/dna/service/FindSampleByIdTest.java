package com.gooalgene.dna.service;

import com.github.pagehelper.PageInfo;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.SNP;
import com.gooalgene.dna.entity.result.DNARunSearchResult;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml","classpath:spring-mongodb.xml"}))
public class FindSampleByIdTest extends TestCase{
    @Autowired
    private SNPService snpService;
    @Autowired
    DNAMongoService dnaMongoService;
    @Autowired
    DNARunService dnaRunService;
    @Test
    public void testFindSNPBySNPId() {
        Map oneSNP = snpService.findSampleById("GlyI001090");
//        assertEquals();
        System.out.println(oneSNP);
    }


    @Test
    public void testGenotypeTransform() {
        SNP snp = dnaMongoService.findDataById("SNP", "Chr01", "GlyS001012353596");
        Map transformSNP = snpService.genotypeTransform(snp, "SNP");
        System.out.println(transformSNP);
    }

    @Test
    public void testGetQueryList() {
        List<DNARun> list= dnaRunService.getQueryList("{\"cultivar\":\"PI 339871A,PI 393551,ZJ-Y2300-1,?F1\"}");
        for (DNARun dnaRun : list) {
            List<String> list1 = dnaRunService.querySamples(dnaRun);
            System.err.println(list1);
        }
    }

    @Test
    public void testQueryDNARunByCondition() {
        Map<String, List<String>> result = dnaRunService.queryDNARunByCondition("[{\"name\":\"品种名PI 562565,品种名PI 339871A,品种名PI 393551,品种名ZJ-Y108,品种名ZJ-Y2300-1,品种名PI 366121,品种名PI 593983\",\"id\":1511837787324},{\"name\":\"品种名PI 562565,品种名PI 339871A,品种名PI 393551,品种名ZJ-Y108,品种名ZJ-Y2300-1,品种名PI 366121,品种名PI 59398\",\"id\":1511837787324,\"condition\":{\"cultivar\":\"PI 562565,PI 339871A,PI 393551,ZJ-Y108,ZJ-Y2300-1,PI 366121,PI 59398,?F1\"}}]");
        System.err.println(result);
    }

    @Test
    public void testGetListByConditionWithTypeHandler(){
        PageInfo<DNARunSearchResult> list = dnaRunService.getListByConditionWithTypeHandler(new DnaRunDto(), 1, 10, "1");
        assertNotNull(list);
        for (DNARunSearchResult result : list.getList()){
            System.out.println(result.getHeight());
        }
    }
}
