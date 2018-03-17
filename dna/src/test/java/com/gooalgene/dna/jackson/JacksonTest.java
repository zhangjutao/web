package com.gooalgene.dna.jackson;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.dna.entity.result.GroupCondition;
import com.gooalgene.dna.util.JacksonUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JacksonTest {
    private JsonGenerator jsonGenerator = null;
    private ObjectMapper objectMapper = null;
    private AccountBean accountBean = null;

    @Before
    public void init(){
        accountBean = new AccountBean();
        accountBean.setAddress("湖北武汉");
        accountBean.setEmail("crabime@gmail.com");
        accountBean.setId(1);
        accountBean.setName("Crabime");
        objectMapper = new ObjectMapper();
        try {
            //输出到控制台
            jsonGenerator = objectMapper.getFactory().createGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Object转JSON
     */
    @Test
    public void testWriteEntityJSON(){
        try {
            System.out.println("jsonGenerator");
            jsonGenerator.writeObject(accountBean);
            System.out.println("\r\n=============");

            System.out.println("ObjectMapper");
            objectMapper.writeValue(System.out, accountBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWriteMapToJSON(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", accountBean.getName());
            map.put("account", accountBean);
            accountBean = new AccountBean();
            accountBean.setAddress("China-Wuhan");
            accountBean.setEmail("7422@qq.com");
            map.put("account2", accountBean);

            System.out.println("jsonGenerator");
            jsonGenerator.writeObject(map);
            System.out.println("\r\n=============");
            System.out.println("objectMapper");
            objectMapper.writeValue(System.out, map);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * JSON字符串转Object
     */
    @Test
    public void testJSONToObject(){
        try {
            String json = "{\"id\":1,\"name\":\"Crabime\",\"email\":\"crabime@gmail.com\",\"address\":\"湖北武汉\",\"birthday\":null}";
            AccountBean bean = objectMapper.readValue(json, AccountBean.class);
            System.out.println(bean.getName());
            System.out.println(bean.getAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGroupConditionJsonToBean() throws IOException {
        String groupCondition = "{" +
                "\t\"name\": \"Pleurotus tuoliensis\",\n" +
                "\t\"condition\": {\n" +
                "\t\t\"locality\": \"Beijing, China\",\n" +
                "\t\t\"strainName\": \"Zhongnongduanqi1hao\"\n" +
                "\t}\n" +
                "}";
        GroupCondition entity = objectMapper.readValue(groupCondition, GroupCondition.class);
        System.out.println(entity.getName());
    }

    @Test
    public void testInterceptStringWithoutProperty() throws IOException {
        String groupCondition = "{\n" +
                "    \"name\": \"物种名称Pleurotus tuoliensis\",\n" +
                "    \"condition\": {\n" +
                "      \"scientificName\": \"Pleurotus tuoliensis\"\n" +
                "    }\n" +
                "  }";
        GroupCondition entity = JacksonUtils.convertJsonToObject(groupCondition, GroupCondition.class);
        System.out.println(entity.getName());
    }

    @Test
    public void testJacksonToClassList() throws IOException {
        String jsonArray = "[\n" +
                "{\n" +
                "\t\"name\": \"Pleurotus tuoliensis\",\n" +
                "\t\"condition\": {\n" +
                "\t\t\"locality\": \"Beijing, China\",\n" +
                "\t\t\"strainName\": \"Zhongnongduanqi1hao\"\n" +
                "\t}\n" +
                "},\n" +
                "{\n" +
                "\t\"name\": \"Pleurotus tuoliensis\",\n" +
                "\t\"condition\": {\n" +
                "\t\t\"preservationLocation\": \"Jilin agricultural university\",\n" +
                "\t\t\"strainName\": \"Zhongnongduanqi1hao\"\n" +
                "\t}\n" +
                "}\n" +
                "]";
        List<GroupCondition> result = objectMapper.readValue(jsonArray, new TypeReference<List<GroupCondition>>() {});
        System.out.println(result.size()); // 2
        System.out.println(result.get(1).getCondition().get("preservationLocation"));
        List<GroupCondition> list = JacksonUtils.convertJsonToArray(jsonArray, GroupCondition.class);
        System.out.println("输出结果：" + list.get(0).getName());
    }

    @After
    public void destroy(){
        try {
            if (jsonGenerator != null) {
                jsonGenerator.flush();
            }
            if (!jsonGenerator.isClosed()){
                jsonGenerator.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
