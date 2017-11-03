package com.gooalgene.qtl.service;

import com.gooalgene.qtl.dao.TraitListDao;
import com.gooalgene.entity.TraitList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/07/08.
 */
@Service
public class TraitListService {

    @Autowired
    private TraitListDao traitListDao;

    public List<TraitList> getTraitListsByQtlId(String id){
        return traitListDao.getTraitListsByQtlId(id);
    }

    public List<TraitList> findAllList(){
        return traitListDao.findAllList();
    }

    public TraitList findById(int id){
        return traitListDao.findById(id);
    }

    @Transactional(readOnly = false)
    public boolean add(TraitList traitList){
        return traitListDao.add(traitList);
    }

    @Transactional(readOnly = false)
    public int update(TraitList traitList){
        return traitListDao.update(traitList);
    }

    @Transactional(readOnly = false)
    public boolean delete(int id){
        return traitListDao.deleteById(id);
    }
}
