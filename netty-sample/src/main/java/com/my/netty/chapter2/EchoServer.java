package com.my.netty.chapter2;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class EchoServer {
	
	private final int port;
	
	public EchoServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception{
		new EchoServer(8080).start();
	}
	
	public void start() throws Exception {
		final EchoServerHandler serverHandler = new EchoServerHandler();
		EventLoopGroup group = new NioEventLoopGroup();	//创建EventLoopGroup
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(group)
				.channel(NioServerSocketChannel.class)	//指定所使用的NIO传输Channel
				.localAddress(new InetSocketAddress(port))	//指定所使用的套接字地址
				.childHandler(new ChannelInitializer<SocketChannel>() {
					//添加一个EchoServerHandler到子Channel的ChannelPipeline
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						
						//EchoServerHandler被标注为@Shareable,所以我们总是使用同样的实例
						ch.pipeline().addLast(serverHandler); //使用一个EchoServerHandler的实例初始化每一个新的Channel;
					}
					
				});
				ChannelFuture f = b.bind().sync();	//异步地绑定服务器，调用sync()方法阻塞等待直到绑定完成
				System.out.println("应用服务启动...");
				f.channel().closeFuture().sync();	//获取Channel的CloseFuture,并且阻塞当前线程直到它完成
				System.out.println("应用服务关闭...");
		} finally {
			group.shutdownGracefully().sync();
			System.out.println("资源回收...");
		}
	}
}
