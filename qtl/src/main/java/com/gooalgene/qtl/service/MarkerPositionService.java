package com.gooalgene.qtl.service;

import com.gooalgene.common.Page;
import com.gooalgene.qtl.dao.MarkerPositionDao;
import com.gooalgene.entity.MarkerPosition;
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
public class MarkerPositionService {

    @Autowired
    private MarkerPositionDao markerPositionDao;

    public List<MarkerPosition> findAllList() {
        return markerPositionDao.findAllList();
    }

    @Transactional(readOnly = false)
    public boolean add(MarkerPosition markerPosition) {
        return markerPositionDao.insert(markerPosition);
    }

    public MarkerPosition findById(int id) {
        return markerPositionDao.findById(id);
    }

    @Transactional(readOnly = false)
    public int update(MarkerPosition markerPosition) {
        return markerPositionDao.update(markerPosition);
    }

    @Transactional(readOnly = false)
    public boolean deleteById(int id) {
        return markerPositionDao.deleteById(id);
    }

    public JSONArray searchMarkerPositionsbyKeywords(String type, String keywords, Page<MarkerPosition> page) {
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索
        JSONArray data = new JSONArray();
        MarkerPosition markerPosition = new MarkerPosition();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                markerPosition.setMarkerName(keywords);
                markerPosition.setMarkerType(keywords);
                markerPosition.setVersion(keywords);
                markerPosition.setChr(keywords);
            }//空白查询所有
        } else {
            return data;
        }
        markerPosition.setPage(page);
        List<MarkerPosition> list = null;
        if ("all".equalsIgnoreCase(type)) {//all的时候需要全文匹配，查询字段使用or进行关联
            list = markerPositionDao.findByTypeAllList(markerPosition);
        }
        for (MarkerPosition m : list) {
            Map map = new HashMap();
            map.put("id", m.getId());
            map.put("markerName", m.getMarkerName());
            map.put("markerType", m.getMarkerType());
            map.put("chr", m.getChr());
            map.put("version", m.getVersion());
            map.put("startPos", m.getStartPos());
            map.put("endPos", m.getEndPos());
            data.add(map);
        }
        page.setList(list);
        return data;
    }
}
