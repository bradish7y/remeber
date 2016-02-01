package com.xzm.netty.adapter;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by Bradish7Y on 15/5/27.
 */
public class MessageDecoder extends ReplayingDecoder<State> {
    private Message message = new Message();

    public MessageDecoder() {
        super(State.HEAD);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case HEAD:
                message.setLength(in.readInt());
                Preconditions.checkArgument(message.getLength() < 1024 * 1024, "length should be less than 1024 * 1024");
                checkpoint(State.BODY);
            case BODY:
                byte[] buf = new byte[message.getLength()];
                in.readBytes(buf);
                message.setBody(new String(buf));
                checkpoint(State.HEAD);
                out.add(message);
                break;
            default:
                throw new Error("Shouldn't reach here.");
        }
    }
}
