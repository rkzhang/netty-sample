package com.my.netty.chapter2;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

public class EchoClient {	
	
	private final String host;
	
	private final int port;
	
	public static final AttributeKey<String> id = AttributeKey.newInstance("ID");
	
	private static final Bootstrap b = new Bootstrap();
	
	private static final EventLoopGroup group = new NioEventLoopGroup();	
	
	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void start() throws Exception {	
		b.group(group)
			.channel(NioSocketChannel.class)
			.remoteAddress(new InetSocketAddress(host, port))
			.handler(new ChannelInitializer<SocketChannel>() {								
				
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//IdleStateHandler将在被触发时发送一个IdleStateEvent事件
					ch.pipeline().addLast(new IdleStateHandler(0, 0, 3, TimeUnit.SECONDS));
					ch.pipeline().addLast(new EchoClientHandler());
				}
				
			});
		
		for(int i = 0; i < 1; i++) {
			b.attr(id, i + "");
			//ChannelFuture f = b.connect().sync();	//连接到远程节点，阻塞等待直到连接完成
			ChannelFuture f = b.connect().sync();
			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if(future.isSuccess()) {
						System.out.println("Connection established");
					} else {
						System.out.println("Connection attempt failed");
						future.cause().printStackTrace();
					}
				}
			});
			
			System.out.println("has sent");	
			f.channel().closeFuture(); //Channel关闭
		}
		
		
		Thread.sleep(3000);
		//System.out.println(300000 / intervel + "request per second");
		
	}
	
	public void close() throws InterruptedException {
		group.shutdownGracefully().sync();
		System.out.println("客户端退出...");
	}

	public static void main(String[] args) throws InterruptedException {
		EchoClient client = null;
		try {
			client = new EchoClient("127.0.0.1", 8080);
			client.start();
			CountDownLatch latch = new CountDownLatch(1);
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
			client.close();
		} finally {
			client.close();
		}
	}

}
