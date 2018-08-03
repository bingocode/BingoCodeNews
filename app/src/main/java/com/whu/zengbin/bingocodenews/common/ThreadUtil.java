package com.whu.zengbin.bingocodenews.common;


import android.os.Handler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zengbin on 2018/1/24.
 */

public class ThreadUtil {
    private static Handler sUiHandler = null;

    private static final int CPU_COUNT = Runtime.getRuntime()
        .availableProcessors();
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    private static final ThreadFactory mThreadFactory = new ThreadFactory() {
        // 一种通过线程安全的Integer
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Task:" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(
        128);
    public static final Executor threadPools = new ThreadPoolExecutor(
        CPU_COUNT + 1, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
        sPoolWorkQueue);

    public static void runOnUi(Runnable r) {
        sUiHandler.post(r);
    }
    public static void postOnUiDelayed(Runnable r, int delay) {
        sUiHandler.postDelayed(r,delay);
    }

    public static void startup() {
        sUiHandler = new Handler();
    }
}
