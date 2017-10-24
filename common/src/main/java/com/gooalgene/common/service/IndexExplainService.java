package com.gooalgene.common.service;

import com.gooalgene.common.dao.IndexExplainDao;
import com.gooalgene.entity.IndexExplain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ShiYun on 2017/8/28 0028.
 */
@Service
public class IndexExplainService {

    @Autowired
    private IndexExplainDao indexExplainDao;

    public IndexExplain queryByType(String type) {
        return indexExplainDao.findByType(type);
    }

    public List<IndexExplain> queryAll() {
        return indexExplainDao.findList(new IndexExplain());
    }

    public int update(IndexExplain indexExplain) {
        return indexExplainDao.update(indexExplain);
    }
}
