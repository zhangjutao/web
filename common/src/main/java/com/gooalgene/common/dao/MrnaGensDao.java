package com.gooalgene.common.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.MrnaGens;

import java.util.List;
import java.util.Map;

/**
 * Created by 陈冬 on 2017/8/22.
 */
@MyBatisDao
public interface MrnaGensDao extends CrudDao<MrnaGens> {

    List<MrnaGens> findMrnaGensList(MrnaGens mrnaGens);

    boolean add(MrnaGens mrnaGens);

    MrnaGens findById(int id);

    boolean deleteById(int id);

    int insertBatch(List<MrnaGens> toInsert);

    List<Map> findMrnaGensMap(MrnaGens mrnaGens);

    boolean deleteSpecificMrnaGensInfo();

    int findMrnaGensInfoByGene(String gene);

    boolean updateByGene(MrnaGens mrnaGens);

    int updateBatch(List<MrnaGens> toUpdate);
}
