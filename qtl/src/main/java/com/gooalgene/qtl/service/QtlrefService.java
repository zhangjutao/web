package com.gooalgene.qtl.service;

import com.gooalgene.common.Page;
import com.gooalgene.qtl.dao.QtlrefDao;
import com.gooalgene.entity.Qtlref;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/07/08.
 */
@Service
public class QtlrefService {

    @Autowired
    private QtlrefDao qtlrefDao;

    public List<Qtlref> findAllList() {
        return qtlrefDao.findAllList();
    }

    @Transactional(readOnly = false)
    public boolean add(Qtlref qtlref) {
        return qtlrefDao.add(qtlref);
    }

    public Qtlref findById(int id) {
        return qtlrefDao.findById(id);
    }

    @Transactional(readOnly = false)
    public int update(Qtlref qtlref) {
        return qtlrefDao.update(qtlref);
    }

    @Transactional(readOnly = false)
    public boolean deleteById(int id) {
        return qtlrefDao.deleteById(id);
    }

    /**
     * 后台管理查询qtlref分页
     *
     * @param type
     * @param keywords
     * @param page
     * @return
     */
    public JSONArray searchQtlrefbyKeywords(String type, String keywords, Page<Qtlref> page) {
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索
        JSONArray data = new JSONArray();
        Qtlref qtlref = new Qtlref();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                qtlref.setQtlName(keywords);
                qtlref.setAuthors(keywords);
                qtlref.setSource(keywords);
                qtlref.setTitle(keywords);
                qtlref.setPubmed(keywords);
                qtlref.setPubmed(keywords);
            }//空白查询所有
        } else {
            return data;
        }
        qtlref.setPage(page);
        List<Qtlref> list = null;
        if ("all".equalsIgnoreCase(type)) {//all的时候需要全文匹配，查询字段使用or进行关联
            list = qtlrefDao.findByTypeAllList(qtlref);
        }
        for (Qtlref m : list) {
            Map map = new HashMap();
            map.put("id", m.getId());
            map.put("qtlName", m.getQtlName());
            map.put("authors", m.getAuthors());
            map.put("title", m.getTitle());
            map.put("source", m.getSource());
            map.put("pubmed", m.getPubmed());
            map.put("abs", m.getSummary());
            data.add(map);
        }
        page.setList(list);
        return data;
    }
}
