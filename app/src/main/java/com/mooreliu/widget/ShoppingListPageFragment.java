package com.mooreliu.widget;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooreliu.R;
import com.mooreliu.controller.OrderController;
import com.mooreliu.db.model.OrderModel;
import com.mooreliu.listener.OnSwitchFragmentListener;
import com.mooreliu.net.NetWorkUtil;
import com.mooreliu.util.LogUtil;

import java.util.List;

/**
 * Created by liuyi on 15/8/29.
 */
public class ShoppingListPageFragment extends BaseFragment {

    private static final String TAG = "ShoppingListPageFragment";
    private OrderController oc;
    private View mView;
<<<<<<< HEAD
    private OnSwitchFragmentListener listener;
    @Override
    public void onVisible() {
        LogUtil.e(TAG, "ShoppingListPageFragment onVisible");
        if(NetWorkUtil.isNetworkConnected()) {
            listener.switchFragment(1, true);
        } else {
            listener.switchFragment(1, false);
        }
    }
=======
    private OnSwitchFragmentListener mOnSwitchFragmentListener;
    protected boolean isVisible;
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            //onInvisible();
        }
    }
    protected void onVisible() {
        LogUtil.e(TAG, "onVisible()");
//        if(!NetWorkUtil.isNetworkConnected())
//            mOnSwitchFragmentListener.switchFragment(0,false);
    }
//    @Override
//    public void onVisible() {
//        LogUtil.e(TAG, "onVisible()");
//        if(!NetWorkUtil.isNetworkConnected())
//            mOnSwitchFragmentListener.switchFragment(0,false);
////        if(!NetWorkUtil.isNetworkConnected()) {
////            LogUtil.e(TAG,"ShoppingListPageFragment newwork no avail");
////            FragmentTransaction trans = getFragmentManager().beginTransaction();
////            //trans.replace(R.id.shoppinglist_fragment_root_id, ReloadFragment.newInstance());
////            trans.replace(R.id.shoppinglist_fragment_root_id, ReloadFragment.newInstance());
////
////            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
////            trans.addToBackStack(null);
////            trans.commit();
////        }
//
//
//    }
>>>>>>> acd3e5e17d54cb8195ef69a576fe23ed2cf4e75c

    public ShoppingListPageFragment(OnSwitchFragmentListener listener) {
        super();
        mOnSwitchFragmentListener = listener;
        LogUtil.e(TAG, "ShoppingListPageFragment listener 构造函数");
    }
    public ShoppingListPageFragment() {
        super();
        LogUtil.e(TAG, "ShoppingListPageFragment 构造函数");
    }

    public ShoppingListPageFragment(OnSwitchFragmentListener listener) {
        super();
        this.listener = listener;
        LogUtil.e(TAG, "ShoppingListPageFragment 构造函数");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_shoppinglist, container, false);
        findViews();
        initViews();
        setOnclick();
<<<<<<< HEAD

=======
>>>>>>> acd3e5e17d54cb8195ef69a576fe23ed2cf4e75c
        //init();
        return mView;
    }

    public static ShoppingListPageFragment newInstance() {
        ShoppingListPageFragment fragment = new ShoppingListPageFragment();
        return  fragment;
    }
//    public static ShoppingListPageFragment init(int position) {
//        ShoppingListPageFragment fragment = new ShoppingListPageFragment();
//        //Bundle args = new Bundle();
//        //args.putInt("args",position);
//        //fragment.setArguments(args);
//        return fragment;
////        oc = new OrderController();
////        List<OrderModel> orderList = oc.getOrdersByUserId(getActivity(), 1);
////        OrderModel model = orderList.get(0);
////        LogUtil.e(TAG, model.getOrderExpressState());
////        LogUtil.e(TAG, "orderList.size()"+orderList.size());
//    }

    private void findViews() {

    }

    private void initViews() {

    }

    private void setOnclick() {

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
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e(TAG, "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e(TAG,"onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e(TAG,"onDetach");
    }
}
