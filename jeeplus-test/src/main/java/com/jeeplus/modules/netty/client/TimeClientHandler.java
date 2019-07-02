package com.jeeplus.modules.netty.client;


import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter{
	
	private static final Logger log = Logger.getLogger(TimeClientHandler.class.getName());
	
	private int counter = 0;
	private byte[] req;
	
	public TimeClientHandler() {
		req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warning("Unexpected exception from downstream:"+cause.getMessage());
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("链接成功");
		ByteBuf message =null;
		for (int i = 0; i < 10; i++) {
			System.out.println(i);
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		ByteBuf buf = (ByteBuf) msg;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
		String body = (String) msg;//new String(req,"UTF-8");
		System.out.println("Now is:" + body+";counter :"+ ++counter);
	}
	
}
