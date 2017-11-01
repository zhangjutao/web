package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.AdvanceInfo;

import java.util.List;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@MyBatisDao
public interface AdvanceInfoDao extends CrudDao<AdvanceInfo> {

    List<AdvanceInfo> findList();

    boolean saveAdvanceinfo(AdvanceInfo advanceInfo);

    AdvanceInfo findAdvanceInfoById(int id);

    boolean updateAdvanceInfo(AdvanceInfo advanceInfo);

    boolean deleteAdvanceInfo(int id);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int insertBatch(List<AdvanceInfo> list);

    List<AdvanceInfo> findByTypeAllList(AdvanceInfo advanceInfo);
}
