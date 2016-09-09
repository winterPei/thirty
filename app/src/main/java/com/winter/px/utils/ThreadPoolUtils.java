package com.winter.px.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by peiyangyang on 2016/9/8.
 */
public class ThreadPoolUtils {
    ExecutorService executorService;

    public ExecutorService getThreadPool(int nThreads) {
        if (executorService == null) {
            synchronized ((ExecutorService.class)) {
                if (executorService == null) {
                    executorService = Executors.newFixedThreadPool(nThreads);
                }
            }
        }
        return executorService;
    }
}
