package com.gooalgene.qtl.service;

import com.gooalgene.common.Page;
import com.gooalgene.qtl.dao.MarkerDao;
import com.gooalgene.entity.Marker;
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
public class MarkerService {

    @Autowired
    private MarkerDao markerDao;

    public List<Marker> findMarkerList() {
        return markerDao.findList();
    }

    @Transactional(readOnly = false)
    public boolean saveMarker(Marker marker) {
        return markerDao.saveMarker(marker);
    }

    public Marker findMarkerById(int id) {
        return markerDao.findMarkerById(id);
    }

    @Transactional(readOnly = false)
    public boolean updateMarker(Marker marker) {
        return markerDao.updateMarker(marker);
    }

    @Transactional(readOnly = false)
    public boolean deleteMarker(int id) {
        return markerDao.deleteMarker(id);
    }

    public JSONArray searchMarkersbyKeywords(String type, String keywords, Page<Marker> page) {
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索
        JSONArray data = new JSONArray();
        Marker marker = new Marker();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                marker.setMarkerName(keywords);
                marker.setMarkerType(keywords);
                marker.setMarkerLg(keywords);
                marker.setAmplificationInfo(keywords);
                marker.setProvider(keywords);
                marker.setRef(keywords);
            }
        } else {
            return data;
        }
        marker.setPage(page);
        List<Marker> list = null;
        if ("all".equalsIgnoreCase(type)) {//all的时候需要全文匹配，查询字段使用or进行关联
            list = markerDao.findByTypeAllList(marker);
        }
        for (Marker m : list) {
            Map map = new HashMap();
            map.put("id", m.getId());
            map.put("markerName", m.getMarkerName());
            map.put("markerType", m.getMarkerType());
            map.put("markerLg", m.getMarkerLg());
            map.put("position", m.getPosition());
            map.put("amplificationInfo", m.getAmplificationInfo()==null?"":m.getAmplificationInfo());
            map.put("provider", m.getProvider()==null?"":m.getProvider());
            map.put("ref", m.getRef()==null?"":m.getRef());
            data.add(map);
        }
        page.setList(list);
        return data;
    }
}
