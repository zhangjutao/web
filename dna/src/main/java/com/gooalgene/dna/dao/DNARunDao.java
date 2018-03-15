package com.gooalgene.dna.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.dto.SampleInfoDto;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.result.DNARunSearchResult;

import java.util.List;

/**
 * Created by ShiYun on 2017/9/6 0006.
 */
@MyBatisDao
public interface DNARunDao extends CrudDao<DNARun> {

    int insertBatch(List<DNARun> list);

    boolean deleteById(int id);

    List<DNARun> getListByCondition(DnaRunDto dnaRunDto);

    List<DNARunSearchResult> getListByConditionWithTypeHandler(DnaRunDto dnaRunDto);

    List<DNARun> getByCultivar(List<String> list);

    List<DNARun> getByCultivarForExport(List<String> list);

    List<DNARunSearchResult> findListWithTypeHandler(SampleInfoDto sampleInfoDto);
}
