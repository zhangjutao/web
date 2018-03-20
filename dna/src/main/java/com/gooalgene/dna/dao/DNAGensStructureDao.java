package com.gooalgene.dna.dao;


import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.dna.dto.DNAGenStructureDto;
import com.gooalgene.dna.entity.DNAGenStructure;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@MyBatisDao
public interface DNAGensStructureDao {
    int insert(DNAGenStructure record);

    int insertSelective(DNAGenStructure record);

    List<DNAGenStructureDto> getByGeneId(String geneId);

    List<DNAGenStructureDto> getByStartEnd(@Param("chromosome")String chr, @Param("start")long start,
                                           @Param("end")long end, @Param("geneIds")Set<String> geneIds);

    List<DNAGenStructure> getGeneStructureByCondition(@Param("chromosome")String chr, @Param("start")Long start, @Param("end")Long end);

    List<DNAGenStructure> fetchAllChromosomeAndID(String chromosome);

    List<String> fetchAllChromosome();
}