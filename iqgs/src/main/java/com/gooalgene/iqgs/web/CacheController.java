package com.gooalgene.iqgs.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "/cache")
public class CacheController implements InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private CacheManager cacheManager;

    private Cache cache;

    @RequestMapping(value = "/admin")
    public ModelAndView cacheAdminPage(){
        ModelAndView view = new ModelAndView("iqgs/cache-admin");
        Cache advanceSearchCache = cacheManager.getCache("advanceSearch");  //存储高级搜索相关缓存数据
        com.google.common.cache.Cache advacenSearchGuavaCache = ((GuavaCache) advanceSearchCache).getNativeCache();  //排序相关数据缓存
        com.google.common.cache.Cache guavaCache = ((GuavaCache) cache).getNativeCache();
        ConcurrentMap cacheResult = guavaCache.asMap();
        ConcurrentMap advanceSearchCacheMap = advacenSearchGuavaCache.asMap();
        cacheResult.putAll(advanceSearchCacheMap);
        //获取系统运行状况相关信息
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        Method[] declaredMethods = operatingSystemMXBean.getClass().getDeclaredMethods();
        for (Method method : declaredMethods){
            method.setAccessible(true);
            Object value = null;
            if (method.getName().equals("getProcessCpuLoad")){
                try {
                    value = method.invoke(operatingSystemMXBean);
                    view.addObject("processCpuLoad", value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    logger.error("获取系统CPU负载错误", e.getCause());
                }
            }
        }
        view.addObject("cacheResult", cacheResult);
        view.addObject("keySize", cacheResult.size());
        return view;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = cacheManager.getCache("sortCache");
    }
}
