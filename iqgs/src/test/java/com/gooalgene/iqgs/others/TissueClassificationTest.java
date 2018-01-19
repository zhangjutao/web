package com.gooalgene.iqgs.others;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.iqgs.entity.Tissue;
import com.gooalgene.mrna.entity.Classifys;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by crabime on 1/18/18.
 * 传入任意一个组织,判断该组织是否需要查询root组织fpkm值
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class TissueClassificationTest {

    private Stack<String> podSet;
    private Stack<String> seedSet;
    private Stack<String> rootSet;
    private Stack<String> shootSet;
    private Stack<String> leafSet;
    private Stack<String> seedlingSet;
    private Stack<String> flowerSet;
    private Stack<String> stemSet;
    private Set<String> filterRootSet;  //过滤后应该要拿一级组织FPKM值的集合

    private Tissue tissue;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setUp(){
        filterRootSet = new HashSet<>();
        List<Classifys> classifyCollection = mongoTemplate.findAll(Classifys.class, "classifys");
        try {
            String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(classifyCollection);
            int len = JsonPath.read(result, "$.length()");
            for (int i = 0; i < len; i++){
                List<String> allHierarchyName = JsonPath.read(result, "$[" + i + "]..name");
                String firstHierarchyName = allHierarchyName.get(0);
                switch (firstHierarchyName){
                    case "pod_All":
                        podSet = new Stack<>();
                        podSet.addAll(allHierarchyName);
                        break;
                    case "seed_All":
                        seedSet = new Stack<>();
                        seedSet.addAll(allHierarchyName);
                        break;
                    case "root_All":
                        rootSet = new Stack<>();
                        rootSet.addAll(allHierarchyName);
                        break;
                    case "shoot_All":
                        shootSet = new Stack<>();
                        shootSet.addAll(allHierarchyName);
                        break;
                    case "leaf_All":
                        leafSet = new Stack<>();
                        leafSet.addAll(allHierarchyName);
                        break;
                    case "seedling_All":
                        seedlingSet = new Stack<>();
                        seedlingSet.addAll(allHierarchyName);
                        break;
                    case "flower_All":
                        flowerSet = new Stack<>();
                        flowerSet.addAll(allHierarchyName);
                        break;
                    case "stem_All":
                        stemSet = new Stack<>();
                        stemSet.addAll(allHierarchyName);
                        break;
                    default:
                        System.out.println("无法匹配");
                        break;
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        tissue = new Tissue();
        tissue.setPod(31d);
        tissue.setAxis(28d);
    }

    @Test
    public void testReflectTissue(){
        Class<? extends Tissue> tissueClass = tissue.getClass();
        Field[] tissueClassFields = tissueClass.getDeclaredFields();  //拿到所有tissue的property
        for (int i = 0; i < tissueClassFields.length; i++){
            String fieldName = tissueClassFields[i].getName();
            try {
                Field field = tissueClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value =  field.get(tissue);
                if (value != null){
                    if (podSet.contains(fieldName)){
                        podSet.remove(fieldName);  //当该set最后只剩下一个root组织时,该组织的一级组织就要求fpkm值了
                    }
                    if (seedSet.contains(fieldName)){
                        seedSet.remove(fieldName);
                    }
                    if (rootSet.contains(fieldName)){
                        rootSet.remove(fieldName);
                    }
                    if (shootSet.contains(fieldName)){
                        shootSet.remove(fieldName);
                    }
                    if (leafSet.contains(fieldName)){
                        leafSet.remove(fieldName);
                    }
                    if (seedlingSet.contains(fieldName)){
                        seedlingSet.remove(fieldName);
                    }
                    if (flowerSet.contains(fieldName)){
                        flowerSet.remove(fieldName);
                    }
                    if (stemSet.contains(fieldName)){
                        stemSet.remove(fieldName);
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (podSet.size() == 1){
            filterRootSet.add(podSet.firstElement());
        }
        if (seedSet.size() == 1){
            filterRootSet.add(seedSet.firstElement());
        }
        if (rootSet.size() == 1){
            filterRootSet.add(rootSet.firstElement());
        }
        if (shootSet.size() == 1){
            filterRootSet.add(shootSet.firstElement());
        }
        if (leafSet.size() == 1){
            filterRootSet.add(leafSet.firstElement());
        }
        if (seedlingSet.size() == 1){
            filterRootSet.add(seedlingSet.firstElement());
        }
        if (flowerSet.size() == 1){
            filterRootSet.add(flowerSet.firstElement());
        }
        if (stemSet.size() == 1){
            filterRootSet.add(stemSet.firstElement());
        }
        System.out.println(filterRootSet);
    }


    @Test
    public void testGetFieldValueThroughApache(){
        Field field = FieldUtils.getField(tissue.getClass(), "pod", true);
        try {
            System.out.println(field.get(tissue));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
