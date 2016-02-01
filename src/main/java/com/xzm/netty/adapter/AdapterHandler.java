package com.xzm.netty.adapter;

import com.xzm.proto.ProtoBufferPractice;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bradish7Y on 15/5/27.
 */
public class AdapterHandler extends ChannelInboundHandlerAdapter {
    private int s;

    private ExecutorService executor ;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        final ProtoBufferPractice.MsgInfo info = (ProtoBufferPractice.MsgInfo)msg;


        executor.execute(new Runnable() {
            public void run() {
                System.out.println("info.toString() = " + info.toString());
            }
        });
        //TimeUnit.SECONDS.sleep(10000L);
//        System.out.println("###############"+ctx.pipeline().hashCode()) ;
//        Message message = (Message) msg;
//        System.out.println("message = " + message);
//
//        //AbstractBootstrap.option/childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
//        System.out.println("ChannelHandlerContext.alloc():" + ctx.alloc().toString());
//        //内存池都是一个
//        System.out.println("ChannelHandlerContext.alloc().compare.Pooled:" + ctx.alloc().hashCode());
//        System.out.println("ChannelHandlerContext.alloc().compare.Pooled:" + PooledByteBufAllocator.DEFAULT.hashCode());
//
//        //-Dio.netty.allocator.type
//        System.out.println("ByteBufUtil:" + ByteBufAllocator.DEFAULT.isDirectBufferPooled());
//
//        //以上都是需要配置的，以下直接使用
//        //todo 是否每次都是重新new
//        System.out.println("PooledByteBufAllocator:" + PooledByteBufAllocator.DEFAULT.isDirectBufferPooled());
//        System.out.println("UnPooledByteBufAllocator:" + UnpooledByteBufAllocator.DEFAULT.isDirectBufferPooled());
//        System.out.println("UnPooledByteBufAllocator:" + Unpooled.directBuffer());
//
//        System.out.println("in adapter");
//        //ctx.write(Unpooled.copiedBuffer("欢迎光临", CharsetUtil.UTF_8));
//        //ctx.fireChannelRead(msg);
//        ctx.channel().write(Unpooled.copiedBuffer("欢迎光临", CharsetUtil.UTF_8)) ;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        executor = Executors.newSingleThreadExecutor() ;

        ProtoBufferPractice.MsgInfo.Builder msginfo = ProtoBufferPractice.MsgInfo.newBuilder() ;
        msginfo.setGoodID(1L) ;
        msginfo.setGuid("hello protobuf from server") ;
        msginfo.setID(5) ;
        msginfo.setOrder(6) ;
        msginfo.setType("server") ;
        msginfo.setUrl("www.baidu.com") ;

        ProtoBufferPractice.MsgInfo ret = msginfo.build() ;
        ctx.writeAndFlush(ret) ;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

    }
}
