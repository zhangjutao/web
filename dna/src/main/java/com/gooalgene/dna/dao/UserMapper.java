package com.gooalgene.dna.dao;

import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.dna.entity.testG.User;
import com.gooalgene.dna.entity.testG.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}