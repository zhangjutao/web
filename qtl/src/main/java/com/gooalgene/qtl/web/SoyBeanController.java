package com.gooalgene.qtl.web;

import com.gooalgene.entity.Soybean;
import com.gooalgene.qtl.service.SoyBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by 陈冬 on 2017/7/14.
 */
@Controller
@RequestMapping(value = "/soybean")
public class SoyBeanController {

    @Autowired
    private SoyBeanService soyBeanService;

    @RequestMapping(value = "/list")
    public String soybeanList(Model model){
        List<Soybean> soybeanList = soyBeanService.findSoyBeanList();
        model.addAttribute("soybeanList", soybeanList);
        return "soybean/soybean-list";
    }

    @RequestMapping(value = "/toaddsoybean")
    public String toAddSoybean(){
        return "soybean/add";
    }

    @RequestMapping(value = "/savenewsoybean")
    public String saveNewSoybean(Soybean soybean){
        soyBeanService.save(soybean);
        return "redirect:/soybean/list";
    }

    @RequestMapping(value = "/toedit")
    public String toEditSoybean(int id, Model model){
        Soybean soybean = soyBeanService.findSoyBeanById(id);
        model.addAttribute("soybean", soybean);
        return "soybean/edit";
    }

    @RequestMapping(value = "/saveedit")
    public String saveEditedSoybean(Soybean soybean){
        soyBeanService.updateSoybean(soybean);
        return "redirect:/soybean/list";
    }

    @RequestMapping(value = "/delete")
    public String deleteSoybean(int id){
        soyBeanService.deleteById(id);
        return "redirect:/soybean/list";
    }


}
