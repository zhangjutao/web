package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.Qtlref;

import java.util.List;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@MyBatisDao
public interface QtlrefDao extends CrudDao<Qtlref> {

    List<Qtlref> findAllList();

    boolean add(Qtlref qtlref);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int insertBatch(List<Qtlref> list);

    Qtlref findById(int id);

    boolean deleteById(int id);

    List<Qtlref> findByTypeAllList(Qtlref qtlref);
}
