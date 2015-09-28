package com.mooreliu.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.mooreliu.AppContext;
import com.mooreliu.util.LogUtil;

/**
 * Created by mooreliu on 2015/9/2.
 */
public class ServiceCheckNetConnect extends IntentService {
  private static final String TAG = "ServiceCheckNetConnect";
  private Context mContext;
  private BroadcastReceiver mReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) { /*LogUtil.e(TAG,"onReceive");*/
      mContext = context;
      String action = intent.getAction();
      if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION))
        Toast.makeText(AppContext.getContext(), "network change", Toast.LENGTH_SHORT).show();
    }
  };

  public ServiceCheckNetConnect() {
    super("ServiceCheckNetConnect");
    LogUtil.e(TAG, "ServiceCheckNetConnect");
  }

  @Override
  public void onHandleIntent(Intent intent) {
    //LogUtil.e(TAG,"Service onHandleIntent()");
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    registerReceiver(mReceiver, intentFilter);
  }

  @Override
  public void onCreate() {
    LogUtil.e(TAG, "onCreate");
    super.onCreate();
  }

  @Override
  public void onDestroy() {
    LogUtil.e(TAG, "onDestroy");
    unregisterReceiver(mReceiver);
    super.onDestroy();
  }


  private boolean isConnected() {
    ConnectivityManager manager =
            (ConnectivityManager) AppContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo info = manager.getActiveNetworkInfo();
    while (true) {
      if (info == null) {
        if (!info.isAvailable())
          Toast.makeText(AppContext.getContext(), "��������������(:", Toast.LENGTH_SHORT);

      }
    }

  }

}
