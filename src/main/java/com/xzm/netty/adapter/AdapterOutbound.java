package com.xzm.netty.adapter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * Created by Bradish7Y on 15/5/28.
 */
public class AdapterOutbound extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("in outbound");
        ctx.writeAndFlush(msg) ;

    }
}
