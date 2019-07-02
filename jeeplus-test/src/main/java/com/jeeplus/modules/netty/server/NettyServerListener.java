package com.jeeplus.modules.netty.server;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeeplus.modules.netty.NettyProperties;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 服务启动监听
 * @author Jin
 */
@Component
public class NettyServerListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerListener.class);
	
	private NettyProperties properties;
	private ServerBootstrap serverBootstrap;
	private EventLoopGroup boss;
	private EventLoopGroup work;
	
	@Resource
	private ServerChannelHandlerAdapter handler;
	
	@Autowired
	public NettyServerListener(NettyProperties properties) {
		this.properties = properties;
		this.boss = new NioEventLoopGroup(properties.getBossGroupThreadCount());
		this.work = new NioEventLoopGroup(properties.getWorkerGroupThreadCount());
		this.serverBootstrap = new ServerBootstrap();
	}
	
	@PreDestroy
	public void close() {
		LOGGER.info("Netty 服务端关闭");
		boss.shutdownGracefully();
		work.shutdownGracefully();
	}

	// 开启服务
	public void start() {
		try {
			serverBootstrap.group(boss, work)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, properties.getSoBackLog())
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()
						/** 拆包/粘包 换行文本 解码器 */
						.addLast(new LineBasedFrameDecoder(1024))
						.addLast(new StringDecoder())
						.addLast(handler);
					}
				});
			int port = properties.getPort();
			LOGGER.info("netty服务器在[{}]端口启动监听", port);
            ChannelFuture f = serverBootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
		} catch(Exception e) {
			LOGGER.info("Netty 服务端异常，释放资源");
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}
	}
}
