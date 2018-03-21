package com.gooalgene.dna.jackson;

import com.gooalgene.dna.entity.SampleInfo;
import com.gooalgene.utils.FrontEndReflectionUtils;
import junit.framework.TestCase;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by crabime on 3/18/18.
 */
public class SampleInfoReflectionTest extends TestCase {

    @Test
    public void testSampleInfo() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("name", "123456");
        conditions.put("value", "science");
        conditions.put("deserve", "BeiJing");
        TestSample sample = FrontEndReflectionUtils.constructNewInstance("com.gooalgene.dna.jackson.TestSample", conditions);
        System.out.println(sample.getName());
        System.out.println(sample.getDeserveLocation());
    }

    @Test
    public void testReflection() {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("runNo", "123456");
        conditions.put("scientificName", "science");
        conditions.put("randomProperty", "x24fa");
        SampleInfo sampleInfo = FrontEndReflectionUtils.constructNewInstance("com.gooalgene.dna.entity.SampleInfo", conditions);
        System.out.println(sampleInfo.getScientificName());
    }

}
