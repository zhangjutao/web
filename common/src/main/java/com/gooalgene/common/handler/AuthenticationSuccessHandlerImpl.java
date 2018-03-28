package com.gooalgene.common.handler;

import com.gooalgene.common.authority.LoginInfo;
import com.gooalgene.common.authority.SecurityUser;
import com.gooalgene.common.service.LoginInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {
    private final static Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

    @Autowired
    private LoginInfoService loginInfoService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 远程访问IP
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        logger.info("用户登录地址为: " + ipAddress);
        SecurityUser securityUser=(SecurityUser)authentication.getPrincipal();
        LoginInfo loginInfo=new LoginInfo(securityUser.getId(),new Date(),null);
        loginInfo.setLoginAddress(ipAddress);
        loginInfoService.insertLoginInfo(loginInfo);
        super.onAuthenticationSuccess(request,response,authentication);
    }
}
