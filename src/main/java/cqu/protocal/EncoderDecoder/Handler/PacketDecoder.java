package cqu.protocal.EncoderDecoder.Handler;

import cqu.protocal.EncoderDecoder.PacketEncDec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out){
        out.add(PacketEncDec.INSTANCE.decode(in));
    }
}
