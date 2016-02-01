package com.xzm.netty.adapter;

import com.xzm.proto.ProtoBufferPractice;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by Bradish7Y on 15/5/27.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        EventLoopGroup work = new NioEventLoopGroup();
        Bootstrap boot = new Bootstrap();
        boot.channel(NioSocketChannel.class)
                .group(work)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        ch.pipeline().addLast(new ProtobufDecoder(ProtoBufferPractice.MsgInfo.getDefaultInstance()));
                        ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                        ch.pipeline().addLast(new ProtobufEncoder());
                        ch.pipeline().addLast(new AdapterClientHandler());
                    }
                }).option(ChannelOption.SO_KEEPALIVE, true);
        for (int i = 0; i < 1; i++) {
            ChannelFuture f = boot.connect("127.0.0.1", 8080).sync() ;

            f.awaitUninterruptibly();

            assert (f.isDone());
            f.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {

                    if (!future.isSuccess()) {
                        System.out.println("need to reconnect");
                    } else {
                        System.out.println("success");
                    }
                }
            });
        }

        System.out.println("end");
        // future.channel().closeFuture().sync() ;

        ChannelGroup s= new DefaultChannelGroup(GlobalEventExecutor.INSTANCE) ;
    }
}
