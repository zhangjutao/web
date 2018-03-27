package com.gooalgene.common.cache;

import com.gooalgene.common.authority.token.TokenPojo;
import com.gooalgene.common.constant.CommonConstant;
import com.gooalgene.utils.Function;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * create by Administrator on2018/2/23 0023
 */
@Service
public class RedisService {
    private Logger log= LoggerFactory.getLogger(RedisService.class);

    public <T> T excute(Function<T,Jedis> fun){
        try (Jedis jedis=jedisPool.getResource()){
            return fun.callback(jedis);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return null;
    }

    @Autowired
    private JedisPool jedisPool;

    public Long del(final String key){
        return excute(new Function<Long, Jedis>() {
            @Override
            public Long callback(Jedis jedis) {
                Long result=jedis.del(key);
                return result;
            }
        });
    }

    public String setex(final String key,final Integer time ,final String value){
        return excute(new Function<String, Jedis>() {
            @Override
            public String callback(Jedis jedis) {
                String result = jedis.setex(key,time, value);
                return result;
            }
        });
    }

    public String set(final String key, final String value){
        return excute(new Function<String, Jedis>() {
            @Override
            public String callback(Jedis jedis) {
                String result = jedis.set(key, value);
                return result;
            }
        });
    }

    public String get(final String key){
        return excute(new Function<String, Jedis>() {
            @Override
            public String callback(Jedis jedis) {
                String result = jedis.get(key);
                return result;
            }
        });
    }

    public byte[] get(final byte[] key){
        return excute(new Function<byte[], Jedis>() {
            @Override
            public byte[] callback(Jedis jedis) {
                byte[] result = jedis.get(key);
                return result;
            }
        });
    }

    public String hget(final String prefix,final String key){
        return excute(new Function<String, Jedis>() {
            @Override
            public String callback(Jedis jedis) {
                String result = jedis.hget(prefix,key);
                return result;
            }
        });
    }

    private RuntimeSchema<TokenPojo> jwtPojoSchema=RuntimeSchema.createFrom(TokenPojo.class);
    private RuntimeSchema<OAuth2Authentication> authenticationSchema=RuntimeSchema.createFrom(OAuth2Authentication.class);

    public TokenPojo getJwt(final String tokenValue){
        return excute(new Function<TokenPojo, Jedis>() {
            @Override
            public TokenPojo callback(Jedis jedis) {
                byte[] bytes=jedis.get(tokenValue.getBytes());
                if(bytes!=null){
                    TokenPojo refreshToken=jwtPojoSchema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,refreshToken,jwtPojoSchema);
                    return refreshToken;
                }
                return null;
            }
        });
    }
    public String setJwt(final String tokenValue,final TokenPojo refreshToken){
        return excute(new Function<String, Jedis>() {
            @Override
            public String callback(Jedis jedis) {
                byte[] bytes=ProtostuffIOUtil.toByteArray(refreshToken,jwtPojoSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                String setex = jedis.setex(tokenValue.getBytes(), 60 * 60 * 3, bytes);
                return setex;
            }
        });
    }

    public OAuth2Authentication getAuthentication(final String key){
        return excute(new Function<OAuth2Authentication, Jedis>() {
            @Override
            public OAuth2Authentication callback(Jedis jedis) {
                byte[] bytes=jedis.get(key.getBytes());
                if(bytes!=null){
                    OAuth2Authentication authentication = authenticationSchema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,authentication,authenticationSchema);
                    return authentication;
                }
                return null;
            }
        });
    }
    public String setAuthentication(final String key,final OAuth2Authentication authentication){
        return excute(new Function<String, Jedis>() {
            @Override
            public String callback(Jedis jedis) {
                byte[] bytes=ProtostuffIOUtil.toByteArray(authentication,authenticationSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                String setex = jedis.setex(key.getBytes(), CommonConstant.REMEMBER_ME_TIME_OUT, bytes);
                return setex;
            }
        });
    }
}
