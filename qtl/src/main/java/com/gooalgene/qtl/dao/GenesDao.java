package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.Genes;

import java.util.List;

/**
 * Created by ShiYun on 2017/8/9 0009.
 */
@MyBatisDao
public interface GenesDao extends CrudDao<Genes> {

    /**
     * 批量入库
     *
     * @param list
     * @return
     */
    int insertBatch(List<Genes> list);

    int getGeneTotal();

    int deleteById(int id);
}
