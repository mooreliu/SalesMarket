package com.mooreliu.widget;

import android.support.v4.app.Fragment;

import com.mooreliu.util.LogUtil;

/**
 * Created by mooreliu on 2015/9/17.
 */
public abstract class BaseFragment extends Fragment{
    private static final String TAG = "BaseFragment";
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtil.e(TAG, "setUserVisibleHint");
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            LogUtil.e(TAG, " isVisible = true");
            onVisible();
        } else {
            isVisible = false;
            LogUtil.e(TAG, " isVisible = false");
            onInvisible();
        }
    }
    public abstract void onVisible();

    //lazyLoad();
    //protected abstract void lazyLoad();
    public void onInvisible(){}
}
