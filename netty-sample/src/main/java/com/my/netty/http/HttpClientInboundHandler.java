package com.my.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	//String id = ctx.channel().attr(EchoClient.id).get();
        if (msg instanceof HttpResponse) 
        {
            HttpResponse response = (HttpResponse) msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
            System.out.println("HTTP STATUS:" + response.status().code());
        }
        if(msg instanceof HttpContent)
        {
            HttpContent content = (HttpContent)msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();
        }
    }
}
