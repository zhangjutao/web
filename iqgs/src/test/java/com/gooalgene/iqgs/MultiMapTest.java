package com.gooalgene.iqgs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.*;

public class MultiMapTest extends TestCase {

    @Test
    public void testMultiMap(){
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.<String, String>create();
        List<String> items = new ArrayList<>();
        items.add("seed");
        items.add("seed coat");

        items.add("embryo");
        multimap.putAll("seed", items);

        List<String> seeds = multimap.get("seed");
        assertEquals(3, seeds.size());
    }

    /**
     * 测试jackson是否可以序列化Multimap
     */
    @Test
    public void testWriteMultimapJson(){
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.<String, String>create();
        List<String> items = new ArrayList<>();
        items.add("seed");
        items.add("seed coat");

        items.add("embryo");
        multimap.putAll("seed", items);
        List<String> podItems = new ArrayList<>();
        podItems.add("seed");
        podItems.add("dogTail");
        multimap.putAll("pod", podItems);
        //将multimap转换为普通map
        Map<String, Collection<String>> stringCollectionMap = multimap.asMap();

        //jackson转换普通map
        Map<String, String> normalMap = new HashMap<>();
        normalMap.put("key", "value");
        normalMap.put("hence", "whether");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //序列化普通map
            String normalMapResult = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(normalMap);
            System.out.println(normalMapResult);
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(stringCollectionMap);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
