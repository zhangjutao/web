package com.gooalgene.common.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.gooalgene.common.WebSocket;
import com.gooalgene.common.authority.token.TokenPojo;
import com.gooalgene.common.cache.RedisService;
import com.gooalgene.common.constant.CommonConstant;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * create by Administrator on2018/1/18 0018
 */
@RestController
public class SsoTokenController implements InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(SsoTokenController.class);
    private ObjectMapper objectMapper=new ObjectMapper();

    @Autowired
    private RedisService redisService;
    @Autowired
    private CacheManager guavaCacheManager;
    private Cache cache;

    private static PropertiesLoader loader = new PropertiesLoader("classpath:oauth/oauth.properties");

    @Autowired
    private ApplicationContext context;
    @Override
    public void afterPropertiesSet() throws Exception {
        if (context.getParent() != null) {
            cache = guavaCacheManager.getCache("config");
        }
    }
    @Autowired
    private WebSocket webSocket;
    @RequestMapping(value = "/websocket", method = RequestMethod.GET)
    public void websocket() throws IOException {
        webSocket.sendMessage("sssss");
    }



    @Autowired
    private RandomValueStringGenerator generator;
    @RequestMapping(value = "/sso/authorize", method = RequestMethod.GET)
    public void authorize(@RequestParam Map<String, String> parameters, HttpServletResponse response ) throws IOException {
        String authorizeUrl = "http://localhost:9999/server/oauth/authorize";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(authorizeUrl);
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            builder.queryParam(param.getKey(), param.getValue());
        }
        builder.queryParam("state", generator.generate());
        String redirectUrl = response.encodeRedirectURL(builder.build().encode().toUriString());
        response.sendRedirect(redirectUrl);
    }

    @RequestMapping(value = "/sso/token", method = RequestMethod.POST)
    public ResultVO token(@RequestParam Map<String, String> parameters, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String result = TokenFactory.getToken(parameters);
        System.out.println(result);
        try {
            TokenPojo tokenPojo=objectMapper.readValue(result,TokenPojo.class);
            redisService.setJwt(tokenPojo.getAccess_token(),tokenPojo);
            String autoLoginCheckbox = parameters.get("autoLoginCheckbox");
            if(autoLoginCheckbox!=null){
                //将用户存入redis，key(随机数)放入cookie中，value为jwt的token,过期时间为7天
                String rememberMeInRediskey=UUID.randomUUID().toString();
                Authentication authentication = TokenFactory.getAuthenticationByJwt(tokenPojo.getAccess_token(), null);
                redisService.setex(rememberMeInRediskey, CommonConstant.REMEMBER_ME_TIME_OUT,authentication.getPrincipal().toString());
                CookieUtils.setCookie(response, CommonConstant.REMEMBER_ME_KEY,rememberMeInRediskey, CommonConstant.REMEMBER_ME_TIME_OUT);
            }
            //将clientId存入cookie
            /*String clientIdKey = CookieUtils.getCookie(request,CommonConstant.CLIENT_ID_IN_COOKIE);
            TokenFactory.takeClientIdInCookie(redisService,loader.getProperty("client_id"),response,clientIdKey);*/
            return ResultUtil.success(tokenPojo.getAccess_token());
        } catch (UnrecognizedPropertyException e) {
            e.printStackTrace();
            Map map = JsonUtils.Json2Bean(result, Map.class);
            return ResultUtil.error(999,(String)map.get("errorMessgae"));
        }
    }
}
