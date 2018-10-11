package com.fanfte.rpc.core.server;

import com.fanfte.rpc.model.packet.MessageRequest;
import com.fanfte.rpc.model.packet.MessageResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.reflect.MethodUtils;
import sun.reflect.misc.MethodUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class MessageRecvInitializeTask implements Runnable{

    private MessageRequest request = null;
    private MessageResponse response = null;
    private Map<String, Object> handlerMap = null;
    private ChannelHandlerContext ctx = null;

    public MessageResponse getResponse() {
        return response;
    }

    public MessageRequest getRequest() {
        return request;
    }

    public void setRequest(MessageRequest request) {
        this.request = request;
    }

    MessageRecvInitializeTask(MessageRequest request, MessageResponse response, Map<String, Object> handlerMap, ChannelHandlerContext ctx) {
        this.request = request;
        this.response = response;
        this.handlerMap = handlerMap;
        this.ctx = ctx;
    }

    @Override
    public void run() {
        response.setMessageId(request.getMessageId());
        try {
            Object result = invoke(request);
            System.out.println("result" + result);
            response.setResultDesc(result);

        } catch (Throwable t) {
            response.setError(t.toString());
            t.printStackTrace();
            System.out.println("invoke" + request.getMethodName() +  "error");
        }
        ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                System.out.println("RPC sent messageId response complete: " + request.getMessageId());
            }
        });
    }

    public Object invoke(MessageRequest request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parametersVal = request.getParametersVal();
        return MethodUtils.invokeMethod(serviceBean, methodName, parametersVal);
    }
}
