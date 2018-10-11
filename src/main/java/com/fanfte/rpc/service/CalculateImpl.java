package com.fanfte.rpc.service;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class CalculateImpl implements Calculate {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
