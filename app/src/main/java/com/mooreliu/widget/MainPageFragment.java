package com.mooreliu.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mooreliu.R;
import com.mooreliu.adapter.CustomRecyclerListAdapter;
import com.mooreliu.db.model.MerchandiseModel;
import com.mooreliu.listener.OnProductClickListener;
import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.sync.EventType;
import com.mooreliu.sync.NotifyInfo;
import com.mooreliu.task.TaskGetMerchandiseListFromServer;
import com.mooreliu.util.Constants;
import com.mooreliu.util.GsonHelper;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.TextUtil;
import com.mooreliu.util.UserUtil;
import com.mooreliu.view.InternetOffLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyi on 15/8/29. 首页
 */
public class MainPageFragment extends BaseObserverFragment {
    private final static String TAG = "MainPageFragment";
    private List<MerchandiseModel> mMerchandiseList;
    private RecyclerView mRecyclerView;
    private CustomRecyclerListAdapter mCustomRecyclerListAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadComplete = false;

    @Override
    public int onSetUpLayout() {
        return R.layout.fragment_mainpage;
    }

    @Override
    public void onVisible() {
        LogUtil.e(TAG, "MainPageFragment onVisible");
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);
        LogUtil.e(TAG, "onActivityCreated");
    }

    @Override
    protected void findViews() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerview_merchandise_list);
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
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMerchandiseList = new ArrayList<>();
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
        LogUtil.e(TAG, this + "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e(TAG, "MainPageFragment onDetach ");
    }

    private synchronized void initList() {
        LogUtil.e(TAG, "initList");
        if (NetWorkUtil.isNetworkConnected()) {
            TaskGetMerchandiseListFromServer getMerichandiseListTast = new TaskGetMerchandiseListFromServer() {
                @Override
                public void postExecute(String response) {
                    mMerchandiseList = GsonHelper.getMerchandiseListFromGsonString(response);
                    initRecyclerView();
                }
            };
            getMerichandiseListTast.execute();
        } else {
            LogUtil.e(TAG, getString(R.string.msg_networkNotAvail));
            if (getActivity() != null) { // 要保证fragment没有被销毁
                Toast.makeText(getActivity(), getString(R.string.msg_networkNotAvail), Toast.LENGTH_SHORT).show();
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
        CustomRecyclerListAdapter adapter = new CustomRecyclerListAdapter(mRecyclerView, getActivity(), mMerchandiseList, this.getResources());
        mRecyclerView.setAdapter(adapter);
    }

    private void initRecyclerView() {
        LogUtil.e(TAG, "initRecyclerView start");
        mCustomRecyclerListAdapter = new CustomRecyclerListAdapter(mRecyclerView, getActivity(), mMerchandiseList, this.getResources());
        mCustomRecyclerListAdapter.setOnProductClick(new OnProductClickListener() {
            @Override
            public void onTouch(View v, MerchandiseModel model) {
                if (UserUtil.isLogined()) {
                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                     /* Option 1  Bundle putString && putExtras*/
//                    Bundle extras = new Bundle();
//                    extras.putString(Constants.EXTRA_IMAGE_KEY,
//                              TextUtil.hashKeyForDisk(model.getmerchandiseImageUrl()));
//                    intent.putExtras(extras);

                    /* Option 2  Bunndle putSerializable() && putExtras*/
                    /* String类型是Serializable的 所以可以直接putSerializable */
//                     extras.putSerializable(Constants.EXTRA_IMAGE_KEY,
//                            TextUtil.hashKeyForDisk(model.getmerchandiseImageUrl()));
//                    intent.putExtras(extras);

                    /* Option 3  Intent.putExtra(String key, Bundle value)*/

                    intent.putExtra(Constants.EXTRA_IMAGE_KEY,
                            TextUtil.hashKeyForDisk(model.getmerchandiseImageUrl()));

                    /* Option 3  error!!!..can not getExtras */
                    //Bundle extras = intent.getExtras();
                    //if(extras != null)
                    //    extras.putString(Constants.EXTRA_IMAGE_KEY,
                    //        TextUtil.hashKeyForDisk(model.getmerchandiseImageUrl()));
                    //else
                    //    LogUtil.e(TAG, "extras == null");

                    /* Option TEST  StringArray putSerializable test */
                    //String [] stringArray = new String[] {
                    //        TextUtil.hashKeyForDisk(model.getmerchandiseImageUrl()),
                    //        "line 2"
                    //};
                    //extras.putSerializable(Constants.EXTRA_IMAGE_KEY,
                    //        stringArray);
                    //intent.putExtras(extras);

                    startActivity(intent);
                } else {
                    //Intent intent = new Intent(getActivity(), LoginActivity.class);
                    Intent intent = LoginActivity.getStartIntent(getActivity());
                    startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(mCustomRecyclerListAdapter);
        isLoadComplete = true;
        //LogUtil.e(TAG, " isLoadComplete" + isLoadComplete);
    }

    @Override
    public String[] getObserverEventTypes() {
        return new String[]{
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
