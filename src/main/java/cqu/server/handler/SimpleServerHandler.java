package cqu.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf byteBuf=(ByteBuf)  msg;
        System.out.println(
                        new Date()+
                        ":server received handler shake from clinet----" +
                        byteBuf.toString(Charset.forName("utf-8"))
        );

        System.out.println(new Date()+":writeback to client");
        ByteBuf buffer=connectedMessage(ctx);
        ctx.channel().writeAndFlush(buffer);
    }
    private ByteBuf connectedMessage(ChannelHandlerContext ctx){
        ByteBuf buf=ctx.alloc().buffer();
        byte[] bytes="hi,client!".getBytes(Charset.forName("utf-8"));
        buf.writeBytes(bytes);
        return buf;
    }
}
