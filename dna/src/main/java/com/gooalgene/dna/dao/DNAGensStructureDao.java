package com.gooalgene.dna.dao;


import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.dna.entity.DNAGenStructure;

import java.util.List;

@MyBatisDao
public interface DNAGensStructureDao {
    int insert(DNAGenStructure record);

    int insertSelective(DNAGenStructure record);

    List<DNAGenStructure> getByGeneId(String geneId);
}