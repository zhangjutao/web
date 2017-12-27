package com.gooalgene.iqgs.service;

import com.gooalgene.iqgs.dao.RegularityNetworkDao;
import com.gooalgene.iqgs.entity.RegularityLink;
import com.gooalgene.iqgs.entity.RegularityNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RegularityNetworkService {

    private final static Logger logger = LoggerFactory.getLogger(RegularityNetworkService.class);

    private static int ZERO = 0;  //原点位置

    private static int FIRST = 1;  //第一层级

    private static int SECOND = 2;  //第二层级

    @Autowired
    private RegularityNetworkDao regularityNetworkDao;

    /**
     * 根据传入的GeneID查找与它调控关联的N个基因，支持正向与反向
     * @param geneId 基因ID
     * @return 所有关联的调控网络数据
     */
    public List<RegularityLink> findRelateGene(String geneId){
        List<RegularityLink> result = new ArrayList<>();
        List<RegularityLink> relateGenes = regularityNetworkDao.findRelateGene(geneId, FIRST);  //查询第一层级关联基因
        result.addAll(relateGenes);
        logger.info("查询出来的一级总数为：" + relateGenes.size());
        RegularityLink node = null;
        for (int i = 0; i < relateGenes.size(); i++){
            node = relateGenes.get(i);
            String targetGeneId = node.getTarget();  //拿到目标基因
            List<RegularityLink> secondHierarchyGenes = regularityNetworkDao.findReverseRelateGene(targetGeneId, SECOND);
            logger.info("基因" + targetGeneId + "关联基因总个数为" + secondHierarchyGenes.size());
            result.addAll(secondHierarchyGenes);
            logger.info("当前序数为：" + i);
        }
        return result;
    }

    /**
     * 从一系列线中拿到所有不同的点
     * 判断规则：
     *      先判断是否为原点，如果source为原点，那target肯定都是第二层级，第二层级的100个元素肯定都互不相同
     *      如果source不为原点，那肯定为第二层级，则target肯定为第三层级，此时需要检查第三层级是否有重复，
     *      这里构建一个永不重复的List，如果该target之前出现过，则这个target不放入到集合中；否则，
     *      将node、targetGeneId放入各自集合中。这样就可以保证所有nodeList不重复，所有distinctGeneList也不重复
     *
     * @param links 由source、target组成的线
     * @param origin 原点基因
     * @return 所有基因ID组成的node
     */
    public List<RegularityNode> getAllDistinctGeneId(List<RegularityLink> links, String origin){
        List<RegularityNode> nodeList = new ArrayList<>();
        List<String> distinctGeneList = new ArrayList<>();  //存在不重复的基因ID
        RegularityNode sourceNode = null;
        RegularityNode targetNode = null;
        if (links == null || links.size() == 0){
            return nodeList;
        }
        for (int i = 0; i < links.size(); i++) {
            RegularityLink link = links.get(i);
            String source = link.getSource();
            String target = link.getTarget();
            // 原点基因定义基础层级0
            if (source.equals(origin)){
                sourceNode = new RegularityNode(source, ZERO);
                nodeList.add(sourceNode);
                distinctGeneList.add(source);
                targetNode = new RegularityNode(target, FIRST);
                nodeList.add(targetNode);
                distinctGeneList.add(target);
            }else {
                boolean containsSuchTarget = nodeList.contains(target);  //如果存在该种target
                if (!containsSuchTarget){
                    targetNode = new RegularityNode(target, SECOND);
                    nodeList.add(targetNode);
                    distinctGeneList.add(target);
                }
            }
        }
        return nodeList;
    }
}
