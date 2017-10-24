package com.gooalgene.qtl.web;

import com.gooalgene.entity.Chrlg;
import com.gooalgene.entity.Qtl;
import com.gooalgene.qtl.service.ChrlgService;
import com.gooalgene.qtl.service.QtlService;
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
@RequestMapping("/chrlg")
public class ChrlgController {
    Logger logger = LoggerFactory.getLogger(ChrlgController.class);

    @Autowired
    private ChrlgService chrlgService;
    @Autowired
    private QtlService qtlService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request,Model model) {
        model.addAttribute("chrlg",chrlgService.findAllList());
        return "chrlg/chrlg";
    }

    @RequestMapping("/toadd")
    public String toadd(Model model) {
        return "chrlg/add";
    }

    @RequestMapping("/saveadd")
    public String add(Chrlg chrlg, Model model) {
//        chrlg.setCmBp(chrlg.getBps()/chrlg.getcMs());
        System.out.println(chrlg.toString());
        boolean flag = chrlgService.insert(chrlg);
        System.out.println("flag:"+flag);
        return "redirect:/chrlg/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id,Model model) {
        Chrlg chrlg = chrlgService.findById(id);
        model.addAttribute("chrlg",chrlg);
        return "chrlg/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(Chrlg chrlg,Model model) {
        int flage = chrlgService.update(chrlg);
        System.out.println("flage:"+flage);
        return "redirect:/chrlg/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<Qtl> qtls = qtlService.getQtlByChrlgId(id);
        if(qtls.size()>0){
            return "无法删除，关联表qtl有关联数据";
        }else {
            boolean flage = chrlgService.delete(Integer.parseInt(id));
            System.out.println("flage:" + flage);
            if (flage == true) {
                return "删除成功";
            } else {
                return "删除失败";
            }
        }
    }
}