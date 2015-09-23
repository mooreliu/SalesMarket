package com.mooreliu.sync.imple;

import android.os.Handler;
import android.os.Looper;

import com.mooreliu.event.Notify;
import com.mooreliu.sync.IEventObserver;
import com.mooreliu.sync.NotifyInfo;
import com.mooreliu.util.LogUtil;

/**
 * Created by mooreliu on 2015/9/23.
 */
public abstract class EventObserver implements IEventObserver{

    private static final String TAG = "EventObserver";
    private Handler mHandler;

    public abstract void onUpdate(NotifyInfo notifyInfo);

    public EventObserver() {
        mHandler = new Handler( Looper.getMainLooper());
    }
    @Override
    public void update(NotifyInfo notifyInfo) {
        LogUtil.e(TAG ,"update called");

        mHandler.post(new UpdateRunnable(notifyInfo));
    }

    private class  UpdateRunnable implements Runnable {
        private NotifyInfo mNotifyInfo;
        public UpdateRunnable(NotifyInfo notifyInfo) {
            mNotifyInfo = notifyInfo;
        }
        @Override
        public void run() {
            LogUtil.e(TAG ,"UpdateRunnable run");
            onUpdate(mNotifyInfo);
        }
    }

}