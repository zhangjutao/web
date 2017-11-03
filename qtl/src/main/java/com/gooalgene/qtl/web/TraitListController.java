package com.gooalgene.qtl.web;

import com.gooalgene.entity.TraitCategory;
import com.gooalgene.entity.TraitList;
import com.gooalgene.qtl.service.TraitListService;
import com.gooalgene.qtl.service.TraitCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by Administrator on 2017/07/08.
 */
@Controller
@RequestMapping("/traitlist")
public class TraitListController {
    Logger logger = LoggerFactory.getLogger(TraitListController.class);

    @Autowired
    private TraitListService traitListService;
    @Autowired
    private TraitCategoryService traitCategoryService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request,Model model) {
        model.addAttribute("traitlist",traitListService.findAllList());
        return "traitlist/trait-list";
    }

    @RequestMapping("/toadd")
    public String toadd(TraitList traitList, Model model) {
        List<TraitCategory> list = traitCategoryService.findAllList();
        model.addAttribute("list",list);
        return "traitlist/add";
    }

    @RequestMapping("/saveadd")
    public String add(TraitList traitList,Model model) {
        boolean flage = traitListService.add(traitList);
        System.out.println("flage:"+flage);
        return "redirect:/traitlist/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id,Model model) {
        TraitList traitList = traitListService.findById(id);
        List<TraitCategory> list = traitCategoryService.findAllList();
        model.addAttribute("list",list);
        model.addAttribute("traitList",traitList);
        return "traitlist/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(TraitList traitList,Model model) {
        int flage = traitListService.update(traitList);
        System.out.println("flage:"+flage);
        return "redirect:/traitlist/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean flage = traitListService.delete(Integer.parseInt(id));
        System.out.println("flage:"+flage);
        if(flage==true){
            return "删除成功";
        }else {
            return "删除失败";
        }
    }
}