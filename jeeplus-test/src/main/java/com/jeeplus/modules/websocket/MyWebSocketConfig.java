package com.jeeplus.modules.websocket;

import com.jeeplus.common.utils.PropertiesLoader;

/**
 * WebSocket配置
 * @author Jin
 *
 */
public final class MyWebSocketConfig {

	private static PropertiesLoader loader = new PropertiesLoader("/websocketclient.properties");
	private static MyWebSocketConfig config = new MyWebSocketConfig();
	private String uri;
	private String clientName;
	
	private MyWebSocketConfig() {
		this.uri = loader.getProperty("socket.server.uri");
		this.clientName = loader.getProperty("socket.client.name");
	}
	
	public String getUri() {
		return uri;
	}
	public String getClientName() {
		return clientName;
	}
	
	public static MyWebSocketConfig newInstance() {
		return config;
	}
}
