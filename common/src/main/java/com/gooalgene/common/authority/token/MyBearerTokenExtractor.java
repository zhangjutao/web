package com.gooalgene.common.authority.token;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * create by Administrator on2018/2/6 0006
 */
public class MyBearerTokenExtractor extends BearerTokenExtractor {
    private final static Log logger = LogFactory.getLog(MyBearerTokenExtractor.class);

    private String tokenType;



    @Override
    public String extractToken(HttpServletRequest request) {
        // first check the header...
        String token = extractHeaderToken(request);

        // bearer type allows a request parameter as well
        if (token == null) {
            logger.debug("Token not found in headers. Trying request parameters.");

            Map map=request.getParameterMap();
            Set keSet=map.entrySet();
            for(Iterator itr = keSet.iterator(); itr.hasNext();){
                Map.Entry me=(Map.Entry)itr.next();
                Object ok=me.getKey();
                Object ov=me.getValue();
                String[] value=new String[1];
                if(ov instanceof String[]){
                    value=(String[])ov;
                }else{
                    value[0]=ov.toString();
                }
                for(int k=0;k<value.length;k++){
                    logger.info("草 ："+ok+"="+value[k]);
                }
            }

            Enumeration enu=request.getParameterNames();
            while(enu.hasMoreElements()){
                String paraName=(String)enu.nextElement();
                logger.info("草2 ："+paraName+": "+request.getParameter(paraName));
            }

            token = request.getParameter(OAuth2AccessToken.ACCESS_TOKEN);
            if (token == null) {
                logger.debug("Token not found in request parameters.  Not an OAuth2 request.");
            }
            else {
                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, OAuth2AccessToken.BEARER_TYPE);
            }
        }

        return token;
    }

    @Override
    protected String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while (headers.hasMoreElements()) { // typically there is only one (most servers enforce that)
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
                // Add this here for the auth details later. Would be better to change the signature of this method.
                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE,
                        value.substring(0, OAuth2AccessToken.BEARER_TYPE.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }

        return null;
    }
}
