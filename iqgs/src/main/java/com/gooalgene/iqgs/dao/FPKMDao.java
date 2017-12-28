package com.gooalgene.iqgs.dao;

import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.iqgs.entity.GeneFPKM;

import java.util.List;

@MyBatisDao
public interface FPKMDao {

    /**
     * 在某一个samplerun下找到位于指定FPKM区间的基因
     * @param sampleRunId 位于study表中sampleRun的ID值
     * @param begin FPKM起始值
     * @param end FPKM终止值
     * @return 满足要求的基因集合
     */
    List<GeneFPKM> findProperGeneUnderSampleRun(int sampleRunId, double begin, double end);
}
