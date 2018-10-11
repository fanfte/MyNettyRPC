package com.fanfte.rpc.test.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by tianen on 2018/10/10
 *
 * @author fanfte
 * @date 2018/10/10
 **/
public class LogInvocationHandler implements InvocationHandler {

    private Hello hello;

    public LogInvocationHandler(Hello hello) {
        this.hello = hello;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("sayHello".equals(method.getName())) {
            System.out.println("hello invoke." + Arrays.toString(args));
        }
        Object invoke = method.invoke(hello, args);
        System.out.println("hello invoke jdk. " );
        return invoke;
    }


}
