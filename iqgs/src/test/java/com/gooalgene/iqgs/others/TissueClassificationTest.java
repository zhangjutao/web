package com.gooalgene.iqgs.others;

import com.gooalgene.iqgs.entity.Tissue;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by crabime on 1/18/18.
 * 传入任意一个组织,判断该组织是否需要查询root组织fpkm值
 */
public class TissueClassificationTest {

    private Map<String, Set<String>> category;

    private Set<String> podSet = new HashSet<>();

    private Set<String> seedSet = new HashSet<>();

    private Tissue tissue;

    @Before
    public void setUp(){
        podSet.add("pod");
        //todo 使用jayway去读取classify中组织信息,分别放入set中
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
//            Class<?> fieldType = tissueClassFields[i].getType();
            try {
                Field field = tissueClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value =  field.get(tissue);
                if (value != null){
                    if (podSet.contains(fieldName)){
                        podSet.remove(fieldName);  //当该set最后只剩下一个root组织时,该组织的一级组织就要求fpkm值了
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
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
