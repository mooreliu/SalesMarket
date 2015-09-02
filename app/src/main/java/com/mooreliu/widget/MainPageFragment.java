package com.mooreliu.widget;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mooreliu.R;
import com.mooreliu.adapter.CustomRecyclerListAdapter;
import com.mooreliu.adapter.OnProductClickListener;
import com.mooreliu.model.ProductModel;
import com.mooreliu.model.ProductModelNet;
import com.mooreliu.net.HttpUtil;
import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.util.Constants;
import com.mooreliu.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyi on 15/8/29.
 * 首页
 */
public class MainPageFragment extends Fragment {
    private final static String TAG ="MainPageFragment";
    private View mView;
    private List<ProductModel> mList;
    private RecyclerView mRecyclerView;
    private CustomRecyclerListAdapter mCustomRecyclerListAdapter;
//    private StaggeredGridLayoutManager layoutManager;
    private LinearLayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_mainpage, container, false);
        findView();
        initView();
        setClick();
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);
        initList();

        //LogUtil.w(TAG, Constants.jsonProductList);

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
        LogUtil.e(TAG,"onResume");
        if(!NetWorkUtil.isNetworkConnected())
            Toast.makeText(getActivity(), getString(R.string.networkNotAvail), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e(TAG,"onPause");
        if(!NetWorkUtil.isNetworkConnected())
            Toast.makeText(getActivity(), getString(R.string.networkNotAvail), Toast.LENGTH_SHORT).show();

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
        List<ProductModelNet> retList = gson.fromJson(response,
//        List<ProductModelNet> retList = gson.fromJson(Constants.jsonProductList,
                new TypeToken<List<ProductModelNet>>() {
                }.getType());
        //for(int i =0 ;i< Constants.productUrls.length ;i++) {
        //mList.add(new ProductModel(Constants.productUrls[i],i));
        for(int i =0 ;i< retList.size()-1 ;i++) {
            mList.add(new ProductModel(retList.get(i).getUrl(),i));
        }
    }
    private void initRecyclerView() {
        LogUtil.e(TAG,"mList size"+mList.size());
        mRecyclerView =(RecyclerView)getActivity().findViewById(R.id.mainpage_recyclerview);
//        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        layoutManager = new LinearLayoutManager(getActivity());
        //LogUtil.e(TAG, "layoutmanager " + layoutManager);
        mRecyclerView.setLayoutManager(layoutManager);
//        LogUtil.e(TAG, "getActivity :" + getActivity());
        mCustomRecyclerListAdapter = new CustomRecyclerListAdapter(mRecyclerView ,getActivity() ,mList ,this.getResources());
        mRecyclerView.setAdapter(mCustomRecyclerListAdapter);
        mCustomRecyclerListAdapter.setOnProductClick(new OnProductClickListener() {
            @Override
            public void onTouch(View v, ProductModel model) {
                //Toast.makeText(getActivity(),"product clicked",Toast.LENGTH_SHORT).show();
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
