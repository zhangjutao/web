package com.gooalgene.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * create by Administrator on2018/2/8 0008
 */
@ServerEndpoint("/webSocket")
@Component
public class WebSocket {
    private Logger log= LoggerFactory.getLogger(WebSocket.class);

    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSocketSet=new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSocketSet.add(this);
        log.info("有新的连接，总数：{}",webSocketSet.size());
    }
    @OnClose
    public void OnClose(){
        webSocketSet.remove(this);
        log.info("连接断开，总数：{}",webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("接到消息：{}",message);
    }

    public void sendMessage(String message) throws IOException {
        log.info("向客户端推送消息：{}",message);
        for(WebSocket webSocket:webSocketSet){
            webSocket.session.getBasicRemote().sendText(message);
        }
    }

}
