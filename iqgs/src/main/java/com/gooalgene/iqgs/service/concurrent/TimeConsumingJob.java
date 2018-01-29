package com.gooalgene.iqgs.service.concurrent;

import java.util.concurrent.Callable;

public abstract class TimeConsumingJob<T> implements Callable<T> {
    private int priority;

    public TimeConsumingJob(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
