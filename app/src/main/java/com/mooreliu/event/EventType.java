package com.mooreliu.event;

import java.util.HashSet;
import java.util.Set;

/**
 * 事件定义中心
 * @author zhiwu_yan
 * @version 1.0
 * @since 2015-06-30  14:46
 */
public class EventType {

    private static volatile EventType mEventType;
    private final static Set<String> eventsTypes = new HashSet<String>();

    public final static String EVENT_LOGIN="com.mooreliu.login";//登录成功
    public final static String EVENT_LOGINOUT="com.mooreliu.loginout";//注销登录信息
    private EventType(){
        eventsTypes.add(EVENT_LOGIN);
        eventsTypes.add(EVENT_LOGINOUT);
    }

    public static EventType getInstance(){
        if(mEventType==null){
            mEventType=new EventType();
        }
        return mEventType;
    }

    public boolean contains(String eventType){
        return eventsTypes.contains(eventType);
    }
}
