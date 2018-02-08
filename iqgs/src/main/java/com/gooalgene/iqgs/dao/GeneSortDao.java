package com.gooalgene.iqgs.dao;

import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.iqgs.entity.sort.CalculateScoreResult;
import com.gooalgene.iqgs.entity.sort.SortedResult;
import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基因排序DAO层
 */
@MyBatisDao
public interface GeneSortDao {

    List<SortedSearchResultView> findViewByGeneId(@Param("geneIds") List<String> geneIds,@Param("fields") String fields);

    List<CalculateScoreResult> findCalculateSortedResult(@Param("geneIds") List<String> geneIds,@Param("fields") List<String> fields,
                                                         @Param("traitCategoryId") int traitCategoryId, @Param("fieldSize") int size);

    List<SortedResult> findSortedResultThroughGeneId(@Param("geneIdList") List<String> geneIdList);

    List<String> getQtlNamesByTrait(Integer categoryId);
}
