package com.clown.sell.service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class Websocket {
    
    private Session session;
    
    private static CopyOnWriteArraySet<Websocket> webSocketSet = new CopyOnWriteArraySet<Websocket>();
    
    @OnOpen
    public void opOpen(Session session) {
	this.session = session;
	webSocketSet.add(this);
	log.info("【webSocket消息】 有新的连接，总数： {} ", webSocketSet.size());
    }
    
    @OnClose
    public void onClose() {
   	webSocketSet.remove(this);
   	log.info("【webSocket消息】连接断开，总数： {} ", webSocketSet.size());
    }
    
    @OnMessage
    public void onMessage(String message) {
	log.info("【webSocket消息】收到客户端发来的消息：{} ", message);
    }
    
    public void sendMessage(String message) {
	for (Websocket websocket : webSocketSet) {
	    log.info("【webSocket消息】广播消息, message={} ", message);
	    try {
		websocket.session.getBasicRemote().sendText(message);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }
}
