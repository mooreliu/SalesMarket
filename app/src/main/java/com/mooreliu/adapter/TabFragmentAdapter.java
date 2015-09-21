package com.mooreliu.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.mooreliu.AppContext;
import com.mooreliu.R;
import com.mooreliu.listener.OnLoadingOverListener;
import com.mooreliu.listener.OnSwitchFragmentListener;
import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.util.LogUtil;
import com.mooreliu.widget.LoadingFragment;
import com.mooreliu.widget.MainPageFragment;
import com.mooreliu.widget.MainPageRootFragment;
import com.mooreliu.widget.MyPageFragment;
import com.mooreliu.widget.ReloadFragment;
import com.mooreliu.widget.ShoppingListPageFragment;
import com.mooreliu.widget.ShoppingListRootFragment;
import com.mooreliu.widget.MyPageRootFragment;

/**
 * Description:  ViewPager适配器
 * User: mooreliu
 * Date: 2015/8/29
 * Time: 15:12
 */

public class TabFragmentAdapter extends FragmentPagerAdapter{

    private final static String TAG = "TabFragmentAdapter";
    int[] imageResId={
            R.mipmap.main_navigation_home,
            R.mipmap.main_navigation_car,
            R.mipmap.main_navigation_catagory
    };
    private  Fragment mFragmentAtPos0;
    private  Fragment mFragmentAtPos1;
    private  Fragment mFragmentAtPos2;

    private FragmentManager fm;
    private int baseId = 0;

    public TabFragmentAdapter(FragmentManager fm){//}, List<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        //LogUtil.e(TAG, "TabFragmentAdapter构造函数");
    }

    @Override
    public Fragment getItem(int position) {
        LogUtil.e(TAG, "getItem +" + position);
        switch (position) {
            case 0:
                return new MainPageRootFragment();
            case 1:
                return new ShoppingListRootFragment();
            case 2:
                return new MyPageRootFragment();
            default:
                return ReloadFragment.newInstance(0);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // LogUtil.e(TAG,position+" position");
        Drawable image = AppContext.getContext().getResources().getDrawable(imageResId[position]);
        image.mutate().setColorFilter(Color.argb(255,255, 255, 255), PorterDuff.Mode.SRC_IN);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }


}