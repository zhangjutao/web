package com.gooalgene.iqgs.dao;

import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.iqgs.entity.sort.UserAssociateTraitFpkm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface UserAssociateTraitFpkmDao {
    int insert(UserAssociateTraitFpkm record);

    int insertSelective(UserAssociateTraitFpkm record);
}