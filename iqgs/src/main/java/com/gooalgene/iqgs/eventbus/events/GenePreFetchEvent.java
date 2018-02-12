package com.gooalgene.iqgs.eventbus.events;

/**
 * 基因基本信息Mongodb数据预加载事件
 */
public class GenePreFetchEvent<T> {

    private String geneId;

    /**
     * 预加载结果数据
     */
    private final Class<T> type;

    public GenePreFetchEvent(String geneId, Class<T> t) {
        this.geneId = geneId;
        this.type = t;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public Class<T> getType() {
        return type;
    }
}
