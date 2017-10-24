package com.gooalgene.mrna.test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/08/31.
 */
public class Test {

    public static void main(String[] args) {
        String[] s1 = new String[]{"123aerf", "abc", "def", "756"};
        List<String> list = Arrays.asList(s1);

        String fileName = "C:\\Users\\Administrator\\Desktop\\123.txt";
        String fileName1 = "C:\\Users\\Administrator\\Desktop\\SRP1777405\\SRP1777405.txt";
        try {
//            FileUtils.writeLines(new File(fileName), list, "\n", false);
            List<String> list1 = FileUtils.readLines(new File(fileName1));
            for (String line : list1) {
                String[] ss = line.split("\t");
                System.out.println(ss[0] + "\t" + ss[1]);
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
