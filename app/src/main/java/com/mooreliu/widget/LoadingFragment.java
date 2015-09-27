//package com.mooreliu.widget;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import com.mooreliu.R;
//import com.mooreliu.listener.OnLoadingOverListener;
//import com.mooreliu.util.LogUtil;
//
///**
// * Created by mooreliu on 2015/9/17.
// */
//public class LoadingFragment extends BaseFragment  {
//    private static final String TAG = "LoadingFragment";
//
//    private OnLoadingOverListener loadingOverListener;
//    private View rootView;
//    private Button show;
//    protected boolean isVisible;
//    private int fragmentId;
//    private int testId =-1;
//    public void onVisible(){
//        LogUtil.e(TAG, "onVisible");
//        LogUtil.e(TAG, "onVisible testId = " +testId);
//        if (loadingOverListener == null) {
//            LogUtil.e(TAG, "loadingOverListener 为空");
//        }
//        //if (loadingOverListener != null)
//            //loadingOverListener.LoadingOver(fragmentId);
//        //lazyLoad();
//    }
//    //protected abstract void lazyLoad();
//    public void onInvisible(){}
//
//    public LoadingFragment(int testId) {
//        super();
//        testId = testId;
//        LogUtil.e(TAG,"testId="+testId);
//    }
//    public static LoadingFragment newinstance(int fragmentId, OnLoadingOverListener listener) {
//        return new LoadingFragment(fragmentId, listener);
//    }
//    public LoadingFragment(int fragmentId,OnLoadingOverListener listener) {
//        loadingOverListener = listener;
//        this.fragmentId = fragmentId;
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        LogUtil.e(TAG, "onCreateView");
//        rootView = inflater.inflate(R.layout.layout_fragment_loading, container, false);
//        show = (Button) rootView.findViewById(R.id.show);
//        return rootView;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle onSavedInstanceState) {
//        super.onActivityCreated(onSavedInstanceState);
//        show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (loadingOverListener == null) {
//                    LogUtil.e(TAG, "loadingOverListener 为空");
//                }
//                //if (loadingOverListener != null)
//                    //loadingOverListener.LoadingOver(fragmentId);
//            }
//        });
//    }
//
//    @Override
//    public void onResume() {
//
//
//        super.onResume();
//    }
//
//
//
//}
