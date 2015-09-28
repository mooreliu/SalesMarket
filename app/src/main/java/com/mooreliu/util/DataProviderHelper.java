package com.mooreliu.util;

import android.content.ContentValues;
import android.database.Cursor;

import com.mooreliu.db.model.BaseModel;

import java.util.List;

/**
 * Created by mooreliu on 2015/9/15.
 */
public class DataProviderHelper {
  private static final String TAG = "DataProviderHelper";

  public static String parseString(Cursor cusor, String columnName) {
    return cusor.getString(cusor.getColumnIndexOrThrow(columnName));
  }

  public static int parseInt(Cursor cursor, String colunmName) {
    return cursor.getInt(cursor.getColumnIndexOrThrow(colunmName));
  }

  public static ContentValues[] getContentValuesByModels(List<? extends BaseModel> models) {
    int size = models.size();
    if (models == null || size == 0) {
      return null;
    }
    ContentValues[] values = new ContentValues[size];
    for (int i = 0; i < models.size(); i++) {
      values[i] = models.get(i).values();
    }
    return values;
  }
}
