package com.gooalgene.listener;

import com.gooalgene.common.Global;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;


public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        WebApplicationContext context = super.initWebApplicationContext(servletContext);
        startListener(context);
        return context;
    }

    private void startListener(WebApplicationContext context) {
        printKeyLoadMessage();
    }

    /**
     * 获取Key加载信息
     */
    public static boolean printKeyLoadMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n======================================================================\r\n");
        sb.append("\r\n    欢迎使用 " + Global.getConfig("productName") + "  - Powered By http://www.gooalgene.com/\r\n");
        sb.append("\r\n======================================================================\r\n");
        System.out.println(sb.toString());
        return true;
    }

}
