package com.mooreliu.sync;

import com.mooreliu.sync.imple.EventObservable;
import com.mooreliu.sync.imple.EventObserver;
import com.mooreliu.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by mooreliu on 2015/9/23.
 */
public class Notify {

    public static volatile Notify mNotify;
    private static final String TAG ="Notify";
    private Notify() {

    }

    public static Notify getInstance() {
        if(mNotify == null)
            mNotify = new Notify();
        return mNotify;
    }

    public void notifyFragment(NotifyInfo notifyInfo) {
        String eventType = notifyInfo.getEventType();
        EventObservable eventObservable = EventObservable.getInstance();
        EventType eventTypes = EventType.getInstance();
        //LogUtil.e(TAG, "notify Fragment+" + notifyInfo.getEventType());
        if(eventTypes.contains(notifyInfo.getEventType()));
            eventObservable.notifyObservers(notifyInfo);
    }

}
