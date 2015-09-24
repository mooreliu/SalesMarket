package com.mooreliu.widget;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mooreliu.R;
import com.mooreliu.controller.OrderController;
import com.mooreliu.db.model.OrderModel;
import com.mooreliu.listener.OnSwitchFragmentListener;
import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.sync.EventType;
import com.mooreliu.sync.NotifyInfo;
import com.mooreliu.util.LogUtil;
import com.mooreliu.view.InternetOffLayout;

import java.util.List;

/**
 * Created by liuyi on 15/8/29.
 */
public class ShoppingListPageFragment extends BaseObserverFragment {

    private static final String TAG = "ShoppingListPageFragment";
    private InternetOffLayout mInternetOffLayout;
    private OrderController oc;
    private View mView;

    @Override
    public String[] getObserverEventTypes() {
        return new String[] {
                EventType.NETWORK_OK,
                EventType.NETWORK_NOT_OK
        };
    }

    @Override
    public void onUpdate(NotifyInfo notifyInfo) {
        switch (notifyInfo.getEventType()) {
            case EventType.NETWORK_OK:
                mInternetOffLayout.setInternetOnView();
                break;

            case EventType.NETWORK_NOT_OK:
                mInternetOffLayout.setInternetOffView();
                break;
            default:
                LogUtil.e(TAG, "default");
        }
    }

    @Override
    public int setUpLayout() {
        return R.layout.layout_mypage;
    }
    public void onVisible() {
    }

    public ShoppingListPageFragment() {
        super();
        LogUtil.e(TAG, "ShoppingListPageFragment 构造函数");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_shoppinglist, container, false);
        LogUtil.e(TAG, "onCreateView()");

        return mView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        initViews();
        setOnclick();
    }
    public static ShoppingListPageFragment newInstance() {
        ShoppingListPageFragment fragment = new ShoppingListPageFragment();
        return  fragment;
    }
    private void findViews() {

    }

    private void initViews() {
        mInternetOffLayout = new InternetOffLayout(getActivity(), mView, R.id.shoppinglist_parent_view) {
            public void initData() {
                initViewData();
            }
        };
        mInternetOffLayout.showInternetOffLayout();
        if(mInternetOffLayout.checkNerworkForView())
            initViewData();
    }
    private void initViewData() {

    }
    private void setOnclick() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onDetach();
        LogUtil.e(TAG, "onAttach " + activity.toString());
    }
    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        LogUtil.e(TAG, "onCreate");
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e(TAG, "onResume");
        if(mInternetOffLayout.checkNerworkForView() ) {
            initViewData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e(TAG, "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e(TAG,"onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e(TAG,"onDetach");
    }
}
