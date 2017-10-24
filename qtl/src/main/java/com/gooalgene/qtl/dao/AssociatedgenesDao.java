package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.Associatedgenes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    Associatedgenes findAssociatedgenesById(int id);

    boolean updateAssociatedgenes(Associatedgenes associatedgenes);

    boolean deleteAssociatedgenes(int id);

    List<Associatedgenes> findByTypeAllList(Associatedgenes associatedgenes);
}
