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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:spring-mvc.xml"}))
public class SignUpServiceTest extends TestCase {
    @Autowired
    private WebApplicationContext webApplicationContext;
    MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSignUpUsernameExists() throws Exception{
        mockMvc.perform(get("/signup/nameexists")
                .param("username", "crabime"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exists").value(true))
                .andReturn();
    }

    @Test
    public void testForgetPasswordController() throws Exception {
        mockMvc.perform(post("/signup/forget")
                .param("username", "huyao")
                .param("email", "crabime@gmail.com"))
                .andExpect(status().isOk())
                .andReturn();
    }

    /**
     * 这里使用MockHttpServletRequest竟然返回的是空!
     * @throws Exception
     */
    @Test
    public void testGetContextPath() throws Exception {
        mockMvc.perform(get("/signup/getContextPath"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testChangeEnable()throws Exception{
        mockMvc.perform(post("/manager/change/enable")
                .param("id", "44"))
                .andExpect(status().isOk())
                .andDo(print())
                 .andReturn();
    }

    @Test
    public void testFoget()throws Exception{
        mockMvc.perform(post("/signup/forget")
               .param("username","test")
               .param("email","1415775989@qq.com"))
                .andExpect(status().isOk())
                .andDo(print());
    }



}
