package cqu.client.handler;

import cqu.protocal.EncoderDecoder.PacketEncDec;
import cqu.protocal.Packet;
import cqu.protocal.PacketImp.LoginRequestPacket;
import cqu.protocal.PacketImp.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(new Date()+":welcome!");
        LoginRequestPacket loginRequest=new LoginRequestPacket();

        Scanner in = new Scanner(System.in);
        System.out.print("input username:>> ");
        String username = in.nextLine();
        System.out.print("input password:>> ");
        String pwd = in.nextLine();
        loginRequest.setUserId(UUID.randomUUID().toString());
        loginRequest.setUsername(username);
        loginRequest.setPassword(pwd);
        ByteBuf byteBuf= PacketEncDec.INSTANCE.encode(ctx.alloc().ioBuffer(),loginRequest);
        in.close();
        ctx.channel().writeAndFlush(byteBuf);



    }
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf byteBuf=(ByteBuf)  msg;
        Packet packet=PacketEncDec.INSTANCE.decode(byteBuf);
        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponse=(LoginResponsePacket) packet;
            if(loginResponse.isSuccess()){
                System.out.println(new Date()+": login sucess");
            }else {
                System.out.println(new Date()+": login failed");
            }
        }

    }
    private ByteBuf connectedMessage(ChannelHandlerContext ctx){
        ByteBuf buf=ctx.alloc().buffer();
        byte[] bytes="hi,server!".getBytes(Charset.forName("utf-8"));
        buf.writeBytes(bytes);
        return buf;
    }
}
