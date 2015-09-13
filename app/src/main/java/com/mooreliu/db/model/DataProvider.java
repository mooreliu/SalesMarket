package com.mooreliu.db.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.mooreliu.db.DbHelper;
import com.mooreliu.db.IDataProvider;
import com.mooreliu.util.Constants;
import com.mooreliu.util.LogUtil;

/**
 * Created by liuyi on 15/9/13.
 */
public class DataProvider extends ContentProvider implements IDataProvider{

    private static final String TAG = "DataProvider";
    private DbHelper dbHelper;

    private static final int PRODUCT = 1;
    private static final int PRODUCT_ID = 2;
    private static UriMatcher sUriMatcher;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Constants.AUTHORITY, ProductColumns.TABLE_NAME , PRODUCT);
        sUriMatcher.addURI(Constants.AUTHORITY, ProductColumns.TABLE_NAME+"id/*" , PRODUCT_ID);
    }
    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case PRODUCT:
                return ProductColumns.CONTENT_TYPE;
            case PRODUCT_ID:
                return ProductColumns.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("illegal arguments query"+uri);
        }
    }

    @Override
    public Cursor query(Uri uri ,String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case PRODUCT:
                return  queryAllList(uri, projection, selection, selectionArgs, sortOrder);
            case PRODUCT_ID:
                return queryById(uri);
            default:
                throw new IllegalArgumentException("illegal arguments query"+uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues value , String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case PRODUCT:
                return updateByCondition(uri, value, selection, selectionArgs);
            case PRODUCT_ID:
                return updateByUri(uri, value);
            default:
                throw new IllegalArgumentException("illegal arguments query"+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues value) {
        switch (sUriMatcher.match(uri)) {
            case PRODUCT:
                insertItemByUri(uri, value);
                return  uri;
            case PRODUCT_ID:
            default:
                throw new IllegalArgumentException("illegal arguments query"+uri);
        }

    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case PRODUCT:
                return deleteItemByCondition(uri, selection, selectionArgs);
            case PRODUCT_ID:
                return  deleteItemByUri(uri);
            default:
                throw new IllegalArgumentException("illegal arguments query"+uri);
        }
    }
    public Cursor  queryAllList(Uri uri, String[] columns, String selection, String[] selectionArgs, String orderBy) {
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(tableName, null ,selection, selectionArgs, null, null , orderBy);
        return queryWithNotification(cursor, uri);
    }


    public Cursor queryById(Uri uri) {
        String tableName = uri.getPathSegments().get(0);
        String id = uri.getPathSegments().get(2);
        String where = BaseColumns._ID + " =?";
        String[] whereArgs = new String[] {id};
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(tableName, null, where, whereArgs, null, null, null);
        return queryWithNotification(cursor, uri);

    }

    public void insertItemByUri(Uri uri ,ContentValues value) {
        if(value == null) {
            LogUtil.e(TAG ,"insertItemByUri value is null");
            return;
        }
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        long rowId = sqLiteDatabase.insert(tableName, null, value);
        getContext().getContentResolver().notifyChange(uri, null);
        if(rowId > 0) {
            LogUtil.e(TAG, "insertItemByUri success new item rows id =" + rowId);
        }
    }


    public int deleteItemByUri(Uri uri){
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String id = uri.getPathSegments().get(2);
        String where = BaseColumns._ID+"=?";
        String[] whereArgs = new String[]{id};
        int rowId = sqLiteDatabase.delete(tableName, where, whereArgs);
        getContext().getContentResolver().notifyChange(uri , null);
        return  rowId;
    }


    public  int deleteItemByCondition(Uri uri,String selection ,String[] selectionArgs) {
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        int rowId =  sqLiteDatabase.delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri , null);
        return  rowId;
    }


    public  int updateByUri(Uri uri , ContentValues value) {
        if(value == null) {
            LogUtil.e(TAG, "updateByUri value is null");
        }
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String id = uri.getPathSegments().get(2);
        String where = BaseColumns._ID+"=?";
        String[] whereArgs = new String[]{id};
        int rowId = sqLiteDatabase.update(tableName, value, where, whereArgs);
        getContext().getContentResolver().notifyChange(uri , null);
        return  rowId;
    }


    public  int updateByCondition(Uri uri , ContentValues value , String selection , String[] selectionArgs) {
        if(value == null) {
            LogUtil.e(TAG, "updateByCondition value is null");
        }
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int rowId = sqLiteDatabase.update(tableName, value, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri , null);
        return  rowId;
    }

    public Cursor queryWithNotification(Cursor cursor, Uri uri) {
        if (cursor == null) {
            LogUtil.e(TAG, "cursor is null");
        } else {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return  cursor;
    }


}
