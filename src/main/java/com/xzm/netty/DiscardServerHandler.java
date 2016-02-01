package com.xzm.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created by Bradish7Y on 15/5/25.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
//            ByteBuf bb = (ByteBuf) msg;
//            System.out.println("bb.readableBytes() = " + bb.readableBytes());
//            byte[] b = new byte[bb.readableBytes()];
//            bb.readBytes(b);
//            System.out.println("b = " + bb.toString(CharsetUtil.US_ASCII));
//            bb.release();
            ctx.write(msg) ;
            ctx.flush() ;
            //ctx.close();
        }finally {
            //ReferenceCountUtil.release(msg) ;
        }
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        System.out.println("active....");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
        System.out.println("end...");
//        final ByteBuf time = ctx.alloc().buffer(8);
//
//        //PooledByteBufAllocator.DEFAULT.directBuffer(1024) ;
//
//        time.writeBytes("Welcome".getBytes()) ;
//        final ChannelFuture f = ctx.writeAndFlush(time);
//        //f.addListener(ChannelFutureListener.CLOSE) ;
//        f.addListener(new ChannelFutureListener() {
//            public void operationComplete(ChannelFuture future) {
//                //assert f == future;
//                //future.channel().close() ;
//            }
//        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
