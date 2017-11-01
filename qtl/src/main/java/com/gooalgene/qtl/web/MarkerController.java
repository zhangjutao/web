package com.gooalgene.qtl.web;

import com.gooalgene.common.Page;
import com.gooalgene.entity.Marker;
import com.gooalgene.qtl.service.MarkerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 陈冬 on 2017/7/17.
 */
@Controller
@RequestMapping(value = "/marker")
public class MarkerController {

    Logger logger = LoggerFactory.getLogger(MarkerController.class);

    @Autowired
    private MarkerService markerService;

    @RequestMapping(value = "/list")
    public String markerList(Model model) {
        return "marker/marker-list";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, Model model, HttpServletResponse response) {
        Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数 每次加1
        JSONObject jsonobj = new JSONObject();
        Page<Marker> page = new Page<Marker>(request, response);
        Integer start = Integer.valueOf(request.getParameter("iDisplayStart"));
        Integer length = Integer.valueOf(request.getParameter("iDisplayLength"));
        page.setPageNo(start / length + 1);
        page.setPageSize(length);
        String sSearch = request.getParameter("sSearch");
        String type = "All";
        logger.info(start + "\t" + length + "\t" + (start / length + 1) + ",Type:" + type + ",search:" + sSearch);
        JSONArray result = markerService.searchMarkersbyKeywords(type, sSearch, page);
        jsonobj.put("aData", result);
        Map<String, Object> map = new HashMap<String, Object>();
        // 为操作次数加1，必须这样做
        int initEcho = sEcho + 1;
        map.put("sEcho", initEcho);
        map.put("iTotalRecords", page.getCount());
        map.put("iTotalDisplayRecords", page.getCount());
        map.put("aData", result);
        model.addAttribute("aData", result);
        return map;
    }

    @RequestMapping(value = "/toaddmarker")
    public String toAddMarker() {
        return "marker/add";
    }

    @RequestMapping(value = "/savenewmarker")
    public String saveNewMarker(Marker marker) {
        markerService.saveMarker(marker);
        return "redirect:/marker/list";
    }

    @RequestMapping(value = "/toedit")
    public String toEditMarker(int id, Model model) {
        Marker marker = markerService.findMarkerById(id);
        model.addAttribute("marker", marker);
        return "marker/edit";
    }

    @RequestMapping(value = "/saveeditmarker")
    public String saveeditmarker(Marker marker) {
        markerService.updateMarker(marker);
        return "redirect:/marker/list";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public boolean deleteMarker(int id) {
        return markerService.deleteMarker(id);
    }

}
