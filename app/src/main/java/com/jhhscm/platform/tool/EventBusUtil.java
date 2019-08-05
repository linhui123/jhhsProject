package com.jhhscm.platform.tool;


import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/5/17.
 */
public class EventBusUtil {
    public static void registerEvent(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }

    public static void unregisterEvent(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    public static void post(IEvent event) {
        EventBus.getDefault().post(event);
    }


    public static interface IEvent {

    }
}
