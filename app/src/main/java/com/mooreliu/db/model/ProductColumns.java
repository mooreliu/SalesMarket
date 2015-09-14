package com.mooreliu.db.model;

import android.content.ContentResolver;
import android.net.Uri;

import com.mooreliu.util.Constants;

/**
 * Created by liuyi on 15/9/13.
 */
public class ProductColumns extends BaseColumns {

    public static final String TABLE_NAME = "t_product";
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_DESCRIPTION = "product_description";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_IMAGE_URL = "product_image_url";

   // public static final String AUTHORITY = "com.mooreliu.db.provider";
    public static final Uri BASE_URI = Uri.parse("content://" + Constants.AUTHORITY);
   // public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, TABLE_NAME);
    public static final Uri CONTENT_URI = Uri.parse("content://"+ Constants.AUTHORITY+"/"+TABLE_NAME);

    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME + "("
            + _ID + " integer primary key autoincrement , "
            + PRODUCT_ID + " integer , "
            + PRODUCT_NAME + " text not null , "
            + PRODUCT_PRICE + " text not null ,"
            + PRODUCT_DESCRIPTION + " text not null , "
            + PRODUCT_IMAGE_URL + " text not null ,"
            + CREATE_TIME + " text not null,"
            + UPDATE_TIME + " text not null,"
            + "unique ( "
            + PRODUCT_NAME
            + " ) on conflict ignore "
            +" )";

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.mooreliu.product";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.mooreliu.product";

}
