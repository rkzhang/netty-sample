package com.my.netty.chapter2;

import org.apache.commons.codec.binary.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARBEAT", CharsetUtil.UTF_8));

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {	
		String id = ctx.channel().attr(EchoClient.id).get();
		System.out.println("send id --- " + id);
		StringBuilder builder = createContent();
		System.out.println("total size of the  packet --- " + StringUtils.getBytesUtf8(builder.toString()).length);
		byte[] content = builder.toString().getBytes("UTF-8");
		ByteBuf bf = Unpooled.buffer(content.length);
		bf.writeBytes(content);
		
		System.out.println("size in buffer --- " + bf.array().length);
		
		ctx.writeAndFlush(bf);
		//bf.release();
	}

	/**
	 * 处理触发的IdleStateEvent事件
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		System.out.println("heart beat");
		if(evt instanceof IdleStateEvent) {
			//发送心跳信息， 并在发送失败时关闭连接						
			ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
		} else {
			super.userEventTriggered(ctx, evt);
		}
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		String id = ctx.channel().attr(EchoClient.id).get();
		System.out.println("read id --- " + id + " Client received: " + msg.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	private StringBuilder createContent() {
		StringBuilder builder = new StringBuilder();
		builder.append("#################################################################################################");
		builder.append("简介");
		builder.append("发动机及涡轮机联盟弗里的希哈芬股份有限公司（简称MTU）");
		builder.append("MTU为戴姆勒-奔驰集团属下公司，是世界领先的柴油发动机制造商，其柴油发动机功率从35kw-9000kw，广泛用于舰船、重型汽车和工程机械、铁路机车。MTU不仅仅制造柴油发动机，还制造面向最终用户的完整成套产品。");
		builder.append("#################################################################################################");
		return builder;
	}

}
