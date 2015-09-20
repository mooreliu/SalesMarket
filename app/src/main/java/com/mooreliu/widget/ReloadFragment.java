package com.mooreliu.widget;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.mooreliu.R;
import com.mooreliu.listener.OnLoadingOverListener;
import com.mooreliu.listener.OnSwitchFragmentListener;
import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.util.CommonUtil;
import com.mooreliu.util.LogUtil;

/**
 * Created by mooreliu on 2015/9/11.
 */
public class ReloadFragment extends BaseFragment implements OnClickListener {

    private static final String TAG = "ReloadFragment";
    private View rootView;
    Button mReloadButton;
    Button mGoToSetting;
    OnSwitchFragmentListener mOnSwitchFragmentListener;
    private int fragmentId;
//    public ReloadFragment() {
//        super();
//    }
//    public ReloadFragment(int fragmentId, OnSwitchFragmentListener listener) {
//        super();
//        this.fragmentId = fragmentId;
    OnLoadingOverListener mOnLoadingOverListener;
    public static ReloadFragment newInstance(int fragmentId, OnLoadingOverListener listener ) {
        LogUtil.e(TAG,"ReloadFragment newInstance");
        ReloadFragment f = new ReloadFragment();
        Bundle args = new Bundle();
        args.putInt("fragmentId", fragmentId);
        f.setArguments(args);
        f.mOnLoadingOverListener = listener;
        f.fragmentId = fragmentId;
        return  f;
//        return new ReloadFragment(listener , fragmentId);
    }
    private  boolean isOnVisible = true;
    public static ReloadFragment newInstance() {
        return new ReloadFragment();
    }
    @Override
    public void onVisible() {
        LogUtil.e(TAG,"ReloadFragment onVisible()");
        if(NetWorkUtil.isNetworkConnected()) {
            if(isOnVisible) {
                LogUtil.e(TAG,"ReloadFragment before lodingOvER()");
                mOnLoadingOverListener.LoadingOver();
                LogUtil.e(TAG, "ReloadFragment after lodingOvER()");

                isOnVisible = false;
            }
        }
        //else
         //   mOnSwitchFragmentListener.switchFragment(fragmentId ,false);
    }
    public ReloadFragment() {
        super();
    }
    public ReloadFragment(OnLoadingOverListener listener, int fragmentId) {
        super();
        mOnLoadingOverListener = listener;
    }
    public ReloadFragment(int fragmentId , OnSwitchFragmentListener listener) {
        super();
        fragmentId = fragmentId;
        mOnSwitchFragmentListener = listener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_reload, container, false);
        findView();
        initView();
        setOnClick();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);
    }

    private void findView() {
        mReloadButton = (Button) rootView.findViewById(R.id.reload);
        mGoToSetting = (Button) rootView.findViewById(R.id.goto_setting);
    }

    private void initView() {

    }

    private void setOnClick() {
        if(mReloadButton != null)
            mReloadButton.setOnClickListener(this);
        if(mGoToSetting !=null)
            mGoToSetting.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.reload:
                LogUtil.e(TAG,"reload button pressed");

                //CommonUtil.toastMessage(getResources().getString(R.string.reload));
//                if(NetWorkUtil.isNetworkConnected())
//                    mOnSwitchFragmentListener.switchFragment(fragmentId, true);
//                else
//                    mOnSwitchFragmentListener.switchFragment(fragmentId, false);
//                CommonUtil.toastMessage(getResources().getString(R.string.reload));
                break;
            case R.id.goto_setting:
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                break;
            default:
                break;
        }
    }


}
