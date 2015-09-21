package com.mooreliu.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooreliu.R;
import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.util.LogUtil;

/**
 * Created by mooreliu on 2015/9/21.
 */
public class MainPageRootFragment extends BaseFragment{

    private static final String TAG = "MainPageRootFragment";
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_root_main_page, container, false);
        LogUtil.e(TAG, "onCreateView()");
        initViews();
        return rootView;
    }
    private void replaceFragment(boolean isNetworkConnected) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        if(isNetworkConnected) {
            Fragment mainPagePageFragment = new MainPageFragment();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack if needed
            transaction.replace(R.id.mainpage_fragment_root_id, mainPagePageFragment);
            //Create new fragment and transacnt);
        } else {
            Fragment reloadFragment = ReloadFragment.newInstance(0);
            transaction.replace(R.id.mainpage_fragment_root_id, reloadFragment);
        }
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    public void checkNetwork() {
        if(NetWorkUtil.isNetworkConnected())
            replaceFragment(true);
        else
            replaceFragment(false);
    }
    @Override
    public void onVisible() {
        LogUtil.e(TAG, "onVisible()");
        checkNetwork();
    }
    private void initViews() {
//        LogUtil.e(TAG, "initViews()");
//        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.mainpage_fragment_root_id);
//        if(!(fragment instanceof MainPageFragment || fragment instanceof ReloadFragment)) {
//            LogUtil.e(TAG,"!(fragment instanceof MainPageFragment || fragment instanceof ReloadFragment)" );
//            checkNetwork();
//        } else if (fragment == null) {
//            LogUtil.e(TAG, "fragment == null");
//            checkNetwork();
//        }
    }
}
