package com.gooalgene.dna.service;

import com.gooalgene.common.Page;
import com.gooalgene.dna.dto.SNPDto;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.SNP;
import com.google.common.collect.Lists;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ShiYun on 2017/9/11 0011.
 */
@Service
public class SNPService {

    @Autowired
    private DNAMongoService dnaMongoService;

    @Autowired
    private DNARunService dnaRunService;
    @Autowired
    private SNPService snpService;
    @Autowired
    private DNAGenStructureService dnaGenStructureService;



    public Map searchSNPinRegion(String type, String ctype, String chr, String startPos, String endPos, String group, Page<DNARun> page) {

        List<SNP> snps = dnaMongoService.searchInRegin(type, ctype, chr, startPos, endPos, page);
        Map<String, List<String>> group_runNos = dnaRunService.queryDNARunByCondition(group);
        Map result = new HashMap();
        result.put("conditions", chr + "," + startPos + "," + endPos);
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        JSONArray data = new JSONArray();
        for (SNP snp : snps) {
            JSONObject snp_Json = snp.toJSON();
//            countMajorAllele(snp, snp_Json);
            JSONArray freqData = getFrequeData(snp.getSamples(), group_runNos);
            snp_Json.put("freq", freqData);
            //todo 加入一个字段GenoType，调奶哥服务(只有SNP时有)
            if(StringUtils.equals(type,"SNP")){
                Map map = snpService.findSampleBySNPId();
                snp_Json.put("geneType",map);
            }
            data.add(snp_Json);
        }
        result.put("total", page.getCount());
        result.put("data", data);
        return result;
    }

    public Map findSampleBySNPId() {
        return new HashMap();
    }

    public Map searchSNPinGene(String type, String ctype, String gene, String upsteam, String downsteam, String group, Page<DNAGens> page) {
        List<DNAGenStructure> dnaGenStructures=dnaGenStructureService.getByGeneId(gene);

        List<SNP> snps = dnaMongoService.searchInGene(type, ctype, gene, upsteam, downsteam, page);
        Map<String, List<String>> group_runNos = dnaRunService.queryDNARunByCondition(group);
        Map result = new HashMap();
        result.put("conditions", gene + "," + upsteam + "," + downsteam);
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        JSONArray data = new JSONArray();
        for (SNP snp : snps) {
            JSONObject snp_Json = snp.toJSON();
//          countMajorAllele(snp, snp_Json);
            JSONArray freqData = getFrequeData(snp.getSamples(), group_runNos);
            snp_Json.put("freq", freqData);
            if(StringUtils.equals(type,"SNP")){
                Map map = snpService.findSampleBySNPId();
                snp_Json.put("geneType",map);
            }
            data.add(snp_Json);
        }
        result.put("total", page.getCount());
        result.put("data", data);
        return result;
    }

    public Map searchSNPinGene2(String type, String ctype, String gene, String upsteam, String downsteam, String group, Page<DNAGens> page) {
        List<DNAGenStructure> dnaGenStructures=dnaGenStructureService.getByGeneId(gene);

        List<SNP> snps = dnaMongoService.searchInGene(type, ctype, gene, upsteam, downsteam, page);
        Map<String, List<String>> group_runNos = dnaRunService.queryDNARunByCondition(group);
        Map result = new HashMap();
        result.put("conditions", gene + "," + upsteam + "," + downsteam);
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        //JSONArray data = new JSONArray();
        List<SNPDto> data= Lists.newArrayList();
        for (SNP snp : snps) {
            /*JSONObject snp_Json = snp.toJSON();
//          countMajorAllele(snp, snp_Json);
            JSONArray freqData = getFrequeData(snp.getSamples(), group_runNos);
            snp_Json.put("freq", freqData);
            if(StringUtils.equals(type,"SNP")){
                Map map = snpService.findSampleBySNPId();
                snp_Json.put("geneType",map);
            }
            data.add(snp_Json);*/
            SNPDto snpDto=new SNPDto();
            BeanUtils.copyProperties(snp,snpDto);
            JSONArray freqData = getFrequeData(snp.getSamples(), group_runNos);
            snpDto.setFreq(freqData);
            if(StringUtils.equals(type,"SNP")){
                Map map = snpService.findSampleBySNPId();
                snpDto.setGeneType(map);
            }
            data.add(snpDto);
        }
        result.put("total", page.getCount());
        result.put("data", data);
        result.put("dnaGenStructures",dnaGenStructures);
        return result;
    }

    /**
     * 计算MajorAllele显示的碱基
     *
     * @param snp
     * @param snpJSON
     */
    public void countMajorAllele(SNP snp, JSONObject snpJSON) {
        int num_0_0 = 0;
        int num_0_1 = 0;
        int num_1_1 = 0;
        int num_total = 0;
        Map<String, String> samples = snp.getSamples();
        String ref = snp.getRef();
        String alt = snp.getAlt();
        for (String key : samples.keySet()) {
            String value = samples.get(key);
            if (value.equals("0/0")) {
                num_0_0++;
                num_total++;
            } else if (value.equals("0/1")) {
                num_0_1++;
                num_total++;
            } else if (value.equals("1/1")) {
                num_1_1++;
                num_total++;
            } else {

            }
        }
        //频率高的为Major,低的为Minor
        if (num_total != 0) {
            Double a = (num_0_0 + 0.5 * num_0_1) / num_total; //-->> AA  ref
            Double b = (num_1_1 + 0.5 * num_0_1) / num_total; //-->> GG  alt
            if (a.compareTo(b) >= 0) {
                snpJSON.put("majorallen", ref);
                snpJSON.put("minorallen", alt);
            } else {
                snpJSON.put("majorallen", alt);
                snpJSON.put("minorallen", ref);
            }
        } else {
            snpJSON.put("majorallen", "-");
            snpJSON.put("minorallen", "-");
        }
    }

    /**
     * 按群体计算样本频率
     *
     * @param sample
     * @param groups
     * @return
     */
    private JSONArray getFrequeData(Map<String, String> sample, Map<String, List<String>> groups) {
        JSONArray jsonArray = new JSONArray();
        for (String group : groups.keySet()) {
            JSONObject jsonObject = new JSONObject();
            List<String> samples = groups.get(group);
            int length = samples.size();
            jsonObject.put("name", group);
            jsonObject.put("size", length);
            int num_0_0 = 0;
            int num_0_1 = 0;
            int num_1_1 = 0;
            int num_total = 0;
//            System.out.println("==========================");
            for (int j = 0; j < length; j++) {
                String k = samples.get(j);
                String s = sample.get(k);
//                System.out.println(k + ":" + s);
                if (s != null) {
                    if (s.equals("0/0")) {
                        num_0_0++;
                        num_total++;
                    } else if (s.equals("0/1")) {
                        num_0_1++;
                        num_total++;
                    } else if (s.equals("1/1")) {
                        num_1_1++;
                        num_total++;
                    } else {

                    }
                } else {
                    System.out.println(k + " is " + s);
                }
            }
//            System.out.println("G:" + group + "=====[1/1]:" + num_1_1 + "\t[0/0]:" + num_0_0 + "\t[0/1]:" + num_0_1 + "\tTotal" + num_total);
            double major = 0, minor = 0;//频率高的为Major,低的为Minor
            if (num_total != 0) {
                Double a = (num_0_0 + 0.5 * num_0_1) / num_total;
                Double b = (num_1_1 + 0.5 * num_0_1) / num_total;
                if (a.compareTo(b) >= 0) {
                    major = a;
                    minor = b;
                } else {
                    major = b;
                    minor = a;
                }
            }
//            System.out.println(major + "\t" + minor);
            jsonObject.put("major", major);
            jsonObject.put("minor", minor);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public Map searchSNPByGene(String type, String gene, Page<DNAGens> page) {
        List<SNP> snps = dnaMongoService.searchByGene(type,gene,page);
        Map result = new HashMap();
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        JSONArray data = new JSONArray();
        for (SNP snp : snps) {
            JSONObject snp_Json = snp.toJSON();

            JSONArray freqData = getFrequeData(snp.getSamples(), new HashMap<String, List<String>>());
            snp_Json.put("freq", freqData);
            data.add(snp_Json);
        }
        result.put("total", page.getCount());
        result.put("data", data);
        return result;
    }
}
