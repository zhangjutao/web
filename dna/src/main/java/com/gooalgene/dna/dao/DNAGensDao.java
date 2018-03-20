package com.gooalgene.dna.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.dna.entity.DNAGens;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by 陈冬 on 2017/8/22.
 */
@MyBatisDao
public interface DNAGensDao extends CrudDao<DNAGens> {

    List<DNAGens> findDNAGensList(DNAGens dnaGens);

    boolean add(DNAGens dnaGens);

    DNAGens findById(int id);

    DNAGens findByGeneId(String geneId);

    boolean deleteById(int id);

    int insertBatch(List<DNAGens> toInsert);

    DNAGens findDNAGensInfoByGene(String gene);

    Set<String> getByRegion(@Param("chr") String chr, @Param("start") long start, @Param("end") long end);
}
