package com.xzm.netty.adapter;

import com.google.common.base.Strings;
import com.xzm.proto.ProtoBufferPractice;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Bradish7Y on 15/5/27.
 */
public class AdapterClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ProtoBufferPractice.MsgInfo info = (ProtoBufferPractice.MsgInfo)msg ;
        System.out.println("info.toString() = " + info.toString());

        ProtoBufferPractice.MsgInfo.Builder msginfo = ProtoBufferPractice.MsgInfo.newBuilder() ;
        msginfo.setGoodID(1L) ;
        msginfo.setGuid("hello protobuf from client") ;
        msginfo.setID(5) ;
        msginfo.setOrder(6) ;
        msginfo.setType(Strings.repeat("client", 1)) ;
        msginfo.setUrl("www.163.com") ;

        ProtoBufferPractice.MsgInfo ret = msginfo.build() ;
        ctx.writeAndFlush(ret) ;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

//        Message m = new Message() ;
//        m.setBody("bradish");
//        m.setLength(m.getBody().length());
//        ByteBuf bb = Unpooled.buffer(10) ;
//        bb.writeInt(m.getLength()) ;
//        bb.writeBytes(m.getBody().getBytes()) ;
//
//        ctx.writeAndFlush(bb) ;
        //ctx.writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8)) ;

    }
}
