package com.gooalgene.dna.dao;


import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.dna.entity.DNAGenStructure;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface DNAGensStructureDao {
    int insert(DNAGenStructure record);

    int insertSelective(DNAGenStructure record);

    List<DNAGenStructure> getByGeneId(String geneId);

    List<DNAGenStructure> getByStartEnd(@Param("chromosome")String chr,@Param("start")Integer start,@Param("end")Integer end);
}