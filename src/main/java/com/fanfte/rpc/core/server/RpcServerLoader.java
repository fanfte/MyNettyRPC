package com.fanfte.rpc.core.server;

import com.fanfte.rpc.core.client.MessageSendHandler;
import com.fanfte.rpc.core.client.MessageSendInitializeTask;
import com.fanfte.rpc.core.threadpool.RpcThreadPool;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class RpcServerLoader {

    private volatile static RpcServerLoader rpcServerLoader;

    private MessageSendHandler messageSendHandler = null;

    private final static String DELIMITER = ":";

    private Lock lock = new ReentrantLock();
    private Condition signal = lock.newCondition();

    private final static int parallel = Runtime.getRuntime().availableProcessors() * 2;

    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);

    private static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) RpcThreadPool.getExecutor(16, -1);

    private RpcServerLoader() {
    }

    public static RpcServerLoader getInstance() {
        if(rpcServerLoader == null) {
            synchronized (RpcServerLoader.class) {
                if(rpcServerLoader == null) {
                    rpcServerLoader = new RpcServerLoader();
                }
            }
        }
        return rpcServerLoader;
    }

    public void setMessageSendHandler(MessageSendHandler messageSendHandler) {
        try {
            lock.lock();
            this.messageSendHandler = messageSendHandler;
            signal.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public MessageSendHandler getMessageSendHandler() throws InterruptedException {
        try {
            lock.lock();
            if(messageSendHandler == null) {
                // 等待初始化线程MessageSendInitializer初始化完成，回调connect的完成事件，完成设置RpcServerLoader的messageSendHandler
                signal.await();
            }
            return messageSendHandler;
        } finally {
            lock.unlock();
        }
    }

    public void load(String serverAddress) {
        String[] ipAddr = serverAddress.split(RpcServerLoader.DELIMITER);
        if(ipAddr.length == 2) {
            String host = ipAddr[0];
            int port = Integer.parseInt(ipAddr[1]);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);

            threadPoolExecutor.submit(new MessageSendInitializeTask(eventLoopGroup, inetSocketAddress, this));
        }
    }

    public void unload() {
        messageSendHandler.close();
        threadPoolExecutor.shutdown();
        eventLoopGroup.shutdownGracefully();
    }
}
