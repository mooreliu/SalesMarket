package com.mooreliu.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.mooreliu.AppContext;
import com.mooreliu.R;
import com.mooreliu.util.LogUtil;

import java.util.List;

/**
 * Description:  ViewPager适配器
 * User: mooreliu
 * Date: 2015/8/29
 * Time: 15:12
 */

public class TabFragmentAdapter extends FragmentStatePagerAdapter {
    private final static String TAG = "TabFragmentAdapter";

    int[] imageResId={
        R.mipmap.main_navigation_home,
        R.mipmap.main_navigation_car,
        R.mipmap.main_navigation_catagory

    };
    private List<Fragment> mFragments;

    public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        LogUtil.e(TAG ,"getItem +"+position);
        return mFragments.get(position);
//        if(NetWorkUtil.isNetworkConnected())
//            return mFragments.get(position);
//        else
//            return mFragments.get(3);
    }

    @Override
    public int getCount() {
        //LogUtil.e(TAG ,"getCount() return mFragments.size() :"+mFragments.size());
        return mFragments.size()-1;
    }


    @Override
    public CharSequence getPageTitle(int position) {
       // LogUtil.e(TAG,position+" positoin");
        Drawable image = AppContext.getContext().getResources().getDrawable(imageResId[position]);
        image.mutate().setColorFilter(Color.argb(255,255, 255, 255), PorterDuff.Mode.SRC_IN);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

//    public CharSequence getPageTitle(int position) {
//        return mTitles.get(position);
//    }


}
