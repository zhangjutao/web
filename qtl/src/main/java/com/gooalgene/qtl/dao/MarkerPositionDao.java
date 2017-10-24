package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.MarkerPosition;

import java.util.List;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@MyBatisDao
public interface MarkerPositionDao extends CrudDao<MarkerPosition> {

    /**
     * 批量添加数据，单条添加耗时
     *
     * @param list
     * @return
     */
    int insertBatch(List<MarkerPosition> list);

    List<MarkerPosition> findAllList();

    MarkerPosition findById(int id);

    boolean deleteById(int id);

    /**
     * 后台管理 分页
     *
     * @param markerPosition
     * @return
     */
    List<MarkerPosition> findByTypeAllList(MarkerPosition markerPosition);
}
