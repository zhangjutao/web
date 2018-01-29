package com.gooalgene.iqgs.service.concurrent;

import java.util.concurrent.Callable;

public class TimeConsumingJob implements Callable<Long> {
    private int priority;

    public TimeConsumingJob(int priority) {
        this.priority = priority;
    }

    public Long call() throws InterruptedException {
        System.out.println("Executing: " + priority);
        long num = 1000000;
        for (int i = 0; i < 1000000; i++) {
            num *= Math.random() * 1000;
            num /= Math.random() * 1000;
            if (num == 0)
                num = 1000000;
        }
        Thread.sleep(1000);
        Thread.yield();
        return num;
    }

    public int getPriority() {
        return priority;
    }
}
