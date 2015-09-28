package com.mooreliu.controller;

import android.content.Context;
import android.database.Cursor;

import com.mooreliu.db.model.OrderColumns;
import com.mooreliu.db.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mooreliu on 2015/9/15.
 */
public class OrderController extends BaseController {
  public List<OrderModel> getOrdersByUserId(Context context, int userId) {
    if (context == null || userId < 0) {
      return null;
    }
    List<OrderModel> list = new ArrayList<>();
    Cursor cursor = context.getContentResolver().query(
            OrderColumns.CONTENT_URI, null, OrderColumns.ORDER_USER_ID + "=?", new String[]{userId + ""}, null);
    if (cursor != null) {
      while (cursor.moveToNext()) {
        OrderModel orderModel = new OrderModel().getModel(cursor);
        list.add(orderModel);
      }
    }
    return list;
  }
}
