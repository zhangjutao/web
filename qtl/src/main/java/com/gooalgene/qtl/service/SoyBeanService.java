package com.gooalgene.qtl.service;

import com.gooalgene.qtl.dao.SoybeanDao;
import com.gooalgene.entity.Soybean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 陈冬 on 2017/7/14.
 */
@Service
public class SoyBeanService {

    @Autowired
    private SoybeanDao soyBeanDao;

    public List<Soybean> findSoyBeanList() {
        return soyBeanDao.findList();
    }

    @Transactional(readOnly = false)
    public void save(Soybean soybean) {
        soyBeanDao.save(soybean);
    }

    public Soybean findSoyBeanById(int id) {
        return soyBeanDao.findSoyBeanById(id);
    }

    @Transactional(readOnly = false)
    public void updateSoybean(Soybean soybean) {
        soyBeanDao.updateSoybean(soybean);
    }

    @Transactional(readOnly = false)
    public void deleteById(int id) {
        soyBeanDao.deleteById(id);
    }
}
