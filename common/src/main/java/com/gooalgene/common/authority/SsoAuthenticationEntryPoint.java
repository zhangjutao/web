package com.gooalgene.common.authority;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by Administrator on2018/2/9 0009
 */
public class SsoAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private String targetUrl;

    public SsoAuthenticationEntryPoint(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse resp, AuthenticationException authException) throws IOException, ServletException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charser=utf-8");
        //resp.getWriter().print("<h1>没有认证</h1>");
        String contextPath = request.getContextPath();
        resp.sendRedirect(contextPath+targetUrl);
    }
}
