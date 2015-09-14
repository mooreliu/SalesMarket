package com.mooreliu.db.model;

import android.content.ContentResolver;
import android.net.Uri;

import com.mooreliu.util.Constants;

/**
 * Created by mooreliu on 2015/9/14.
 */
public class UserColumns extends  BaseColumns{
    public static final String TABLE_NAME = "t_user";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_PHONE_NUMBER = "user_phone_number";
    public static final String USER_MAILING_ADDRESS = "user_mailing_address";


    public static final Uri BASE_URI = Uri.parse("content://" + Constants.AUTHORITY);
    public static final Uri CONTENT_URI = Uri.parse("content://"+ Constants.AUTHORITY+"/"+TABLE_NAME);

    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME + "("
            + _ID + " integer primary key autoincrement , "
            + USER_NAME + " text not null , "
            + USER_PASSWORD + " text not null ,"
            + USER_PHONE_NUMBER + " text not null , "
            + USER_MAILING_ADDRESS + " text not null ,"
            + CREATE_TIME + " text not null,"
            + UPDATE_TIME + " text not null,"
            + "unique ( "
            + USER_NAME
            + " ) on conflict ignore "
            +" )";

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.mooreliu.product";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.mooreliu.product";
}
