package com.gooalgene.iqgs.eventbus;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class EventBusRegister {

    private List<EventBusListener> listeners = new ArrayList<>();

    private EventBus eventBus;

    public EventBusRegister(String eventBusName, List<EventBusListener> listeners) {
        eventBus = new EventBus(eventBusName);
        this.listeners = listeners;
        registerListener(this.listeners);
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    /**
     * eventBus注册监听器
     */
    private void registerListener(List<EventBusListener> eventBusListeners){
        checkNotNull(eventBusListeners);
        for (int i = 0; i < eventBusListeners.size(); i++){
            eventBus.register(eventBusListeners.get(i));
        }
    }

    public List<EventBusListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<EventBusListener> listeners) {
        this.listeners = listeners;
    }
}
