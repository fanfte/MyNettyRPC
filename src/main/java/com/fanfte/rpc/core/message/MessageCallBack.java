package com.fanfte.rpc.core.message;

import com.fanfte.rpc.model.packet.MessageRequest;
import com.fanfte.rpc.model.packet.MessageResponse;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
            try {
                await();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            if(this.response != null && response.getResultDesc() != null) {
                return response.getResultDesc();
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    private void await() throws TimeoutException {
        boolean timeout = false;
        try {
            timeout = conditon.await(10 * 1000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!timeout) {
            throw new TimeoutException("Invoke timeout.");
        }
    }

    public void over(MessageResponse response) {
        try {
            lock.lock();
            conditon.signalAll();
            this.response = response;
        } finally {
            lock.unlock();
        }
    }
}
