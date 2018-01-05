package com.gooalgene.iqgs.dao;

import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.condition.GeneExpressionCondition;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;

import java.util.List;

@MyBatisDao
public interface FPKMDao {

    /**
     * 通过基因表达量条件查找关联基因
     * @param condition 基因表达量筛选条件,每一大组织对应一个GeneExpressionConditionEntity
     * @return 所有关联基因ID
     */
    // todo 增加SNP、INDEL做关联查询
    List<String> findGeneThroughGeneExpressionCondition(List<GeneExpressionConditionEntity> condition,
                                                        List<String> selectSnp, List<String> selectIndel);
}
