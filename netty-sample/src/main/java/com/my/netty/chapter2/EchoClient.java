package com.my.netty.chapter2;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.UUID;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class EchoClient {
	
	private final String host;
	
	private final int port;
	
	public static final AttributeKey<String> id = AttributeKey.newInstance("ID");
	
	private static final Bootstrap b = new Bootstrap();
	
	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			b.group(group)
				.channel(NioSocketChannel.class)
				.remoteAddress(new InetSocketAddress(host, port))
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new EchoClientHandler());
					}
				});
			Date begin = new Date();
			for(int i = 0; i < 30000; i++) {
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
				//f.channel().closeFuture().sync();	//阻塞，直到Channel关闭
				f.channel().closeFuture();
				System.out.println("has closed");
			}
			Date end = new Date();
			long intervel = (end.getTime() - begin.getTime())/1000;
			
			Thread.sleep(3000);
			System.out.println(300000 / intervel + "request per second");
		} finally {
			group.shutdownGracefully().sync();
			System.out.println("客户端退出...");
		}
	}

	public static void main(String[] args) {
		try {
			new EchoClient("127.0.0.1", 8080).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
