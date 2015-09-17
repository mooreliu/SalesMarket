package com.mooreliu.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.mooreliu.AppContext;
import com.mooreliu.R;
import com.mooreliu.listener.OnLoadingOverListener;
import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.util.LogUtil;
import com.mooreliu.widget.LoadingFragment;
import com.mooreliu.widget.ShoppingListPageFragment;

/**
 * Description:  ViewPager适配器
 * User: mooreliu
 * Date: 2015/8/29
 * Time: 15:12
 */

//public class TabFragmentAdapter extends FragmentStatePagerAdapter {
public class TabFragmentAdapter extends FragmentPagerAdapter{

    private final static String TAG = "TabFragmentAdapter";
    private  Fragment mFragmentAtPos0;
    private FragmentManager fm;
    private int baseId = 0;
    //private List<Fragment> mFragments;
    private  MainPageFragmentListener mMainPageFragmentListener;// = new MainPageFragmentListener();
    int[] imageResId={
        R.mipmap.main_navigation_home,
        R.mipmap.main_navigation_car,
        R.mipmap.main_navigation_catagory

    };
    private final class MainPageFragmentListener implements OnLoadingOverListener {
        @Override
        public void LoadingOver() {
            LogUtil.e(TAG, "MainPageFragmentListener LoadingOver回调函数");
            fm.beginTransaction().remove(mFragmentAtPos0).commit();
            mFragmentAtPos0 =  ShoppingListPageFragment.newInstance();
            notifyChangeInPosition(0);
            notifyDataSetChanged();
        }
    }

    public TabFragmentAdapter(FragmentManager fm){//}, List<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        LogUtil.e(TAG, "TabFragmentAdapter构造函数");
        mMainPageFragmentListener = new MainPageFragmentListener();
        if(mMainPageFragmentListener == null) {
            LogUtil.e(TAG, "mMainPageFragmentListener为空");
        }
       // mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        LogUtil.e(TAG ,"getItem +"+position);
        switch (position) {
            case 0:
                if(mFragmentAtPos0 == null)
                    mFragmentAtPos0 = new LoadingFragment(mMainPageFragmentListener);
                return mFragmentAtPos0;
            case 1:
                //if (!NetWorkUtil.isNetworkConnected())
                    return new LoadingFragment();
            case 2:
                return new LoadingFragment();
            default:
                return new LoadingFragment();
        }
    }

    @Override
    public int getCount() {
        //LogUtil.e(TAG ,"getCount() return mFragments.size() :"+mFragments.size());
        //return mFragments.size()-1;
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

    @Override
    public int getItemPosition(Object object)
    {
        LogUtil.e(TAG, "getItemPosition object="+object);
        LogUtil.e(TAG, "getItemPosition mFragmentAtPos0="+mFragmentAtPos0);
        if (object instanceof  LoadingFragment && mFragmentAtPos0 instanceof ShoppingListPageFragment) {
            LogUtil.e(TAG, "POSITION_NONE");
            return POSITION_NONE;
        }
        return POSITION_NONE;
       // return POSITION_UNCHANGED;
    }

    @Override
    public long getItemId(int position) {
        // give an ID different from position when position has been changed
        return baseId + position;
    }

    /**
     * Notify that the position of a fragment has been changed.
     * Create a new ID for each position to force recreation of the fragment
     * @param n number of items which have been changed
     */
    public void notifyChangeInPosition(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        baseId += getCount() + n;
    }


}
