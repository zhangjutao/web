package com.gooalgene.iqgs.entity.condition;

import com.gooalgene.iqgs.entity.RegularityLink;
import com.gooalgene.iqgs.entity.RegularityNode;

import java.util.List;

/**
 * 调控网络输出结果
 * @author crabime
 */
public class RegularityResult {
    /**
     * 所有线
     */
    private List<RegularityLink> links;

    /**
     * 所有点
     */
    private List<RegularityNode> nodes;

    public RegularityResult(List<RegularityLink> links, List<RegularityNode> nodes) {
        this.links = links;
        this.nodes = nodes;
    }

    public List<RegularityLink> getLinks() {
        return links;
    }

    public void setLinks(List<RegularityLink> links) {
        this.links = links;
    }

    public List<RegularityNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<RegularityNode> nodes) {
        this.nodes = nodes;
    }
}
