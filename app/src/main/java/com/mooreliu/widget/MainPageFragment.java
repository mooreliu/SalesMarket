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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mooreliu.util.Constants;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.TextUtil;
import com.mooreliu.util.UserUtil;
import com.mooreliu.view.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyi on 15/8/29.
 * 首页
 */
public class MainPageFragment extends BaseFragment {
    private final static String TAG ="MainPageFragment";
    private View noInternetView = null;
    private View mView;
    private List<MerchandiseModel> mList;
    private RecyclerView mRecyclerView;
    private CustomRecyclerListAdapter mCustomRecyclerListAdapter;
    private LinearLayoutManager layoutManager;
    private CustomProgressDialog progressDialog;
    private boolean isLoadComplete = false;
    private OnSwitchFragmentListener mOnSwitchFragmentListener;
    @Override
    public void onVisible() {

    }
    public MainPageFragment() {
        super();
        LogUtil.e(TAG, "MainPageFragment构造函数");
    }
    public MainPageFragment(OnSwitchFragmentListener listener) {
        super();
        mOnSwitchFragmentListener =listener;
        LogUtil.e(TAG, "MainPageFragment listener构造函数");
    }
    public static MainPageFragment newInstance() {
        MainPageFragment fragment = new MainPageFragment();
        return  fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e(TAG, "onCreateView");
        mView = inflater.inflate(R.layout.layout_mainpage, container, false);
        LogUtil.e(TAG, "onCreateView isLoadComplete"+isLoadComplete);
        return mView;
    }

    private void initNoInternetView (View view) {
        if(view == null)
            return;
        RelativeLayout parentView = (RelativeLayout)view.findViewById(R.id.parent_view);
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        noInternetView = inflater.inflate(R.layout.layout_reload, null);
        noInternetView.setLayoutParams(
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        noInternetView.setClickable(true);
        parentView.addView(noInternetView);
        final Button btnGotoSetting = (Button)noInternetView.findViewById(R.id.goto_setting);
        btnGotoSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });

        final Button btnReload = (Button)noInternetView.findViewById(R.id.reload);
        if(noInternetView != null)
            noInternetView.setVisibility(View.GONE);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mview) {
                showProgress(R.string.reconnect_to_internet);
                checkNerworkForView();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);
        findView();
        initView();
        setClick();
        LogUtil.e(TAG, "onActivityCreated");

    }

    public void showProgress(int resID) {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        progressDialog = new CustomProgressDialog(getActivity(), getResources()
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
    private void findView() {
        mRecyclerView =(RecyclerView)mView.findViewById(R.id.mainpage_recyclerview);

    }
    private void initView() {
        initNoInternetView(mView);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        if(checkNerworkForView()&&isLoadComplete==false)
            initList();

    }
    private void setClick() {

    }
    private boolean checkNerworkForView() {
        if (!NetWorkUtil.isNetworkConnected()) {
            if (noInternetView != null) {
                noInternetView.setVisibility(View.VISIBLE);
                noInternetView.bringToFront();
            }
            return false;
        } else {
            if (noInternetView != null)
                noInternetView.setVisibility(View.GONE);
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(checkNerworkForView() ) {
            LogUtil.e(TAG,"onResume isLoadComplete"+isLoadComplete);
            initList();
        }
        LogUtil.e(TAG, "onResume");
    }

    @Override
    public void onPause() {
        LogUtil.e(TAG,"onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e(TAG,"onStop");
    }


    @Override
    public void onAttach(Activity activity) {
        super.onDetach();
        LogUtil.e(TAG,"onAttach "+activity.toString());
    }
    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        LogUtil.e(TAG, "onCreate");
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


    private void initList(){
        mList = new ArrayList<>();
        //GzipTest();
        if(NetWorkUtil.isNetworkConnected()) {
            TaskGetProductListFromServer getProductListTast = new TaskGetProductListFromServer();
            getProductListTast.execute();
        } else {
            LogUtil.e(TAG,getString(R.string.networkNotAvail));
            if(getActivity() != null) { // 要保证fragment没有被销毁
                Toast.makeText(getActivity(), getString(R.string.networkNotAvail), Toast.LENGTH_SHORT).show();
                GzipTest(Constants.jsonProductList, false);
                initRecyclerView();
            }
        }
    }

    private class TaskGetProductListFromServer extends AsyncTask<Void,Void ,Boolean> {
        @Override
        public Boolean doInBackground(Void ...parms) {
            String getUrl = "http://192.168.1.115:8080/user";
            String response;
            response = HttpUtil.HttpGETResponseString(getUrl ,5*1000 ,5*1000);
            if(response !=null) {
                LogUtil.e(TAG ,"response ="+response);
                GzipTest(response ,true);
                return true;
            }
            return  false;
        }

        @Override
        public void onPostExecute(Boolean isSuccess) {
            LogUtil.e(TAG, "download RecyclerView data from server isSuccess   " + isSuccess);
            if(isSuccess == false)
                GzipTest(Constants.jsonProductList ,false);
            if(getActivity() != null)
                initRecyclerView();

        }


    }

    private void GzipTest(String response,boolean downloadSuccess) {
        if(mList.size() != 0 &&downloadSuccess ==true) {
            mList.clear();
        }
        Gson gson = new Gson();
        //Gzip Compress
//        try {
//            String gzipStr = GzipUtil.compress(Constants.jsonProductList);
//            LogUtil.e(TAG,"gzipStr Compressed "+gzipStr);
//            String unCompressedStr = GzipUtil.decompress(gzipStr);
//            LogUtil.e(TAG," decompressed "+unCompressedStr);
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
        List<MerchandiseModel> retList = gson.fromJson(response,
        // List<MerchandiseModel> retList = gson.fromJson(Constants.jsonProductList,
                new TypeToken<List<MerchandiseModel>>() {
                }.getType());
        for(int i =0 ;i< retList.size()-1 ;i++) {
            mList.add(new MerchandiseModel(retList.get(i).getmerchandiseImageUrl()));
        }
    }
    private void initRecyclerView() {
        mCustomRecyclerListAdapter = new CustomRecyclerListAdapter(mRecyclerView ,getActivity() ,mList ,this.getResources());
        mRecyclerView.setAdapter(mCustomRecyclerListAdapter);
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

        isLoadComplete = true;
        LogUtil.e(TAG, " isLoadComplete" + isLoadComplete);

    }

}
