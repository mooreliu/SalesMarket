package com.mooreliu.db.model;

import android.content.ContentResolver;
import android.net.Uri;

import com.mooreliu.util.Constants;

/**
 * Created by liuyi on 15/9/13.
 */
public class MerchandiseColumns extends BaseColumns {
    public static final String TABLE_NAME = "t_merchandise";
    public static final String MERCHANDISE_ID = "merchandise_id";
    public static final String MERCHANDISE_NAME = "merchandise_name";
    public static final String MERCHANDISE_DESCRIPTION = "merchandise_description";
    public static final String MERCHANDISE_PRICE = "merchandise_price";
    public static final String MERCHANDISE_IMAGE_URL = "merchandise_image_url"; /* public static final String AUTHORITY = "com.mooreliu.db.provider";*/
    public static final Uri BASE_URI = Uri.parse("content://" + Constants.AUTHORITY); /* public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, TABLE_NAME);*/
    public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY + "/" + TABLE_NAME);
    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME + "("
            + _ID + " integer primary key autoincrement , "
            + MERCHANDISE_ID + " integer , "
            + MERCHANDISE_NAME + " text not null , "
            + MERCHANDISE_PRICE + " text not null ,"
            + MERCHANDISE_DESCRIPTION + " text not null , "
            + MERCHANDISE_IMAGE_URL + " text not null ,"
            + CREATE_TIME + " text not null,"
            + UPDATE_TIME + " text not null,"
            + "unique ( "
            + MERCHANDISE_NAME
            + " ) on conflict ignore "
            + " )";

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.mooreliu.merchandise";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.mooreliu.merchandise";

}
