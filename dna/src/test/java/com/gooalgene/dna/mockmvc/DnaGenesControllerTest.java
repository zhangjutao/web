package com.gooalgene.dna.mockmvc;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:spring-mvc.xml"}))
public class DnaGenesControllerTest extends TestCase {

    @Autowired
    private WebApplicationContext webApplicationContext;
    MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testListPage() throws Exception {
        mockMvc.perform(get("/dnagens/list"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/views/dnagens/dnagens.jsp"))
                .andReturn();
    }
}
