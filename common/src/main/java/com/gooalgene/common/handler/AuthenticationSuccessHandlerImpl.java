package com.gooalgene.common.handler;

import com.gooalgene.common.authority.LoginInfo;
import com.gooalgene.common.authority.SecurityUser;
import com.gooalgene.common.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private LoginInfoService loginInfoService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityUser securityUser=(SecurityUser)authentication.getPrincipal();
        LoginInfo loginInfo=new LoginInfo(securityUser.getId(),new Date(),null);
        loginInfoService.insertLoginInfo(loginInfo);
        super.onAuthenticationSuccess(request,response,authentication);
    }
}
