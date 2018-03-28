package com.gooalgene.common.authority.token;


import org.springframework.security.crypto.codec.Base64;

/**
 * create by Administrator on2018/2/7 0007
 */
public class TokenPojo {
    private String access_token;
    private String refresh_token;
    private String jti;
    private Integer expires_in;
    private String scope;
    private String token_type;
    private String uniqueKey;
    private String clientId="qtl";

    public String getAuthorizationByClientId(){
        String clientIdAndSecret=clientId+":"+clientId;
        byte[] encode = Base64.encode(clientIdAndSecret.getBytes());
        return new String(encode);
    }



    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
