package com.mooreliu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.sync.Notify;
import com.mooreliu.sync.EventType;
import com.mooreliu.sync.NotifyInfo;
import com.mooreliu.util.LogUtil;
import com.mooreliu.widget.MainActivity;
import com.mooreliu.listener.OnNetworkChangeListener;
/**
 * Created by mooreliu on 2015/9/2.
 */
public class BroadcastReceiverNetCheck extends BroadcastReceiver implements OnNetworkChangeListener {
    private static final String TAG = "BroadcastReceiverNetCheck";

    @Override
    public void onReceive(Context context ,Intent intent) {
        LogUtil.e(TAG,"BroadcastReceiverNetCheck an action");
        String action = intent.getAction();
        if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            LogUtil.e(TAG, "Connection Change");
            NotifyInfo notifyInfo = null;
            if(NetWorkUtil.isNetworkConnected())
                 notifyInfo = new NotifyInfo(EventType.NETWORK_OK);
            else
                notifyInfo = new NotifyInfo(EventType.NETWORK_NOT_OK);
            Notify.getInstance().notifyFragment(notifyInfo);

        } else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            LogUtil.e(TAG, "boot completed");
            Intent newintent = new Intent(context, MainActivity.class);
            newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //注意，必须添加这个标记，否则启动会失败
            context.startActivity(newintent);
        }
    }

    public void updateView() {

    }


}
