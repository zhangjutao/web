package com.gooalgene.iqgs.dao;

import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
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
    List<AdvanceSearchResultView> findGeneThroughGeneExpressionCondition(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                                                         @Param("snp") List<String> selectSnp,
                                                                         @Param("indel") List<String> selectIndel,
                                                                         @Param("firstHierarchyQtlId") List<Integer> firstHierarchyQtlId,
                                                                         @Param("qtl") List<Integer> associateGeneId,
                                                                         @Param("searchById") DNAGenBaseInfo searchedGene,
                                                                         @Param("structure") DNAGenStructure geneStructure);

    /**
     * 拿到前一百个基因对应的高级搜索信息
     */
    List<AdvanceSearchResultView> fetchFirstHundredGene(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                                        @Param("snp") List<String> selectSnp,
                                                        @Param("indel") List<String> selectIndel,
                                                        @Param("qtl") List<Integer> associateGeneId,
                                                        @Param("geneId") List<Integer> firstHundredGeneId);

    /**
     * 拿到前一百个基因对应的高级搜索信息，这里根据基因结构ID来进行IN查询
     */
    List<AdvanceSearchResultView> fetchFirstHundredGeneInGeneStructure(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                                                       @Param("snp") List<String> selectSnp,
                                                                       @Param("indel") List<String> selectIndel,
                                                                       @Param("qtl") List<Integer> associateGeneId,
                                                                       @Param("structureId") List<DNAGenStructure> firstHundredStructureId);

    /**
     * 检查某一基因对应consequencetype中是否存在SNP
     */
    boolean checkExistSNP(int fpkmId, String snpConsequenceType);
}
