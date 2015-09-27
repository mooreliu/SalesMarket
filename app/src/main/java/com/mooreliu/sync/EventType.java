package com.mooreliu.sync;

import com.mooreliu.sync.imple.EventObservable;
import com.mooreliu.util.LogUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mooreliu on 2015/9/23.
 */
public class EventType {

    private static final String TAG = "EventType";
    public  static final String LOGIN = "com.mooreliu.login";
    public  static final String LOGIN_OUT = "com.mooreliu.logout";
    public  static final String NETWORK_CHANGEED="com.mooreliu.network.change";
    public  static final String NETWORK_OK = "com.mooreliu.network.ok";
    public  static final String NETWORK_NOT_OK = "com.mooreliu.network.not.ok";
    public static volatile EventType mEventType;
    public static Set<String> eventTypes = new HashSet<>();

    private EventType() {
        eventTypes.add(LOGIN);
        eventTypes.add(LOGIN_OUT);
        eventTypes.add(NETWORK_CHANGEED);
        eventTypes.add(NETWORK_OK);
        eventTypes.add(NETWORK_NOT_OK);
    }

    public static EventType getInstance() {
        if(mEventType == null)
            mEventType = new EventType();
        return mEventType;
    }

    public static boolean contains(String eventType) {
        if(eventTypes.contains(eventType))
            return true;
        return false;
        //LogUtil.e(TAG, "contains eventType"+eventType);
        //LogUtil.e(TAG, "eventTypes.contains(eventType) " + eventTypes.contains(eventType));
    }

}
