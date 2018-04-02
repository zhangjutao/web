package com.gooalgene.common.handler;

import com.gooalgene.common.annotations.TestExcluded;
import com.gooalgene.common.dao.LoginInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@TestExcluded
@Component("authenticationFailureHandler")
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger= LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);

    /**
     * 用来保存spring security在bad credential情况下之前输入的用户值并回显到页面
     */
    public static final String LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

    /*@Autowired
    private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;*/

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.info("登录失败，异常信息：{}",e.getMessage());
        String errorMessgae="";
        if(e instanceof BadCredentialsException){
            errorMessgae="密码错误";
        }else if(e instanceof AuthenticationServiceException){
            errorMessgae="账号不存在";
        }else if(e instanceof AccountExpiredException){
            errorMessgae="账户已过期";
        }else if(e instanceof DisabledException){
            errorMessgae="账户未启用";
        }
        //String usernameParameter = usernamePasswordAuthenticationFilter.getUsernameParameter();
        //String lastUsername = request.getParameter(usernameParameter); //拿到填入的用户名
        HttpSession session = request.getSession(false);
        if (session != null){
            //request.getSession().setAttribute(LAST_USERNAME_KEY, lastUsername);
        }
        //RedirectAttributes
        redirectStrategy.sendRedirect(request,response,"/login?error="+ URLEncoder.encode(errorMessgae,"UTF-8"));
    }
}
