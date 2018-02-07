package com.gooalgene.iqgs.dao;

import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.iqgs.entity.ConsequenceEntity;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.entity.condition.DNAGeneSearchResult;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import com.gooalgene.iqgs.entity.condition.RangeSearchResult;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     * 根据associateGeneId查找与之关联的搜索列表
     * @param associateGeneId QTL关联的ID
     * @param start MySQL分页的起始位置
     * @param end MySQL分页终点位置
     */
    List<RangeSearchResult> findViewByQtl(@Param("qtl") List<Integer> associateGeneId, int start, int end);

    /**
     * 计算QTL查询的总基因个数
     */
    int countBySearchQtl(@Param("qtl") List<Integer> associateGeneId);

    /**
     * 缓存QTL一级搜索全部基因列表
     */
    List<String> cacheFindViewByQtl(@Param("qtl") List<Integer> associateGeneId);

    /**
     * 一级搜索为QTL的高级搜索查询DAO层
     */
    List<RangeSearchResult> advanceSearchByQtl(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                               @Param("snp") List<Integer> selectSnp,
                                               @Param("indel") List<Integer> selectIndel,
                                               @Param("firstHierarchyQtlId") List<Integer> firstHierarchyQtlId,
                                               @Param("qtl") List<Integer> associateGeneId, @Param("start") int start, @Param("end") int end);

    /**
     * 计算QTL高级搜索总条数
     */
    int countAdvanceSearchByQtl(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                @Param("snp") List<Integer> selectSnp,
                                @Param("indel") List<Integer> selectIndel,
                                @Param("firstHierarchyQtlId") List<Integer> firstHierarchyQtlId,
                                @Param("qtl") List<Integer> associateGeneId);

    /**
     * 缓存根据QTL高级搜索获取的基因列表
     */
    List<String> cacheAdvanceSearchByQtl(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                         @Param("snp") List<Integer> selectSnp,
                                         @Param("indel") List<Integer> selectIndel,
                                         @Param("firstHierarchyQtlId") List<Integer> firstHierarchyQtlId,
                                         @Param("qtl") List<Integer> associateGeneId);

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
     * 染色体区间高级搜索
     */
    List<RangeSearchResult> advanceSearchByRegion(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                           @Param("snp") List<Integer> selectSnp,
                                           @Param("indel") List<Integer> selectIndel,
                                           @Param("qtl") List<Integer> associateGeneId,
                                           @Param("chromosome") String chromosome,
                                           @Param("geneStart") int start,
                                           @Param("geneEnd") int end,
                                           @Param("start") int offset,
                                           @Param("end") int pageSize);

    /**
     * 染色体区间搜索count总值
     */
    int countAdvanceSearchByRegion(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                   @Param("snp") List<Integer> selectSnp,
                                   @Param("indel") List<Integer> selectIndel,
                                   @Param("qtl") List<Integer> associateGeneId,
                                   @Param("chromosome") String chromosome,
                                   @Param("geneStart") int start,
                                   @Param("geneEnd") int end);

    /**
     * 缓存根据染色体区间高级搜索结果
     */
    List<String> cacheAdvanceSearchByRegion(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                            @Param("snp") List<Integer> selectSnp,
                                            @Param("indel") List<Integer> selectIndel,
                                            @Param("qtl") List<Integer> associateGeneId,
                                            @Param("chromosome") String chromosome,
                                            @Param("geneStart") int start,
                                            @Param("geneEnd") int end);

    /**
     * function搜索后对应的高级搜索结果
     */
    List<RangeSearchResult> advanceSearchByFunction(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                                  @Param("snp") List<Integer> selectSnp,
                                                  @Param("indel") List<Integer> selectIndel,
                                                  @Param("qtl") List<Integer> associateGeneId,
                                                  @Param("func") String function,
                                                  @Param("start") int start,
                                                  @Param("end") int pageSize);

    /**
     * function高级搜索总条数
     */
    int countAdvanceSearchByFunction(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                     @Param("snp") List<Integer> selectSnp,
                                     @Param("indel") List<Integer> selectIndel,
                                     @Param("qtl") List<Integer> associateGeneId,
                                     @Param("func") String function);

    /**
     * 缓存根据NAME/FUNCTION高级搜索结果列表基因ID
     */
    List<String> cacheAdvanceSearchByFunction(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                              @Param("snp") List<Integer> selectSnp,
                                              @Param("indel") List<Integer> selectIndel,
                                              @Param("qtl") List<Integer> associateGeneId,
                                              @Param("func") String function);

    /**
     * Mybatis无法对连接查询进行分页,这里先手动计算总条数
     */
    int countBySearchRegion(String chromosome, int start, int end);

    List<RangeSearchResult> searchByRegion(String chromosome, int start, int end, int pageNo, int pageSize);

    /**
     * 缓存染色体区间搜索一级搜索基因列表
     */
    List<String> cacheSearchByRegion(String chromosome, int start, int end);

    /**
     * 保证基因ID不为空，根据ID高级搜索
     */
    List<RangeSearchResult> advanceSearchByGeneId(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                                        @Param("snp") List<Integer> selectSnp,
                                                        @Param("indel") List<Integer> selectIndel,
                                                        @Param("qtl") List<Integer> associateGeneId,
                                                        @Param("geneId") String geneId, @Param("start") int start, @Param("end") int end);

    /**
     * 计算根据基因ID高级搜索查询总条数
     */
    int countAdvanceSearchByGeneId(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                   @Param("snp") List<Integer> selectSnp,
                                   @Param("indel") List<Integer> selectIndel,
                                   @Param("qtl") List<Integer> associateGeneId,
                                   @Param("geneId") String geneId);

    /**
     * 缓存用户通过GeneId高级搜索查询出来的GeneId，只给EventBus接收者调用
     */
    List<String> cacheAdvanceSearchByGeneId(@Param("geneExpression") List<GeneExpressionConditionEntity> condition,
                                            @Param("snp") List<Integer> selectSnp,
                                            @Param("indel") List<Integer> selectIndel,
                                            @Param("qtl") List<Integer> associateGeneId,
                                            @Param("geneId") String geneId);

    /**
     * 检查某一基因对应consequencetype中是否存在SNP
     */
    boolean checkExistSNP(String fpkmGeneId, String snpConsequenceType);

    /**
     * 获取所有snp_consequencetype或indel_consequencetype表中consequencetype、id字段,启动时放入缓存
     * @param type "SNP"、"INDEL"
     * @return 存放consequencetype、id的map
     */
    List<ConsequenceEntity> getAllConsequenceTypeAndItsId(@Param("type") String type);

}
