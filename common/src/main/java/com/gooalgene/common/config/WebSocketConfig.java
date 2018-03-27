package com.gooalgene.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Set;

/**
 * create by Administrator on2018/2/11 0011
 */
public class WebSocketConfig implements ServerApplicationConfig {
    private Logger logger= LoggerFactory.getLogger(WebSocketConfig.class);
    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> set) {
        return null;
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> set) {
        logger.info("过滤websocket");
        return set;
    }
}
