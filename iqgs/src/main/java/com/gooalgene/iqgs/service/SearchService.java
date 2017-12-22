package com.gooalgene.iqgs.service;

import com.gooalgene.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 搜索相关服务
 * @author crabime
 */
@Service
public class SearchService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<String> findAllDistinctSNP(){
        return mongoTemplate.getCollection(CommonConstant.FIRSTSNPCHROMOSOME).distinct(CommonConstant.CONSEQUENCETYPE);
    }

    public List<String> findAllDistinctINDEL(){
        return mongoTemplate.getCollection(CommonConstant.FIRSTINDELCHROMOSOME).distinct(CommonConstant.CONSEQUENCETYPE);
    }
}
