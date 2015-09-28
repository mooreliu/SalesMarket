package com.mooreliu.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by liuyi on 15/9/13.
 */
public interface IDataProvider { /** @param uri @param value @return */
  /**
   * @param uri
   * @param value
   */

  public abstract void insertItemByUri(Uri uri, ContentValues value);

  /**
   * @return
   */
  public abstract Cursor queryAllList(Uri uri, String[] columns, String selection, String[] selectionArgs, String orderBy);

  /**
   * @param uri @return
   */
  public abstract Cursor queryById(Uri uri);

  /**
   * @param uri
   * @return
   */
  public abstract int deleteItemByUri(Uri uri);

  /**
   * @param uri
   * @param selection
   * @param selectionArgs
   * @return
   */
  public abstract int deleteItemByCondition(Uri uri, String selection, String[] selectionArgs);

  /**
   * @param uri
   * @param value
   * @return
   */
  public abstract int updateByUri(Uri uri, ContentValues value);

  /**
   * @param uri
   * @param value
   * @param selection
   * @param selectionArgs
   * @return
   */
  public abstract int updateByCondition(Uri uri, ContentValues value, String selection, String[] selectionArgs);
}
