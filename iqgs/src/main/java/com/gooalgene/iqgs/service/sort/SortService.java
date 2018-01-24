package com.gooalgene.iqgs.service.sort;

import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;

public interface SortService {
    /**
     * 对查询出来的结果视图进行计算排序均值
     * @param view 根据基因ID查询出来的待排序结果
     */
    int sort(SortedSearchResultView view);
}
