package cqu.server.handler;

import cqu.protocal.PacketImp.Request.LoginRequestPacket;
import cqu.protocal.PacketImp.Response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequest) {
        LoginResponsePacket loginRespons=new LoginResponsePacket();
        loginRespons.setVersion(loginRequest.getVersion());

        if(validUser(loginRequest)){
            System.out.println(new Date()+"a user login: "+loginRequest.getUsername());
            loginRespons.setSuccess(true);
        }else {
            System.out.println(new Date()+"a user login failed!");
            loginRespons.setReason("pwd wrong");
            loginRespons.setSuccess(false);
        }
        ctx.channel().writeAndFlush(loginRespons);
    }
    private boolean validUser(LoginRequestPacket loginRequestPacket){return true;}

}
