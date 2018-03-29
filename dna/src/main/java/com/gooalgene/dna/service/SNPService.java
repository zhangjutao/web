package com.gooalgene.dna.service;

import com.gooalgene.common.Page;
import com.gooalgene.dna.dto.SNPDto;
import com.gooalgene.dna.entity.*;
import com.gooalgene.dna.entity.result.GroupCondition;
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

@Service
public class SNPService {

    @Autowired
    private DNAMongoService dnaMongoService;

    @Autowired
    private DNARunService dnaRunService;

    @Autowired
    private SNPService snpService;

    @Autowired
    private DNAGensService dnaGensService;

    /**
     * 将samples中的genotype格式（0/0、0/1、1/1字符串）对应转换为ref+ref、ref+alt、alt+alt字符串
     * 计算单个snp中三种genotype的比率
     */
    public Map<String, Object> genotypeTransform(SNP snp, String type) {
        // 获取该SNP中所有sample->result关系
        Map<String, String> originSamples = snp.getSamples();
        Map<String, String> transformedSamples = new HashMap<>();
        Map<String, Object> transformResult = new HashMap<>();
        String ref = snp.getRef();
        String alt = snp.getAlt();
        int totalRefAndRef = 0;
        int totalRefAndAlt = 0;
        int totalAltAndAlt = 0;
        BigDecimal bigDecimalTotalSamples = BigDecimal.valueOf(snp.getSamples().size());
        // 将所有SNP中0、1关系转换为alt、ref关系
        for (Map.Entry<String, String> entry : originSamples.entrySet()) {
            if (entry.getValue().equals("0/0")) {
                transformedSamples.put(entry.getKey(), ref + ref);
                totalRefAndRef += 1;
            } else if (entry.getValue().equals("0/1")) {  // 生信数据无1/0格式
                transformedSamples.put(entry.getKey(), ref + alt);
                totalRefAndAlt += 1;
            } else {
                transformedSamples.put(entry.getKey(), alt + alt);
                totalAltAndAlt += 1;
            }
        }
        // 将每一个样本中0/0转换为A/A等
        snp.setSamples(transformedSamples);
        if(type.equals("INDEL")) {
            transformResult.put("INDELData", snp);
            return transformResult;
        }
        transformResult.put("snpData", snp);
        BigDecimal bigDecimalRAR = BigDecimal.valueOf(totalRefAndRef);
        BigDecimal bigDecimalRAA = BigDecimal.valueOf(totalRefAndAlt);
        BigDecimal bigDecimalAAA = BigDecimal.valueOf(totalAltAndAlt);
        // 分别计算Alt/Ref之间组合占的比例
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
    public Map<String, Object> findSampleById(String id) {
        Map<String, Object> oneDataResult = new HashMap<>();
        if (!id.matches("(PT\\d{1}[a-z]{1}[0-9]*)")) {
            return oneDataResult;
        }
        String[] chromosomeAndType = CommonUtil.getChromosomeAndType(id);
        String type = chromosomeAndType[0];
        String chr = "chr" + chromosomeAndType[1];
        SNP oneData = dnaMongoService.findDataById(type, chr, id);
        if (oneData == null) {
            return oneDataResult;
        }
        double major = oneData.getMajor();
        double resultData = DataFormatUtils.keepTwoFraction(major);
        oneDataResult = genotypeTransform(oneData, type);
        oneDataResult.put("major", resultData);
        oneDataResult.put("type", type);
        return oneDataResult;
    }

    public Map<String, Object> findSampleById(SNP snp) {
        String snpId = snp.getId();
        return this.findSampleById(snpId);
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

    /**
     * 统一searchSNPinRegion与searchSNPInGene方法
     *
     * @return DNA数据库点击确定查询表格结果
     * @throws IOException
     */
    public TableSearchResult searchSNPResult(String type, String ctype, String chr, String startPos, String endPos,
                                             List<GroupCondition> groupConditions, int pageNo, int pageSize) throws IOException {
        TableSearchResult result = new TableSearchResult();
        List<SNP> snps = dnaMongoService.querySNPByRegion(type, ctype, chr, startPos, endPos, pageNo, pageSize, result);
        Map<String, List<String>> groupIdReflection = dnaRunService.queryDNARunByCondition(groupConditions);

        List<SNPDto> data = Lists.newArrayList();
        for (SNP snp : snps) {
            SNPDto snpDto = new SNPDto();
            BeanUtils.copyProperties(snp, snpDto);
            Map<String, Object> map = snpService.findSampleById(snp);
            snpDto.setGeneType(map);
            List<SampleFrequency> sampleFrequencyList = null;
            if(StringUtils.equals(type,"SNP")){
                sampleFrequencyList = getFrequency((SNP) map.get("snpData"), groupIdReflection);
                map.remove("snpData");  // 前台无需返回该的数据
            }else {
                sampleFrequencyList = getFrequency((SNP) map.get("INDELData"), groupIdReflection);
                map.remove("INDELData");
            }
            snpDto.setSamples(null);  // 减少前台数据返回量
            snpDto.setFreq(sampleFrequencyList);
            data.add(snpDto);
        }
        result.setData(data);
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
            Map map = snpService.findSampleById(snp);
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
    private List<SampleFrequency> getFrequency(SNP snp, Map<String, List<String>> groups) {
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
        String start = Long.toString(dnaGens.getStart() - 2000 < 0 ? 0 : dnaGens.getStart() - 2000);
        String end = Long.toString(dnaGens.getEnd() + 2000);
        List<SNP> snps = dnaMongoService.searchInGene(type,ctypeList[0],gene,start,end,page);
        Map result = new HashMap();
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        result.put("total", page.getCount());
        return result;
    }

}
