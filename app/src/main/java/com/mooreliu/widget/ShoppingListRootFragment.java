package com.mooreliu.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooreliu.R;
import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.util.LogUtil;

/**
 * Created by liuyi on 15/9/18.
 */
public class ShoppingListRootFragment extends BaseFragment {

    private static final String TAG = "ShoppingListRootFragment";
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_root_shopping_list, container, false);
        LogUtil.e(TAG, "onCreateView()");
        initViews();
        return rootView;
    }
    public FragmentTransaction  getShoppingListRootFragmentTransaction() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        return transaction;

    }
    private void replaceFragment(boolean isNetworkConnected) {
        //FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        if(isNetworkConnected) {
            Fragment shoppingListPageFragment = new ShoppingListPageFragment();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack if needed
            transaction.replace(R.id.shoppinglist_fragment_root_id, shoppingListPageFragment);
            //Create new fragment and transacnt);
        } else {
            Fragment reloadFragment =  ReloadFragment.newInstance();
            transaction.replace(R.id.shoppinglist_fragment_root_id, reloadFragment);
        }
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
    private void checkNetwork() {
        if(NetWorkUtil.isNetworkConnected())
            replaceFragment(true);
        else
            replaceFragment(false);
    }
    @Override
    public void onVisible() {
        LogUtil.e(TAG, "onVisible() ");
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.shoppinglist_fragment_root_id);
        LogUtil.e(TAG, "fragment = "+fragment);
//        if(fragment instanceof ShoppingListRootFragment) {
//            LogUtil.e(TAG, "fragment instanceof ShoppingListRootFragment");
//
//        }
//        if(!(fragment instanceof ShoppingListPageFragment || fragment instanceof ReloadFragment)) {
//            LogUtil.e(TAG,"!(fragment instanceof ShoppingListPageFragment || fragment instanceof ReloadFragment)" );
//            checkNetwork();
//        } else if (fragment == null) {
//            LogUtil.e(TAG, "fragment == null");
//            checkNetwork();
//        }
        checkNetwork();
    }
    @Override
    public void onInvisible() {

    }
    private void initViews() {


    }
}
