package cqu.client;

import cqu.Util.Session.LoginUtil;
import cqu.client.handler.LoginResponseHandler;
import cqu.client.handler.MessageResponseHandler;
import cqu.protocal.EncoderDecoder.Handler.PacketDecoder;
import cqu.protocal.EncoderDecoder.Handler.PacketEncoder;
import cqu.protocal.EncoderDecoder.PacketEncDec;
import cqu.protocal.PacketImp.Request.LoginRequestPacket;
import cqu.protocal.PacketImp.Request.MessageRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.UUID;

public class WXclient {
    public static void main(String[] args){
        NioEventLoopGroup handlerGroup=new NioEventLoopGroup();

        Bootstrap bootstrap=new Bootstrap();
        bootstrap
                .group(handlerGroup)
                .channel(NioSocketChannel.class)
                .handler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new PacketDecoder());
                                ch.pipeline().addLast(new LoginResponseHandler());
                                ch.pipeline().addLast(new MessageResponseHandler());
                                ch.pipeline().addLast(new PacketEncoder());
                            }
                        }
                );
        clientStart(bootstrap,"127.0.0.1",8080,5);



    }
    private static void clientStart(final Bootstrap bootstrap,String host,final int port,final int retry){
        bootstrap.connect(host,port).addListener(future -> {
                    if(future.isSuccess()){
                        System.out.println("connet sucess in host:"+host);
                        Channel channel = ((ChannelFuture) future).channel();
                        startConsoleThread(channel);
                    }else if(retry==0){
                        System.out.println("connet host error ,stop");
                    }else{
                        System.out.println("connet host error retry,left "+retry+" times ");
                        clientStart(bootstrap,host,port,retry-1);
                    }
                }
        );
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);

            LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
            loginRequestPacket.setUserId(UUID.randomUUID().toString());
            System.out.print("username>>");
            String username=sc.nextLine();
            System.out.print("password>>");
            String pwd=sc.nextLine();
            loginRequestPacket.setUsername(username);
            loginRequestPacket.setPassword(pwd);
            ByteBuf loginbuf = PacketEncDec.INSTANCE.encode(channel.alloc().ioBuffer(), loginRequestPacket);
            channel.writeAndFlush(loginbuf);


            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    System.out.print(">>");
                    String line = sc.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                    ByteBuf byteBuf = PacketEncDec.INSTANCE.encode(channel.alloc().ioBuffer(), packet);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }

}
