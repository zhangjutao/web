package com.gooalgene.dna.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.dna.dao.DNARunDao;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.dto.SampleInfoDto;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.SampleInfo;
import com.gooalgene.dna.entity.result.DNARunSearchResult;
import com.gooalgene.dna.entity.result.GroupCondition;
import com.google.common.collect.Lists;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by ShiYun on 2017/9/6 0006.
 */
@Service
public class DNARunService {
    private static final Logger logger = LoggerFactory.getLogger(DNARunService.class);
    @Autowired
    private DNARunDao dnaRunDao;

    public int insertBatch(List<DNARun> list) {
        return dnaRunDao.insertBatch(list);
    }

    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 根据页面分组查询对应的样本信息
     */
    public Map<String, List<String>> queryDNARunByCondition(String group) throws IOException {
        //List<GroupCondition> result = objectMapper.readValue(group, new TypeReference<List<GroupCondition>>() {});
        GroupCondition entity = objectMapper.readValue(group, GroupCondition.class);
        if (group.equals("[{}]")) {
            group = "[]";
        }
        Map<String, List<String>> result = new HashMap<>();
        if (StringUtils.isNotBlank(group)) {
            logger.info(group);
            JSONArray data = JSONArray.fromObject(group);
            int len = data.size();
            for (int i = 0; i < len; i++) {
                JSONObject one = data.getJSONObject(i);
                String groupName = one.getString("name");
                if (one.containsKey("condition")) {
                    String condition = one.getString("condition");
                    if (condition.indexOf("cultivar") != -1) {
                        List<String> runNoList = new ArrayList<String>();
                        List<DNARun> dnaRunList = getQueryList(condition);
                        for (DNARun dnaRun : dnaRunList) {
                            List<String> list = querySamples(dnaRun);
                            for (String runNo : list) {
                                runNoList.add(runNo);
                            }
                        }
                        result.put(groupName, runNoList);
                    } else {
                        /*SampleInfo dnaRun = getQuery(condition);
                        List<String> list = querySamples(dnaRun);
                        result.put(groupName, list);*/
                    }
                }
            }
        }
        return result;
    }

    /**
     * 根据条件获取对应样本编号
     *
     * @param dnaRun
     * @return
     */
    public List<String> querySamples(DNARun dnaRun) {
        List<String> result = new ArrayList<String>();
        List<DNARun> list = dnaRunDao.findList(dnaRun);
        for (DNARun oneDnaRun : list) {
            result.add(oneDnaRun.getRunNo());
        }
        return result;
    }
    /**
     * 根据条件查询dnaRun
     */
    public List<DNARun> queryByondition(DNARun dnaRun,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<DNARun> list = dnaRunDao.findList(dnaRun);
        return list;
    }

    /**
     * 根据条件查询样本数据
     *
     * @param group
     * @param page
     * @return
     */
    public Map queryDNARunByGroup(String group, Page<SampleInfoDto> page) {
        Map result = new HashMap();
        result.put("group", group);
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        JSONArray data = new JSONArray();
        if (StringUtils.isNotBlank(group)) {
            JSONObject one = JSONObject.fromObject(group);
            // 群组名字并未出现在查询中,为什么会使用到?
            String groupName = one.getString("name");
            String condition= one.getString("condition");
            SampleInfo sampleInfo=getQuery(condition);
            SampleInfoDto sampleInfoDto=new SampleInfoDto();
            BeanUtils.copyProperties(sampleInfo,sampleInfoDto);
            com.github.pagehelper.Page<Object> resultPage = PageHelper.startPage(page.getPageNo(), page.getPageSize());
            List<SampleInfoDto> list=dnaRunDao.findListWithTypeHandler(sampleInfoDto);
            page.setCount(resultPage.getTotal());
            page.setList(list);
            for (SampleInfoDto sampleInfoDtoItem : list) {
                data.add(sampleInfoDtoItem);
            }
        }
        result.put("total", page.getCount());
        result.put("data", data);
        return result;
    }

    /**
     * 动态查询dnarun
     */
    public PageInfo<DNARun> getByCondition(DnaRunDto dnaRunDto,Integer pageNum,Integer pageSize,String isPage){
        if(!StringUtils.isBlank(isPage)){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<DNARun> list=dnaRunDao.getListByCondition(dnaRunDto);
        PageInfo<DNARun> pageInfo=new PageInfo(list);
        return pageInfo;
    }

    public PageInfo<SampleInfoDto> getListByConditionWithTypeHandler(SampleInfoDto sampleInfoDto, Integer pageNum, Integer pageSize, String isPage){
        if(!StringUtils.isBlank(isPage)){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<SampleInfoDto> list=dnaRunDao.getListByConditionWithTypeHandler(sampleInfoDto);
        PageInfo<SampleInfoDto> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    /*public PageInfo<DNARunSearchResult> findListWithTypeHandler(DnaRunDto dnaRunDto, Integer pageNum, Integer pageSize, String isPage){
        if(!StringUtils.isBlank(isPage)){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<DNARunSearchResult> list=dnaRunDao.findListWithTypeHandler(dnaRunDto);
        PageInfo<DNARunSearchResult> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }*/

    public  List<DNARun> getAll(){
        return dnaRunDao.getListByCondition(new DnaRunDto());
    }

    public List<DNARun> getQueryList(String conditions) {
        JSONObject jsonObject = JSONObject.fromObject(conditions);
        List<DNARun> dnaRunList = new ArrayList<DNARun>();
        List<String> cultivarList = Arrays.asList(jsonObject.getString("cultivar").split(","));
        for (String cultivar:cultivarList) {
            DNARun dnaRun = new DNARun();
            if (cultivar.startsWith("?")) {
                String cultivarToSampleName = cultivar.substring(1);
                dnaRun.setSampleName(cultivarToSampleName);
                dnaRunList.add(dnaRun);
            }else {
                dnaRun.setCultivar(cultivar);
                dnaRunList.add(dnaRun);
            }
        }
        return dnaRunList;
    }

    /**
     * 根据查询参数json转换为DNARun实体
     *
     * @param conditions
     * @return
     */
    private SampleInfo getQuery(String conditions) {
        JSONObject jsonObject = JSONObject.fromObject(conditions);
        SampleInfo sampleInfo = new SampleInfo();
        if (jsonObject.containsKey("scientificName")) {
            sampleInfo.setScientificName(jsonObject.getString("scientificName"));
        }
        if (jsonObject.containsKey("locality")) {
            sampleInfo.setLocality(jsonObject.getString("locality"));
        }
        if (jsonObject.containsKey("sampleId")) {
            sampleInfo.setSampleId(jsonObject.getString("sampleId"));
        }
        if (jsonObject.containsKey("strainName")) {
            sampleInfo.setStrainName(jsonObject.getString("strainName"));
        }
        if (jsonObject.containsKey("preservationLocation")) {
            sampleInfo.setPreservationLocation(jsonObject.getString("preservationLocation"));
        }
        if (jsonObject.containsKey("type")) {
            sampleInfo.setType(jsonObject.getString("type"));
        }
        if (jsonObject.containsKey("environment")) {
            sampleInfo.setEnvironment(jsonObject.getString("environment"));
        }
        if (jsonObject.containsKey("materials")) {
            sampleInfo.setMaterials(jsonObject.getString("materials"));
        }
        if (jsonObject.containsKey("treat")) {
            sampleInfo.setTreat(jsonObject.getString("treat"));
        }
        if (jsonObject.containsKey("definitionTime")) {
            sampleInfo.setDefinitionTime(jsonObject.getString("definitionTime"));
        }
        if (jsonObject.containsKey("taxonomy")) {
            sampleInfo.setTaxonomy(jsonObject.getString("taxonomy"));
        }
        if (jsonObject.containsKey("myceliaPhenotype")) {
            sampleInfo.setMyceliaPhenotype(jsonObject.getString("myceliaPhenotype"));
        }
        if (jsonObject.containsKey("myceliaDiameter")) {
            sampleInfo.setMyceliaDiameter(jsonObject.getString("myceliaDiameter"));
        }
        if (jsonObject.containsKey("myceliaColor")) {
            sampleInfo.setMyceliaColor(jsonObject.getString("myceliaColor"));
        }
        return sampleInfo;
    }

    public JSONArray searchStudybyKeywords(String type, String keywords, Page<DNARun> page) {
        JSONArray data = new JSONArray();
        DNARun dnaRun = new DNARun();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                dnaRun.setKeywords(keywords);
            }//空白查询所有
        }
        dnaRun.setPage(page);
        List<DNARun> list = dnaRunDao.findList(dnaRun);
        for (DNARun dnaRun1 : list) {
            data.add(dnaRun1.toJSON());
        }
        page.setList(list);
        return data;
    }

    public boolean add(DNARun dnaRun) {
        return dnaRunDao.insert(dnaRun);
    }

    public DNARun findById(int id) {
        return dnaRunDao.get(String.valueOf(id));
    }

    public int update(DNARun dnaRun) {
        return dnaRunDao.update(dnaRun);
    }

    public boolean delete(int id) {
        return dnaRunDao.deleteById(id);
    }

    public PageInfo<DNARun> getByCultivar(List<String> ids,Integer pageNum,Integer pageSize){
        List<DNARun> list= Lists.newArrayList();
        PageHelper.startPage(pageNum,pageSize);
        if(CollectionUtils.isNotEmpty(ids)){
            list=dnaRunDao.getByCultivar(ids);
        }
        return new PageInfo<>(list);
    }
}
