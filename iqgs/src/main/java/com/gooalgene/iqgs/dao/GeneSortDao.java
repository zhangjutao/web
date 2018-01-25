package com.gooalgene.iqgs.dao;

import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基因排序DAO层
 */
@MyBatisDao
public interface GeneSortDao {

    SortedSearchResultView findViewByGeneId(@Param("geneIds") List<String> geneIds,@Param("fields") String fields,@Param("categoryId") Integer categoryId);
}
