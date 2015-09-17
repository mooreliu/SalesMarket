package com.mooreliu.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mooreliu.R;
import com.mooreliu.listener.OnLoadingOverListener;
import com.mooreliu.util.LogUtil;

/**
 * Created by mooreliu on 2015/9/17.
 */
public class LoadingFragment extends BaseFragment  {
    private static final String TAG = "LoadingFragment";

    private OnLoadingOverListener loadingOverListener;
    private View rootView;
    private Button show;
    protected boolean isVisible;
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    protected void onVisible(){
        LogUtil.e(TAG, "onVisible");
        if (loadingOverListener == null) {
            LogUtil.e(TAG, "loadingOverListener 为空");
        }
        if (loadingOverListener != null)
            loadingOverListener.LoadingOver();
        //lazyLoad();
    }
    //protected abstract void lazyLoad();
    protected void onInvisible(){}

    public LoadingFragment() {
        super();
    }
    public LoadingFragment(OnLoadingOverListener listener) {
        loadingOverListener = listener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e(TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.layout_fragment_loading, container, false);
        show = (Button) rootView.findViewById(R.id.show);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loadingOverListener == null) {
                    LogUtil.e(TAG, "loadingOverListener 为空");
                }
                if (loadingOverListener != null)
                    loadingOverListener.LoadingOver();
            }
        });
    }

    @Override
    public void onResume() {


        super.onResume();
    }



}
