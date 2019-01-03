package com.shang.admin.bookstore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {

    private ExecutorService executorService;

    private ThreadPoolManager() {
        executorService = Executors.newFixedThreadPool(5);
    }

    private static class SingletonInstance {
        private static final ThreadPoolManager INSTANCE = new ThreadPoolManager();
    }

    public static ThreadPoolManager getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public void executed(Runnable runnable){
        executorService.execute(runnable);
    }
}
