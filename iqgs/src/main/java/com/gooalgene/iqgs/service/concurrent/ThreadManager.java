package com.gooalgene.iqgs.service.concurrent;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 整合数据库线程统一调度服务类
 */
@Service
public class ThreadManager implements InitializingBean {

    private ExecutorService threadPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        threadPool = Executors.newCachedThreadPool();
    }
}
