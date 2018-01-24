package com.gooalgene.iqgs.eventbus;

import java.util.ArrayList;
import java.util.List;

public class EventBusRegister {

    public static EventBusRegister eventBusRegister;

    private static List<EventBusListener> listeners = new ArrayList<>();

    private EventBusRegister() {
    }

    public static EventBusRegister newInstance() {
        if (eventBusRegister == null){
            eventBusRegister = new EventBusRegister();
        }
        return eventBusRegister;
    }

    public static void addListener(EventBusListener listener){
        listeners.add(listener);
    }
}
