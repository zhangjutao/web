package com.gooalgene.qtl.listeners;

import com.gooalgene.common.eventbus.EventBusListener;
import com.gooalgene.entity.Associatedgenes;
import com.gooalgene.entity.Qtl;
import com.gooalgene.qtl.dao.AssociatedgenesDao;
import com.gooalgene.qtl.dao.QtlDao;
import com.gooalgene.qtl.entity.QtlSearchResult;
import com.gooalgene.qtl.listeners.events.QtlSearchResultEvent;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component("qtlSearchDownloadListener")
public class SearchDownloadListener implements EventBusListener, InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(SearchDownloadListener.class);

    @Autowired
    private QtlDao qtlDao;

    @Autowired
    private AssociatedgenesDao associatedGenesDao;

    @Autowired
    private CacheManager cacheManager;

    protected Cache cache;

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = cacheManager.getCache("sortCache");
    }

    /**
     * 缓存key规则：事件名称 + "-" + 用户已选择的表头 + "-" + 搜索关键字
     * @param searchResult 事件参数
     */
    @AllowConcurrentEvents
    @Subscribe
    public void listenQtlSearch(QtlSearchResultEvent<?> searchResult){
        Object target = searchResult.getT();
        if (!(target instanceof Qtl)) {
            return;
        }
        Qtl qtl = (Qtl) target;
        String alternativeKeyWord = searchResult.getAlternativeKeyWord();
        List<QtlSearchResult> allSearchResult = qtlDao.findByCondition(qtl);
        // 拿到所有关联Qtl ID
        Collection<Integer> allAssociatedQtlId = Collections2.transform(allSearchResult, new Function<QtlSearchResult, Integer>() {
            @Override
            public Integer apply(QtlSearchResult input) {
                return input.getAssociateGeneId();
            }
        });
        List<Associatedgenes> allAssociatedGene = associatedGenesDao.getAllAssociatedGene(new ArrayList<>(allAssociatedQtlId));
        String qtlName = null;
        for (QtlSearchResult result : allSearchResult) {
            qtlName = result.getQtlName();
            Associatedgenes associatedGenes = getAssociatedGeneInList(allAssociatedGene, qtlName);
            if (associatedGenes != null) {
                // 拿到关联基因
                result.setAssociateGenes(associatedGenes.getAssociatedGenes());
            }
        }
        String key = searchResult.getClass().getSimpleName() + "-" + alternativeKeyWord;
        logger.info("当前写入缓存的key值为：" + key);
        // 将预加载的值放入缓存中，缓存两小时
        cache.putIfAbsent(key, allSearchResult);
    }

    /**
     * 检查目标qtl是否存在于集合中
     */
    private Associatedgenes getAssociatedGeneInList(List<Associatedgenes> associatedgenesList, String targetQtl) {
        for (Associatedgenes associatedgenes : associatedgenesList) {
            if (associatedgenes.getQtlName().equals(targetQtl)) {
                return associatedgenes;
            }
        }
        return null;
    }
}
