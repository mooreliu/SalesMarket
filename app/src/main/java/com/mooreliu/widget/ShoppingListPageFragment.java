package com.mooreliu.widget;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooreliu.R;
import com.mooreliu.controller.OrderController;
import com.mooreliu.db.model.OrderModel;
import com.mooreliu.util.LogUtil;

import java.util.List;

/**
 * Created by liuyi on 15/8/29.
 */
public class ShoppingListPageFragment extends Fragment {

    private static final String TAG = "ShoppingListPageFragment";
    private OrderController oc;
    private View mView;

    public ShoppingListPageFragment() {
        super();
        LogUtil.e(TAG, "ShoppingListPageFragment 构造函数");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_shoppinglist, container, false);
        findViews();
        initViews();
        setOnclick();


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
