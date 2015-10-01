package com.mooreliu.widget;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.mooreliu.R;
import com.mooreliu.adapter.FlashImageAdapter;
import com.mooreliu.util.BitmapUtil;
import com.mooreliu.util.LogUtil;
import com.mooreliu.view.RatioImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开机闪屏 Created by liuyi on 15/8/29.
 */
public class FlashActivity extends Activity implements OnPageChangeListener, OnClickListener {
    private static final String TAG = "FlashActivity";
    private static final String FIRST_PREF = "FIRST_PREF";
    private static final String KEY_IS_FRIST_LAUNCH = "KEY_IS_FIRST_LAUNCH";
    private static final int[] imageRes = {R.mipmap.flash, R.mipmap.flash2, R.mipmap.flash3, R.mipmap.flash1};
    private boolean isFirstLaunch = true;
    private int currentIndex;
    private ImageView[] mImageViewsDots;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);
        checkForFirstTimeLaunch();
        findViews();
        initViews();
        setOnclick();
    }

    private void findViews() {
        if (isFirstLaunch) mViewPager = (ViewPager) findViewById(R.id.app_desc_viewpagers);
    }

    private void initViews() {
        if (isFirstLaunch) {
            initDots();
            initFlashPage();
        }
    }

    private void setOnclick() {

    }

    @Override
    public void onClick(View view) {
        int viewID = view.getId();
        LogUtil.e(TAG, "viewId " + viewID);

    }

    private void checkForFirstTimeLaunch() {
        SharedPreferences settings = getSharedPreferences(FIRST_PREF, 0);
        if (settings.getBoolean(KEY_IS_FRIST_LAUNCH, true)) {
            settings.edit().putBoolean(KEY_IS_FRIST_LAUNCH, false).commit();
            isFirstLaunch = true;
            setContentView(R.layout.layout_app_description_view_pagers);
        } else {
            isFirstLaunch = false;
            setContentView(R.layout.activity_flash);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(FlashActivity.this, MainActivity.class);
                    startActivity(intent);
                    FlashActivity.this.finish();
                }
            }, 1000);
        }
    }

    private void initDots() {
        mImageViewsDots = new ImageView[4];
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_dots);
        for (int i = 0; i < mImageViewsDots.length; i++) {
            mImageViewsDots[i] = (ImageView) linearLayout.getChildAt(i);
            mImageViewsDots[i].setEnabled(true);
            mImageViewsDots[i].setOnClickListener(this);
            mImageViewsDots[i].setTag(i);
        }
        currentIndex = 0;
        mImageViewsDots[currentIndex].setEnabled(false);
    }

    private void initFlashPage() {
        List<View> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            RatioImageView iv = new RatioImageView(this);
            iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//          iv.setAdjustViewBounds(true);
            iv.setImageBitmap(BitmapUtil.bitmapDecode(getResources(), imageRes[i], 640, 360));
            list.add(iv);
        }

        FlashImageAdapter adapter = new FlashImageAdapter(list);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        ExitDialog dialog = new ExitDialog(FlashActivity.this);
        dialog.show();
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageSelected(int position) {
        LogUtil.e(TAG, "onPageSelected postion = " + position);
        if (3 == position) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(FlashActivity.this, MainActivity.class);
                    startActivity(intent);
                    FlashActivity.this.finish();
                }
            }, 1000);
        }

    }
}
