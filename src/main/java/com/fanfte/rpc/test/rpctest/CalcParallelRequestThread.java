package com.fanfte.rpc.test.rpctest;

import com.fanfte.rpc.core.proxy.MessageSendExecutor;
import com.fanfte.rpc.service.Calculate;

import java.util.concurrent.CountDownLatch;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class CalcParallelRequestThread implements Runnable {

    private CountDownLatch signal;
    private CountDownLatch finish;
    private MessageSendExecutor executor;
    private int taskNumber = 0;

    public CalcParallelRequestThread(MessageSendExecutor executor, CountDownLatch signal, CountDownLatch finish, int taskNumber) {
        this.signal = signal;
        this.finish = finish;
        this.taskNumber = taskNumber;
        this.executor = executor;
    }

    @Override
    public void run() {
        try {
            signal.await();
            Calculate calc = MessageSendExecutor.execute(Calculate.class);
            int add = calc.add(taskNumber, taskNumber);
            System.out.println("calc add result:[" + add + "]");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            finish.countDown();
        }
    }
}
