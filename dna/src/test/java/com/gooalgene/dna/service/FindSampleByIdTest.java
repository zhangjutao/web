package com.gooalgene.dna.service;

import com.github.pagehelper.PageInfo;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.dto.SampleInfoDto;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.SNP;
import com.gooalgene.dna.entity.SampleInfo;
import com.gooalgene.dna.entity.result.DNARunSearchResult;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
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
    public void testGenotypeTransform() {
        SNP snp = dnaMongoService.findDataById("SNP", "Chr01", "GlyS001012353596");
        Map transformSNP = snpService.genotypeTransform(snp, "SNP");
        System.out.println(transformSNP);
    }

    @Test
    public void testQueryDNARunByCondition() throws IOException {
        Map<String, List<String>> result = dnaRunService.queryDNARunByCondition("[{\"name\":\"品种名PI 562565,品种名PI 339871A,品种名PI 393551,品种名ZJ-Y108,品种名ZJ-Y2300-1,品种名PI 366121,品种名PI 593983\",\"id\":1511837787324},{\"name\":\"品种名PI 562565,品种名PI 339871A,品种名PI 393551,品种名ZJ-Y108,品种名ZJ-Y2300-1,品种名PI 366121,品种名PI 59398\",\"id\":1511837787324,\"condition\":{\"cultivar\":\"PI 562565,PI 339871A,PI 393551,ZJ-Y108,ZJ-Y2300-1,PI 366121,PI 59398,?F1\"}}]");
        System.err.println(result);
    }

    @Test
    public void testGetListByConditionWithTypeHandler(){
        PageInfo<SampleInfoDto> list = dnaRunService.getListByConditionWithTypeHandler(new SampleInfoDto(), 1, 10, "1");
        assertNotNull(list);
        for (SampleInfoDto result : list.getList()){
        }
    }
}
