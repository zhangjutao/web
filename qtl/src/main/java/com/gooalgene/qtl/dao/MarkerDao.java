package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.Marker;

import java.util.List;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@MyBatisDao
public interface MarkerDao extends CrudDao<Marker> {

    List<Marker> findList();

    boolean saveMarker(Marker marker);

    Marker findMarkerById(int id);

    boolean updateMarker(Marker marker);

    boolean deleteMarker(int id);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int insertBatch(List<Marker> list);

    /**
     * 后台管理分页查询
     *
     * @param marker
     * @return
     */
    List<Marker> findByTypeAllList(Marker marker);
}
