package com.fanfte.rpc.core.client;

import com.fanfte.rpc.core.server.RpcServerLoader;
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
        // 初始化客户端的Netty 消息发送线程
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new MessageSendChannelInitializer());

        ChannelFuture connect = b.connect(serverAddress);
        connect.addListener((ChannelFutureListener) future -> {
            if(future.isSuccess()) {
                MessageSendHandler messageSendHandler = future.channel().pipeline().get(MessageSendHandler.class);
                MessageSendInitializeTask.this.rpcServerLoader.setMessageSendHandler(messageSendHandler);
            }
        });
    }
}
