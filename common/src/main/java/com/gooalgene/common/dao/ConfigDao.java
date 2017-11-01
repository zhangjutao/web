package com.gooalgene.common.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.Configuration;

import java.util.List;

@MyBatisDao
public interface ConfigDao extends CrudDao<Configuration> {
    List<Configuration> getAllConfig();
}
