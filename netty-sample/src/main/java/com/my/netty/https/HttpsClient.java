package com.my.netty.https;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLEngine;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;


public class HttpsClient {
	
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	
	private Bootstrap b = new Bootstrap();
	
	private String host;
	
	private int port;
	
    public void connect(String host, int port) throws Exception {
        	this.host = host;
        	this.port = port;
        	 // Configure SSL.
            final SslContext sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        	
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                	
                    SSLEngine engine = sslCtx.newEngine(ch.alloc());              	
                	ch.pipeline().addFirst("ssl", new SslHandler(engine));  //1
            		ch.pipeline().addLast("codec", new HttpClientCodec());
                    ch.pipeline().addLast(new HttpsClientInboundHandler());
                }
            });
    }
    
    public void close() {
    	workerGroup.shutdownGracefully();
    }
    
    public String post(Map<String, Object> params, String uriStr) throws Exception {
    	 // Start the client.
        ChannelFuture f = b.connect(host, port).sync();
        URI uri = new URI(uriStr);
        String msg = null;
        if(params != null && !params.isEmpty()) {
        	List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        	for(Entry<String, Object> keyVal : params.entrySet()){
        		NameValuePair pair = new BasicNameValuePair(keyVal.getKey(), keyVal.getValue().toString());
        		parameters.add(pair);
        	}      	
        	msg = URLEncodedUtils.format(parameters, Charset.forName("UTF-8"));
        	System.out.println("parameters --- " + msg);
        }
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

        // 构建http请求头
        request.headers().set(HttpHeaders.Names.HOST, host);
        request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
        request.headers().set(HttpHeaders.Names.CONTENT_TYPE, HttpHeaders.Values.APPLICATION_X_WWW_FORM_URLENCODED);
   
        // 发送http请求
        ChannelFuture future = f.channel().write(request).sync();
        future.addListener(new ChannelFutureListener() {
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
        f.channel().flush();
        ChannelFuture closeFuture = f.channel().closeFuture();
        closeFuture.addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						if(future.isSuccess()) {
							System.out.println("Connection closed");								
						} else {
							System.out.println("Connection close failed");
							future.cause().printStackTrace();
						}
					}
				});
    	return null;
    }

    public static void main(String[] args) throws Exception {
    	HttpsClient client = null;
    	try {
	        client = new HttpsClient();
	        client.connect("192.168.1.111", 443);
	        for(int i = 0; i < 10000; i++) {
		        Map<String, Object> params = new HashMap<>();
		        params.put("username", "18912345671");
		        params.put("password", "12345678");
		        client.post(params, "/api/user-login");
	        }
    	} finally {
    		client.close();
    	}
    	
    }
}
