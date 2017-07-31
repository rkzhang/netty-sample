package com.my.netty.http;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class HttpClient {
	
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	
	private Bootstrap b = new Bootstrap();
	
	private String host;
	
	private int port;
	
    public void connect(String host, int port) throws Exception {
        	this.host = host;
        	this.port = port;
        	
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new HttpClientInboundHandler());
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
        f.channel().write(request);
        f.channel().flush();
        f.channel().closeFuture().sync();
    	return null;
    }

    public static void main(String[] args) throws Exception {
    	HttpClient client = null;
    	try {
	        client = new HttpClient();
	        client.connect("127.0.0.1", 8000);
	        Map<String, Object> params = new HashMap<>();
	        params.put("username", "18912345671");
	        params.put("password", "12345678");
	        client.post(params, "http://127.0.0.1:8000/api/user-login");
    	} finally {
    		client.close();
    	}
    	
    }
}