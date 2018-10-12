package com.fanfte.rpc.core.proxy;

import com.fanfte.rpc.core.message.MessageCallBack;
import com.fanfte.rpc.core.server.MessageSendHandler;
import com.fanfte.rpc.core.server.RpcServerLoader;
import com.fanfte.rpc.model.packet.MessageRequest;
import com.google.common.reflect.AbstractInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class MessageSendProxy<T> extends AbstractInvocationHandler {

    private Class<T> cls;

    public MessageSendProxy(Class<T> rpcInterface) {
        this.cls = rpcInterface;
    }

//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
////        System.out.println("proxy " +  proxy );
//        System.out.println("method " + method.getName());
//        if(method != null && method.getName().toLowerCase().contains("add")) {
//            MessageRequest request = new MessageRequest();
//            request.setMessageId(UUID.randomUUID().toString());
//            request.setClassName(method.getDeclaringClass().getName());
//            request.setParameterTypes(method.getParameterTypes());
//            request.setMethodName(method.getName());
//            request.setParametersVal(args);
//
//            MessageSendHandler messageSendHandler = RpcServerLoader.getInstance().getMessageSendHandler();
//            MessageCallBack callBack = messageSendHandler.sendRequest(request);
//            return callBack.start();
//        } else {
//            return null;
//        }
//    }

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        MessageRequest request = new MessageRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setMethodName(method.getName());
        request.setParametersVal(args);

        // getMessageSendHandler的时候可能Netty客户端没有初始化完成，没有绑定MessageSendHandler完成
        // 所以getMessageSendHandler要使用signal.await();等待初始化完成
        MessageSendHandler messageSendHandler = RpcServerLoader.getInstance().getMessageSendHandler();
        // netty发送消息
        MessageCallBack callBack = messageSendHandler.sendRequest(request);
        return callBack.start();
    }
}
