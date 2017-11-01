package com.gooalgene.qtl.service;

import com.gooalgene.qtl.dao.ChrlgDao;
import com.gooalgene.entity.Chrlg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/07/08.
 */
@Service
public class ChrlgService {

    @Autowired
    private ChrlgDao chrlgDao;

    public List<Chrlg> findAllList(){
        return chrlgDao.findAllList();
    }

    public List<Chrlg> findAList(Chrlg chrlg){
        return chrlgDao.findList(chrlg);
    }


    @Transactional(readOnly = false)
    public boolean insert(Chrlg chrlg){
        return chrlgDao.insert(chrlg);
    }

    public boolean delete(int id) {
        return chrlgDao.deleteById(id);
    }

    public Chrlg findById(int id) {
        return chrlgDao.get(id);
    }

    public int update(Chrlg chrlg) {
        return chrlgDao.update(chrlg);
    }

    public Chrlg queryByChrAndVersion(String chr,String version){
        return chrlgDao.selectByChrAndVersion(chr,version);
    }
}
