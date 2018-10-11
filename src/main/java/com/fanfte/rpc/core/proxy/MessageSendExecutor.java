package com.fanfte.rpc.core.proxy;

import com.fanfte.rpc.core.server.RpcServerLoader;

import java.lang.reflect.Proxy;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class MessageSendExecutor {

    private RpcServerLoader loader = RpcServerLoader.getInstance();

    public MessageSendExecutor(String serverAddr) {
        loader.load(serverAddr);
    }

    public void stop() {
        loader.unload();
    }

    public static <T> T execute(Class<T> rpcInterface) {
        return (T) Proxy.newProxyInstance(rpcInterface.getClassLoader()
                                        , new Class<?>[] {rpcInterface}
                                        , new MessageSendProxy<T>(rpcInterface));
    }
}
