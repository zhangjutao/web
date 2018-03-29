package com.gooalgene.dna.mockmvc;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:spring-mvc.xml", "classpath:spring-security.xml"}))
public class DnaGenesControllerTest extends TestCase {

    @Autowired
    private WebApplicationContext webApplicationContext;
    MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        TestingAuthenticationToken token = new TestingAuthenticationToken("crabime", "123456");
        ProviderManager providerManager = (ProviderManager)webApplicationContext.getBean("authenticationManager");
        List<AuthenticationProvider> list = new ArrayList<>();
        TestingAuthenticationProvider testingAuthenticationProvider = new TestingAuthenticationProvider();
        list.add(testingAuthenticationProvider);
        providerManager.setProviders(list);
        SecurityContextImpl securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(token);
        SecurityContextHolder.setContext(securityContext);
    }
}
