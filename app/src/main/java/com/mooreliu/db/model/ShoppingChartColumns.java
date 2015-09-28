package com.mooreliu.db.model;

import android.content.ContentResolver;
import android.net.Uri;

import com.mooreliu.util.Constants;

/**
 * Created by mooreliu on 2015/9/14.
 */
public class ShoppingChartColumns extends BaseColumns {
    public static final String SHOPPING_CHART_USER_ID = "shopping_chart_user_id";/*该订单所*/
    public static final String SHOPPING_CHART_MERCHANDISE_ID = "shopping_chart_merchandise_id"; /*订单状态*/
    public static final String SHOPPING_CHART_MERCHANDISE_NUMBER = "shopping_chart_merchandise_number"; /* 订单商品ID*/
    public static final String TABLE_NAME = "t_shopping_chart";
    public static final Uri BASE_URI = Uri.parse("content://" + Constants.AUTHORITY);
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, TABLE_NAME);
    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME + "("
            + _ID + " integer primary key autoincrement,"
            + SHOPPING_CHART_USER_ID + " integer,"
            + SHOPPING_CHART_MERCHANDISE_ID + " integer,"
            + SHOPPING_CHART_MERCHANDISE_NUMBER + " integer,"
            + CREATE_TIME + " text not null,"
            + UPDATE_TIME + " text not null"
            + ")";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.mooreliu.shoppingchart";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.mooreliu.shoppingchart";
    private static final String TAG = "ShoppingChartColumns";


}
