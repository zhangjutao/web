package com.gooalgene.iqgs.service.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * 整合数据库线程统一调度服务类
 */
@Service
public class ThreadManager implements InitializingBean, DisposableBean {

    private final static Logger logger = LoggerFactory.getLogger(ThreadManager.class);

    private ExecutorService threadPool;

    //规定优先队列大小
    private final int priorityQueueSize = 30;

    private PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue(priorityQueueSize, new PriorityFutureComparator());

    @Override
    public void afterPropertiesSet() throws Exception {

        threadPool = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, priorityBlockingQueue){
            @Override
            protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
                return super.newTaskFor(callable);
            }
        };
    }

    public synchronized  <T> T submitTask(TimeConsumingJob<T> job) throws ExecutionException, InterruptedException {
        logger.debug(Thread.currentThread().getName() + "正在执行任务,优先级:" + job.getPriority());
        Future<T> submitTask = threadPool.submit(job);
        return submitTask.get();
    }

    public boolean isShutdown(){
        return threadPool.isShutdown();
    }

    public void shutdown(){
        threadPool.shutdown();
    }

    @Override
    public void destroy() throws Exception {
        if (threadPool != null && !threadPool.isShutdown())
            threadPool.shutdown();
    }
}
