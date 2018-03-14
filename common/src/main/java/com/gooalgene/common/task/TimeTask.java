package com.gooalgene.common.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Component
public class TimeTask {


    @Scheduled(cron = "0 0 0 1/3 * ?")
    public void ClearDownloadedFile() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        // 得到文件绝对路径
        String realPath = servletContext.getRealPath("/");
        String path = realPath + "tempFile";
        File file = new File(path);       //获取临时文件存放的文件夹
        File[] files = file.listFiles();              //取文件夹下所有文件
        for (File f : files) {                  //遍历删除所有文件
            f.delete();
            System.out.println("当前时间是" + new Date().toString());
        }
    }
}
