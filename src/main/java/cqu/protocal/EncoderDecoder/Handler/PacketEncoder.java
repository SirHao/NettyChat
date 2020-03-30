package cqu.protocal.EncoderDecoder.Handler;

import cqu.protocal.EncoderDecoder.PacketEncDec;
import cqu.protocal.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketEncDec.INSTANCE.encode(out, packet);
    }
}