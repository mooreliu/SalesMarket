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
import com.mooreliu.listener.OnSwitchFragmentListener;
import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.util.LogUtil;
import com.mooreliu.widget.LoadingFragment;
import com.mooreliu.widget.MainPageFragment;
import com.mooreliu.widget.MyPageFragment;
import com.mooreliu.widget.ReloadFragment;
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
    private  Fragment mFragmentAtPos1;
    private  Fragment mFragmentAtPos2;
<<<<<<< HEAD

    private FragmentManager fm;
    private int baseId = 0;
    //private List<Fragment> mFragments;
    private  MainPageFragmentListener mMainPageFragmentListener;// = new MainPageFragmentListener();
    private SwitchFragmentListener mSwitchFragmentListener;
=======
    private FragmentManager fm;
    private int baseId = 0;
    //private List<Fragment> mFragments;
//    private  LoadingOverListener mLoadingOverListener;// = new MainPageFragmentListener();
>>>>>>> acd3e5e17d54cb8195ef69a576fe23ed2cf4e75c
    int[] imageResId={
            R.mipmap.main_navigation_home,
            R.mipmap.main_navigation_car,
            R.mipmap.main_navigation_catagory

    };
<<<<<<< HEAD
    private final class MainPageFragmentListener implements OnLoadingOverListener {
        @Override
        public void LoadingOver() {
            LogUtil.e(TAG, "MainPageFragmentListener LoadingOver回调函数");
            fm.beginTransaction().remove(mFragmentAtPos0).commit();
            mFragmentAtPos0 =  MainPageFragment.newInstance();
            //notifyChangeInPosition(0);
            notifyDataSetChanged();
        }
    }
    private final class SwitchFragmentListener implements OnSwitchFragmentListener {
        @Override
        public void switchFragment(int fragmentId, boolean isNetworkAvail) {
            LogUtil.e(TAG, "swtchFragment fragmentId= "+fragmentId+"isNetworkAvail "+isNetworkAvail);
            switch (fragmentId) {
                case 0:
                    break;
                case 1:
                    if(isNetworkAvail) {
                        LogUtil.e(TAG, "fragmentId =1 network is  available");
                        fm.beginTransaction().remove(mFragmentAtPos1).commit();
                        mFragmentAtPos1 = new ShoppingListPageFragment(mSwitchFragmentListener);
                        //notifyChangeInPosition(0);
                        notifyDataSetChanged();
                    } else {
                        LogUtil.e(TAG, "fragmentId =1 network is not available");
                        fm.beginTransaction().remove(mFragmentAtPos1).commit();
                        mFragmentAtPos1 = new ReloadFragment(1, mSwitchFragmentListener);
                        //notifyChangeInPosition(0);
                        notifyDataSetChanged();
                    }
                    break;
                case 2:
                    break;
            }
        }

    }
=======
//    private final class LoadingOverListener implements OnLoadingOverListener {
//        @Override
//        public void LoadingOver(int fragmentId) {
//            LogUtil.e(TAG, "MainPageFragmentListener LoadingOver回调函数 fragmentId+ "+fragmentId);
//            switch (fragmentId) {
//                case 0:
//                    fm.beginTransaction().remove(mFragmentAtPos0).commit();
//                    mFragmentAtPos0 =  MainPageFragment.newInstance();
//                    //notifyChangeInPosition(0);
//                    notifyDataSetChanged();
//                    break;
//                case 1:
//                    fm.beginTransaction().remove(mFragmentAtPos1).commit();
//                    mFragmentAtPos1 =  ShoppingListPageFragment.newInstance();
//                    //notifyChangeInPosition(0);
//                    notifyDataSetChanged();
//                    break;
//                case 2:
//                    fm.beginTransaction().remove(mFragmentAtPos2).commit();
//                    mFragmentAtPos2 =  MyPageFragment.newInstance();
//                    //notifyChangeInPosition(0);
//                    notifyDataSetChanged();
//                    break;
//            }
//
//        }
//    }
>>>>>>> acd3e5e17d54cb8195ef69a576fe23ed2cf4e75c

    public TabFragmentAdapter(FragmentManager fm){//}, List<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        LogUtil.e(TAG, "TabFragmentAdapter构造函数");
<<<<<<< HEAD
        mSwitchFragmentListener = new SwitchFragmentListener();
        mMainPageFragmentListener = new MainPageFragmentListener();
        if(mMainPageFragmentListener == null) {
            LogUtil.e(TAG, "mMainPageFragmentListener为空");
        }
       // mFragments = fragments;
=======
//        mLoadingOverListener = new LoadingOverListener();
//        if(mLoadingOverListener == null) {
//            LogUtil.e(TAG, "mMainPageFragmentListener为空");
//        }
        // mFragments = fragments;
>>>>>>> acd3e5e17d54cb8195ef69a576fe23ed2cf4e75c
    }

    @Override
    public Fragment getItem(int position) {
        LogUtil.e(TAG ,"getItem +"+position);
        switch (position) {
            case 0:
<<<<<<< HEAD
                if(mFragmentAtPos0 == null)
                    mFragmentAtPos0 = new LoadingFragment();
=======
                if (mFragmentAtPos0 == null) {
                    mFragmentAtPos0 = ReloadFragment.newInstance(new OnLoadingOverListener() {
                        @Override
                        public void LoadingOver() {
                            LogUtil.e(TAG, "ReloadFragment notifyDataSetChanged(); LoadingOver ");
                            fm.beginTransaction().remove(mFragmentAtPos0).commit();
                            mFragmentAtPos0 = MainPageFragment.newInstance();
                            notifyDataSetChanged();
                        }
                    });
                }
>>>>>>> acd3e5e17d54cb8195ef69a576fe23ed2cf4e75c
                return mFragmentAtPos0;

            case 1:
<<<<<<< HEAD
                LogUtil.e(TAG, "CASE 1");
                if(mFragmentAtPos1 == null)
                    mFragmentAtPos1 = new ShoppingListPageFragment(mSwitchFragmentListener);
                LogUtil.e(TAG, "before return  1");
                return mFragmentAtPos1;
            case 2:
                return new MyPageFragment();
            default:
                return new ReloadFragment();
=======
                if (mFragmentAtPos1 == null) {
                    mFragmentAtPos1 = ReloadFragment.newInstance(new OnLoadingOverListener() {
                        @Override
                        public void LoadingOver() {
                            fm.beginTransaction().remove(mFragmentAtPos1).commit();
                            mFragmentAtPos1 = ShoppingListPageFragment.newInstance();
                            notifyDataSetChanged();
                        }
                    });
                }
                return mFragmentAtPos1;
            case 2:
                if (mFragmentAtPos2== null) {
                    mFragmentAtPos2= ReloadFragment.newInstance(new OnLoadingOverListener() {
                        @Override
                        public void LoadingOver() {
                            fm.beginTransaction().remove(mFragmentAtPos2).commit();
                            mFragmentAtPos2 = MyPageFragment.newInstance();
                            notifyDataSetChanged();
                        }
                    });
                }
                return mFragmentAtPos2;

            default:
                LogUtil.e(TAG, "default");
                return new LoadingFragment(3);
>>>>>>> acd3e5e17d54cb8195ef69a576fe23ed2cf4e75c
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
<<<<<<< HEAD
    public int getItemPosition(Object object)
    {
//        LogUtil.e(TAG, "getItemPosition object="+object);
//        LogUtil.e(TAG, "getItemPosition mFragmentAtPos0="+mFragmentAtPos0);
//        if (mFragmentAtPos0 instanceof MainPageFragment && object instanceof ReloadFragment) {
//            LogUtil.e(TAG, "POSITION_NONE");
//            return POSITION_NONE;
//        }
//        if (mFragmentAtPos1 instanceof ShoppingListPageFragment && object instanceof ReloadFragment) {
//            LogUtil.e(TAG, "POSITION_NONE");
//            return POSITION_NONE;
//        }
//        if (mFragmentAtPos2 instanceof MyPageFragment && object instanceof ReloadFragment) {
//            LogUtil.e(TAG, "POSITION_NONE");
//            return POSITION_NONE;
//        }
        return POSITION_UNCHANGED;
       // return POSITION_UNCHANGED;
    }

    @Override
    public long getItemId(int position) {
        // give an ID different from position when position has been changed
        LogUtil.e(TAG, "getItemId position = "+position);
        return baseId + position;
    }
=======
    public int getItemPosition(Object object) {
        if(object instanceof ReloadFragment && mFragmentAtPos0 instanceof MainPageFragment) {
            LogUtil.e(TAG,"object instanceof ReloadFragment && mFragmentAtPos0 instanceof MainPageFragment return POSITION_NONE " );
            return POSITION_NONE;
        }
        if( object instanceof ReloadFragment && mFragmentAtPos1 instanceof ShoppingListPageFragment)
            return POSITION_NONE;
        if( object instanceof ReloadFragment && mFragmentAtPos2 instanceof MyPageFragment)
            return POSITION_NONE;
        LogUtil.e(TAG,"POSITION_UNCHANGED");
        return POSITION_UNCHANGED;
    }

//    @Override
//    public int getItemPosition(Object object)
//    {
////        LogUtil.e(TAG, "getItemPosition object="+object);
////        LogUtil.e(TAG, "getItemPosition mFragmentAtPos0="+mFragmentAtPos0);
////        if ((mFragmentAtPos0 instanceof  LoadingFragment) && (object instanceof MainPageFragment )) {
////            LogUtil.e(TAG, "POSITION_NONE");
////            return POSITION_NONE;
////        }
////        if ((object instanceof  LoadingFragment) && (mFragmentAtPos1 instanceof ShoppingListPageFragment)) {
////            LogUtil.e(TAG, "POSITION_NONE");
////            return POSITION_NONE;
////        }
////        if ((object instanceof  LoadingFragment) && (mFragmentAtPos2 instanceof MyPageFragment)) {
////            LogUtil.e(TAG, "POSITION_NONE");
////            return POSITION_NONE;
////        }
////        LogUtil.e(TAG, " return POSITION_UNCHANGED");
//        return POSITION_UNCHANGED;
//        // return POSITION_UNCHANGED;
//    }

//    @Override
//    public long getItemId(int position) {
//        // give an ID different from position when position has been changed
//        return baseId + position;
//    }
>>>>>>> acd3e5e17d54cb8195ef69a576fe23ed2cf4e75c

    /**
     * Notify that the position of a fragment has been changed.
     * Create a new ID for each position to force recreation of the fragment
     * @param n number of items which have been changed
     */
<<<<<<< HEAD
    public void notifyChangeInPosition(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        baseId += n;
        LogUtil.e(TAG,"after notifiChangedInPosition baseId= "+baseId);
    }
=======
//    public void notifyChangeInPosition(int n) {
//        // shift the ID returned by getItemId outside the range of all previous fragments
////        baseId += getCount() + n;
//        baseId =  n;
//    }
>>>>>>> acd3e5e17d54cb8195ef69a576fe23ed2cf4e75c


}