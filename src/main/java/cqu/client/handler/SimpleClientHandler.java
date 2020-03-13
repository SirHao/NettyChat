package cqu.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(new Date()+"client shake handle");
        ByteBuf buffer=connectedMessage(ctx);
        ctx.channel().writeAndFlush(buffer);


    }
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf byteBuf=(ByteBuf)  msg;
        System.out.println(
                new Date()+
                        ":client received handler shake from server----" +
                        byteBuf.toString(Charset.forName("utf-8"))
        );

    }
    private ByteBuf connectedMessage(ChannelHandlerContext ctx){
        ByteBuf buf=ctx.alloc().buffer();
        byte[] bytes="hi,server!".getBytes(Charset.forName("utf-8"));
        buf.writeBytes(bytes);
        return buf;
    }
}
