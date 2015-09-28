package com.mooreliu.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.facebook.common.internal.Preconditions;
import com.mooreliu.db.model.BaseColumns;
import com.mooreliu.db.model.MerchandiseColumns;
import com.mooreliu.db.model.OrderColumns;
import com.mooreliu.db.model.ShoppingChartColumns;
import com.mooreliu.db.model.UserColumns;
import com.mooreliu.util.Constants;
import com.mooreliu.util.LogUtil;

/**
 * Created by liuyi on 15/9/13.
 */
public class DataProvider extends ContentProvider implements IDataProvider {
    public static final int PRODUCT = 1;
    public static final int PRODUCT_ID = 2;
    public static final int USER = 3;
    public static final int USER_ID = 4;
    public static final int ORDER = 5;
    public static final int ORDER_ID = 6;
    public static final int SHOPPING_CHART = 7;
    public static final int SHOPPING_CHART_ID = 8;
    private static final String TAG = "DataProvider";
    private static UriMatcher sUriMatcher;

    static {
        LogUtil.e(TAG, "Sales DataProvider static data initilize");
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Constants.AUTHORITY, MerchandiseColumns.TABLE_NAME, PRODUCT);
        sUriMatcher.addURI(Constants.AUTHORITY, MerchandiseColumns.TABLE_NAME + "id/*", PRODUCT_ID);
        sUriMatcher.addURI(Constants.AUTHORITY, OrderColumns.TABLE_NAME, USER);
        sUriMatcher.addURI(Constants.AUTHORITY, OrderColumns.TABLE_NAME + "id/*", USER_ID);
        sUriMatcher.addURI(Constants.AUTHORITY, UserColumns.TABLE_NAME, ORDER);
        sUriMatcher.addURI(Constants.AUTHORITY, UserColumns.TABLE_NAME + "id/*", ORDER_ID);
        sUriMatcher.addURI(Constants.AUTHORITY, ShoppingChartColumns.TABLE_NAME, SHOPPING_CHART);
        sUriMatcher.addURI(Constants.AUTHORITY, ShoppingChartColumns.TABLE_NAME + "id/*", SHOPPING_CHART_ID);
    }

    private DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        LogUtil.e(TAG, "DataProvider onCreate()");
        dbHelper = new DbHelper(getContext());
        dbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case PRODUCT:
                return MerchandiseColumns.CONTENT_TYPE;
            case PRODUCT_ID:
                return MerchandiseColumns.CONTENT_ITEM_TYPE;
            case USER:
                return UserColumns.CONTENT_TYPE;
            case USER_ID:
                return UserColumns.CONTENT_ITEM_TYPE;
            case ORDER:
                return OrderColumns.CONTENT_TYPE;
            case ORDER_ID:
                return OrderColumns.CONTENT_ITEM_TYPE;
            case SHOPPING_CHART:
                return ShoppingChartColumns.CONTENT_TYPE;
            case SHOPPING_CHART_ID:
                return ShoppingChartColumns.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("illegal arguments query" + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) { /*LogUtil.e(TAG, uri+"");*/
        switch (sUriMatcher.match(uri)) {
            case ORDER_ID:
            case USER_ID:
            case PRODUCT_ID:
            case SHOPPING_CHART_ID:
                return queryById(uri);
            case USER:
            case ORDER:
            case PRODUCT:
            case SHOPPING_CHART:
                return queryAllList(uri, projection, selection, selectionArgs, sortOrder);
            default:
                throw new IllegalArgumentException("illegal arguments query" + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues value, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case USER:
            case ORDER:
            case PRODUCT:
            case SHOPPING_CHART:
                return updateByCondition(uri, value, selection, selectionArgs);
            case ORDER_ID:
            case USER_ID:
            case PRODUCT_ID:
            case SHOPPING_CHART_ID:
                return updateByUri(uri, value);
            default:
                throw new IllegalArgumentException("illegal arguments query" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues value) {
        switch (sUriMatcher.match(uri)) {
            case USER:
            case ORDER:
            case PRODUCT:
            case SHOPPING_CHART:
                insertItemByUri(uri, value);
                return uri;
            case ORDER_ID:
            case USER_ID:
            case PRODUCT_ID:
            case SHOPPING_CHART_ID:
            default:
                throw new IllegalArgumentException("illegal arguments query" + uri);
        }
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        Preconditions.checkNotNull(uri);
        int numberInserted = 0;
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ContentValues value : values) {
                long id = db.insert(tableName, null, value);
                if (id > 0)
                    numberInserted++;
            }
            db.setTransactionSuccessful();
            ;
            getContext().getContentResolver().notifyChange(uri, null);
        } finally {
            db.endTransaction();
        }
        return numberInserted;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case USER:
            case ORDER:
            case PRODUCT:
            case SHOPPING_CHART:
                return deleteItemByCondition(uri, selection, selectionArgs);
            case ORDER_ID:
            case USER_ID:
            case PRODUCT_ID:
            case SHOPPING_CHART_ID:
                return deleteItemByUri(uri);
            default:
                throw new IllegalArgumentException("illegal arguments query" + uri);
        }
    }

    public Cursor queryAllList(Uri uri, String[] columns, String selection, String[] selectionArgs, String orderBy) {
        String tableName = uri.getPathSegments().get(0);
//        LogUtil.e(TAG,"queryAllList tableName="+tableName);
        if (dbHelper != null) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(tableName, null, selection, selectionArgs, null, null, orderBy);
            return queryWithNotification(cursor, uri);
        } else {
            LogUtil.e(TAG, "queryAllList dbHelper is null");
            return null;
        }
    }

    public Cursor queryById(Uri uri) {
        String tableName = uri.getPathSegments().get(0);
        String id = uri.getPathSegments().get(2);
        String where = BaseColumns._ID + " =?";
        String[] whereArgs = new String[]{id};
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(tableName, null, where, whereArgs, null, null, null);
        return queryWithNotification(cursor, uri);
    }

    public void insertItemByUri(Uri uri, ContentValues value) {
        if (value == null) {
            LogUtil.e(TAG, "insertItemByUri value is null");
            return;
        }
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase sqLiteDatabase;
        if (dbHelper != null) {
            sqLiteDatabase = dbHelper.getWritableDatabase();
            long rowId = sqLiteDatabase.insert(tableName, null, value);

            getContext().getContentResolver().notifyChange(uri, null);
            if (rowId > 0) {
//                LogUtil.e(TAG, "insertItemByUri success new item rows id =" + rowId);
            } else {
//                LogUtil.e(TAG, "insert rowId<0  rowId =" + rowId);
            }
        } else {
            LogUtil.e(TAG, "dbHelper is null");
        }
    }


    public int deleteItemByUri(Uri uri) {
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String id = uri.getPathSegments().get(2);
        String where = BaseColumns._ID + "=?";
        String[] whereArgs = new String[]{id};
        int rowId = sqLiteDatabase.delete(tableName, where, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowId;
    }


    public int deleteItemByCondition(Uri uri, String selection, String[] selectionArgs) {
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        int rowId = sqLiteDatabase.delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowId;
    }


    public int updateByUri(Uri uri, ContentValues value) {
        if (value == null) {
            LogUtil.e(TAG, "updateByUri value is null");
        }
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String id = uri.getPathSegments().get(2);
        String where = BaseColumns._ID + "=?";
        String[] whereArgs = new String[]{id};
        int rowId = sqLiteDatabase.update(tableName, value, where, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowId;
    }


    public int updateByCondition(Uri uri, ContentValues value, String selection, String[] selectionArgs) {
        if (value == null) {
            LogUtil.e(TAG, "updateByCondition value is null");
        }
        String tableName = uri.getPathSegments().get(0);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int rowId = sqLiteDatabase.update(tableName, value, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowId;
    }

    public Cursor queryWithNotification(Cursor cursor, Uri uri) {
        if (cursor == null) {
            LogUtil.e(TAG, "cursor is null");
        } else {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }


}
