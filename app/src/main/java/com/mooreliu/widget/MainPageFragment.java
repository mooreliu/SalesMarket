package com.mooreliu.widget;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import com.mooreliu.adapter.OnProductClickListener;
import com.mooreliu.db.model.MerchandiseModel;
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
public class MainPageFragment extends Fragment {
    private final static String TAG ="MainPageFragment";
    private View noInternetView = null;
    private View mView;
    private List<MerchandiseModel> mList;
    private RecyclerView mRecyclerView;
    private CustomRecyclerListAdapter mCustomRecyclerListAdapter;
//    private StaggeredGridLayoutManager layoutManager;
    private LinearLayoutManager layoutManager;
    private CustomProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_mainpage, container, false);
        findView();
        initView();
        setClick();
        initNoInternetView(mView);

        if(!NetWorkUtil.isNetworkConnected()) {
            if(noInternetView != null) {
                noInternetView.setVisibility(View.VISIBLE);
                noInternetView.bringToFront();
            }
        }
        return mView;
    }

    private void initNoInternetView (View view) {
        if(view == null)
            return;
        RelativeLayout parentView = (RelativeLayout)view.findViewById(R.id.parent_view);

        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        noInternetView = inflater.inflate(R.layout.layout_reload, null);
        noInternetView.setLayoutParams(
                new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        noInternetView.setClickable(true);
        parentView.addView(noInternetView);

        final Button button = (Button)noInternetView.findViewById(R.id.reload);
        if(noInternetView != null)
            noInternetView.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mview) {
                showProgress(R.string.reconnect_to_internet);
                if (!NetWorkUtil.isNetworkConnected()) {
                    if (noInternetView != null) {
                        noInternetView.setVisibility(View.VISIBLE);
                        noInternetView.bringToFront();
                    }
                } else {
                    if (noInternetView != null)
                        noInternetView.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);
        initList();
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
        handler.postDelayed(r,200);

    }
    private void findView() {

    }
    private void initView() {

    }
    private void setClick() {

    }

    @Override
    public void onResume() {
        super.onResume();
//        LogUtil.e(TAG,"onResume");
//        if(!NetWorkUtil.isNetworkConnected())
//            Toast.makeText(getActivity(), getString(R.string.networkNotAvail), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
//        LogUtil.e(TAG,"onPause");
//        if(!NetWorkUtil.isNetworkConnected())
//            Toast.makeText(getActivity(), getString(R.string.networkNotAvail), Toast.LENGTH_SHORT).show();

    }

    private void initList(){
        mList = new ArrayList<>();
        //GzipTest();
        if(NetWorkUtil.isNetworkConnected()) {
            TaskGetProductListFromServer getProductListTast = new TaskGetProductListFromServer();
            getProductListTast.execute();
        } else {
            LogUtil.e(TAG,getString(R.string.networkNotAvail));
            Toast.makeText(getActivity(), getString(R.string.networkNotAvail), Toast.LENGTH_SHORT).show();
            GzipTest(Constants.jsonProductList ,false);
            initRecyclerView();
        }
    }

    private class TaskGetProductListFromServer extends AsyncTask<Void,Void ,Boolean> {
        @Override
        public Boolean doInBackground(Void ...parms) {
            String getUrl = new String("http://192.168.1.115:8080/user");
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
            LogUtil.e(TAG, "isSuccess" + isSuccess);
            if(isSuccess == false)
                GzipTest(Constants.jsonProductList ,false);
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
        mRecyclerView =(RecyclerView)getActivity().findViewById(R.id.mainpage_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mCustomRecyclerListAdapter = new CustomRecyclerListAdapter(mRecyclerView ,getActivity() ,mList ,this.getResources());
        mRecyclerView.setAdapter(mCustomRecyclerListAdapter);
        mCustomRecyclerListAdapter.setOnProductClick(new OnProductClickListener() {
            @Override
            public void onTouch(View v, MerchandiseModel model) {
                if(UserUtil.isLogined()) {
                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("ImageKey", TextUtil.hashKeyForDisk(model.getmerchandiseImageUrl()));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity() , LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
