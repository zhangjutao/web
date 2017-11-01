package com.gooalgene.common.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.IndexExplain;

/**
 * Created by ShiYun on 2017/8/28 0028.
 */
@MyBatisDao
public interface IndexExplainDao extends CrudDao<IndexExplain> {

    IndexExplain findByType(String type);

    int update(IndexExplain indexExplain);
}
