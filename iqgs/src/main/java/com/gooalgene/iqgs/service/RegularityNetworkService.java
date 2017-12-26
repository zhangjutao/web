package com.gooalgene.iqgs.service;

import com.gooalgene.iqgs.dao.RegularityNetworkDao;
import com.gooalgene.iqgs.entity.RegularityNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegularityNetworkService {

    private final static Logger logger = LoggerFactory.getLogger(RegularityNetworkService.class);

    private static int FIRST = 1;  //第一层级

    private static int SECOND = 2;  //第二层级

    @Autowired
    private RegularityNetworkDao regularityNetworkDao;

    /**
     * 根据传入的GeneID查找与它调控关联的N个基因，支持正向与反向
     * @param geneId 基因ID
     * @return 所有关联的调控网络数据
     */
    public List<RegularityNode> findRelateGene(String geneId){
        List<RegularityNode> result = new ArrayList<>();
        List<RegularityNode> relateGenes = regularityNetworkDao.findRelateGene(geneId, FIRST);  //查询第一层级关联基因
        result.addAll(relateGenes);
        logger.info("查询出来的一级总数为：" + relateGenes.size());
        RegularityNode node = null;
        for (int i = 0; i < relateGenes.size(); i++){
            node = relateGenes.get(i);
            String targetGeneId = node.getTarget();  //拿到目标基因
            List<RegularityNode> secondHierarchyGenes = regularityNetworkDao.findReverseRelateGene(targetGeneId, SECOND);
            logger.info("基因" + targetGeneId + "关联基因总个数为" + secondHierarchyGenes.size());
            result.addAll(secondHierarchyGenes);
            logger.info("当前序数为：" + i);
        }
        return result;
    }
}
