package com.gooalgene.utils;

import com.gooalgene.common.cache.RedisService;
import com.gooalgene.common.constant.CommonConstant;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * create by Administrator on2018/2/24 0024
 */
public class TokenFactory {
    private final static Logger logger = LoggerFactory.getLogger(TokenFactory.class);

    private static String location = "classpath:oauth.properties"; //写成classpath:config/*/oauth.properties不识别

    public static Map<String, String> oauthInfo = new HashMap<String, String>();

    static{
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources;
         PropertiesLoader loader = new PropertiesLoader("classpath:oauth.properties");
        try {
            resources = patternResolver.getResources(location);
            location = resources[0].getFile().getAbsolutePath();
            logger.info("location" + location);
            Properties props = new Properties();
            try {
                if (location.contains("dev")) {
                    props = PropertiesLoaderUtils.loadAllProperties("dev/oauth.properties");
                } else if (location.contains("test")) {
                    props = PropertiesLoaderUtils.loadAllProperties("test/oauth.properties");
                } else if (location.contains("production")) {
                    props = PropertiesLoaderUtils.loadAllProperties("production/oauth.properties");
                }else {
                    props = PropertiesLoaderUtils.loadAllProperties("oauth.properties");
                }
                for (Object key : props.keySet()) {
                    logger.warn(key + " : " + (String) props.get(key));
                    oauthInfo.put((String) key, (String) props.get(key));
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final FormHttpMessageConverter FORM_MESSAGE_CONVERTER = new FormHttpMessageConverter();
    //private static final List<HttpMessageConverter<?>> MESSAGE_CONVERTERS = Collections.singletonList(new StringHttpMessageConverter());
    private static List<HttpMessageConverter<?>> MESSAGE_CONVERTERS = Lists.newArrayList();

    public static String getToken(Map<String, String> parameters){
        String accessTokenUri = oauthInfo.get("oauth_url")+oauthInfo.get("request_and_refresh_token");
        final HttpHeaders headers = new HttpHeaders();
        //headers.add("Authorization", "Basic Y2xpZW50MjpjbGllbnQy");
        String authorization=parameters.get("authorization");
        headers.add("Authorization", "Basic "+authorization);
        final MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        String grant_type = parameters.get("grant_type");
        form.add("grant_type", grant_type);
        form.add("tokenType",parameters.get("tokenType"));
        if(org.apache.commons.lang3.StringUtils.equals(grant_type,"authorization_code")){
            form.add("code", parameters.get("code"));
        }else if(org.apache.commons.lang3.StringUtils.equals(parameters.get("grant_type"),"password")){
            form.add("username", parameters.get("username"));
            form.add("password", parameters.get("password"));
            //form.add("scope", "all");
        }
        form.add("redirect_uri", oauthInfo.get("redirect_uri"));
        RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void doWithRequest(ClientHttpRequest request) throws IOException {
                request.getHeaders().putAll(headers);
                request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED));
                FORM_MESSAGE_CONVERTER.write(form, MediaType.APPLICATION_FORM_URLENCODED, request);
            }
        };
        ResponseExtractor<String> responseExtractor = new ResponseExtractor<String>() {
            @Override
            public String extractData(ClientHttpResponse response) throws IOException {
                if(MESSAGE_CONVERTERS.size()<1){
                    MESSAGE_CONVERTERS.add(new StringHttpMessageConverter());
                }
                return new HttpMessageConverterExtractor<String>(String.class, MESSAGE_CONVERTERS).extractData(response);
            }
        };
        String result = new RestTemplate().execute(accessTokenUri, HttpMethod.POST, requestCallback, responseExtractor);
        return result;
    }

    public static String getTokenByRefreshToken(String refreshToken,String authorization){
        String accessTokenUri = oauthInfo.get("oauth_url")+oauthInfo.get("request_and_refresh_token");
        final HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Basic "+oauthInfo.get("authorization"));
        headers.add("Authorization", "Basic "+authorization);
        final MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("grant_type", "refresh_token");
        form.add("refresh_token", refreshToken);
        //form.add("tokenType", tokenType);
        RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void doWithRequest(ClientHttpRequest request) throws IOException {
                request.getHeaders().putAll(headers);
                request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED));
                FORM_MESSAGE_CONVERTER.write(form, MediaType.APPLICATION_FORM_URLENCODED, request);
            }
        };
        ResponseExtractor<String> responseExtractor = new ResponseExtractor<String>() {
            @Override
            public String extractData(ClientHttpResponse response) throws IOException {
                if (MESSAGE_CONVERTERS.size() < 1) {
                    MESSAGE_CONVERTERS.add(new StringHttpMessageConverter());
                }
                return new HttpMessageConverterExtractor<String>(String.class, MESSAGE_CONVERTERS).extractData(response);
            }
        };
        String result = new RestTemplate().execute(accessTokenUri, HttpMethod.POST, requestCallback, responseExtractor);
        return result;
    }

    public static Authentication getAuthenticationByJwt(String accessToken, UserDetailsService userDetailsService) throws ExpiredJwtException, UnsupportedEncodingException {
        Claims claims = Jwts.parser().setSigningKey(CommonConstant.JWT_KEY.getBytes("UTF-8")).parseClaimsJws(accessToken).getBody();
        Authentication authentication = extractAuthentication(claims, userDetailsService);
        return authentication;
    }

    public static Authentication getAuthenticationBySample(String accessToken, RedisService redisService) throws ExpiredJwtException, UnsupportedEncodingException {
        //TODO 通过sampleToken从redis中取出序列化好的Authentication对象
        Authentication authentication = redisService.getAuthentication(accessToken);
        return authentication;
    }

    public static Authentication extractAuthentication(Claims claims, UserDetailsService userDetailsService) {
        if (claims.containsKey("user_name")) {
            Object principal = claims.get("user_name");
            Collection<? extends GrantedAuthority> authorities = getAuthorities(claims);
            if (userDetailsService != null) {
                UserDetails user = userDetailsService.loadUserByUsername((String) claims.get("user_name"));
                authorities = user.getAuthorities();
                principal = user;
            }
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        }
        return null;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        /*if (!map.containsKey("authorities")) {
            return defaultAuthorities;
        }*/
        Object authorities = claims.get("authorities");
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }

    public static void takeClientIdInCookie(RedisService redisService, String clientId, HttpServletResponse response,String oldClienrIdKey){
        if(oldClienrIdKey!=null){
            redisService.del(oldClienrIdKey);
        }
        String newClientIdKey= org.apache.commons.lang3.StringUtils.substring(UUID.randomUUID().toString()+System.currentTimeMillis(),5,18) ;
        redisService.setex(newClientIdKey, CommonConstant.REFRESH_TOKEN_TIME_OUT,clientId);
        CookieUtils.setCookie(response, CommonConstant.CLIENT_ID_IN_COOKIE,newClientIdKey, CommonConstant.REFRESH_TOKEN_TIME_OUT);
    }
}
