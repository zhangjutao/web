package com.gooalgene.dna.service;

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


/**
 * Created by liuyan on 2017/11/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:spring-mvc.xml","classpath:spring-security.xml"}))
public class ExportDataTest {
    @Autowired
    private WebApplicationContext applicationContext;
    MockMvc mockMvc;

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
        MvcResult mvcResult = mockMvc.perform(get("/dna/dataExport").contentType(MediaType.APPLICATION_JSON)
                        .param("model","SAMPLES")
                        .param("choices","species,locality,sampleName,cultivar,weightPer100seeds,oil,protein,floweringDate,maturityDate,height,seedCoatColor,hilumColor,cotyledonColor,flowerColor,podColor,pubescenceColor,yield,upperLeafletLength,linoleic,linolenic,oleic,palmitic,stearic")
                        .param("flag","group")
                        .param("total","50")
                        //.param("cultivar","PI 339871A,PI 393551,ZJ-Y108,ZJ-Y2300-1,PI 366121")
                        .param("group","{\"name\":\"物种Glycine gracilis\",\"id\":2,\"condition\":{\"species\":\"Glycine gracilis\"}}")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

}
