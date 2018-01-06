package com.gooalgene.qtl.service;

import com.gooalgene.common.Page;
import com.gooalgene.qtl.dao.QtlDao;
import com.gooalgene.entity.Qtl;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/07/08.
 */
@Service
public class QtlService {

    @Autowired
    private QtlDao qtlDao;

    public List<Qtl> getQtlByChrlgId(String chrlgId) {
        return qtlDao.getQtlByChrlgId(chrlgId);
    }

    public List<Qtl> findAllList() {
        return qtlDao.findAllList();
    }

    @Transactional(readOnly = false)
    public boolean add(Qtl qtl) {
        return qtlDao.add(qtl);
    }

    public boolean delete(int id) {
        return qtlDao.deleteById(id);
    }

    public Qtl findById(int id) {
        return qtlDao.findById(id);
    }

    public int update(Qtl qtl) {
        return qtlDao.update(qtl);
    }

    public List<Qtl> findQtlsByName(String qtlName){
        if (StringUtils.isBlank(qtlName)){
            return null;
        }
        //针对前台在一次搜索出几个QTL选中后，回显到输入框，重新查询以Al tolerance 1-2,Al tolerance 1-3方式进行拆分
        if (qtlName.contains(",")){
            String[] allQtl = qtlName.split(",");
            return qtlDao.findQTLsInArray(Arrays.asList(allQtl));
        }else {
            return qtlDao.findQtlsByName(qtlName);
        }
    }

    /**
     * 后台管理查询qtl分页处理
     *
     * @param type
     * @param keywords
     * @param page
     * @return
     */
    public JSONArray searchQtlsbyKeywords(String type, String keywords, Page<Qtl> page) {
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索
        JSONArray data = new JSONArray();
        Qtl qtl = new Qtl();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                qtl.setKeywords(keywords);
            }//空白查询所有
        }
        qtl.setPage(page);
        List<Map> list = qtlDao.findQTLMap(qtl);
        for (Map m : list) {
            Float lod = (Float) m.get("lod");
            if (lod == null) {
                m.put("lod", "");
            }
            data.add(m);
        }
        List<Qtl> list1 = qtlDao.findQTLList(qtl);
        page.setList(list1);
        return data;
    }
}
