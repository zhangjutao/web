package com.gooalgene.common.service;

import com.gooalgene.common.authority.Token;
import com.gooalgene.common.dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuyan on 2017/11/3.
 */
@Service
public class TokenService {
    @Autowired
     private  TokenDao tokenDao;


     public Token getTokenByUserId(int userid){
         Token token= tokenDao.getTokenByUserId(userid);
         return token;
     }

    public boolean insertToken(Token token){
        return tokenDao.insertToken(token);
    }

    public boolean updateToken(Token token){
        return tokenDao.updateToken(token);
    }
}
