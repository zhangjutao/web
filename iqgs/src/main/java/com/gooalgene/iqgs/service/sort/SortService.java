package com.gooalgene.iqgs.service.sort;

import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;

import java.util.List;
import java.util.Map;

public interface SortService {
    /**
     * 对查询出来的结果视图进行计算排序均值s
     * @param views 根据基因ID查询出来的待排序结果
     */
    List<SortedSearchResultView> sort(List<SortedSearchResultView> views) throws IllegalAccessException;
}
