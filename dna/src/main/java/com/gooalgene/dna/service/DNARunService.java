package com.gooalgene.dna.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.dna.dao.DNARunDao;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.dto.SampleInfoDto;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.result.DNARunSearchResult;
import com.google.common.collect.Lists;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

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


    /**
     * 根据页面分组查询对应的样本信息
     *
     * @param group
     * @return
     */
    public Map<String, List<String>> queryDNARunByCondition(String group) {
        if (group.equals("[{}]")) {
            group = "[]";
        }
        Map<String, List<String>> result = new HashMap();
        if (StringUtils.isNotBlank(group)) {
            logger.info(group);
            JSONArray data = JSONArray.fromObject(group);
            int len = data.size();
            for (int i = 0; i < len; i++) {
                JSONObject one = data.getJSONObject(i);
                String groupName = one.getString("name");
                if (one.containsKey("condition")) {
                    String condition = one.getString("condition");
                    if (condition.indexOf("{\"”cultivar") != -1) {
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
                        DNARun dnaRun = getQuery(condition);
                        List<String> list = querySamples(dnaRun);
                        result.put(groupName, list);
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
    public Map queryDNARunByGroup(String group, Page<DNARunSearchResult> page) {
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
            DNARun dnaRun=getQuery(condition);
            SampleInfoDto sampleInfoDto=new SampleInfoDto();
            BeanUtils.copyProperties(dnaRun,sampleInfoDto);
            com.github.pagehelper.Page<Object> resultPage = PageHelper.startPage(page.getPageNo(), page.getPageSize());
            List<DNARunSearchResult> list=dnaRunDao.findListWithTypeHandler(sampleInfoDto);
            page.setCount(resultPage.getTotal());
            page.setList(list);
            for (DNARunSearchResult dnaRunSearchResult : list) {
                data.add(dnaRunSearchResult.toJSON());
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

    public PageInfo<DNARunSearchResult> getListByConditionWithTypeHandler(DnaRunDto dnaRunDto, Integer pageNum, Integer pageSize, String isPage){
        if(!StringUtils.isBlank(isPage)){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<DNARunSearchResult> list=dnaRunDao.getListByConditionWithTypeHandler(dnaRunDto);
        PageInfo<DNARunSearchResult> pageInfo=new PageInfo<>(list);
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
    private DNARun getQuery(String conditions) {
        JSONObject jsonObject = JSONObject.fromObject(conditions);
        DNARun dnaRun = new DNARun();
        if (jsonObject.containsKey("species")) {
            dnaRun.setSpecies(jsonObject.getString("species"));
        }
        if (jsonObject.containsKey("locality")) {
            dnaRun.setLocality(jsonObject.getString("locality"));
        }
        if (jsonObject.containsKey("weightPer100seeds")) {
            JSONObject content = jsonObject.getJSONObject("weightPer100seeds");
            dnaRun.setWeightPer100seeds_min(content.getString("min"));
            dnaRun.setWeightPer100seeds_max(content.getString("max"));
        }
        if (jsonObject.containsKey("oil")) {
            JSONObject content = jsonObject.getJSONObject("oil");
            dnaRun.setOil_min(content.getString("min"));
            dnaRun.setOil_max(content.getString("max"));
        }
        if (jsonObject.containsKey("protein")) {
            JSONObject content = jsonObject.getJSONObject("protein");
            dnaRun.setProtein_min(content.getString("min"));
            dnaRun.setProtein_max(content.getString("max"));
        }
        if (jsonObject.containsKey("floweringDate")) {
//            dnaRun.setFloweringDate(jsonObject.getString("floweringDate"));
        }
        if (jsonObject.containsKey("maturityDate")) {
            dnaRun.setMaturityDate(jsonObject.getString("maturityDate"));
        }
        if (jsonObject.containsKey("height")) {
            JSONObject content = jsonObject.getJSONObject("height");
            dnaRun.setHeight_min(content.getString("min"));
            dnaRun.setHeight_max(content.getString("max"));
        }
        if (jsonObject.containsKey("seedCoatColor")) {
            dnaRun.setSeedCoatColor(jsonObject.getString("seedCoatColor"));
        }
        if (jsonObject.containsKey("hilumColor")) {
            dnaRun.setHilumColor(jsonObject.getString("hilumColor"));
        }
        if (jsonObject.containsKey("cotyledonColor")) {
            dnaRun.setCotyledonColor(jsonObject.getString("cotyledonColor"));
        }
        if (jsonObject.containsKey("flowerColor")) {
            dnaRun.setFlowerColor(jsonObject.getString("flowerColor"));
        }
        if (jsonObject.containsKey("podColor")) {
            dnaRun.setPodColor(jsonObject.getString("podColor"));
        }
        if (jsonObject.containsKey("pubescenceColor")) {
            dnaRun.setPubescenceColor(jsonObject.getString("pubescenceColor"));
        }
        if (jsonObject.containsKey("yield")) {
            JSONObject content = jsonObject.getJSONObject("yield");
            dnaRun.setYield_min(content.getString("min"));
            dnaRun.setYield_max(content.getString("max"));
        }
        if (jsonObject.containsKey("upperLeafletLength")) {
            JSONObject content = jsonObject.getJSONObject("upperLeafletLength");
            dnaRun.setUpperLeafletLength_min(content.getString("min"));
            dnaRun.setUpperLeafletLength_max(content.getString("max"));
        }
        if (jsonObject.containsKey("linoleic")) {
            JSONObject content = jsonObject.getJSONObject("linoleic");
            dnaRun.setLinoleic_min(content.getString("min"));
            dnaRun.setLinoleic_max(content.getString("max"));
        }
        if (jsonObject.containsKey("linolenic")) {
            JSONObject content = jsonObject.getJSONObject("linolenic");
            dnaRun.setLinolenic_min(content.getString("min"));
            dnaRun.setLinolenic_max(content.getString("max"));
        }
        if (jsonObject.containsKey("oleic")) {
            JSONObject content = jsonObject.getJSONObject("oleic");
            dnaRun.setOleic_min(content.getString("min"));
            dnaRun.setOleic_max(content.getString("max"));
        }
        if (jsonObject.containsKey("palmitic")) {
            JSONObject content = jsonObject.getJSONObject("palmitic");
            dnaRun.setPalmitic_min(content.getString("min"));
            dnaRun.setPalmitic_max(content.getString("max"));
        }
        if (jsonObject.containsKey("stearic")) {
            JSONObject content = jsonObject.getJSONObject("stearic");
            dnaRun.setStearic_min(content.getString("min"));
            dnaRun.setStearic_max(content.getString("max"));
        }
        if (jsonObject.containsKey("cultivar")) {
            dnaRun.setCultivar(jsonObject.getString("cultivar"));
        }
        return dnaRun;
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

    public PageInfo<DNARun> getByCultivar(List<String> cultivars,Integer pageNum,Integer pageSize){
        List<DNARun> list= Lists.newArrayList();
        PageHelper.startPage(pageNum,pageSize);
        if(CollectionUtils.isNotEmpty(cultivars)){
            list=dnaRunDao.getByCultivar(cultivars);
        }
        return new PageInfo<>(list);
    }
}
