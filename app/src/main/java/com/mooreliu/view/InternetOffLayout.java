package com.mooreliu.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mooreliu.R;
import com.mooreliu.net.NetWorkUtil;

/**
 * Created by mooreliu on 2015/9/24.
 */
public abstract class InternetOffLayout {

    private static final String TAG = "InternetOffLayout";
    private Context mContext;
    private View mRootView;
    private int mParentViewId;
    private View InternetOffView;
    private CustomProgressDialog progressDialog;

    public InternetOffLayout(Context context, View rootView, int parentViewId) {
        mContext = context;
        mRootView = rootView;
        mParentViewId = parentViewId;
    }
    public void showInternetOffLayout() {
        if(mRootView == null)
            return;
       // RelativeLayout parentView = (RelativeLayout)mRootView.findViewById(mParentViewId);
        FrameLayout parentView = (FrameLayout)mRootView.findViewById(mParentViewId);
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        InternetOffView = inflater.inflate(R.layout.layout_reload, null);
        InternetOffView.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        InternetOffView.setClickable(true);
        parentView.addView(InternetOffView);

        final Button btnGotoSetting = (Button)InternetOffView.findViewById(R.id.goto_setting);
        btnGotoSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });

        final Button btnReload = (Button)InternetOffView.findViewById(R.id.reload);
        if(InternetOffView != null)
            InternetOffView.setVisibility(View.GONE);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mview) {
                showProgress(R.string.reconnect_to_internet);
                checkNerworkForView();
            }
        });
    }

    public void showProgress(int resID) {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        progressDialog = new CustomProgressDialog(mContext, mContext.getResources()
                .getString(resID));
        progressDialog.show();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        };
        handler.postDelayed(r, 200);

    }

    public boolean checkNerworkForView() {
        if (!NetWorkUtil.isNetworkConnected()) {
            if (InternetOffView != null) {
                InternetOffView.setVisibility(View.VISIBLE);
                InternetOffView.bringToFront();
            }
            return false;
        } else {
            if (InternetOffView != null) {
                initData();
                InternetOffView.setVisibility(View.GONE);
            }
            return true;
        }
    }

    public void setInternetOffView() {
        if(InternetOffView != null)
            InternetOffView.setVisibility(View.VISIBLE);
            InternetOffView.bringToFront();
    }

    public void setInternetOnView() {
        if (InternetOffView != null) {
            initData();
            InternetOffView.setVisibility(View.GONE);
        }
    }

    public abstract void initData();
}
