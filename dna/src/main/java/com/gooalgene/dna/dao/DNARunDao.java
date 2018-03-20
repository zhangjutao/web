package com.gooalgene.dna.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.dto.SampleInfoDto;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.SampleInfo;
import com.gooalgene.dna.entity.result.DNARunSearchResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ShiYun on 2017/9/6 0006.
 */
@MyBatisDao
public interface DNARunDao extends CrudDao<SampleInfo> {

    int insertBatch(List<DNARun> list);

    boolean deleteById(int id);

    List<SampleInfoDto> getListByCondition(SampleInfoDto sampleInfoDto);

    List<SampleInfoDto> getListByConditionWithTypeHandler(SampleInfoDto sampleInfoDto);

    List<DNARun> getByCultivar(List<String> list);

    /**
     * 根据一组基因ID列表,获取完整dna_run信息
     * @param list 基因ID列表
     * @param onlyRunNo 结果列表是否只显示ID字段
     * @return 完整dna_run信息
     */
    List<SampleInfoDto> getByCultivarForExport(@Param("list") List<String> list, @Param("onlyRunNo") boolean onlyRunNo);

    List<SampleInfoDto> findListWithTypeHandler(SampleInfoDto sampleInfoDto);
}
