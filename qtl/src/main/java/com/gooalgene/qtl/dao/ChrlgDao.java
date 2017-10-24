package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.Chrlg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@MyBatisDao
public interface ChrlgDao extends CrudDao<Chrlg> {
    Chrlg selectByChrAndLg(@Param("chr") String chr, @Param("lg") String lg);

    List<Chrlg> findAllList();

    boolean deleteById(int id);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int insertBatch(List<Chrlg> list);

    Chrlg get(int id);

    Chrlg selectByChrAndVersion(@Param("chr") String chr, @Param("version") String version);
}
