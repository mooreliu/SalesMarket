package com.mooreliu.widget;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_shoppinglist, container, false);
        findViews();
        initViews();
        setOnclick();
        //init();
        return mView;
    }

    private void init() {
        oc = new OrderController();
        List<OrderModel> orderList = oc.getOrdersByUserId(getActivity(), 1);
        OrderModel model = orderList.get(0);
        LogUtil.e(TAG, model.getOrderExpressState());
        LogUtil.e(TAG, "orderList.size()"+orderList.size());
    }

    private void findViews() {

    }

    private void initViews() {

    }

    private void setOnclick() {

    }
}
