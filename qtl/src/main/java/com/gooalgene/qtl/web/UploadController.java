package com.gooalgene.qtl.web;

import com.gooalgene.qtl.service.ChangeService;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ShiYun on 2017/7/27 0027.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private ChangeService changeService;

    //文件上传的临时目录
    private String uploadedDir = "/static/tmp/";

    @RequestMapping(value = "import", method = RequestMethod.POST)
    @ResponseBody
    public String importRouter(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, Model model) {
        boolean flag = false;
//      String path = uploadedDir;
        String path = request.getSession().getServletContext().getRealPath(uploadedDir);
//      System.out.println(path);
        String type = request.getParameter("type");
        String mes = "";
        String suffix = file.getOriginalFilename();
        suffix = suffix.substring(suffix.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + "-" + type + suffix;
        logger.info("上传表文件类型Type:" + type + ",path:" + path + ",fileName:" + fileName);
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            System.out.println(targetFile.getAbsolutePath());
            file.transferTo(targetFile);
            String name = targetFile.getName().toUpperCase();
            //保存
            List<String> list = new ArrayList<String>();
            if (name.endsWith(".CSV") || name.endsWith(".TXT")) {
                try {
                    list = FileUtils.readLines(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    mes = "读取文件异常";
                }
                try {
                    if (!list.isEmpty()) {
                        flag = changeService.insertBatch(list, type, mes);
                    } else {
                        mes = "文件内容为空";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mes = "导入数据库异常！";
                }
            } else {
                mes = "不支持的文件格式。请上传csv格式文件。";
            }
        } catch (IOException e) {
            e.printStackTrace();
            mes = "上报文件异常。";
        }
        JSONObject result = new JSONObject();
        result.put("result", flag);
        result.put("mes", mes);
        return result.toString();
    }
}
