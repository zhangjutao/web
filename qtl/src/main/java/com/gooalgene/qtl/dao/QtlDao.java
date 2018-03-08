package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.Qtl;
import com.gooalgene.qtl.entity.AsObjectForQtlTableEntity;
import com.gooalgene.qtl.entity.QtlTableEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@MyBatisDao
public interface QtlDao extends CrudDao<Qtl> {

    /**
     * 后台管理使用
     *
     * @param qtl
     * @return
     */
    List<Qtl> findQTLList(Qtl qtl);

    List<Map> findQTLMap(Qtl qtl);

    /**
     * 批量入库
     *
     * @param list
     * @return
     */
    int insertBatch(List<Qtl> list);

    /**
     * 根据条件匹配查询
     *
     * @param qtl
     * @return
     */
    List<AsObjectForQtlTableEntity> findByCondition(Qtl qtl);

    /**
     * 根据qtlName查询对应详情(由于qtl数据不用区分版本，此方法对应的的version字段已经在xml文件中去掉)
     *
     * @param qtlName
     * @return
     */
    Qtl getByQtlNameAndVersion(@Param("name") String qtlName, @Param("version") String version);

    /**
     * 提供画图数据
     *
     * @param lg
     * @return
     */
    List<Map> queryQtlByLg(String lg);

    /**
     * 首页搜索
     *
     * @param qtl
     * @return
     */
    List<Qtl> findByTypeAllList(Qtl qtl);

    /**
     * 根据chrlgId查找 qtl
     *
     * @param chrlgId
     * @return
     */
    List<Qtl> getQtlByChrlgId(String chrlgId);

    List<Qtl> findAllList();

    boolean add(Qtl qtl);

    boolean deleteById(int id);

    Qtl findById(int id);

    int findQtlByNameNum(String qlt_name);

    List<Qtl> findQtlsByName(String qtlName);

    /**
     * 获取QTL Name在该QTL集合中的所有QTL,这里必须为精准查询
     * @param qtlArray QTL Name集合
     */
    List<Qtl> findQTLsInArray(List<String> qtlArray);

    /**
     * 根据基因查询对应的QTL数据
     *
     * @param map
     * @return
     */
    List<Map> findListByGene(Map map);
}
