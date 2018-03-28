package com.gooalgene.common.authority.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.common.WebSocket;
import com.gooalgene.common.authority.token.MyBearerTokenExtractor;
import com.gooalgene.common.authority.token.TokenPojo;
import com.gooalgene.common.cache.RedisService;
import com.gooalgene.utils.PropertiesLoader;
import com.gooalgene.utils.TokenFactory;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by Administrator on2018/2/2 0002
 */
public class SsoAuthenticationFilterByJwt extends OncePerRequestFilter {

    @Autowired
    private CacheManager guavaCacheManager;
    private Cache cache;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.initFilterBean();
        cache = guavaCacheManager.getCache("config");

    }

    private static PropertiesLoader loader = new PropertiesLoader("classpath:oauth/oauth.properties");

    private Logger log = LoggerFactory.getLogger(SsoAuthenticationFilterByJwt.class);

    private MyBearerTokenExtractor tokenExtractor = new MyBearerTokenExtractor();

    private UserDetailsService userDetailsService;

    private RedisService redisService;

    private WebSocket webSocket;

    public SsoAuthenticationFilterByJwt(RedisService redisService, WebSocket webSocket, UserDetailsService userDetailsService) {
        this.redisService = redisService;
        this.webSocket = webSocket;
        this.userDetailsService = userDetailsService;
    }

    public SsoAuthenticationFilterByJwt(RedisService redisService, WebSocket webSocket) {
        this.redisService = redisService;
        this.webSocket = webSocket;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            log.info("用户{}已认证", authentication.getPrincipal());
        } else {
            try {
                String token = tokenExtractor.extractToken(request);
                if (StringUtils.isBlank(token)) {
                    //未携带token时
                } else {
                    TokenPojo tokenPojo = null;
                    try {
                        //jwtPojo = redisService.getJwt(token);
                       /* if (jwtPojo == null) {
                            //redis中已将token删除,说明刷新token已过期,需重新登录
                            log.info("刷新token已过期,需重新登录");
                        } else {*/
                        authentication = TokenFactory.getAuthenticationByJwt(token,userDetailsService);
                        //authentication = JwtUtil.getAuthenticationByJwt(jwtPojo.getAccess_token(),userDetailsService);
                        if (authentication!=null&&authentication.isAuthenticated()) {
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            authenticationSuccessHandler.onAuthenticationSuccess(request,response,authentication);
                        } else {
                            SecurityContextHolder.clearContext();
                        }
                        //}
                    } catch (ExpiredJwtException e) {
                        log.info("token过期");
                        tokenPojo=redisService.getJwt(token);
                        if(tokenPojo!=null){
                            //jwtPojo为空说明用户已超过三小时未操作，需重新登录
                            String refreshToken = tokenPojo.getRefresh_token();
                            String result = TokenFactory.getTokenByRefreshToken(refreshToken,"jwt");
                            System.out.println(result);
                            //在jwtPojp被重新赋值之前，先删除redis中存在原先的jwt对象
                            redisService.del(tokenPojo.getAccess_token());
                            //todo 当刷新token返回是异常信息时需要扩展
                            tokenPojo = objectMapper.readValue(result, TokenPojo.class);
                            authentication = TokenFactory.getAuthenticationByJwt(tokenPojo.getAccess_token(),userDetailsService);
                            if (authentication!=null && authentication.isAuthenticated()) {
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                                //向前台推送刷新后的token，存入sessionSotrage，取代之前token
                                webSocket.sendMessage(tokenPojo.getAccess_token());
                                redisService.setJwt(tokenPojo.getAccess_token(), tokenPojo);
                                authenticationSuccessHandler.onAuthenticationSuccess(request,response,authentication);
                            } else {
                                SecurityContextHolder.clearContext();
                            }
                        }
                    } catch (SignatureException e) {
                        //e.printStackTrace();
                        SecurityContextHolder.clearContext();
                    } catch (Exception e) {
                        //e.printStackTrace();
                        SecurityContextHolder.clearContext();
                    }
                }
            } catch (OAuth2Exception failed) {
                SecurityContextHolder.clearContext();
                logger.debug("Authentication request failed: " + failed);
            }
        }
        chain.doFilter(request, response);
    }


}