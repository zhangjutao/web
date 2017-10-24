package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.TraitList;

import java.util.List;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@MyBatisDao
public interface TraitListDao extends CrudDao<TraitList> {

    List<TraitList> getTraitListsByQtlId(String id);

    List<TraitList> findAllList();

    boolean add(TraitList traitList);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int insertBatch(List<TraitList> list);

    boolean deleteById(int id);

    TraitList findById(int id);
}
