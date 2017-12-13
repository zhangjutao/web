package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.TraitCategory;
import com.gooalgene.qtl.views.TraitCategoryWithinMultipleTraitList;

import java.util.List;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@MyBatisDao
public interface TraitCategoryDao extends CrudDao<TraitCategory> {
    boolean add(TraitCategory traitCategory);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int insertBatch(List<TraitCategory> list);

    boolean deleteById(int id);

    TraitCategory findById(int id);

    /**
     * 查找所有trait category以及所有trait list，trait list中包含的所有的qtl name
     */
    List<TraitCategoryWithinMultipleTraitList> findAllTraitCategoryAndItsTraitList();
}
