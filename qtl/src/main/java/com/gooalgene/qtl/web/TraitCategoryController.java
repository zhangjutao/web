package com.gooalgene.qtl.web;

import com.gooalgene.entity.TraitCategory;
import com.gooalgene.entity.TraitList;
import com.gooalgene.qtl.service.TraitListService;
import com.gooalgene.qtl.service.TraitCategoryService;
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
@RequestMapping("/traitcategory")
public class TraitCategoryController {

    @Autowired
    private TraitCategoryService traitCategoryService;
    @Autowired
    private TraitListService traitListService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request,Model model) {
        List<TraitCategory> list = traitCategoryService.findAllList();
        model.addAttribute("list",list);
        return "traitcategory/trait-category";
    }

    @RequestMapping("/toadd")
    public String toadd(TraitList traitList, Model model) {
        List<TraitCategory> list = traitCategoryService.findAllList();
        model.addAttribute("list",list);
        return "traitcategory/add";
    }

    @RequestMapping("/saveadd")
    public String add(TraitCategory traitCategory,Model model) {
        System.out.println(traitCategory.toString());
        boolean flage = traitCategoryService.add(traitCategory);
        System.out.println("flage:"+flage);
        return "redirect:/traitcategory/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id,Model model) {
        TraitCategory traitCategory = traitCategoryService.findById(id);
        model.addAttribute("traitCategory",traitCategory);
        return "traitcategory/edit";
    }

    @RequestMapping("/saveedit")
    public String update(TraitCategory traitCategory,Model model) {
        int flage= traitCategoryService.update(traitCategory);
        System.out.println("flage:"+flage);
        return "redirect:/traitcategory/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<TraitList> traitLists = traitListService.getTraitListsByQtlId(id);
        if(traitLists.size()>0){
            return "无法删除，关联表traitList有关联数据";
        }else {
            boolean flage = traitCategoryService.delete(Integer.parseInt(id));
            System.out.println("flage:" + flage);
            if (flage == true) {
                return "删除成功";
            } else {
                return "删除失败";
            }
        }
    }
}