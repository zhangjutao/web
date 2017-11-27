package com.gooalgene.dna.mockmvc;

import com.gooalgene.common.Page;
import com.gooalgene.dna.entity.SNP;
import com.gooalgene.dna.service.DNAMongoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(locations ={"classpath:spring-context.xml", "classpath:spring-mvc.xml","classpath:spring-security.xml"} ))
public class WjyTest {

    @Autowired
    private WebApplicationContext applicationContext;
    MockMvc mockMvc;

    @Autowired
    private DNAMongoService dnaMongoService;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        TestingAuthenticationToken token = new TestingAuthenticationToken("crabime", "123456");
        ProviderManager providerManager = (ProviderManager)applicationContext.getBean("authenticationManager");
        List<AuthenticationProvider> list = new ArrayList<>();
        TestingAuthenticationProvider testingAuthenticationProvider = new TestingAuthenticationProvider();
        list.add(testingAuthenticationProvider);
        providerManager.setProviders(list);
        SecurityContextImpl securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(token);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testExportData() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/dna/searchIdAndPosInGene").contentType(MediaType.APPLICATION_JSON)
                .param("type", "SNP")
                .param("ctype", "all")
                .param("gene", "Glyma.20G087900")
                //.param("start", "0")
                //.param("end", "100000")
                //.param("pageNo", "1")
                //.param("pageSize", "10")
                //.param("group", "[{\"name\":\"品种名PI 562565\",\"id\":1511515552108,\"condition\":{\"cultivar\":\"PI 562565\"}},{\"name\":\"品种名PI 339871A\",\"id\":1511515552108,\"condition\":{\"cultivar\":\"PI 339871A\"}}]")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        ServletOutputStream outputStream = mvcResult.getResponse().getOutputStream();
        System.out.println(contentAsString);
    }

    @Test
    public void testMongo() throws Exception{
        //List<SNP> snps = dnaMongoService.searchIdAndPosInRegin("INDEL", "all", "Chr01", "0", "100000",null);
        List<SNP> snps = dnaMongoService.findDataByIndex("SNP","Chr01","GlyS001055310",1430,10,"0","55555");
        System.out.println(snps);
    }
}
