package com.mooreliu.widget;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mooreliu.R;
import com.mooreliu.adapter.CustomRecyclerListAdapter;
import com.mooreliu.listener.OnProductClickListener;
import com.mooreliu.db.model.MerchandiseModel;
import com.mooreliu.listener.OnSwitchFragmentListener;
import com.mooreliu.net.HttpUtil;
import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.sync.EventType;
import com.mooreliu.sync.NotifyInfo;
import com.mooreliu.task.TaskGetMerchandiseListFromServer;
import com.mooreliu.util.Constants;
import com.mooreliu.util.GsonHelper;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.NetUtil;
import com.mooreliu.util.TextUtil;
import com.mooreliu.util.UserUtil;
import com.mooreliu.view.CustomProgressDialog;
import com.mooreliu.view.InternetOffLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyi on 15/8/29.
 * 首页
 */
public class MainPageFragment extends BaseObserverFragment {

    private final static String TAG ="MainPageFragment";
    //private View mView;
    private List<MerchandiseModel> mList;
    private RecyclerView mRecyclerView;
    private CustomRecyclerListAdapter mCustomRecyclerListAdapter;
    private LinearLayoutManager layoutManager;
    private InternetOffLayout mInternetOffLayout;
    private boolean isLoadComplete = false;
    private static int count = 0;

    @Override
    public int onSetUpLayout(){
        return R.layout.layout_mainpage;
    }

    @Override
    public void onVisible() {
    }
    public MainPageFragment() {
        super();
        count++;
        LogUtil.e(TAG, "MainPageFragment 构造函数  count=" + count);
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);
        LogUtil.e(TAG, "onActivityCreated");
    }

    @Override
    protected void findViews() {
        mRecyclerView =(RecyclerView)mRootView.findViewById(R.id.mainpage_recyclerview);
    }

    @Override
    protected void initViews() {
        LogUtil.e(TAG, "initViews");
        mInternetOffLayout = new InternetOffLayout(getActivity(), mRootView, R.id.mainpage_parent_view) {
            public void initData() {
                initList();
            }
        };
        mInternetOffLayout.showInternetOffLayout();
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mList = new ArrayList<>();
        initVoidRecyclerView();

    }

    @Override
    protected void setOnClick() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mInternetOffLayout.checkNerworkForView();
    }

    @Override
    public void onPause() {
        LogUtil.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e(TAG, "onStop");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onDetach();
    }
    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        LogUtil.e(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, this+"onDestroy");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        count--;
        LogUtil.e(TAG, "MainPageFragment onDetach  count=" + count);
    }

    private synchronized void initList(){
        LogUtil.e(TAG, "initList");
        if(NetWorkUtil.isNetworkConnected()) {
            TaskGetMerchandiseListFromServer getMerichandiseListTast = new TaskGetMerchandiseListFromServer() {
                @Override
                public void postExecute(String response) {
                mList = GsonHelper.getMerchandiseListFromGsonString(response);
                initRecyclerView();
                }
            };
            getMerichandiseListTast.execute();
        } else {
            LogUtil.e(TAG,getString(R.string.networkNotAvail));
            if(getActivity() != null) { // 要保证fragment没有被销毁
                Toast.makeText(getActivity(), getString(R.string.networkNotAvail), Toast.LENGTH_SHORT).show();
                GsonHelper.getMerchandiseListFromGsonString(Constants.jsonProductList);
                LogUtil.e(TAG, "initRecyclerView");
                initRecyclerView();
            }
        }
    }

    /**
     * 在无网络或者网络数据没有加载前 set一个空的adapter
     */
    private void initVoidRecyclerView() {
        CustomRecyclerListAdapter adapter = new CustomRecyclerListAdapter(mRecyclerView ,getActivity() ,mList ,this.getResources());
        mRecyclerView.setAdapter(adapter);
    }

    private void initRecyclerView() {
        LogUtil.e(TAG, "initRecyclerView start");
        mCustomRecyclerListAdapter = new CustomRecyclerListAdapter(mRecyclerView ,getActivity() ,mList ,this.getResources());
        mCustomRecyclerListAdapter.setOnProductClick(new OnProductClickListener() {
            @Override
            public void onTouch(View v, MerchandiseModel model) {
                if (UserUtil.isLogined()) {
                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("ImageKey", TextUtil.hashKeyForDisk(model.getmerchandiseImageUrl()));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(mCustomRecyclerListAdapter);
        isLoadComplete = true;
        LogUtil.e(TAG, " isLoadComplete" + isLoadComplete);

    }

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

}
