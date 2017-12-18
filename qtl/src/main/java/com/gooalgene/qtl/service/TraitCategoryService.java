package com.gooalgene.qtl.service;

import com.gooalgene.qtl.dao.TraitCategoryDao;
import com.gooalgene.entity.TraitCategory;
import com.gooalgene.qtl.views.TraitCategoryWithinMultipleTraitList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/07/08.
 */
@Service
public class TraitCategoryService {

    @Autowired
    private TraitCategoryDao traitCategoryDao;

    public List<TraitCategory> findAllList(){
        return traitCategoryDao.findAllList();
    }

    public TraitCategory findById(int id){
        return traitCategoryDao.findById(id);
    }

    /**
     * 查找所有trait category以及与其相关的trait list
     * @return 包含所有trait category(其中包含trait list集合构成一对多关系)的集合
     */
    public List<TraitCategoryWithinMultipleTraitList> findAllTraitCategoryAndItsTraitList(){
        return traitCategoryDao.findAllTraitCategoryAndItsTraitList();
    }

    @Transactional(readOnly = false)
    public boolean add(TraitCategory traitCategory){
        return traitCategoryDao.add(traitCategory);
    }

    @Transactional(readOnly = false)
    public int update(TraitCategory traitCategory){
        return traitCategoryDao.update(traitCategory);
    }

    @Transactional(readOnly = false)
    public boolean delete(int id){
        return traitCategoryDao.deleteById(id);
    }
}
