package com.my.netty.chapter2;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.codec.binary.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.channel.ChannelHandler.Sharable;;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	
	public static AtomicLong totalReceivedFileSize = new AtomicLong(0l);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		String content = in.toString(CharsetUtil.UTF_8);
		System.out.println("Server received: " + content); //打印消息至控制台
		
		System.out.println("current received - " + StringUtils.getBytesUtf8(content).length);
		System.out.println("total received - " + totalReceivedFileSize.addAndGet(StringUtils.getBytesUtf8(content).length));
		ctx.write(in); //将接受到的消息写给发送者，而不冲刷出站消息。
		
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
