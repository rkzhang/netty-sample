package com.my.netty.http;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import com.my.netty.chapter2.EchoClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;

public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {

    private HttpRequest request;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
    	
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
            String uri = request.getUri();
            System.out.println("Request Uri ### " + uri);
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            List<NameValuePair> parameters = URLEncodedUtils.parse(buf.toString(io.netty.util.CharsetUtil.UTF_8), Charset.forName("UTF-8"));
            //System.out.println("Request content ### " + URLDecoder.decode(buf.toString(io.netty.util.CharsetUtil.UTF_8), "utf-8"));
            
            StringBuilder res = new StringBuilder();
            System.out.println("################");
            for(NameValuePair param : parameters) {
            	String rest = param.getName() + " --- " + param.getValue();
            	System.out.println(rest);
            	res.append(rest);
            }           
            System.out.println("################");
            buf.release();
           
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                    OK, Unpooled.wrappedBuffer(res.toString().getBytes("UTF-8")));
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH,
                    response.content().readableBytes());
            if (HttpHeaders.isKeepAlive(request)) {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
            }
            ctx.write(response);
            ctx.flush();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(cause.getMessage());
        ctx.close();
    }

}