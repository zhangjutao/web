package com.gooalgene.common.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.MrnaGens;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 陈冬 on 2017/8/22.
 */
@MyBatisDao
public interface MrnaGensDao extends CrudDao<MrnaGens> {

    /**
     * 根据gens_baseinfo表中的基因ID，查询它对应的GENE_NAME、FUNCTION
     * @param geneId 基因ID
     * @return MRNAGens对象
     */
    MrnaGens findMRNAGeneByGeneId(@Param("geneId") String geneId);

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
