package cqu.server.handler;

import cqu.protocal.EncoderDecoder.PacketEncDec;
import cqu.protocal.Packet;
import cqu.protocal.PacketImp.LoginRequestPacket;
import cqu.protocal.PacketImp.LoginResponsePacket;
import cqu.protocal.PacketImp.MessageRequestPacket;
import cqu.protocal.PacketImp.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


import java.nio.charset.Charset;
import java.util.*;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf byteBuf=(ByteBuf)  msg;
        Packet packet= PacketEncDec.INSTANCE.decode(byteBuf);

        if(packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequest=(LoginRequestPacket) packet;

            LoginResponsePacket loginRespons=new LoginResponsePacket();
            loginRespons.setVersion(loginRequest.getVersion());

            if(validUser(loginRequest)){
                System.out.println(new Date()+" user login: "+loginRequest.getUsername());
                loginRespons.setSuccess(true);
            }else {
                System.out.println(new Date()+" user failed!");
                loginRespons.setReason("pwd wrong");
                loginRespons.setSuccess(false);

            }
            ByteBuf responsebuf=PacketEncDec.INSTANCE.encode(ctx.alloc().ioBuffer(),loginRespons);
            ctx.channel().writeAndFlush(responsebuf);
        }else if(packet instanceof MessageRequestPacket){
            MessageRequestPacket messageRequestPacket=(MessageRequestPacket) packet;
            System.out.println(new Date()+":receive client msg: "+messageRequestPacket.getMessage());
            MessageResponsePacket messageResponsePacket=new MessageResponsePacket();
            messageResponsePacket.setMessage("【server reply】"+messageRequestPacket.getMessage());
            ByteBuf resp=PacketEncDec.INSTANCE.encode(ctx.alloc().ioBuffer(),messageResponsePacket);
            ctx.channel().writeAndFlush(resp);
        }

    }
    private boolean validUser(LoginRequestPacket loginRequestPacket){
        return true;
    }

    private ByteBuf connectedMessage(ChannelHandlerContext ctx){
        ByteBuf buf=ctx.alloc().buffer();
        byte[] bytes="hi,client!".getBytes(Charset.forName("utf-8"));
        buf.writeBytes(bytes);
        return buf;
    }
}
