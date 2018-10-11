package com.fanfte.rpc.core.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class BootStrap {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("config/rpc-invoke-config.xml");
    }
}
