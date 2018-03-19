package com.gooalgene.dna.service;

import com.gooalgene.common.Page;
import com.gooalgene.dna.dao.DNAGensDao;
import com.gooalgene.dna.dto.DNAGenStructureDto;
import com.gooalgene.dna.dto.SNPDto;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.SNP;
import com.gooalgene.dna.entity.SampleFrequency;
import com.gooalgene.dna.util.DataFormatUtils;
import com.gooalgene.utils.CommonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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
    @Autowired
    private DNAGensService dnaGensService;

    /**
     * 将samples中的genotype格式（0/0、0/1、1/1字符串）对应转换为ref+ref、ref+alt、alt+alt字符串
     * 计算单个snp中三种genotype的比率
     */
    public Map genotypeTransform(SNP snp, String type) {
        Map<String, String> originSamples = snp.getSamples();
        Map<String, String> transformedSamples = new HashMap<>();
        Map transformResult = new HashMap();
        String ref = snp.getRef();
        String alt = snp.getAlt();
        int totalRefAndRef = 0;
        int totalRefAndAlt = 0;
        int totalAltAndAlt = 0;
        BigDecimal bigDecimalTotalSamples = BigDecimal.valueOf(snp.getSamples().size());
        for (Map.Entry<String, String> entry : originSamples.entrySet()) {
            if (entry.getValue().equals("0/0")) {
                transformedSamples.put(entry.getKey(), ref + ref);
                totalRefAndRef += 1;
            } else if (entry.getValue().equals("0/1")) {
                transformedSamples.put(entry.getKey(), ref + alt);
                totalRefAndAlt += 1;
            } else {
                transformedSamples.put(entry.getKey(), alt + alt);
                totalAltAndAlt += 1;
            }
        }
        snp.setSamples(transformedSamples);
        if(type.equals("INDEL")) {
            transformResult.put("INDELData", snp);
            return transformResult;
        }
        transformResult.put("snpData", snp);
        BigDecimal bigDecimalRAR = BigDecimal.valueOf(totalRefAndRef);
        BigDecimal bigDecimalRAA = BigDecimal.valueOf(totalRefAndAlt);
        BigDecimal bigDecimalAAA = BigDecimal.valueOf(totalAltAndAlt);
        transformResult.put("RefAndRefPercent", bigDecimalRAR.divide(
                bigDecimalTotalSamples, 7, BigDecimal.ROUND_HALF_UP).doubleValue());
        transformResult.put("totalRefAndAltPercent", bigDecimalRAA.divide(
                bigDecimalTotalSamples, 7, BigDecimal.ROUND_HALF_UP).doubleValue());
        transformResult.put("totalAltAndAltPercent", bigDecimalAAA.divide(
                bigDecimalTotalSamples, 7, BigDecimal.ROUND_HALF_UP).doubleValue());
        return transformResult;
    }

    /**
     * 通过SNP或INDEL的id查找相应SNP或INDEL数据及相关sample数据
     */
    public Map findSampleById(String id) {
        Map oneDataResult = new HashMap();
        int markNumber = CommonUtil.getCharPositionBeforNum(id);
        if (markNumber == -1) {
            return oneDataResult;
        }
        String type;
        if (id.indexOf("I", markNumber) == markNumber) {
            type = "INDEL";
        } else if (id.indexOf("S", markNumber) == markNumber) {
            type = "SNP";
        } else {
            return oneDataResult;
        }
        String chr = "Chr" + (id.substring(markNumber + 2, markNumber + 4));
        SNP oneData = dnaMongoService.findDataById(type, chr, id);
        if (oneData == null) {
            return oneDataResult;
        }
        double major = oneData.getMajor();
        double resultData = DataFormatUtils.keepTwoFraction(major);
        oneDataResult = genotypeTransform(oneData, type);
        oneDataResult.put("major", resultData);
        if (type.equals("INDEL")) {
            oneDataResult.put("type", type);
        } else {
            oneDataResult.put("type", type);
        }
        return oneDataResult;
    }

    public Map<String, Object> findSampleById(SNP snp) {
        Map<String, Object> oneDataResult = new HashMap<>();
        String geneIdInMongo = snp.getId();
        if (StringUtils.isNotBlank(geneIdInMongo)) {
            String type = String.valueOf(geneIdInMongo.charAt(3));
            if (type.equalsIgnoreCase("i")) {
                type = "INDEL";
            } else {
                type = "SNP";
            }
            double majorResult = DataFormatUtils.keepTwoFraction(snp.getMajor());
            oneDataResult = genotypeTransform(snp, type);
            oneDataResult.put("major", majorResult);
            oneDataResult.put("type", type);
        }
        return oneDataResult;
    }


    /**
     * 根据染色体及其区间位置，查询该区间内所有SNP
     * @param type SNP/INDEL
     * @param ctype 序列类型
     * @param chr 染色体名
     * @param startPos 界面上输入的起点位置
     * @param endPos 界面上输入的终点位置
     * @param group 已选的群组
     * @return pageNo、pageSize、total、geneIds（范围内所有id集合）、frequency（每种group中major、minor频率）
     * @throws IOException
     */
    public Map searchSNPinRegion(String type, String ctype, String chr, String startPos, String endPos, String group, Page<DNARun> page) throws IOException {
        List<SNP> snps = dnaMongoService.searchInRegin(type, ctype, chr, startPos, endPos, page);
        Map<String, List<String>> groupIdReflection = dnaRunService.queryDNARunByCondition(group);
        Map result = new HashMap();
        result.put("conditions", chr + "," + startPos + "," + endPos);
        if (page != null) {
            result.put("pageNo", page.getPageNo());
            result.put("pageSize", page.getPageSize());
            result.put("total", page.getCount());
        }
        List<SNPDto> data = Lists.newArrayList();
        Set<String> geneIds = Sets.newHashSet();
        for (SNP snp : snps) {
            geneIds.add(snp.getGene());
            SNPDto snpDto = new SNPDto();
            BeanUtils.copyProperties(snp, snpDto);
            Map map = snpService.findSampleById(snp);
            snpDto.setGeneType(map);
            JSONArray freqData;
            if(StringUtils.equals(type,"SNP")){
                freqData = getFrequencyInSnp((SNP)map.get("snpData"), groupIdReflection);
            }else {
                freqData = getFrequencyInSnp((SNP)map.get("INDELData"), groupIdReflection);
            }
            snpDto.setFreq(freqData);
            data.add(snpDto);
        }
        result.put("geneIds", geneIds);
        result.put("data", data);
        return result;
    }

    public Map searchSNPinGene(String type, String ctype, String gene, String upsteam, String downsteam, String group, Page<DNAGens> page){
        List<SNP> snps = dnaMongoService.searchInGene(type, ctype, gene, upsteam, downsteam, page);
        Map<String, List<String>> group_runNos = dnaRunService.queryDNARunByCondition(group);
        Map result = new HashMap();
        result.put("conditions", gene + "," + upsteam + "," + downsteam);
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        List<SNPDto> data = Lists.newArrayList();
        for (SNP snp : snps) {
            SNPDto snpDto = new SNPDto();
            BeanUtils.copyProperties(snp, snpDto);
            JSONArray freqData;
            Map map = snpService.findSampleById(snp);
            if(StringUtils.equals(type,"SNP")){
                freqData = getFrequencyInSnp((SNP)map.get("snpData"), group_runNos);
            }else {
                freqData = getFrequencyInSnp((SNP)map.get("INDELData"), group_runNos);
            }
            snpDto.setFreq(freqData);
            snpDto.setGeneType(map);
            data.add(snpDto);
        }
        result.put("total", page.getCount());
        result.put("data", data);
        return result;
    }

    public Map searchSNPResult(String type, String ctype, String chr, String startPos, String endPos, String group, Page<DNARun> page) throws IOException {
        long total = 0;
        List<SNP> snps = dnaMongoService.querySNPByRegion(type, ctype, chr, startPos, endPos, page.getPageNo(), page.getPageSize(), total);
        Map<String, List<String>> groupIdReflection = dnaRunService.queryDNARunByCondition(group);
        Map result = new HashMap();
        result.put("conditions", chr + "," + startPos + "," + endPos);
        if (page != null) {
            result.put("pageNo", page.getPageNo());
            result.put("pageSize", page.getPageSize());
            result.put("total", page.getCount());
        }
        List<SNPDto> data = Lists.newArrayList();
        Set<String> geneIds = Sets.newHashSet();
        for (SNP snp : snps) {
            geneIds.add(snp.getGene());
            SNPDto snpDto = new SNPDto();
            BeanUtils.copyProperties(snp, snpDto);
            Map map = snpService.findSampleById(snp);
            snpDto.setGeneType(map);
            JSONArray freqData;
            if(StringUtils.equals(type,"SNP")){
                freqData = getFrequencyInSnp((SNP)map.get("snpData"), groupIdReflection);
            }else {
                freqData = getFrequencyInSnp((SNP)map.get("INDELData"), groupIdReflection);
            }
            snpDto.setFreq(freqData);
            data.add(snpDto);
        }
        result.put("geneIds", geneIds);
        result.put("data", data);
        return result;
    }

    /*
    * @author 张衍平
    * 按范围查询导出数据使用
    * */
    public Map searchSNPinRegionForExport(String type, String ctype, String chr, String startPos, String endPos, String group, Page<DNARun> page) throws IOException {

        List<SNP> snps = dnaMongoService.searchInRegin(type, ctype, chr, startPos, endPos, page);
        Map<String, List<String>> group_runNos = dnaRunService.queryDNARunByCondition(group);
        Map result = new HashMap();
        result.put("conditions", chr + "," + startPos + "," + endPos);
        if (page != null) {
            result.put("pageNo", page.getPageNo());
            result.put("pageSize", page.getPageSize());
            result.put("total", page.getCount());
        }
        List<SNPDto> data = Lists.newArrayList();
        for (SNP snp : snps) {
            SNPDto snpDto = new SNPDto();
            BeanUtils.copyProperties(snp, snpDto);
            Map map = snpService.findSampleById(snp.getId());
            snpDto.setGeneType(map);
            JSONArray freqData;
            if(StringUtils.equals(type,"SNP")){
                freqData = getFrequencyInSnp((SNP)map.get("snpData"), group_runNos);
            }else {
                freqData = getFrequencyInSnp((SNP)map.get("INDELData"), group_runNos);
            }
            snpDto.setFreq(freqData);
            data.add(snpDto);
        }
        result.put("data", data);
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
    public JSONArray getFrequeData(Map<String, String> sample, Map<String, List<String>> groups) {
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

    /**
     * 计算用户勾选的群体中每一个群体对应的major、minor的比例，如果用户未勾选群体，直接返回空
     * @param snp 在该SNP中每种group对应的major、minor值
     * @param groups 用户勾选的群体
     * @return 返回一个包含major、minor的对象
     */
    public JSONArray getFrequencyInSnp(SNP snp, Map<String, List<String>> groups) {
        Map<String, String> samples = snp.getSamples(); //先拿到SNP中的所有sample
        String alt = snp.getAlt(); //次要变异位点表示1
        String ref = snp.getRef(); //主要变异位点表示0
        final String doubleAlt = alt + alt;
        final String doubleRef = ref + ref;
        final String combination = alt + ref;
        final String reverseCombination = ref + alt;
        JSONArray jsonArray = new JSONArray();
        // 遍历每一个group
        for (String group : groups.keySet()) {
            JSONObject jsonObject = new JSONObject();
            // 获取每一个group中所有idList
            List<String> idList = groups.get(group);
            int length = idList.size();
            jsonObject.put("name", group);
            jsonObject.put("size", length);
            int num_0_0 = 0;
            int num_0_1 = 0;
            int num_1_1 = 0;
            int num_total = 0;
            // 拿到每一种sample下major、minor组合的数值
            for (int j = 0; j < length; j++) {
                String k = idList.get(j);
                // 获取到该种run_no对应的值
                String runNo = samples.get(k);
                if (runNo.equals(doubleRef)) {
                    num_0_0++;
                    num_total++;
                } else if (runNo.equals(combination) || runNo.equals(reverseCombination)) {
                    num_0_1++;
                    num_total++;
                } else {
                    num_1_1++;
                    num_total++;
                }
            }
            // 频率高的为Major,低的为Minor
            double major = 0, minor = 0;
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
            jsonObject.put("major", major);
            jsonObject.put("minor", minor);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 计算用户勾选的群体中每一个群体对应的major、minor的比例，如果用户未勾选群体，直接返回空
     * @param snp 在该SNP中每种group对应的major、minor值
     * @param groups 用户勾选的群体
     * @return 返回一个包含major、minor的对象
     */
    public List<SampleFrequency> getFrequency(SNP snp, Map<String, List<String>> groups) {
        Map<String, String> samples = snp.getSamples(); //先拿到SNP中的所有sample
        List<SampleFrequency> result = new ArrayList<>();
        String alt = snp.getAlt(); //次要变异位点表示1
        String ref = snp.getRef(); //主要变异位点表示0
        final String doubleAlt = alt + alt;
        final String doubleRef = ref + ref;
        final String combination = alt + ref;
        final String reverseCombination = ref + alt;
        SampleFrequency frequency = null;
        // 遍历每一个group
        for (String group : groups.keySet()) {
            // 获取每一个group中所有idList
            List<String> idList = groups.get(group);
            int length = idList.size();
            int num_0_0 = 0;
            int num_0_1 = 0;
            int num_1_1 = 0;
            int num_total = 0;
            // 拿到每一种sample下major、minor组合的数值
            for (int j = 0; j < length; j++) {
                String k = idList.get(j);
                // 获取到该种run_no对应的值
                String runNo = samples.get(k);
                if (runNo.equals(doubleRef)) {
                    num_0_0++;
                    num_total++;
                } else if (runNo.equals(combination) || runNo.equals(reverseCombination)) {
                    num_0_1++;
                    num_total++;
                } else {
                    num_1_1++;
                    num_total++;
                }
            }
            // 频率高的为Major,低的为Minor
            double major = 0, minor = 0;
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
            frequency = new SampleFrequency(group, length, major, minor);
            result.add(frequency);
        }
        return result;
    }

    public Map searchSNPByGene(String type, String[] ctypeList, String gene, Page<DNAGens> page) {
        DNAGens dnaGens = dnaGensService.findByGene(gene);
        String start = Long.toString(dnaGens.getGeneStart() - 2000 < 0 ? 0 : dnaGens.getGeneStart() - 2000);
        String end = Long.toString(dnaGens.getGeneEnd() + 2000);
        List<SNP> snps = dnaMongoService.searchInGene(type,ctypeList[0],gene,start,end,page);
        Map result = new HashMap();
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        JSONArray data = new JSONArray();
        for (SNP snp : snps) {
            JSONObject snp_Json = snp.toJSON();
            getFrequencyInSnp(snp, null);
            JSONArray freqData = getFrequeData(snp.getSamples(), new HashMap<String, List<String>>());
            snp_Json.put("freq", freqData);
            data.add(snp_Json);
        }
        result.put("total", page.getCount());
        result.put("data", data);
        return result;
    }

}
