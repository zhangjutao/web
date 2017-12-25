package com.gooalgene.common.handler;

import com.mongodb.DBObject;
import com.mongodb.MongoException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;

import java.util.List;

public class DocumentCallbackHandlerImpl<T> implements DocumentCallbackHandler {

    private String key; //要从mongodb返回值中取的值

    private List<T> resultCollection; //取出来的值存在这个集合中

    public DocumentCallbackHandlerImpl(String key, List<T> resultCollection) {
        this.key = key;
        this.resultCollection = resultCollection;
    }

    @Override
    public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
        T consequencetype = (T) dbObject.get(key);
        resultCollection.add(consequencetype);
    }
}
