package com.gooalgene.dna.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

public class JacksonTest2 extends TestCase{
    @Test
    public void testMapJackson(){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("exists", true);
        try {
            String result = mapper.writeValueAsString(map);
            JsonPathResultMatchers jsonPath = MockMvcResultMatchers.jsonPath("$.exists", true);
            ResultMatcher value = jsonPath.value(true);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
