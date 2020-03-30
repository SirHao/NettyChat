package cqu.client;

import cqu.Util.Session.LoginUtil;
import cqu.client.handler.SimpleClientHandler;
import cqu.protocal.EncoderDecoder.PacketEncDec;
import cqu.protocal.PacketImp.MessageRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import sun.misc.Queue;

import java.util.Scanner;

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
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                nioSocketChannel.pipeline().addLast(new SimpleClientHandler());
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
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端: ");
                    String line = sc.next();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                    ByteBuf byteBuf = PacketEncDec.INSTANCE.encode(channel.alloc().ioBuffer(), packet);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }

}
