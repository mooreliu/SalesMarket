package com.mooreliu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.facebook.common.internal.Preconditions;
import com.mooreliu.db.model.OrderColumns;
import com.mooreliu.db.model.ProductColumns;
import com.mooreliu.db.model.UserColumns;
import com.mooreliu.util.DateUtil;
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
        db.execSQL("DROP TABLE IF EXISTS " + ProductColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OrderColumns.TABLE_NAME);
        db.execSQL(ProductColumns.CREATE_TABLE);
        db.execSQL(UserColumns.CREATE_TABLE);
        db.execSQL(OrderColumns.CREATE_TABLE);
        initProductTable();
        initUserTable(db);
        initOrderTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db ,int oldVersion , int newVersion) {
        LogUtil.e(TAG, "SQLite database version changed");
        db.execSQL("DROP TABLE IF EXISTS " + ProductColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OrderColumns.TABLE_NAME);
        onCreate(db);
    }

    public void addUser(SQLiteDatabase db, ContentValues value) {
        Preconditions.checkNotNull(db);
//        LogUtil.e(TAG, value.get(UserColumns.)+"");
        db.insert(UserColumns.TABLE_NAME, null, value);
    }
    public void addOrder(SQLiteDatabase db, ContentValues value) {
        Preconditions.checkNotNull(db);
        db.insert(OrderColumns.TABLE_NAME, null, value);
    }
    public void addProduct(SQLiteDatabase db, ContentValues value) {
        Preconditions.checkNotNull(db);
        db.insert(ProductColumns.TABLE_NAME, null, value);
    }
    public  void initUserTable(SQLiteDatabase db) {
        Preconditions.checkNotNull(db);
        ContentValues cv1 = new ContentValues();
        String createTime = DateUtil.getCurrentTime();
        String updateTime = createTime;
        cv1.put(ProductColumns.PRODUCT_ID, 1);
        cv1.put(ProductColumns.PRODUCT_NAME, "Oranges");
        cv1.put(ProductColumns.PRODUCT_PRICE, "1.1");
        cv1.put(ProductColumns.PRODUCT_DESCRIPTION, "Orange Desciption");
        cv1.put(ProductColumns.PRODUCT_IMAGE_URL, "1");
        cv1.put(ProductColumns.CREATE_TIME, createTime);
        cv1.put(ProductColumns.UPDATE_TIME, updateTime);
        ContentValues cv2 = new ContentValues();
        cv2.put(ProductColumns.PRODUCT_ID, 2);
        cv2.put(ProductColumns.PRODUCT_NAME, "Apples");
        cv2.put(ProductColumns.PRODUCT_PRICE, "1.2");
        cv2.put(ProductColumns.PRODUCT_DESCRIPTION, "Apple Desciption");
        cv2.put(ProductColumns.PRODUCT_IMAGE_URL, "2");
        cv2.put(ProductColumns.CREATE_TIME, createTime);
        cv2.put(ProductColumns.UPDATE_TIME, updateTime);
        ContentValues cv3 = new ContentValues();
        cv3.put(ProductColumns.PRODUCT_ID,3);
        cv3.put(ProductColumns.PRODUCT_NAME, "Pears");
        cv3.put(ProductColumns.PRODUCT_PRICE, "1.3");
        cv3.put(ProductColumns.PRODUCT_DESCRIPTION, "Pear Desciption");
        cv3.put(ProductColumns.PRODUCT_IMAGE_URL, "3");
        cv3.put(ProductColumns.CREATE_TIME, createTime);
        cv3.put(ProductColumns.UPDATE_TIME, updateTime);
        addProduct(db, cv1);
        addProduct(db, cv2);
        addProduct(db,cv3);
    }

    public static final void initProductTable() {

    }

    public static final void initOrderTable() {

    }
}
