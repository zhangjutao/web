package com.gooalgene.common.handler;

import com.gooalgene.common.authority.LoginInfo;
import com.gooalgene.common.authority.SecurityUser;
import com.gooalgene.common.dao.LoginInfoDao;
import com.gooalgene.common.service.LoginInfoService;
import com.gooalgene.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

//@Component("authenticationSuccessHandler")
public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {
    private Logger logger= LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

    @Autowired
    private LoginInfoService loginInfoService;

    private RequestCache requestCache=new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功的用户：{}",authentication);
        SecurityUser securityUser=(SecurityUser)authentication. getPrincipal();
        LoginInfo loginInfo=new LoginInfo(securityUser.getId(),new Date(),null);
        loginInfoService.insertLoginInfo(loginInfo);

        /*SavedRequest savedRequest=requestCache.getRequest(request,response);
        String url=savedRequest.getRedirectUrl();
        String contextPath="";
        if(url!=null){
            contextPath= StringUtils.substringAfterLast(url,"/");
        }else {
            contextPath="search";
        }

        redirectStrategy.sendRedirect(request,response,"/"+contextPath+"/index");*/
        super.onAuthenticationSuccess(request,response,authentication);
    }
}
