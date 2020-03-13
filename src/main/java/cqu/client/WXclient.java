package cqu.client;

import cqu.client.handler.SimpleClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

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
                    }else if(retry==0){
                        System.out.println("connet host error ,stop");
                    }else{
                        System.out.println("connet host error retry,left "+retry+" times ");
                        clientStart(bootstrap,host,port,retry-1);
                    }
                }
        );
    }
}
