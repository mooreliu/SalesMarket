package com.mooreliu.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.mooreliu.R;
import com.mooreliu.util.LogUtil;

/**
 * Created by mooreliu on 2015/9/2.
 */
public class BroadcastReceiverNetCheck extends BroadcastReceiver {
    private static final String TAG = "BroadcastReceiverNetCheck";
    @Override
    public void onReceive(Context context ,Intent intent) {
        LogUtil.e(TAG ,"Broadcast onReceive");

        String action = intent.getAction();
        LogUtil.e(TAG ,action);
        if(action == ConnectivityManager.CONNECTIVITY_ACTION) {
            LogUtil.e(TAG ,action);
            LogUtil.e(TAG ,"Toast");
            Toast.makeText(context , R.string.connectivityChanged,Toast.LENGTH_LONG).show();
        }
    }
}
