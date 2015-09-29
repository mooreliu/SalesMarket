package com.mooreliu.widget;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.mooreliu.AppContext;
import com.mooreliu.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by liuyi on 15/8/29.
 */
public abstract class BaseActivity extends ActionBarActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);
        AppContext.setActiveContext(this);
    }

    @Override
    public void setContentView(int layoutId) {
        super.setContentView(layoutId);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        findView();
        initView();
        setOnClick();
        setStatusBarView();
    }

    public void setToolBarTitle(String title) {
        if (!TextUtils.isEmpty(title)) mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        setBackListener();
    }

    protected void setBackListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackEvent();
            }
        });
    }

    /**
     * 设置返回按钮的事件，默认是返回到前一个界面
     */
    protected void setBackEvent() {
        onBackPressed();
    }

    /**
     * 设置状态栏的颜色，目前只是在4.4上面有效
     */
    protected void setStatusBarView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(false);
            tintManager.setTintColor(getResources().getColor(R.color.DodgerBlue));
        }
    }

    protected abstract void findView();

    protected abstract void initView();

    protected abstract void setOnClick();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = AppContext.getRefWatcher(this);
        refWatcher.watch(this);
    }


}
