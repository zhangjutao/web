package com.gooalgene.mrna.service;

import com.gooalgene.common.Page;
import com.gooalgene.common.dao.MrnaGensDao;
import com.gooalgene.entity.MrnaGens;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by 陈冬 on 2017/8/22.
 */
@Service
public class MrnaGensService {

    @Autowired
    private MrnaGensDao mrnaGensDao;

    /**
     * 后台管理查询MrnaGens分页处理
     *
     * @param type
     * @param keywords
     * @param page
     * @return
     */
    public JSONArray searchMrnaGensbyKeywords(String type, String keywords, Page<MrnaGens> page) {
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索
        JSONArray data = new JSONArray();
        MrnaGens mrnaGens = new MrnaGens();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                mrnaGens.setKeywords(keywords);
            }//空白查询所有
        }
        mrnaGens.setPage(page);
        List<Map> list = mrnaGensDao.findMrnaGensMap(mrnaGens);
        for (Map m : list) {
            String geneName = (String) m.get("geneName");
            if (null == geneName){
                m.put("geneName","");
            }
            String functions = (String) m.get("functions");
            if (null == functions){
                m.put("functions","");
            }
            data.add(m);
        }
        List<MrnaGens> list1 = mrnaGensDao.findMrnaGensList(mrnaGens);
        page.setList(list1);
        return data;
    }

    @Transactional(readOnly = false)
    public boolean add(MrnaGens mrnaGens) {
        return mrnaGensDao.add(mrnaGens);
    }

    public MrnaGens findById(int id) {
        return mrnaGensDao.findById(id);
    }

    @Transactional(readOnly = false)
    public int update(MrnaGens mrnaGens) {
        return mrnaGensDao.update(mrnaGens);
    }

    @Transactional(readOnly = false)
    public boolean delete(int id) {
        return mrnaGensDao.deleteById(id);
    }
}
