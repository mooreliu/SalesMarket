package com.mooreliu.sync.imple;

import com.mooreliu.sync.IEventObservable;
import com.mooreliu.sync.NotifyInfo;
import com.mooreliu.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mooreliu on 2015/9/23.
 */
public class EventObservable implements IEventObservable{
    // Singleton mode
    private static final String TAG = "EventObservable";
    public static volatile EventObservable mEventObservable;
    private Map<String, ArrayList<EventObserver>> mEventObservers = new HashMap<>();

    private EventObservable() {

    }
    public synchronized static EventObservable getInstance() {
        if(mEventObservable == null) {
            mEventObservable = new EventObservable();
        }
        return mEventObservable;
    }

    @Override
    public void registerObserver(EventObserver observer, String eventType) {

        //LogUtil.e(TAG, " registerObserver eventType= "+eventType+" observer "+observer);
        //LogUtil.e(TAG, " registerObserver notifyObservers mEventObservers.size() "+mEventObservers.size());
        if(observer != null && eventType != null) {
            synchronized (mEventObservers) {
                ArrayList eventObservers = mEventObservers.get(eventType);
                if (eventObservers == null) {
                    eventObservers = new ArrayList<EventObserver>();
                    //LogUtil.e(TAG, "eventObservers == null");

                }
                if (eventObservers.contains(observer)) {
                    //LogUtil.e(TAG, "eventObservers.contains(observer)  == true");
                    return;
                }

                eventObservers.add(observer);
                mEventObservers.put(eventType, eventObservers);
                //LogUtil.e(TAG , "eventObservers .add(observer)  mEventObservers.size() "+mEventObservers.size());
//                if(eventObservers != null)
                    //LogUtil.e(TAG , "eventObservers  mEventObservers.get(eventType).size(); "+ mEventObservers.get(eventType).size());


            }
        }
    }

    @Override
    public void unregisterObserver(EventObserver observer, String eventType) {
        if(observer != null && eventType != null) {
            synchronized (mEventObservers) {
                ArrayList<EventObserver> eventObservers = mEventObservers.get(eventType);
                if (eventObservers == null) {
                    return;
                }
                if (eventObservers.indexOf(observer) >= 0)
                    mEventObservers.remove(observer);
            }
        }
    }

    @Override
    public void notifyObservers(NotifyInfo notifyInfo) {
//        LogUtil.e(TAG, " notifyObservers notifyInfo getEventType "+notifyInfo.getEventType());
//        LogUtil.e(TAG, " notifyObservers mEventObservers.size() "+mEventObservers.size());
//        LogUtil.e(TAG, " notifyObservers mEventObservers "+mEventObservers);
//        LogUtil.e(TAG, " notifyObservers mEventObservable "+mEventObservable);

        if (mEventObservers != null && mEventObservable != null && mEventObservers.size() != 0
                &&mEventObservers.get(notifyInfo.getEventType()) != null&& notifyInfo != null) {
            for (EventObserver observer : mEventObservers.get(notifyInfo.getEventType())) {
                //LogUtil.e(TAG, " observer.update(notifyInfo);");
                observer.update(notifyInfo);
            }
     }
    }


}
