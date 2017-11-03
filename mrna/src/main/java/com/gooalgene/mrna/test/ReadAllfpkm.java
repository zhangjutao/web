package com.gooalgene.mrna.test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ShiYun on 2017/7/25 0025.
 */
public class ReadAllfpkm {

    static String path = "E:\\古奥科技资料\\mRNA\\03.数据库数据及数据说明\\soybean_RNA";

    public static void main(String[] args) {
        String file = path + "\\all.fpkm.txt";
        try {
            List<String> strings = FileUtils.readLines(new File(file));
            System.out.println("Total:" + strings.size());
            int num = 0;
            for (String s : strings) {
                num++;
                String[] ss = s.split("\t");
                System.out.println(ss.length + "\t" + s);
                if (num == 5) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
