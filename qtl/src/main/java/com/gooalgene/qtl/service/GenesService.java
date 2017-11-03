package com.gooalgene.qtl.service;

import com.gooalgene.qtl.dao.GenesDao;
import com.gooalgene.entity.Genes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ShiYun on 2017/8/9 0009.
 */
@Service
public class GenesService {

    @Autowired
    private GenesDao genesDao;

    public List<Genes> query(Genes genes) {
        return genesDao.findList(genes);
    }

    public int insertBatch(List<Genes> list) {
        return genesDao.insertBatch(list);
    }

    public String[] queryGensByKey(String key) {
        Genes genes = new Genes();//搜索关键字
        genes.setKeywords(key);
        List<Genes> list = query(genes);
        StringBuffer sb = new StringBuffer();
        for (Genes genes1 : list) {
            sb.append(genes1.getGene()).append(",");
        }
        return sb.toString().split(",");
    }
}
