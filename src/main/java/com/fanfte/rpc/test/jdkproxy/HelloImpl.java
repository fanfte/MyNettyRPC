package com.fanfte.rpc.test.jdkproxy;

/**
 * Created by tianen on 2018/10/10
 *
 * @author fanfte
 * @date 2018/10/10
 **/
public class HelloImpl implements Hello {
    @Override
    public String sayHello(String str) {
        return "HelloImpl " + str;
    }
}
