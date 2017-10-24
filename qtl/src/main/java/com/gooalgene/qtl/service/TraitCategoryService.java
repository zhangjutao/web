package com.gooalgene.qtl.service;

import com.gooalgene.qtl.dao.TraitCategoryDao;
import com.gooalgene.entity.TraitCategory;
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
