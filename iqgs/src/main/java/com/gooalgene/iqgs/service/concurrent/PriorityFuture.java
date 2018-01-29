package com.gooalgene.iqgs.service.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 可设置优先级的任务
 * @param <T> 任务执行完成后返回的结果
 */
class PriorityFuture<T> implements RunnableFuture<T> {
    private int priority;
    private RunnableFuture<T> src;

    public PriorityFuture(int priority, RunnableFuture<T> src) {
        this.priority = priority;
        this.src = src;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public void run() {
        src.run();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return src.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return src.isCancelled();
    }

    @Override
    public boolean isDone() {
        return src.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return src.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return src.get(timeout, unit);
    }
}
