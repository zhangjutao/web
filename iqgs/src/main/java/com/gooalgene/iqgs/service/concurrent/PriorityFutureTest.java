package com.gooalgene.iqgs.service.concurrent;

import java.util.concurrent.*;

public class PriorityFutureTest {

    public static void main(String[] args) {
        int nThreads = 2;
        int qInitialSize = 10;
        ExecutorService service = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(qInitialSize, new PriorityFutureComparator())){
            @Override
            protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
                RunnableFuture<T> newTaskFor = super.newTaskFor(callable);
                return new PriorityFuture<>(((TimeConsumingJob)callable).getPriority(), newTaskFor);
            }
        };
        for (int i = 0; i < 20; i++){
            int priority = (int) (Math.random() * 100);
            System.out.println("Scheduling : " + priority);
            TimeConsumingJob job = new TimeConsumingJob(priority);
            service.submit(job);
        }
    }
}
