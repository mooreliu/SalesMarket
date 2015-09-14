package com.mooreliu.db.model;

import android.content.ContentResolver;
import android.net.Uri;

import com.mooreliu.util.Constants;

/**
 * Created by mooreliu on 2015/9/14.
 */
public class OrderColumns extends BaseColumns {

    private static final String TAG = "OrderColumns";

    public static final String ORDER_STATE = "order_state"; //订单状态
    //1 待付款  2 待发货 3 待收货 4 待评价
    public static final String ORDER_PRODUCT_ID = "order_product_id"; // 订单商品ID
    public static final String ORDER_EXPRESS_STATE = "order_express_state"; // 订单快递状态

    public static final String TABLE_NAME = "order";
    public static final Uri BASE_URI = Uri.parse("content://"+ Constants.AUTHORITY);
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, TABLE_NAME);

    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME + "( "
            + _ID + "  integer primary key autoincrement , "
            + ORDER_PRODUCT_ID + " integer , "
            + ORDER_EXPRESS_STATE + " text not null , "
            + ORDER_STATE + " text not null , "
            + CREATE_TIME + " text not null, "
            + UPDATE_TIME + " text not null "
            +" ) ";

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.mooreliu.order";

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.mooreliu.order";



}
