package com.gooalgene.common.service;

import com.gooalgene.common.dao.ConfigDao;
import com.gooalgene.entity.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService {

    @Autowired
    private ConfigDao configDao;

    public List<Configuration> getAllConfig(){
        return configDao.getAllConfig();
    }

    public Configuration findValueByKey(String key){
        return configDao.findValueByKey(key);
    }

    public Configuration findKeyByValue(String value){
        return configDao.findKeyByValue(value);
    }
}
