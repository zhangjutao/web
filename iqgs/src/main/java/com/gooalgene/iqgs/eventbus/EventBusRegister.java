package com.gooalgene.iqgs.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static com.google.common.base.Preconditions.checkNotNull;

public class EventBusRegister {

    private List<EventBusListener> syncListeners = new ArrayList<>();

    private List<EventBusListener> asyncListeners = new ArrayList<>();

    private EventBus eventBus;

    private AsyncEventBus asyncEventBus;

    public EventBusRegister(String eventBusName, List<EventBusListener> syncListeners, List<EventBusListener> asyncListeners) {
        eventBus = new EventBus(eventBusName);
        asyncEventBus = new AsyncEventBus(eventBusName, Executors.newSingleThreadExecutor());
        this.syncListeners = syncListeners;
        this.asyncListeners = asyncListeners;
        registerSyncListener(this.syncListeners);
        registerAsyncListener(this.asyncListeners);
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    public AsyncEventBus getAsyncEventBus() {
        return this.asyncEventBus;
    }

    /**
     * eventBus注册监听异步器
     */
    private void registerAsyncListener(List<EventBusListener> eventBusListeners){
        checkNotNull(eventBusListeners);
        for (int i = 0; i < eventBusListeners.size(); i++){
            asyncEventBus.register(eventBusListeners.get(i));
        }
    }

    private void registerSyncListener(List<EventBusListener> eventBusListeners){
        checkNotNull(eventBusListeners);
        for (int i = 0; i < eventBusListeners.size(); i++){
            eventBus.register(eventBusListeners.get(i));
        }
    }
}
