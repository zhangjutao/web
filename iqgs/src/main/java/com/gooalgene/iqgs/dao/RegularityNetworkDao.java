package com.gooalgene.iqgs.dao;

import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.iqgs.entity.RegularityNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface RegularityNetworkDao {

    /**
     * 查询某一层级上与geneId相关联的调控网络基因
     * @param geneId 传入的GeneId
     * @param hierarchy 层级: 0:为原始基因，1:第一层关系，2:第二层关系
     * @return 返回该层级上应该返回的所有调控网络基因
     */
    List<RegularityNode> findRelateGene(@Param("geneId") String geneId, @Param("hierarchy") int hierarchy);

    /**
     * 查询某一层级上与geneId相关联的调控网络基因（反转查询）
     * source与target之间反转
     * @param geneId 传入的GeneId
     * @param hierarchy 层级: 0:为原始基因，1:第一层关系，2:第二层关系
     * @return 返回该层级上应该返回的所有调控网络基因
     */
    List<RegularityNode> findReverseRelateGene(@Param("geneId") String geneId, @Param("hierarchy") int hierarchy);
}
