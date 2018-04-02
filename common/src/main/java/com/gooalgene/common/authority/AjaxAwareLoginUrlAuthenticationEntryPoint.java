package com.gooalgene.common.authority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAwareLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(AjaxAwareLoginUrlAuthenticationEntryPoint.class);

    public AjaxAwareLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            logger.error("come here");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied"); //对ajax请求不重定向，直接返回错误代码
        } else {
            super.commence(request, response, authException);
        }
    }
}
