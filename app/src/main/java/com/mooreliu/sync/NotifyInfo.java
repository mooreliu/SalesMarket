package com.mooreliu.sync;

/**
 * Created by mooreliu on 2015/9/23.
 */
public class NotifyInfo {
    private static final String TAG = "NotifyInfo";
    private String eventType;

    public NotifyInfo(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return this.eventType;
    }


}
