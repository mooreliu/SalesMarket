package com.mooreliu.db.model;

import android.content.ContentResolver;
import android.net.Uri;

import com.mooreliu.util.Constants;

/**
 * Created by liuyi on 15/9/13.
 */
public class ProductColumns extends BaseColumns {


    public static final String TABLE_NAME = "product";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_DESCRIPTION = "product_description";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_IMAGE_URL = "product_image_url";

    public static final Uri CONTENT_URI = Uri.parse("content://"+ Constants.AUTHORITY+"/"+TABLE_NAME);

    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME + "("
            + _ID + "integer primary key autoincrease , "
            + PRODUCT_NAME + "text not null "
            + PRODUCT_PRICE + "text not null"
            + PRODUCT_DESCRIPTION + "text not null "
            + PRODUCT_IMAGE_URL + "text not null ";

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.mooreliu.product";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.mooreliu.product";

}
