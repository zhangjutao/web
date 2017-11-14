package com.gooalgene.dna.web;




import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.service.DNARunService;
import com.gooalgene.utils.StringUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyan on 2017/11/13
 *
 */
@Controller
public class ExportDataController {

    @Autowired
    private DNARunService dnaRunService;

    @Autowired
    private GuavaCacheManager guavaCacheManager;
    private Cache DnaRunCache;


    @RequestMapping("/export")
    public void exportData(String choices,HttpServletResponse response) throws IOException {

        String titles=choices.substring(0,choices.length()-1);
        String fileName="test";
        String csvStr="";
        DnaRunCache=guavaCacheManager.getCache("config");

        List<DNARun> result= (List<DNARun>) DnaRunCache.get("run_dna").get();

        csvStr=createCsvStr(result,titles.split(","));

        if (!csvStr.equals("")) {
            byte[] buffer = csvStr.getBytes("gbk");
            response.reset();
            String filename = java.net.URLEncoder.encode(fileName, "UTF-8") + ".csv";
            filename = filename.replace("+", "%20");
            response.addHeader("Content-Disposition", "attachment; filename=" + filename + "; filename*=utf-8''" + filename);
            response.addHeader("Content-Length", "" + buffer.length);
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        }
    }

    //调整表头显示
    private static Map<String, String> changeCloumn2Web() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("group", "Group");
        map.put("run", "Run");
        map.put("species", "Species");
        map.put("sampleName", "Sample Name");
        map.put("cultivar", "Cultivar");
        map.put("plantName", "Plant Name");
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

        map.put("weight", "Weight (g) per 100 Seeds");
        map.put("cotyledonColor", "Cotyledon Color");
        map.put("seedCoatColor", "Seed Coat Color");
        map.put("upperLeafletLength", "upper Leaflet Length");

        map.put("maturityGroup", "Maturity Group");
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
            //组别
            if(map.containsKey("group")){
                String group=dnaRun.getGroup();
                sb.append(group!=null?group:"").append(",");
            }
            //编号
            if(map.containsKey("run")){
                String run=dnaRun.getRunNo();
                sb.append(run!=null?run:"").append(",");
            }
            //物种
            if(map.containsKey("species")){
                String species=dnaRun.getSpecies();
                sb.append(species!=null?species:"").append(",");
            }
            //样本名
            if(map.containsKey("sampleName")){
                String sampleName=dnaRun.getSampleName();
                sb.append(sampleName!=null?sampleName:"").append(",");
            }
            //品种名
            if(map.containsKey("cultivar")){
                String cultivar=dnaRun.getCultivar();
                sb.append(cultivar!=null?cultivar:"").append(",");
            }
            //品种名称
            if(map.containsKey("plantName")){
                String plantName=dnaRun.getPlantName();
                sb.append(plantName!=null?plantName:"").append(",");
            }
            //地理位置
            if(map.containsKey("locality")){
                String locality=dnaRun.getLocality();
                sb.append(locality!=null?locality:"").append(",");
            }
            //蛋白质含量
            if(map.containsKey("protein")){
                float protein=dnaRun.getProtein();
                sb.append(protein!=0?String.valueOf(protein):"0").append(",");
            }
            //含油量
            if(map.containsKey("oil")){
                float oil=dnaRun.getOil();
                sb.append(oil!=0?String.valueOf(oil):"0").append(",");
            }

            //脂肪酸的内容
            //亚油酸
            if(map.containsKey("linoleic")){
                float linoleic=dnaRun.getLinoleic();
                sb.append(linoleic!=0?String.valueOf(linoleic):"0").append(",");
            }
            //亚麻酸
            if (map.containsKey("linolenic")){
                float linolenic=dnaRun.getLinolenic();
                sb.append(linolenic!=0?String.valueOf(linolenic):"").append(",");
            }
            //油酸
            if (map.containsKey("oleic")){
                float oleic=dnaRun.getOleic();
                sb.append(oleic!=0?String.valueOf(oleic):"").append(",");
            }
            //软脂酸
            if (map.containsKey("palmitic")){
                float palmitic=dnaRun.getPalmitic();
                sb.append(palmitic!=0?String.valueOf(palmitic):"").append(",");
            }
            //硬脂酸
            if (map.containsKey("stearic")){
                float stearic=dnaRun.getStearic();
                sb.append(stearic!=0?String.valueOf(stearic):"").append(",");
            }
            //株高
            if (map.containsKey("height")){
                float height=dnaRun.getHeight();
                sb.append(height!=0?String.valueOf(height):"").append(",");
            }
            //花色
            if (map.containsKey("flowerColor")){
                String flowerColor= dnaRun.getFlowerColor();
                sb.append(flowerColor!=null?flowerColor:"").append(",");
            }
            //种脐色
            if (map.containsKey("hilumColor")){
                String hilumColor=dnaRun.getHilumColor();
                sb.append(hilumColor!=null?hilumColor:"").append(",");
            }
            //荚色
            if (map.containsKey("podColor")){
                String podColor=dnaRun.getPodColor();
                sb.append(podColor!=null?podColor:"").append(",");
            }
            //茸毛色
            if (map.containsKey("pubescenceColor")){
                String pubescenceColor=dnaRun.getPubescenceColor();
                sb.append(pubescenceColor!=null?pubescenceColor:"").append(",");
            }
            //种皮色
            if (map.containsKey("seedCoatColor")){
                String seedCoatColor=dnaRun.getSeedCoatColor();
                sb.append(seedCoatColor!=null?seedCoatColor:"").append(",");
            }
            //子叶色
            if (map.containsKey("cotyledonColor")){
                String cotyledonColor=dnaRun.getCotyledonColor();
                sb.append(cotyledonColor!=null?cotyledonColor:"").append(",");
            }
            //百粒重
            if (map.containsKey("weight")){
                float weight=dnaRun.getWeightPer100seeds();
                sb.append(weight!=0?String.valueOf(weight):"").append(",");
            }
            //顶端小叶长度
            if (map.containsKey("upperLeafletLength")){
                float upperLeafletLength=dnaRun.getUpperLeafletLength();
                sb.append(upperLeafletLength!=0?String.valueOf(upperLeafletLength):"").append(",");
            }
            //成熟期组
            if (map.containsKey("maturityGroup")){
                String maturityGroup=dnaRun.getMaturityDate();
                sb.append(maturityGroup!=null?maturityGroup:"").append(",");
            }
            //产量
            if (map.containsKey("yield")){
                float yield=dnaRun.getYield();
                sb.append(yield!=0?String.valueOf(yield):"").append(",");
            }
            sb.append("\n");

        }
        return sb.toString();
    }


}
