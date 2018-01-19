package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.Associatedgenes;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@MyBatisDao
public interface AssociatedgenesDao extends CrudDao<Associatedgenes> {

    /**
     * 批量添加，单独添加耗时
     *
     * @param list
     * @return
     */
    int insertBatch(List<Associatedgenes> list);

    /**
     * 根据名称和版本查询关联基因信息
     *
     * @param qtlName
     * @param version
     * @return
     */
    Associatedgenes getByNameAndVersion(@Param("name") String qtlName, @Param("version") String version);

    List<Associatedgenes> findList();

    boolean insert(Associatedgenes associatedgenes);

    /**
     * 根据Id查找AssociateGene
     * @param id AssociateGeneId
     * @return 关联基因
     */
    Associatedgenes findAssociatedgenesById(int id);

    /**
     * 根据Gene ID查找包含它的所有QTL
     * @param geneId 基因ID
     * @return 包含该基因的QTL
     */
    Set<Associatedgenes> findAssociatedGeneByGeneId(@Param("geneId") int geneId);

    boolean updateAssociatedgenes(Associatedgenes associatedgenes);

    boolean deleteAssociatedgenes(int id);

    List<Associatedgenes> findByTypeAllList(Associatedgenes associatedgenes);
}
