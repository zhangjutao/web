package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.Soybean;

import java.util.List;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@MyBatisDao
public interface SoybeanDao extends CrudDao<Soybean> {

    List<Soybean> findList();

    boolean save(Soybean soybean);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int insertBatch(List<Soybean> list);

    Soybean findSoyBeanById(int id);

    boolean updateSoybean(Soybean soybean);

    boolean deleteById(int id);
}
