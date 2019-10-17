package com.adasplus.base.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author:刘净辉
 * Date : 2019/10/16 15:06
 * Description :
 */
public class ThreadPoolUtils {

    //定义核心的线程数，并行的线程数
    private static final int CORE_POOL_SIZE = 3;
    //定义最大的线程数据
    private static final int MAX_POOL_SIZE = 20;
    //额外线程的空闲时间
    private static final int KEEP_ALIVE_TIME = 3000;

    private static ThreadPoolExecutor mThreadPoolExecutor;


    private ThreadPoolUtils(){

    }

    //定义一个阻塞队列
    private static BlockingQueue<Runnable> mWorkQueue = new ArrayBlockingQueue<>(10);

    private static ThreadFactory mThreadFactory = new ThreadFactory() {
        // 原子型的integer变量生成的integer值不会重复
        private final AtomicInteger integer = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"ThreadPool thread:" + integer.getAndIncrement());
        }
    };

    //当线程发生异常的时候回调进入
    private static RejectedExecutionHandler mRejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        }
    };

    static {
        mThreadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS, mWorkQueue, mThreadFactory,
                mRejectedExecutionHandler);
    }

    public static void execute(Runnable runnable){
        mThreadPoolExecutor.execute(runnable);
    }
}
