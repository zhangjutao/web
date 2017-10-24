package com.gooalgene.qtl.web;

import com.gooalgene.common.dao.StudyDao;
import com.gooalgene.qtl.dao.QtlrefDao;
import com.gooalgene.entity.Chrlg;
import com.gooalgene.entity.Qtlref;
import com.gooalgene.qtl.service.ChangeService;
import com.gooalgene.qtl.service.ChrlgService;
import com.gooalgene.qtl.service.GenesService;
import com.gooalgene.qtl.service.QueryService;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 此控制器主要用于数据库初始化处理各类数据 -=====用完之后处于注释状态
 * Created by ShiYun on 2017/7/10 0010.
 */
@Controller
@RequestMapping("/change")
public class ChangeController {

    Logger logger = LoggerFactory.getLogger(ChangeController.class);

    @Autowired
    private ChangeService changeService;

    @Autowired
    private QueryService queryService;

    @Autowired
    private ChrlgService chrlgService;

    @Autowired
    private QtlrefDao qtlrefDao;

    @Autowired
    private StudyDao studyDao;

    //    @RequestMapping("/insertAssociatedGenes")
//    @ResponseBody
    public String trait(HttpServletRequest request) {
        logger.info("insert chrlg list.");
        changeService.insertChrlg_Length();
        logger.info("insert MarkerPosion list.");
//        changeService.insertMarkerPosition();
        logger.info("insert AssociatedGenes list.");
//        changeService.insertAssocitedgenes();
        return "insert success.";
    }

    //    @RequestMapping("/insertQTL")
//    @ResponseBody
    public String insertQTL(HttpServletRequest request) {
        logger.info("insert qtl list.");
//        changeService.insertQTL();
        return "insert success.";
    }

    //    @RequestMapping("/insertTrait")
//    @ResponseBody
    public String insertTrait(HttpServletRequest request) {
        logger.info("insert insertTrait list.");
        changeService.insertTraitList();
        return "insert success.";
    }

    //    @RequestMapping("/updateSoybean")
//    @ResponseBody
    public String updateSoybean(HttpServletRequest request) {
        logger.info("update soybean list.");
        changeService.updateSoybean();
        return "insert success.";
    }


    //    @RequestMapping("/updateMarker")
    @ResponseBody
    public String updateMarker(HttpServletRequest request) {
        logger.info("update marker list.");
        changeService.updateMarker();
        return "insert success.";
    }

    //    @RequestMapping("/foundMarkersNoInQtl")
    @ResponseBody
    public String getMarkerNoFoundInQtls(HttpServletRequest request) {
        logger.info("foundMarkersNoInQtl marker list.");
        Chrlg chrlg = new Chrlg();
        chrlg.setVersion("Gmax_275_v2.0");
        List<Chrlg> chrlgs = chrlgService.findAList(chrlg);
        for (Chrlg chrlg1 : chrlgs) {
            String chr = chrlg1.getChr();
            String lg = chrlg1.getLg();
            String version = chrlg1.getVersion();
            String tmp = null;
            if (chr.startsWith("Chr0")) {
                tmp = chr.substring(4);
            } else {
                tmp = chr.substring(3);
            }
            String a = lg + "(" + tmp + ")";
            System.out.println(chr + "\t" + a + "\t" + version);
            String fileName = lg + "_" + chr + "_" + version + ".txt";
            queryByLg(fileName, lg, a, chr, version);
        }
//        String fileName = "C1_Chr04_Gmax_275_v2.0.txt";
//        List<String> list = queryService.getMarkersFromQtlByLg("C1");
//        System.out.println(list);
//        StringBuffer sb = new StringBuffer();
//        sb.append("qtl中出现的marker:");
//        for (String str : list) {
//            sb.append(str).append(",");
//        }
//        try {
//            FileUtils.write(new File("E:/data/" + fileName), sb.toString() + "\n", true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        JSONArray right = queryService.getMarkerRightDataNotIn("C1(4)");
//        System.out.println(right);
//        try {
//            FileUtils.write(new File("E:/data/" + fileName), "遗传图忽略的marker：" + right.toString() + "\n", true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        JSONArray left = queryService.getMarkerLeftDataNotIn("Chr04", "Gmax_275_v2.0");
//        System.out.println(left);
//        try {
//            FileUtils.write(new File("E:/data/" + fileName), "物理图忽略的marker：" + left.toString() + "\n", true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "insert success.";
    }

    private void queryByLg(String fileName, String lg, String lg2, String chr, String version) {
        List<String> list = queryService.getMarkersFromQtlByLg(lg);
        System.out.println(list);
        StringBuffer sb = new StringBuffer();
        sb.append("qtl中出现的marker:");
        for (String str : list) {
            sb.append(str).append(",");
        }
        try {
            FileUtils.write(new File("E:/data/" + fileName), sb.toString() + "\n", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray right = queryService.getMarkerRightDataNotIn(lg2);
        System.out.println(right);
        try {
            FileUtils.write(new File("E:/data/" + fileName), "遗传图忽略的marker：" + right.toString() + "\n", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray left = queryService.getMarkerLeftDataNotIn(chr, version);
        System.out.println(left);
        try {
            FileUtils.write(new File("E:/data/" + fileName), "物理图忽略的marker：" + left.toString() + "\n", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    @RequestMapping("/checkref")
    @ResponseBody
    public String checkRef(HttpServletRequest request) {
        logger.info("update marker list.");
        String path8 = "E:\\古奥科技资料\\数据+说明\\ref";
        File file = new File(path8);
        int num = 0;
        List<Qtlref> list = new ArrayList<Qtlref>();
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String qtl_name = f.getName().replace(".csv", "");
                String path = f.getAbsolutePath();
//              System.out.println(path + "\t" + m.size() + "\t" + m.get("Title") + "\t" + qtl_name);
                Qtlref qtlref = qtlrefDao.get(qtl_name);
                if (qtlref != null) {
                    System.out.println("exist " + qtl_name);
                } else {
                    num++;
                    System.out.println("no " + qtl_name);
                    qtlref = new Qtlref();
                    qtlref.setQtlName(qtl_name);
                    Map map = getContent(path);
                    String title = (String) map.get("Title");
                    if (title != null) {
                        qtlref.setTitle(title);
                    }
                    String author = (String) map.get("Authors");
                    if (author != null) {
                        qtlref.setAuthors(author);
                    }
                    String source = (String) map.get("Source");
                    if (source != null) {
                        qtlref.setSource(source);
                    }
                    String abs = (String) map.get("Abstract");
                    if (abs != null) {
                        qtlref.setSummary(abs);
                    }
                    String pub = (String) map.get("Pubmed");
                    if (pub != null) {
                        qtlref.setPubmed(pub);
                    }
                    list.add(qtlref);
                }
            }
            System.out.println("num:" + files.length);
        }
        if (!list.isEmpty()) {
            System.out.println(list.size());
            qtlrefDao.insertBatch(list);
        }
        return "insert " + num + " success.";
    }

    public static Map<String, String> getContent(String path) {
        Map<String, String> map = new HashMap<String, String>();
        List<String> list = null;
        try {
            list = FileUtils.readLines(new File(path));
            for (String line : list) {
                String[] strings = line.split("\t");
                if (strings.length >= 2) {
                    map.put(strings[0].replace(":", ""), strings[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> StudyAndLinks(String file) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List<String> result = FileUtils.readLines(new File(file));
            int total = result.size();
            int num = 0;
            for (int i = 0; i < total; i++) {
                String[] ss = result.get(i).split("\t");
                if (ss.length == 2) {
                    String study = ss[0];
                    String links = ss[1];
                    map.put(study, links);
                }
            }
            System.out.println("Total:" + result.size() + ",num:" + num + ",map size:" + map.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Autowired
    private GenesService genesService;

}
