package com.my.netty.initializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Listing 11.7 Sending heartbeats
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel>
    {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //IdleStateHandler将在被触发时发送一个IdleStateEvent事件
        pipeline.addLast(
                new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
        pipeline.addLast(new HeartbeatHandler());
    }

    public static final class HeartbeatHandler
        extends ChannelInboundHandlerAdapter {
    	//发送到远程节点的心跳信息
        private static final ByteBuf HEARTBEAT_SEQUENCE =
                Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(
                "HEARTBEAT", CharsetUtil.ISO_8859_1));
        
        /**
         * 实现userEventTriggered()处理IdleStateEvent事件，这里用以发送心跳消息
         */
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx,
            Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
            	//发送心跳信息并在发送失败时关闭该连接
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                     .addListener(
                         ChannelFutureListener.CLOSE_ON_FAILURE);
            } else {
            	//不是IdleStateEvent事件，所以将它传递给下一个ChannelInboundHandler
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
