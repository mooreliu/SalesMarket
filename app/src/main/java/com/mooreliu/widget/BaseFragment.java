package com.mooreliu.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mooreliu on 2015/9/17.
 */
public abstract class BaseFragment extends Fragment {
  private static final String TAG = "BaseFragment";
  protected boolean isVisible;
  protected View mRootView;

  public abstract int onSetUpLayout();

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mRootView == null) {
      mRootView = inflater.inflate(onSetUpLayout(), container, false);
      findViews();
      initViews();
      setOnClick();
    }
    return mRootView;
  }

  protected abstract void initViews();

  protected abstract void findViews();

  protected abstract void setOnClick();

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (getUserVisibleHint()) {
      isVisible = true;
      onVisible();
    } else {
      isVisible = false;
      onInvisible();
    }
  }

  public abstract void onVisible();

  //protected abstract void lazyLoad();
  public void onInvisible() {
  }
}
