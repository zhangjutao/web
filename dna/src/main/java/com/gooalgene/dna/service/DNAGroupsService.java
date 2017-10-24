package com.gooalgene.dna.service;

import com.gooalgene.common.Page;
import com.gooalgene.dna.dao.DNAGroupsDao;
import com.gooalgene.dna.entity.DNAGroups;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ShiYun on 2017/9/18.
 */
@Service
public class DNAGroupsService {

    @Autowired
    private DNAGroupsDao dnaGroupsDao;

    /**
     * 查询自定义群体组
     *
     * @param keywords
     * @param page
     * @return
     */
    public JSONArray searchDNAGroupsbyKeywords(String keywords, Page<DNAGroups> page) {
        JSONArray data = new JSONArray();
        DNAGroups dnaGroups = new DNAGroups();
        if (StringUtils.isNotBlank(keywords)) {
            dnaGroups.setName(keywords);
        }
        dnaGroups.setPage(page);
        List<DNAGroups> list = dnaGroupsDao.findDNAGroupsList(dnaGroups);
        page.setList(list);
        for (DNAGroups dnaGroups1 : list) {
            data.add(dnaGroups1.toJSON());
        }
        return data;
    }

    @Transactional(readOnly = false)
    public boolean add(DNAGroups dnaGroups) {
        return dnaGroupsDao.add(dnaGroups);
    }

    public DNAGroups findById(int id) {
        return dnaGroupsDao.findById(id);
    }

    @Transactional(readOnly = false)
    public int update(DNAGroups dnaGroups) {
        return dnaGroupsDao.update(dnaGroups);
    }

    @Transactional(readOnly = false)
    public boolean delete(int id) {
        return dnaGroupsDao.deleteById(id);
    }

    public JSONArray searchAll() {
        JSONArray jsonArray = new JSONArray();
        List<DNAGroups> list = dnaGroupsDao.findDNAGroupsList(new DNAGroups());
        for (DNAGroups dnaGroups1 : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", dnaGroups1.getName());
            jsonObject.put("condition", dnaGroups1.getCondition());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
