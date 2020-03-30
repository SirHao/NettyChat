package cqu.server.handler;

import cqu.protocal.EncoderDecoder.PacketEncDec;
import cqu.protocal.PacketImp.Request.MessageRequestPacket;
import cqu.protocal.PacketImp.Response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        System.out.println(new Date()+":receive from client msg: "+messageRequestPacket.getMessage());
        MessageResponsePacket messageResponsePacket=new MessageResponsePacket();
        messageResponsePacket.setMessage("[server reply]"+messageRequestPacket.getMessage());
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
