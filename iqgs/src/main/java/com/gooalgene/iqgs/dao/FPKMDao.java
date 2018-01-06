package com.gooalgene.iqgs.dao;

import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.condition.GeneExpressionCondition;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface FPKMDao {

    /**
     * 通过基因表达量条件查找关联基因
     * @param condition 基因表达量筛选条件,每一大组织对应一个GeneExpressionConditionEntity
     * @param selectSnp 已选SNP值
     * @param selectIndel 已选INDEL值
     * @param associateGeneId 已选QTL associateGeneId
     * @return 所有关联基因ID
     */
    List<String> findGeneThroughGeneExpressionCondition(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                                        @Param("snp") List<String> selectSnp,
                                                        @Param("indel") List<String> selectIndel,
                                                        @Param("qtl") List<Integer> associateGeneId);
}
