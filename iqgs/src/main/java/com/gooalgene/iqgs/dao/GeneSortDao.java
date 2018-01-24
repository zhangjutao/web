package com.gooalgene.iqgs.dao;

import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;

/**
 * 基因排序DAO层
 */
@MyBatisDao
public interface GeneSortDao {

    SortedSearchResultView findViewByGeneId(String geneId);
}
