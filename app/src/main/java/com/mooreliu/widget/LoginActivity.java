package com.mooreliu.widget;

import android.os.Bundle;

import com.mooreliu.R;

/**
 * Created by liuyi on 15/9/6.
 */
public class LoginActivity extends  BaseActivity {

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.layout_login);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
    }

    @Override
    public void findView() {

    }
    @Override
    public void initView() {
        setToolBarTitle(getString(R.string.login));
    }

    @Override
    public void setOnClick() {

    }
}
