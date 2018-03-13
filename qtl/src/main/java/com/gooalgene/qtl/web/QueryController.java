package com.gooalgene.qtl.web;

import com.gooalgene.common.Page;
import com.gooalgene.entity.Qtl;
import com.gooalgene.qtl.entity.QtlSearchResult;
import com.gooalgene.qtl.service.QueryService;
import com.gooalgene.utils.Tools;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 此控制器用于古奥基因搜索相关接口
 * Created by ShiYun on 2017/7/10 0010.
 */
@Controller
@RequestMapping("/query")
public class QueryController {

    Logger logger = LoggerFactory.getLogger(QueryController.class);

    @Autowired
    private QueryService queryService;

    @RequestMapping("/trait")
    @ResponseBody
    public String trait(HttpServletRequest request) {
        logger.info("query trait list.");
        return queryService.queryAll().toString();
    }


    @RequestMapping("/soybean")
    @ResponseBody
    public JSONObject soybean(HttpServletRequest request) {
        logger.info("query soybean list.");
        String name = request.getParameter("name");
        return queryService.queryBySoybeanName(name);
    }

    @RequestMapping("/chrVersion")
    @ResponseBody
    public String chrsAndVersion(HttpServletRequest request) {
        logger.info("query trait list.");
        return queryService.queryChrAndVersion().toString();
    }

    @RequestMapping("/searchbyKeywords")
    @ResponseBody
    public String qtlSearchbyKeywords(HttpServletRequest request, HttpServletResponse response) {
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索，
        String type = request.getParameter("type");
        String keywords = request.getParameter("keywords");//首页查询转跳到搜索结果页需要一个参数param()
        String version = request.getParameter("version");
        Page<Qtl> page = new Page<Qtl>(request, response);
        return queryService.qtlSearchbyKeywords(version, type, keywords, page).toString();
    }

    @RequestMapping("/searchResultbyKeywords")
    @ResponseBody
    public String qtlSearchResultbyKeywords(HttpServletRequest request, HttpServletResponse response) {
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索，
        String type = request.getParameter("type");
        String keywords = request.getParameter("keywords");
        String parameters = request.getParameter("condition");
        String version = request.getParameter("version");
        Page<Qtl> page = new Page<Qtl>(request, response);
        return queryService.qtlSearchByResult(version, type, keywords, parameters, page.getPageNo(),page.getPageSize(), null).toString();
    }

    @RequestMapping("/dataExport")
    @ResponseBody
    public void qtlSearchResultExport(HttpServletRequest request, HttpServletResponse response) {
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索，
        String type = request.getParameter("type");
        String keywords = request.getParameter("keywords");
        String parameters = request.getParameter("condition");
        String version = request.getParameter("version");
        String columns = request.getParameter("choices");
        logger.info("type:" + type + ",keywords:" + keywords + ",condition:" + parameters + ",colums:" + columns);
        try {
            String sb = null;
            if (StringUtils.isNoneBlank(columns)) {
                if (columns.contains("author")) {
//                    columns = columns.replace("author", "reference");
                }
                List<QtlSearchResult> result = queryService.downloadQtlSearchResult(version, type, keywords, parameters, columns);
                sb = serialList(result, columns.split(","));
            }
            String fileName = "SoyBean-" + version + "-" + type + "(" + keywords + ")";
            System.out.println(fileName);
            if (sb != null) {
                byte[] buffer = sb.getBytes("gbk");
                // 清空response
                response.reset();
                // 设置response的Header
                String filename = java.net.URLEncoder.encode(fileName, "UTF-8") + ".csv";
                System.out.println("f:" + filename);
                filename = filename.replace("+", "%20");//空格会被转义为+
                response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                response.addHeader("Content-Length", "" + buffer.length);
                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("text/csv");
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 转换第一行和页面显示的一样
     *
     * @return
     */
    private Map<String, String> changeColumn2Web() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "ID");
        map.put("qtlName", "QTL Name");
        map.put("trait", "Trait");
        map.put("type", "Type");
        map.put("chr", "Chr");
        map.put("lg", "LG");
        map.put("method", "Method");
        map.put("marker1", "Marker1");
        map.put("marker2", "Marker2");
        map.put("genesNum", "Genes");
        map.put("lod", "LOD");
        map.put("parent1", "Parent1");
        map.put("parent2", "Parent2");
        map.put("genomeStart", "Genome start");
        map.put("genomeEnd", "Genome end");
        map.put("reference", "Reference");
        map.put("author", "Reference");
        return map;
    }

    /**
     * 页面传列值: id,qtlName,trait,type,chr,lg,method,marker1,marker2,genesNum,lod,parent1,parent2,genomeStart,genomeEnd,author
     *
     * @param result
     * @param titles
     * @return
     */
    private String serialList(List<QtlSearchResult> result, String[] titles) {
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new HashMap<String, Integer>();
        Map<String, String> titlesMap = changeColumn2Web();
        if (titles != null) {
            sb = new StringBuilder();
            int len = titles.length;
            for (int i = 0; i < len; i++) {
                String title = titles[i];
                sb.append(titlesMap.get(title));
                if (i != (len - 1)) {
                    sb.append("\t");
                } else {
                    sb.append("\n");
                }
                map.put(title, i);
            }
        }
        if (!result.isEmpty() && !map.isEmpty()) {
            Iterator<QtlSearchResult> it = result.iterator();
            while (it.hasNext()) {
                QtlSearchResult data = it.next();
                if (map.containsKey("id")) {
                    sb.append((data.getId())).append("\t");
                }
                if (map.containsKey("qtlName")) {
                    sb.append((data.getQtlName() == null ? "" : data.getQtlName())).append("\t");
                }
                if (map.containsKey("trait")) {
                    sb.append((data.getTrait() == null ? "" : data.getTrait())).append("\t");
                }
                if (map.containsKey("type")) {
                    sb.append((data.getType() == null ? "" : data.getType())).append("\t");
                }
                if (map.containsKey("chr")) {
                    sb.append((data.getChr() == null ? "" : data.getChr())).append("\t");
                }
                if (map.containsKey("lg")) {
                    sb.append((data.getLg() == null ? "" : data.getLg())).append("\t");
                }
                if (map.containsKey("version")) {
                    sb.append((data.getVersion() == null ? "" : data.getVersion())).append("\t");
                }
                if (map.containsKey("method")) {
                    sb.append((data.getMethod() == null ? "" : data.getMethod())).append("\t");
                }
                if (map.containsKey("marker1")) {
                    sb.append((data.getMarker1() == null ? "" : data.getMarker1())).append("\t");
                }
                if (map.containsKey("marker2")) {
                    sb.append((data.getMarker2() == null ? "" : data.getMarker2())).append("\t");
                }
                if (map.containsKey("genesNum")) {
                    Integer gensNum = data.getGenesNum();
                    sb.append(gensNum).append("\t");
                }
                if (map.containsKey("lod")) {
                    sb.append((data.getLod() == null ? "" : data.getLod())).append("\t");
                }
                if (map.containsKey("parent1")) {
                    sb.append((data.getParent1() == null ? "" : data.getParent1())).append("\t");
                }
                if (map.containsKey("parent2")) {
                    sb.append((data.getParent2() == null ? "" : data.getParent2())).append("\t");
                }
                if (map.containsKey("genomeStart")) {
                    sb.append((data.getGeneStart() == null ? "" : data.getGeneStart())).append("\t");
                }
                if (map.containsKey("genomeEnd")) {
                    sb.append((data.getGeneEnd() == null ? "" : data.getGeneEnd())).append("\t");
                }
                if (map.containsKey("reference")) {
                    sb.append((data.getRef() == null ? "" : data.getRef()));
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @RequestMapping("/qtlDetail")
    @ResponseBody
    public String qtlDetail(HttpServletRequest request) {
        logger.info("query qtlDetail list.");
        String qtl_name = request.getParameter("name");
        String qtl_version = request.getParameter("version");
        return queryService.qtlDetailByName(qtl_name, qtl_version).toString();
    }

    @RequestMapping("/reference")
    @ResponseBody
    public String referenceInfomation(HttpServletRequest request) {
        logger.info("query reference list.");
        String qtl_name = request.getParameter("qtlName");
        JSONObject content = queryService.getReferenceInfoByQtlName(qtl_name);
        content.put("qtlName", qtl_name);
        return content.toString();
    }


    @RequestMapping("/marker")
    @ResponseBody
    public String marker(HttpServletRequest request) {
        logger.info("query marker list.");
        String marker_name = request.getParameter("markerName");
        return queryService.getMarkerByName(marker_name).toString();
    }

}
