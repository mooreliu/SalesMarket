package com.mooreliu.controller;

import android.content.Context;

import com.mooreliu.db.model.OrderModel;

import java.util.List;

/**
 * Created by mooreliu on 2015/9/15.
 */
public interface IOrderController {
    public List<OrderModel> getOrdersByUserId(Context context, int userId);
}
