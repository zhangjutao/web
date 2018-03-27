package com.gooalgene.common.authority.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.common.WebSocket;
import com.gooalgene.common.authority.token.MyBearerTokenExtractor;
import com.gooalgene.common.authority.token.TokenPojo;
import com.gooalgene.common.cache.RedisService;
import com.gooalgene.utils.PropertiesLoader;
import com.gooalgene.utils.TokenFactory;
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
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by Administrator on2018/2/2 0002
 */
public class SsoAuthenticationFilterBySample extends OncePerRequestFilter {

    @Autowired
    private CacheManager guavaCacheManager;
    private Cache cache;

    @Autowired
    private ApplicationContext context;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.initFilterBean();
        cache = guavaCacheManager.getCache("config");

    }

    private static final String ACCESS = "access:";
    private static final String AUTH = "auth:";
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private static PropertiesLoader loader = new PropertiesLoader("classpath:oauth/oauth.properties");

    private Logger log = LoggerFactory.getLogger(SsoAuthenticationFilterBySample.class);

    private MyBearerTokenExtractor tokenExtractor = new MyBearerTokenExtractor();

    private UserDetailsService userDetailsService;

    private RedisService redisService;

    private WebSocket webSocket;
    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    public SsoAuthenticationFilterBySample(RedisService redisService, WebSocket webSocket, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.redisService = redisService;
        this.webSocket = webSocket;
    }

    public SsoAuthenticationFilterBySample(RedisService redisService, WebSocket webSocket) {
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
                    //SecurityContextHolder.clearContext();
                } else {
                    //通过token拿到信息（用户，过期时间点，刷新token）
                    OAuth2AccessToken oAuth2AccessToken =getAccessTokenFromRedis(token);
                    OAuth2Authentication oAuth2Authentication;
                    Authentication userAuthentication;
                    if(oAuth2AccessToken!=null){
                        //token未过期
                        oAuth2Authentication = getAuthenticationFromRedis(oAuth2AccessToken.getValue());
                        userAuthentication=oAuth2Authentication.getUserAuthentication();
                        if(userAuthentication!=null&&userAuthentication.isAuthenticated()){
                            //将用户信息设置到securityContext中
                            SecurityContextHolder.getContext().setAuthentication(userAuthentication);
                        }
                    }else {
                        //说明token过期，去拿刷新token请求认证服务器
                        byte[] accessToRefreshKey = serialize(ACCESS_TO_REFRESH + token);
                        byte[] bytes = redisService.get(accessToRefreshKey);
                        String oAuth2RefreshToken = deserializeRefreshToken(bytes);
                        if(oAuth2RefreshToken!=null){
                            //为空说明刷新token已过期，需重新登录
                            String result = TokenFactory.getTokenByRefreshToken(oAuth2RefreshToken,"sample");
                            TokenPojo tokenPojo = objectMapper.readValue(result, TokenPojo.class);
                            oAuth2Authentication =getAuthenticationFromRedis(tokenPojo.getAccess_token());
                            userAuthentication = oAuth2Authentication.getUserAuthentication();
                            if(userAuthentication!=null&&userAuthentication.isAuthenticated()){
                                //将用户信息设置到securityContext中
                                SecurityContextHolder.getContext().setAuthentication(userAuthentication);
                                //向前台推送刷新后的token，存入sessionSotrage，取代之前token
                                webSocket.sendMessage(tokenPojo.getAccess_token());
                            }
                        }

                    }
                }
            } catch (OAuth2Exception failed) {
                SecurityContextHolder.clearContext();
                logger.debug("Authentication request failed: " + failed);
            }catch (Exception e) {
                SecurityContextHolder.clearContext();
                e.printStackTrace();
            }
        }
        chain.doFilter(request, response);
    }

    private OAuth2Authentication getAuthenticationFromRedis(String token) {
        byte[] bytes = null;
        bytes = redisService.get(serialize(AUTH + token));
        OAuth2Authentication auth = deserializeAuthentication(bytes);
        return auth;
    }

    private OAuth2AccessToken getAccessTokenFromRedis(String token) throws Exception{
        byte[] key = serialize(ACCESS + token);
        byte[] bytes = null;
        //try{
            bytes = redisService.get(key);
            OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
            return accessToken;
        /*}catch (SerializationException e){
            //todo 判断是否是获取的用户被踢掉的标识，是的话推送给前台，并在redis中删除对应信息
            *//*Map deserialize = serializationStrategy.deserialize(bytes, Map.class);
            if(StringUtils.equals(deserialize.get("code").toString(),"1")){
                webSocket.sendMessage(deserialize.toString());
            }*//*
            e.printStackTrace();
        }*/
        //return null;
    }
    private byte[] serialize(String string) {
        return serializationStrategy.serialize(string);
    }
    private OAuth2AccessToken deserializeAccessToken(byte[] bytes) {
        return serializationStrategy.deserialize(bytes, OAuth2AccessToken.class);
    }
    private OAuth2Authentication deserializeAuthentication(byte[] bytes) {
        return serializationStrategy.deserialize(bytes, OAuth2Authentication.class);
    }
    private String deserializeRefreshToken(byte[] bytes) {
        return serializationStrategy.deserializeString(bytes);
    }

}