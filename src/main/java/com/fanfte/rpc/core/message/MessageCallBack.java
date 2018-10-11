package com.fanfte.rpc.core.message;

import com.fanfte.rpc.model.packet.MessageRequest;
import com.fanfte.rpc.model.packet.MessageResponse;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class MessageCallBack {

    private MessageRequest request;

    private MessageResponse response;

    private Lock lock = new ReentrantLock();

    private Condition conditon = lock.newCondition();

    public MessageCallBack(MessageRequest request) {
        this.request = request;
    }

    public Object start() throws InterruptedException {
        try{
            lock.lock();
            conditon.await(10 * 1000, TimeUnit.MILLISECONDS);
            if(this.response != null) {
                return response.getResultDesc();
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    public void over(MessageResponse response) {
        try {
            lock.lock();
            conditon.signal();
            this.response = response;
        } finally {
            lock.unlock();
        }
    }
}
