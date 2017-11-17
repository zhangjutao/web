package com.gooalgene.dna.web;

import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.service.DNARunService;
import com.gooalgene.utils.ExcelExportSXXSSF;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import java.util.*;

/**
 * Created by liuyan on 2017/11/13
 *
 */

@RestController
public class ExportDataController {

    private final static Logger logger= LoggerFactory.getLogger(ExportDataController.class);
    @Autowired
    private DNARunService dnaRunService;

    private static List<String> dnaList=new ArrayList<String>();

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public String exportData(HttpServletRequest request) throws IOException {

        String choices=request.getParameter("titles");
        logger.info(choices);
        String titles=choices.substring(0, choices.length() - 1);
        String[] condition=titles.split(",");
        String fileName="";
        List<String> list=new ArrayList<>();
        if(condition.length>5){
            for (int i=0;i<5;i++){
                fileName+=condition[i]+"-";
            }
        }else {
            for(int j=0;j<condition.length;j++){
                fileName+=condition[j]+"-";
            }
        }

        for (int i=0;i<condition.length;i++){
            list.add(condition[i]);
            logger.info(condition[i]);
        }
        fileName+= UUID.randomUUID()+".csv";
        String filePath=request.getSession().getServletContext().getRealPath("/")+"tempFile\\";
        String csvStr="";
        List<DNARun> result=dnaRunService.getAll();

        //使用csv进行导出
        csvStr=createCsvStr(result,condition);
        File tempfile=new File(filePath+fileName);
        if (!tempfile.getParentFile().exists()){
                 if (!tempfile.getParentFile().mkdirs()){
                     return "文件目录创建失败";
                 }
        }

        FileOutputStream tempFile=new FileOutputStream(tempfile);
        tempFile.write(csvStr.getBytes("utf-8"));
        tempFile.flush();
        tempFile.close();
        String path=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/tempFile/"+fileName;
        logger.info(path);
        return path;
    }

    //调整表头显示
    private static Map<String, String> changeCloumn2Web() {
        Map<String, String> map = new HashMap<String, String>();
       // map.put("group", "Group");
       // map.put("run", "Run");
        map.put("species", "Species");
        map.put("sampleName", "Sample Name");
        map.put("cultivar", "Cultivar");
       // map.put("plantName", "Plant Name");
        map.put("locality", "Locality");
        map.put("protein", "Protein");
        map.put("oil", "Oil");
        map.put("linoleic", "Linoleic");
        map.put("linolenic", "Linolenic");
        map.put("oleic", "Oleic");
        map.put("palmitic", "Palmitic");
        map.put("stearic", "Stearic");
        map.put("height", "Height");
        map.put("flowerColor", "Flower Color");
        map.put("hilumColor", "Hilum Color");
        map.put("podColor",   "Pod Color");
        map.put("pubescenceColor", "Pubescence Color");
        map.put("seedCoatColor", "Seed Coat Color");
        map.put("cotyledonColor", "Cotyledon Color");
        map.put("weightPer100seeds", "Weight (g) per 100 Seeds");
        map.put("seedCoatColor", "Seed Coat Color");
        map.put("upperLeafletLength", "upper Leaflet Length");
        map.put("maturityDate", "Maturity Date");
        map.put("yield", "Yield");
        return map;
    }


    //将列表中的数据  生成csv的格式
    /**
     *@param  result 要导出的内容
     *@param  titles  表头
     */

    public static String  createCsvStr(List<DNARun> result,String[] titles){
        StringBuilder sb=new StringBuilder();
        Map<String,Integer> map=new HashedMap();
        Map<String,String> titleMap=changeCloumn2Web();
        int len=titles.length;
        for(int i=0;i<len;i++){
            String title=titles[i];
            sb.append(titleMap.get(title));
            if (i!=len-1){
                sb.append(",");
            }
            if (i==len-1){
                sb.append("\n");
            }
            map.put(titles[i],i);
        }

        int size=result.size();
        for(int j=0;j<size;j++){
            DNARun dnaRun=result.get(j);
            //品种名
            if(map.containsKey("cultivar")){
                String cultivar=dnaRun.getCultivar();
                dnaList.add(cultivar!=null?cultivar:"");
                sb.append(cultivar!=null?cultivar:" ").append(",");
            }

            //物种
            if(map.containsKey("species")){
                String species=dnaRun.getSpecies();
                dnaList.add(species!=null?species:"");
                sb.append(species!=null?species:" ").append(",");
            }

            //地理位置
            if(map.containsKey("locality")){
                String locality=dnaRun.getLocality();
                dnaList.add(locality != null ? locality : "");
                if(locality!=null&&locality.contains(",")){
                    locality=locality.replaceAll(",",".");
                }
                sb.append(locality!=null?locality:" ").append(",");
            }

            //样本名
            if(map.containsKey("sampleName")){
                String sampleName=dnaRun.getSampleName();
                dnaList.add(sampleName!=null?sampleName:"");
                sb.append(sampleName!=null?sampleName:" ").append(",");
            }
            //百粒重
            if (map.containsKey("weightPer100seeds")){
                float weight=dnaRun.getWeightPer100seeds();
                dnaList.add(String.valueOf(weight)!=null?String.valueOf(weight):"");
                sb.append(weight!=0?String.valueOf(weight):" ").append(",");
            }
            //蛋白质含量
            if(map.containsKey("protein")){
                float protein=dnaRun.getProtein();
                dnaList.add(String.valueOf(protein)!=null?String.valueOf(protein):"");
                sb.append(protein!=0?String.valueOf(protein):"0").append(",");
            }
            //含油量
            if(map.containsKey("oil")){
                float oil=dnaRun.getOil();
                dnaList.add(String.valueOf(oil)!=null?String.valueOf(oil):"");
                sb.append(oil!=0?String.valueOf(oil):"0").append(",");
            }
            //成熟期
            if (map.containsKey("maturityDate")){
                String maturityGroup=dnaRun.getMaturityDate();
                dnaList.add(String.valueOf(maturityGroup)!=null?String.valueOf(maturityGroup):"");
                sb.append(maturityGroup!=null?maturityGroup:"").append(",");
            }
            //株高
            if (map.containsKey("height")){
                float height=dnaRun.getHeight();
                dnaList.add(String.valueOf(height)!=null?String.valueOf(height):"");
                sb.append(height!=0?String.valueOf(height):"").append(",");
            }


            //种皮色
            if (map.containsKey("seedCoatColor")){
                String seedCoatColor=dnaRun.getSeedCoatColor();
                dnaList.add(seedCoatColor!=null?seedCoatColor:"");
                sb.append(seedCoatColor!=null?seedCoatColor:"").append(",");
            }
            //种脐色
            if (map.containsKey("hilumColor")){
                String hilumColor=dnaRun.getHilumColor();
                dnaList.add(hilumColor!=null?hilumColor:"");
                sb.append(hilumColor!=null?hilumColor:"").append(",");
            }
            //子叶色
            if (map.containsKey("cotyledonColor")){
                String cotyledonColor=dnaRun.getCotyledonColor();
                dnaList.add(cotyledonColor!=null?cotyledonColor:"");
                sb.append(cotyledonColor!=null?cotyledonColor:"").append(",");
            }

            //花色
            if (map.containsKey("flowerColor")){
                String flowerColor= dnaRun.getFlowerColor();
                dnaList.add(flowerColor!=null?flowerColor:"");
                sb.append(flowerColor!=null?flowerColor:"").append(",");
            }
            //荚色
            if (map.containsKey("podColor")){
                String podColor=dnaRun.getPodColor();
                dnaList.add(podColor!=null?podColor:"");
                sb.append(podColor!=null?podColor:"").append(",");
            }
            //茸毛色
            if (map.containsKey("pubescenceColor")){
                String pubescenceColor=dnaRun.getPubescenceColor();
                dnaList.add(pubescenceColor!=null?pubescenceColor:"");
                sb.append(pubescenceColor!=null?pubescenceColor:"").append(",");
            }
            //产量
            if (map.containsKey("yield")){
                float yield=dnaRun.getYield();
                dnaList.add(String.valueOf(yield)!=null?String.valueOf(yield):"");
                sb.append(yield!=0?String.valueOf(yield):"").append(",");
            }
            //顶端小叶长度
            if (map.containsKey("upperLeafletLength")){
                float upperLeafletLength=dnaRun.getUpperLeafletLength();
                dnaList.add(String.valueOf(upperLeafletLength)!=null?String.valueOf(upperLeafletLength):"");
                sb.append(upperLeafletLength!=0?String.valueOf(upperLeafletLength):"").append(",");
            }
            //脂肪酸的内容
            //亚油酸
            if(map.containsKey("linoleic")){
                float linoleic=dnaRun.getLinoleic();
                dnaList.add(String.valueOf(linoleic)!=null?String.valueOf(linoleic):"");
                sb.append(linoleic!=0?String.valueOf(linoleic):"0").append(",");
            }
            //亚麻酸
            if (map.containsKey("linolenic")){
                float linolenic=dnaRun.getLinolenic();
                dnaList.add(String.valueOf(linolenic)!=null?String.valueOf(linolenic):"");
                sb.append(linolenic!=0?String.valueOf(linolenic):"").append(",");
            }
            //油酸
            if (map.containsKey("oleic")){
                float oleic=dnaRun.getOleic();
                dnaList.add(String.valueOf(oleic)!=null?String.valueOf(oleic):"");
                sb.append(oleic!=0?String.valueOf(oleic):"").append(",");
            }

            //软脂酸
            if (map.containsKey("palmitic")){
                float palmitic=dnaRun.getPalmitic();
                dnaList.add(String.valueOf(palmitic)!=null?String.valueOf(palmitic):"");
                sb.append(palmitic!=0?String.valueOf(palmitic):" ").append(",");
            }
            //硬脂酸
            if (map.containsKey("stearic")){
                float stearic=dnaRun.getStearic();
                dnaList.add(String.valueOf(stearic)!=null?String.valueOf(stearic):"");
                sb.append(stearic!=0?String.valueOf(stearic):" ").append(",");
            }


            //前端数据不包含部分

            //组别
            if(map.containsKey("group")){
                String group=dnaRun.getGroup();
                dnaList.add(group);
                sb.append(group!=null?group:"").append(",");
            }
            //编号
            if(map.containsKey("run")){
                String run=dnaRun.getRunNo();
                dnaList.add(run);
                sb.append(run!=null?run:"").append(",");
            }


            //品种名称
            if(map.containsKey("plantName")){
                String plantName=dnaRun.getPlantName();
                dnaList.add(String.valueOf(plantName));
                sb.append(plantName!=null?plantName:"").append(",");
            }

            sb.append("\n");

        }
        return sb.toString();
    }

}
