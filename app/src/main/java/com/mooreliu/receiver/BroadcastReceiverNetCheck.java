package com.mooreliu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.mooreliu.util.LogUtil;
import com.mooreliu.widget.MainActivity;

/**
 * Created by mooreliu on 2015/9/2.
 */
public class BroadcastReceiverNetCheck extends BroadcastReceiver {
    private static final String TAG = "BroadcastReceiverNetCheck";
    @Override
    public void onReceive(Context context ,Intent intent) {
//        LogUtil.e(TAG ,"Broadcast onReceive");
        LogUtil.e(TAG,"receive an actoin");
        String action = intent.getAction();
//        LogUtil.e(TAG ,action);
        if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//            LogUtil.e(TAG ,action);
//            LogUtil.e(TAG ,"Toast");
//            Toast.makeText(context , R.string.connectivityChanged,Toast.LENGTH_LONG).show();
        } else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            LogUtil.e(TAG, "boot completed");
            Intent newintent = new Intent(context, MainActivity.class);
            newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //注意，必须添加这个标记，否则启动会失败
            context.startActivity(newintent);
        }
    }
}
