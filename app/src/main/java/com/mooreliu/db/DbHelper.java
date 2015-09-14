package com.mooreliu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mooreliu.db.model.OrderColumns;
import com.mooreliu.db.model.ProductColumns;
import com.mooreliu.db.model.UserColumns;
import com.mooreliu.util.LogUtil;

/**
 * Created by liuyi on 15/9/13.
 */
public class DbHelper extends SQLiteOpenHelper{

    public static final String TAG = "DbHelper";

    public static final String DATABASE_NAME = "mooreliu.db";
    public static final int DATABASE_VERSION = 1;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME , null , DATABASE_VERSION);
        LogUtil.e(TAG, "DbHelper Construction");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.e(TAG, "onCreate");
//        db.execSQL("DROP TABLE IF EXISTS " + ProductColumns.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + UserColumns.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + OrderColumns.TABLE_NAME);
        db.execSQL(ProductColumns.CREATE_TABLE);
        db.execSQL(UserColumns.CREATE_TABLE);
        db.execSQL(OrderColumns.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db ,int oldVersion , int newVersion) {
        LogUtil.e(TAG, "SQLite database version changed");
        db.execSQL("DROP TABLE IF EXISTS " + ProductColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OrderColumns.TABLE_NAME);
        onCreate(db);
    }

}
