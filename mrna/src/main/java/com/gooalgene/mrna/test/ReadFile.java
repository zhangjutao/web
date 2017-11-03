package com.gooalgene.mrna.test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ShiYun on 2017/7/25 0025.
 */
public class ReadFile {

    static String path = "E:\\古奥科技资料\\mRNA\\新数据\\组织分类_修改版";

    public static Map<String, List<String>> showDir(String dir) {
        File file = new File(dir);
//        System.out.println("===========" + dir + "==================");
        Map<String, List<String>> m = new HashMap<String, List<String>>();
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String fileName = f.getName();
                try {
                    String classify = fileName.replace(".txt", "");
                    List<String> strings = FileUtils.readLines(f);
//                  System.out.println(classify + ":" + strings.size() + "\t");
                    m.put(classify, strings);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        System.out.println("===========" + dir + "==================");
        return m;
    }

    public static void showMap(Map map) {
        System.out.println("---------------------------");
        for (Object s : map.keySet()) {
            System.out.println(s + "\t" + map.get(s));
        }
    }

    public static void main(String[] args) {
        String classifiy1 = path + "\\一级";
        String classifiy2 = path + "\\二级";
        Map m1 = showDir(classifiy1);
        showMap(m1);
        Map m2 = showDir(classifiy2);
        for (Object s : m1.keySet()) {
            List<String> list = (List<String>) m1.get(s);
            for (int i = 0; i < list.size(); i++) {
                String cl = list.get(i);
                if (!m2.containsKey(cl)) {
                    m2.put(cl, new ArrayList<String>());
                }
            }
        }
        showMap(m2);
        System.out.println("m1 size:" + m1.size() + ",m2 size:" + m2.size());
    }
}
