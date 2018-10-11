package com.fanfte.rpc.test.cglib;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class HelloConcrete {
    public String sayHello(String str) {
        System.out.println("hello " + str);
        return "hello " + str;
    }
}
