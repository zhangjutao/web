package com.gooalgene.mrna.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by Administrator on 2017/07/08.
 */
@Controller
@RequestMapping("/mrnaclassify")
public class MRNAClassifyController {

    Logger logger = LoggerFactory.getLogger(MRNAClassifyController.class);

    @RequestMapping("/list")
    public String index(Model model) {
        return "mrnaclassify/list";
    }


}