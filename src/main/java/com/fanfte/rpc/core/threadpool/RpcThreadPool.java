package com.fanfte.rpc.core.threadpool;

import java.util.concurrent.*;

/**
 * Created by tianen on 2018/10/11
 *
 * @author fanfte
 * @date 2018/10/11
 **/
public class RpcThreadPool {

    public static Executor getExecutor(int threads, int queues) {
        String name = "RpcThreadPool";
        return new ThreadPoolExecutor(threads, threads, 0, TimeUnit.SECONDS
                , queues == 0 ? new SynchronousQueue<Runnable>()
                : (queues < 0 ? new LinkedBlockingDeque<Runnable>()
                : new LinkedBlockingDeque<Runnable>(queues))
                , new NamedThreadFactory(name, true), new AbortPolicyWithReport(name));
    }
}
