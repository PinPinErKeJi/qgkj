package com.jeeplus.modules.netty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * netty 配置
 * @author Jin
 */
@Component
@PropertySource(value= {
		"classpath:netty.properties"
})
public class NettyProperties {

	@Value(value="${netty.isserver}")
	private Boolean isServer;
	
	@Value(value="${netty.host}")
	private String host;
	
	@Value(value="${netty.port}")
	private Integer port;
	
	@Value(value="${netty.boss.thread.count}")
	private Integer bossGroupThreadCount;
	
	@Value(value="${netty.worker.thread.count}")
	private Integer workerGroupThreadCount;
	
	@Value(value="${netty.so.keepalive}")
	private Boolean soKeepAlive;
	
	@Value(value="${netty.so.backlog}")
	private Integer soBackLog;

	public Boolean getIsServer() {
		return isServer;
	}

	public void setIsServer(Boolean isServer) {
		this.isServer = isServer;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getBossGroupThreadCount() {
		return bossGroupThreadCount;
	}

	public void setBossGroupThreadCount(Integer bossGroupThreadCount) {
		this.bossGroupThreadCount = bossGroupThreadCount;
	}

	public Integer getWorkerGroupThreadCount() {
		return workerGroupThreadCount;
	}

	public void setWorkerGroupThreadCount(Integer workerGroupThreadCount) {
		this.workerGroupThreadCount = workerGroupThreadCount;
	}

	public Boolean getSoKeepAlive() {
		return soKeepAlive;
	}

	public void setSoKeepAlive(Boolean soKeepAlive) {
		this.soKeepAlive = soKeepAlive;
	}

	public Integer getSoBackLog() {
		return soBackLog;
	}

	public void setSoBackLog(Integer soBackLog) {
		this.soBackLog = soBackLog;
	}
	
}
