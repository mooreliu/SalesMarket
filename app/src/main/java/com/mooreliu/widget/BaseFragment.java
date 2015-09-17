package com.mooreliu.widget;

import android.support.v4.app.Fragment;

import com.mooreliu.util.LogUtil;

/**
 * Created by mooreliu on 2015/9/17.
 */
public  abstract class BaseFragment extends Fragment{
    private static final String TAG = "BaseFragment";
    protected boolean isVisible;

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
    protected abstract void onVisible();

        //lazyLoad();
    //protected abstract void lazyLoad();
    protected void onInvisible(){}
}
