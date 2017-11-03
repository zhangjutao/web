package com.gooalgene.common.dao;

import com.gooalgene.common.authority.Token;
import com.gooalgene.common.persistence.MyBatisDao;

/**
 * Created by liuyan on 2017/11/3.
 */
@MyBatisDao
public interface TokenDao {
    Token getTokenByUserId(int user_id);
    boolean insertToken(Token token);
    boolean updateToken(Token token);
}
