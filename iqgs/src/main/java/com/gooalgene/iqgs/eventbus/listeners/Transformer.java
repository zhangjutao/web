package com.gooalgene.iqgs.eventbus.listeners;

import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;

import java.util.List;

/**
 * 视图转换器，将AdvanceSearchResultView转换为GeneId
 */
public interface Transformer {

    List<String> transformViewToId(List<AdvanceSearchResultView> searchResult);
}
