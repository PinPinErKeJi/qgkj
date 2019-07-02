package com.jeeplus.modules.websocket;

import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/websocket/cmd/{name}")
public class MyWebSocket {
	private String name;
	private Session session;
	// 后期多的话可以保存到缓存或数据库中
	private static ConcurrentHashMap<String,MyWebSocket> webSocketSet = new ConcurrentHashMap<>();
	
	@OnOpen
	public void onOpen(Session session,@PathParam("name")String name) {
		webSocketSet.put(name, this);
		System.out.println("name:"+name);
		this.session = session;
		this.name =name;
		session.getAsyncRemote().sendText("connect success.");
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@OnClose
    public void close(){
		System.out.println("client - "+this.name +" close.");
        webSocketSet.remove(this);
    }
	@OnMessage
	public void onMessage(String message) {
		System.out.println("server 收到数据:"+message);
	}
	
	public final static boolean sendMessage(String message,String name) {
		try {
			webSocketSet.get(name).getSession().getAsyncRemote().sendText(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Session getSession() {
		return session;
	}
	
}
