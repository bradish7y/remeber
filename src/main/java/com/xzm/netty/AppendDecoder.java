package com.xzm.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Bradish7Y on 15/5/26.
 */
public class AppendDecoder extends ByteToMessageDecoder {
    private int length ;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        if(in.readableBytes() < 3){
//            return ;
//        }
        length ++ ;
        System.out.println("threadId="+Thread.currentThread().getId()+"length = " + length);
        out.add(in.readBytes(in.readableBytes())) ;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
