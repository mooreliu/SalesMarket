package com.mooreliu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mooreliu.db.model.ProductColumns;
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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProductColumns.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db ,int oldVersion , int newVersion) {
        LogUtil.e(TAG, "SQListe database version changed");
        db.execSQL("DROP TABLE IF EXISTS " + ProductColumns.TABLE_NAME);
        onCreate(db);
    }

}
