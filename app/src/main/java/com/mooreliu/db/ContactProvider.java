package com.mooreliu.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.mooreliu.db.model.ProductColumns;
import com.mooreliu.util.Constants;
import com.mooreliu.util.LogUtil;

/**
 * Created by kohoh on 14-11-3.
 */
public class ContactProvider extends ContentProvider {
    private String TAG ="ContactProvider";
    private SQLiteOpenHelper sqLiteOpenHelper;
    private ContentResolver contentResolver;
    private UriMatcher uriMatcher;

    final private int DIR = 0;
    final private int ITEM = 1;

    @Override
    public boolean onCreate() {
        LogUtil.e(TAG,"ContactProvider onCreate()");
        contentResolver = getContext().getContentResolver();
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Constants.AUTHORITY, "contact", DIR);
        uriMatcher.addURI(Constants.AUTHORITY, "contact/#", ITEM);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        if (uriMatcher.match(uri) == ITEM) {
            return null;
        }


        return null;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ITEM:
                return "vnd.android.cursor.item/vnd.con.kohoh.cursorsyncdemo";
            case DIR:
                return "vnd.android.cursor.dir/vnd.con.kohoh.cursorsyncdemo";
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {


        return uri.withAppendedPath(ProductColumns.BASE_URI, String.valueOf(1));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) == ITEM) {
            return 0;
        }

        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();
        int result = database.delete(ProductColumns.BASE_URI+"", selection, selectionArgs);

        if (result > 0) {
            contentResolver.notifyChange(ProductColumns.BASE_URI, null);
        }

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) == ITEM) {
            return 0;
        }

        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();
        int result = database.update(ProductColumns.BASE_URI+"", values, selection, selectionArgs);

        if (result > 0) {
            contentResolver.notifyChange(ProductColumns.BASE_URI, null);
        }

        return result;
    }
}
