package com.fanfte.rpc.test.jdkproxy;

import java.lang.reflect.Proxy;

/**
 * Created by tianen on 2018/10/10
 *
 * @author fanfte
 * @date 2018/10/10
 **/
public class Main {

    public static void main(String[] args) {
        new Main().testProxy();
    }

    public void testProxy() {
        Hello o = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{Hello.class}, new LogInvocationHandler(new HelloImpl()));
        String john = o.sayHello("John");
        System.out.println(john);
    }
}
