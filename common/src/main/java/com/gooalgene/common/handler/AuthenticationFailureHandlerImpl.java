package com.gooalgene.common.handler;

import com.gooalgene.common.dao.LoginInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component("authenticationFailureHandler")
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private Logger logger= LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.info("登录失败，异常信息：{}",e.getMessage());
        String errorMessgae="";
        if(e instanceof BadCredentialsException){
            errorMessgae="无效的账户名或密码";
        }else if(e instanceof AccountExpiredException){
            errorMessgae="账户已过期";
        }
        //RedirectAttributes
        redirectStrategy.sendRedirect(request,response,"/login?error="+ URLEncoder.encode(errorMessgae,"UTF-8"));
    }
}
