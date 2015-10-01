/*
 * Copyright (C) 2010 The Android Open Source Project
 * Created by liuyi on 15/8/29.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mooreliu.widget;

import android.app.Activity;
import android.os.Bundle;

import com.mooreliu.R;
import com.mooreliu.sync.EventType;
import com.mooreliu.sync.NotifyInfo;
import com.mooreliu.util.LogUtil;
import com.mooreliu.view.InternetOffLayout;

public class ShoppingListPageFragment extends BaseObserverFragment {
    private static final String TAG = "ShoppingListPageFragment";

    public static ShoppingListPageFragment newInstance() {
        ShoppingListPageFragment fragment = new ShoppingListPageFragment();
        return fragment;
    }

    @Override
    public String[] getObserverEventTypes() {
        return new String[]{EventType.NETWORK_OK, EventType.NETWORK_NOT_OK};
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

    @Override
    public int onSetUpLayout() {
        return R.layout.fragment_shoppinglist;
    }

    @Override
    public void onVisible() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initViews() {
        mInternetOffLayout = new InternetOffLayout(getActivity(), mRootView, R.id.shoppinglist_parent_view) {
            public void initData() {
                initViewData();
            }
        };
        mInternetOffLayout.showInternetOffLayout();
        if (mInternetOffLayout.checkNerworkForView())
            initViewData();
    }

    private void initViewData() {

    }

    @Override
    protected void setOnClick() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onDetach();
        LogUtil.e(TAG, "onAttach " + activity.toString());
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        LogUtil.e(TAG, "onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e(TAG, "onResume");
        if (mInternetOffLayout.checkNerworkForView()) {
            initViewData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e(TAG, "onDetach");
    }
}
