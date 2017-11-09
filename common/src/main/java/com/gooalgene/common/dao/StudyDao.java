package com.gooalgene.common.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.Study;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by ShiYun on 2017/8/2 0002.
 */
@MyBatisDao
public interface StudyDao extends CrudDao<Study> {

    List<Map> findByCondition(Study study);

    List<Study> findBySID(String id);

    List<Study> findBySampleRuns(String[] runs);

    List<Map> findByRuns(String[] runs);

    List<Map> findGenesForFirst();

    Map findGenesForFirstById(Integer id);

    int updateGenesForFirstById(@Param("id") Integer id, @Param("gene") String gene);

    Study findBySampleRun(String run);

    List<Map> queryScientificNames();

    List<Map> queryLibraryLayouts();

    List<Map> findStudyMap(Study study);

    List<Study> findStudyList(Study study);

    boolean add(Study study);

    Study findById(int id);

    boolean deleteById(int id);

    int insertBatch(List<Study> toInsert);

    List<String> findSampleruns();
}
