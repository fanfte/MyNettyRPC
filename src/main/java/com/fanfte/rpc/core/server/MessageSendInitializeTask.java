package com.fanfte.rpc.core.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class MessageSendInitializeTask implements Runnable {

    private EventLoopGroup eventLoopGroup = null;
    private InetSocketAddress serverAddress = null;
    private RpcServerLoader rpcServerLoader = null;

    public MessageSendInitializeTask(EventLoopGroup eventLooppGroup, InetSocketAddress serverAddress, RpcServerLoader rpcServerLoader) {
        this.eventLoopGroup = eventLooppGroup;
        this.serverAddress = serverAddress;
        this.rpcServerLoader = rpcServerLoader;
    }

    @Override
    public void run() {
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new MessageSendChannelInitializer());

        ChannelFuture connect = b.connect(serverAddress);
        connect.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()) {
                    MessageSendHandler messageSendHandler = future.channel().pipeline().get(MessageSendHandler.class);
                    MessageSendInitializeTask.this.rpcServerLoader.setMessageSendHandler(messageSendHandler);
                }
            }
        });
    }
}
