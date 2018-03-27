package com.gooalgene.common.authority.filter;

import com.gooalgene.common.authority.MyUserDetailsChecker;
import com.gooalgene.common.authority.SecurityUser;
import com.gooalgene.common.cache.RedisService;
import com.gooalgene.common.constant.CommonConstant;
import com.gooalgene.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by Administrator on2018/2/2 0002
 */
public class RememberMeAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler failureHandler;


    private UserDetailsChecker userDetailsChecker = new MyUserDetailsChecker();
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private Logger log = LoggerFactory.getLogger(RememberMeAuthenticationFilter.class);

    private UserDetailsService userDetailsService;

    private RedisService redisService;

    public RememberMeAuthenticationFilter(RedisService redisService, UserDetailsService userDetailsService, AuthenticationFailureHandler failureHandler) {
        this.userDetailsService = userDetailsService;
        this.redisService = redisService;
        this.failureHandler = failureHandler;
    }
    public RememberMeAuthenticationFilter(RedisService redisService) {
        this.redisService = redisService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            log.info("用户{}已认证", authentication.getPrincipal());
        } else {
            //从cookie中取出key，通过key从redis中取出用户，如果存在，置入securityContext，完成登录，如果不存在，什么也不做
            String key = CookieUtils.getCookie(request, CommonConstant.REMEMBER_ME_KEY);
            if(StringUtils.isNotEmpty(key)){
                String username = redisService.get(key);
                if(username!=null){
                    SecurityUser user = null;
                    try {
                        user = (SecurityUser) userDetailsService.loadUserByUsername(username);
                        //对获取到的用户进行检查，防止用户过期等问题，没问题的话便允许访问资源
                        userDetailsChecker.check(user);
                        RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(key, user,
                                user.getAuthorities());
                        if (auth != null && auth.isAuthenticated()) {
                            auth.setDetails(authenticationDetailsSource.buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        } else {
                            SecurityContextHolder.clearContext();
                        }
                    }catch (AuthenticationException e){
                        //todo 校验用户失败，（例如：用户过期或未审核等）
                        e.printStackTrace();
                        unsuccessfulAuthentication(request,response,e);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        //todo 删除cookie
        failureHandler.onAuthenticationFailure(request, response, failed);
    }


}