package com.fanfte.rpc.test.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class Main {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloConcrete.class);
        enhancer.setCallback(new MyMethodInterceptor());

        HelloConcrete o = (HelloConcrete) enhancer.create();
        System.out.println(o.sayHello("world"));
    }
}
