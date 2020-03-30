package cqu.server;

import cqu.protocal.EncoderDecoder.Handler.PacketDecoder;
import cqu.protocal.EncoderDecoder.Handler.PacketEncoder;
import cqu.server.handler.LoginRequestHandler;
import cqu.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class WXserver {
    public static void main(String[] args){
        NioEventLoopGroup listenGroup=new NioEventLoopGroup();
        NioEventLoopGroup handlerGroup=new NioEventLoopGroup();

        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap
                .group(listenGroup,handlerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new PacketDecoder());
                                ch.pipeline().addLast(new LoginRequestHandler());
                                ch.pipeline().addLast(new MessageRequestHandler());
                                ch.pipeline().addLast(new PacketEncoder());
                            }
                        }
                );
        serverStart(serverBootstrap,8080,5);
    }

    private static void serverStart(final ServerBootstrap serverBootstrap,final int port,final int retry){
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("bind port sucess in port:"+port);
                }else if(retry==0){
                    System.out.println("bind port error ,stop");
                }else{
                    System.out.println("bind port error retry,left "+retry+" times ");
                    serverStart(serverBootstrap,port+1,retry-1);
                }
            }
        });
    }



}
