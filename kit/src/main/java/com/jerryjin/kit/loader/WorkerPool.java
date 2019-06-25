package com.jerryjin.kit.loader;

import com.jerryjin.kit.loader.img.DaVinci;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Jerry
 * Generated at: 2019/6/24 16:22
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public class WorkerPool {

    private static final String TAG = "WorkerPool";
    private static final boolean DEBUG = false;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(4, CPU_COUNT - 1));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2;
    private static final int KEEP_ALIVE_SECONDS = 16;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {

        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, DaVinci.class.getSimpleName() + " Task @" + mCount.getAndIncrement());
        }
    };
    private static final BlockingQueue<Runnable> sPoolWorkerQueue = new LinkedBlockingQueue<>(16);
    private static Executor THREAD_POOL_EXECUTOR;

    static {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                        KEEP_ALIVE_SECONDS, TimeUnit.SECONDS, sPoolWorkerQueue, sThreadFactory) {
                    @Override
                    public void execute(Runnable command) {
                        if (getActiveCount() + 1 >= MAXIMUM_POOL_SIZE) {
                            return;
                        }
                        super.execute(command);
                    }
                };
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    private WorkerPool() {
    }

    public static WorkerPool getInstance() {
        return Holder.instance;
    }

    /**
     * Execute a specific command.
     *
     * @param command A command to be executed.
     */
    public void execute(Runnable command) {
        THREAD_POOL_EXECUTOR.execute(command);
    }

    private static class Holder {
        private static final WorkerPool instance = new WorkerPool();
    }
}
