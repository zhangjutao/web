package com.gooalgene.common.handler;

import com.gooalgene.common.authority.LoginInfo;
import com.gooalgene.common.authority.SecurityUser;
import com.gooalgene.common.dao.LoginInfoDao;
import com.gooalgene.common.service.LoginInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component("authenticationSuccessHandler")
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private Logger logger= LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

    @Autowired
    private LoginInfoDao loginInfoDao;
    @Autowired
    private LoginInfoService loginInfoService;

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功的用户：{}",authentication);
        SecurityUser securityUser=(SecurityUser)authentication.getPrincipal();
        LoginInfo loginInfo=new LoginInfo(securityUser.getId(),new Date(),null);
        //loginInfoDao.insertLoginInfo(loginInfo);
        loginInfoService.insertLoginInfo(loginInfo);
        redirectStrategy.sendRedirect(request,response,"/dna/index");
    }
}
