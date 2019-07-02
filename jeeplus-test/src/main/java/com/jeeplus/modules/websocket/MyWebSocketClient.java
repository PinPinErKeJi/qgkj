package com.jeeplus.modules.websocket;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;


/**
 * 客户端
 * @author Jin
 */
public final class MyWebSocketClient extends WebSocketClient{
	
	public MyWebSocketClient(URI serverUri, Draft protocolDraft) {
		super(serverUri, protocolDraft);
	}
	
	private static Object lock = new Object();
	
	private static MyWebSocketClient client;
	
	public static MyWebSocketClient getClient() {
		if(client == null) {
			synchronized (lock) {
				if(client == null) {
					try {
						MyWebSocketConfig config = MyWebSocketConfig.newInstance();
						String url = config.getUri()+"/"+config.getClientName();
						client = new MyWebSocketClient(new URI(url),new Draft_6455());
						// 阻塞线程，直到连接成功
						client.connectBlocking();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return client;
	}
	public final static boolean sendMessage(String message) {
		try {
			if(getClient() == null) {
				return false;
			}
			getClient().send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public void onOpen(ServerHandshake handshakedata) {
		
	}
	@Override
	public void onMessage(String message) {
		// 接收数据
		System.out.println("client 接收到的数据："+message);
	}
	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("连接断开");
		client = null;
	}
	@Override
	public void onError(Exception ex) {
		System.out.println("信息通道");
		client = null;
		ex.printStackTrace();
	}
	
	public static void main(String[] args) {
		sendMessage("hello");
	}
}
