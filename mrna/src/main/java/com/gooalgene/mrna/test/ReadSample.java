package com.gooalgene.mrna.test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by ShiYun on 2017/7/26 0026.
 */
public class ReadSample {

    static String path = "E:\\古奥科技资料\\mRNA\\03.数据库数据及数据说明\\soybean_RNA";

    static String path1 = "E:\\古奥科技资料\\mRNA";

    /**
     * 差异基因详情页
     *
     * @param file2
     */
    public static void show(String file2) {
        String classifiy1 = path + "\\组织分类\\1级";
        String classifiy2 = path + "\\组织分类\\2级";
        String classifiy3 = path + "\\组织分类\\3级";
        Map m1 = ReadFile.showDir(classifiy1);
        Map m2 = ReadFile.showDir(classifiy2);
        Map m3 = ReadFile.showDir(classifiy3);
        for (Object s : m1.keySet()) {
            List<String> list = (List<String>) m1.get(s);
            for (int i = 0; i < list.size(); i++) {
                String cl = list.get(i).toUpperCase();
                if (!m2.containsKey(cl)) {
                    m2.put(cl, new ArrayList<String>());
                }
            }
        }
        for (Object s : m2.keySet()) {
            List<String> list = (List<String>) m2.get(s);
            for (int i = 0; i < list.size(); i++) {
                String cl = list.get(i).toUpperCase();
                if (!m3.containsKey(cl)) {
                    m3.put(cl, new ArrayList<String>());
                }
            }
        }
        System.out.println("m1 size:" + m1.size() + ",m2 size:" + m2.size() + ",m3 size:" + m3.size());
        List<String> list = null;
        try {
            list = FileUtils.readLines(new File(file2));
            int num = 0;
            int use = 0;
            int numm = 0;
            Set<String> set = new HashSet<String>();
            for (String s : list) {
                String[] ss = s.split("\t");
                if (ss.length == 29) {
                    use++;
                    for (int i = 0; i < ss.length; i++) {
                        System.out.println(i + "\t" + ss[i]);
                    }
                    String Run = ss[0];//测序数据检索号
                    String SampleName = ss[1];//样本名称
                    String Tissue = ss[2].toUpperCase();//组织
                    String Stage = ss[5];//生长阶段
                    String Treat = ss[4];//处理方式
                    String Genetype = ss[6];//基因型
                    String Phenotype = ss[7];//表型
                    String Environment = ss[8];//生长环境
                    String Culitvar = ss[13];//品种
                    String SRAStudy = ss[26];//研究课题信息检索号
//                  System.out.println(Run + "|" + SampleName + "|" + Tissue + "|" + Stage + "|" + Treat + "|" + Genetype + "|" + Phenotype + "|" + Environment + "|" + Culitvar);
                    String type = null;
                    if (m1.containsKey(Tissue)) {
                        type = "1";
                    } else if (m2.containsKey(Tissue)) {
                        type = "2";
                    } else if (m3.containsKey(Tissue)) {
                        type = "3";
                    } else {
                        type = "4";
                    }
                    set.add(SRAStudy);
                    if (type == "4") {
//                        System.out.println(Tissue + "|" + Run + "|" + type + "|" + SampleName + "|" + Stage + "|" + Treat + "|" + Genetype + "|" + Phenotype + "|" + Environment + "|" + Culitvar);
                    } else {
                        if (Tissue.equals("LEAF")) {
                            System.out.println(Tissue + "|" + Run + "|" + type + "|" + SampleName + "|" + Stage + "|" + Treat + "|" + Genetype + "|" + Phenotype + "|" + Environment + "|" + Culitvar + "|" + SRAStudy);
                        }
                        numm++;
                    }
                } else {
                    num++;
                }
                break;
            }
            System.out.println("size:" + list.size() + "num:" + num + ",use:" + use + ",numm:" + numm + ",n:" + (use - numm) + ",set size:" + set.size());
            int a = 1;
            for (String s : set) {
                System.out.println(a + ":" + s);
                a++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 差异基因详情页
     *
     * @param file2
     */
    public static void show2(String file2, int cloNum) {
        List<String> list = null;
        try {
            list = FileUtils.readLines(new File(file2));
            int num = 0;
            int use = 0;
            String[] title = list.get(0).split("\t");
            System.out.println("title size:" + title.length);
            Set<String> set = new HashSet<String>();
            int total = list.size();
            int j = cloNum;
            for (int i = 1; i < total; i++) {
                String s = list.get(i);
                String[] ss = s.split("\t");
                if (ss.length == 29) {
                    String content = ss[j];
                    set.add(content);
                    System.out.println(title[j] + ":" + content);
                    use++;
                } else {
                    num++;
                }
//                break;
            }
            System.out.println("size:" + list.size() + "num:" + num + ",use:" + use + ",set size:" + set.size());
            int a = 1;
            StringBuffer stringBuffer = new StringBuffer();
            for (String s : set) {
                System.out.println(a + ":" + s);
                stringBuffer.append(s).append("\n");
                a++;
            }
            FileUtils.write(new File("E:/古奥科技资料/mRNA/data_new/" + title[j] + ".txt"), stringBuffer.toString(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show1(String file3) {
        List<String> list = null;
        try {
            list = FileUtils.readLines(new File(file3));
            int num = 0;
            int use = 0;
            for (String s : list) {
                String[] ss = s.split("\t");
                if (ss.length == 29) {
                    use++;
//                    for (int i = 0; i < ss.length; i++) {
//                        System.out.println(i + "\t" + ss[i]);
//                    }
                    String SampleName = ss[1];//样本名称
                    String Study = ss[17];//研究课题
                    String Reference = ss[16];//参考文献---paper
                    String Tissue = ss[2];//组织
                    String Stage = ss[5];//生长阶段
                    String Treat = ss[4];//处理方式
                    String Genetype = ss[6];//基因型
                    String Phenotype = ss[7];//表型
                    String Environment = ss[8];//生长环境
                    String Culitvar = ss[13];//品种
                    String ScientificName = ss[14];//拉丁文名称
                    String Institution = ss[18];//研究机构
                    String Run = ss[0];//测序数据检索号
                    String SRAStudy = ss[26];//研究课题信息检索号
                    String Experiment = ss[28];//实验信息检索号
                    String LibraryLayout = ss[23];//建库方式

                    String Preservation = ss[3];
                    String Sports = ss[27];
                    System.out.println(Tissue + "|" + Run);
                } else {
                    num++;
                }
                break;
            }
            System.out.println("size:" + list.size() + "num:" + num + ",use:" + use);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        String file2 = path + "\\soybeanRNA数据结果页总表格_原组织.txt";
        String file3 = path + "\\soybeanRNA数据结果页总表格_组织新.txt";

        String file4 = path1 + "\\soybeanRNA数据结果页总表格_原组织.txt";
        String file5 = path1 + "\\soybeanRNA数据结果页总表格_组织新.txt";
//      show(file3);
        for (int i = 0; i < 29; i++) {
            show2(file4, i);
        }
    }
}
