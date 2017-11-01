package com.gooalgene.qtl.service;

import com.gooalgene.common.Page;
import com.gooalgene.qtl.dao.AssociatedgenesDao;
import com.gooalgene.entity.Associatedgenes;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 陈冬 on 2017/7/17.
 */
@Service
public class AssociatedgenesService {

    @Autowired
    private AssociatedgenesDao associatedgenesDao;

    public List<Associatedgenes> findAssociatedgenesList() {
        return associatedgenesDao.findList();
    }

    @Transactional(readOnly = false)
    public void saveAssociatedgenes(Associatedgenes associatedgenes) {
        associatedgenesDao.insert(associatedgenes);
    }

    public Associatedgenes findAssociatedgenesById(int id) {
        return associatedgenesDao.findAssociatedgenesById(id);
    }

    @Transactional(readOnly = false)
    public void saveAssociatedgenesEdit(Associatedgenes associatedgenes) {
        associatedgenesDao.updateAssociatedgenes(associatedgenes);
    }

    public boolean deleteAssociatedgenes(int id) {
        return associatedgenesDao.deleteAssociatedgenes(id);
    }

    public JSONArray searchAssociatedgenessbyKeywords(String type, String keywords, Page<Associatedgenes> page) {
        JSONArray data = new JSONArray();
        Associatedgenes associatedgenes = new Associatedgenes();
        associatedgenes.setQtlName(keywords);
        associatedgenes.setVersion(keywords);
        associatedgenes.setAssociatedGenes(keywords);
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                associatedgenes.setQtlName(keywords);
                associatedgenes.setVersion(keywords);
                associatedgenes.setAssociatedGenes(keywords);
            }//空白查询所有
        } else {
            return data;
        }
        associatedgenes.setPage(page);
        System.out.println(associatedgenes.getPage().getPageNo());
        List<Associatedgenes> list = null;
        if ("all".equalsIgnoreCase(type)) {//all的时候需要全文匹配，查询字段使用or进行关联
            list = associatedgenesDao.findByTypeAllList(associatedgenes);
        }
        for (Associatedgenes m : list) {
            Map map = new HashMap();
            map.put("id", m.getId());
            map.put("qtlName", m.getQtlName());
            map.put("version", m.getVersion());
            map.put("associatedGenes", m.getAssociatedGenes());
            data.add(map);
        }
        page.setList(list);
        return data;
    }
}
