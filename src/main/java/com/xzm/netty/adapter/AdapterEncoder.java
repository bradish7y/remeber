package com.xzm.netty.adapter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Bradish7Y on 15/5/27.
 */
public class AdapterEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Message m = (Message)msg ;
        out.writeInt(m.getLength()) ;
        out.writeBytes(m.getBody().getBytes()) ;
    }
}
