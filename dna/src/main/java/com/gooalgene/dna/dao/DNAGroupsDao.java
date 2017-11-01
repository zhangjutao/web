package com.gooalgene.dna.dao;


import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.dna.entity.DNAGroups;

import java.util.List;

/**
 * Created by ShiYun on 2017/9/18.
 */
@MyBatisDao
public interface DNAGroupsDao extends CrudDao<DNAGroups> {

    List<DNAGroups> findDNAGroupsList(DNAGroups dnaGroups);

    boolean add(DNAGroups dnaGroups);

    DNAGroups findById(int id);

    boolean deleteById(int id);
}
