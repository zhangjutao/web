package com.gooalgene.dna.service;

import com.gooalgene.common.authority.Token;
import com.gooalgene.common.service.TokenService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:spring-context.xml"))
public class TokenServiceTest extends TestCase {

    @Autowired
    private TokenService tokenService;

    @Test
    public void testFindTokenById(){
        Token token = tokenService.getTokenByUserId(44);
        String encryptValue = token.getToken();
//        assertNull(token.getDue_time()); //这个地方为什么取出来的时间为空?
        System.out.println(token.getDue_time());
    }
}
