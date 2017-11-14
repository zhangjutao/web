package com.gooalgene.dna.service;

import com.github.pagehelper.PageHelper;
import com.gooalgene.common.Page;
import com.gooalgene.dna.dao.DNARunDao;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.entity.DNARun;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ShiYun on 2017/9/6 0006.
 */
@Service
public class DNARunService {

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
        Map<String, List<String>> result = new HashMap();
        if (StringUtils.isNotBlank(group)) {
            JSONArray data = JSONArray.fromObject(group);
            int len = data.size();
            for (int i = 0; i < len; i++) {
                JSONObject one = data.getJSONObject(i);
                String groupName = one.getString("name");
                String condition = one.getString("condition");
                DNARun dnaRun = getQuery(condition);
                List<String> list = querySamples(dnaRun);
                System.out.println(groupName + "," + list.size());
                result.put(groupName, list);
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
        for (DNARun dnaRun1 : list) {
            result.add(dnaRun1.getRunNo());
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
    public Map queryDNARunByGroup(String group, Page<DNARun> page) {
        Map result = new HashMap();
        result.put("group", group);
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        JSONArray data = new JSONArray();
        if (StringUtils.isNotBlank(group)) {
            JSONObject one = JSONObject.fromObject(group);
            String groupName = one.getString("name");
            String condition = one.getString("condition");
            DNARun dnaRun = getQuery(condition);
            dnaRun.setPage(page);
            List<DNARun> list = dnaRunDao.findList(dnaRun);
            System.out.println("Size:" + list.size());
            page.setList(list);
            for (DNARun dnaRun1 : list) {
                data.add(dnaRun1.toJSON());
            }
        }
        result.put("total", page.getCount());
        result.put("data", data);
        return result;
    }

    /**
     * 动态查询dnarun
     */
    public List<DNARun> getByCondition(DnaRunDto dnaRunDto){

        return dnaRunDao.getListByCondition(dnaRunDto);
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
}
