package com.jeeplus.modules.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Netty时间服务器客户端
 * @author Jin
 * @date 2018年9月27日
 * @version 1.0
 */
public class TimeClient {
	public static void main(String[] args) {
		try {
			new TimeClient().connect("127.0.0.1", 8088);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void connect(String ip,int port) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
					 .option(ChannelOption.TCP_NODELAY,true)
					 .handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
								.addLast(new StringEncoder())
								.addLast(new LineBasedFrameDecoder(1024))
								.addLast(new StringDecoder())
								.addLast(new TimeClientHandler());
						}
					 });
			// 异步连接请求操作
			ChannelFuture future = bootstrap.connect(ip, port).sync();
			// 等待客户端链路关闭
			future.channel().closeFuture().sync();
			System.out.println("客户端关闭");
		} finally {
			group.shutdownGracefully();
			System.out.println("优雅退出");
		}
	}
}
