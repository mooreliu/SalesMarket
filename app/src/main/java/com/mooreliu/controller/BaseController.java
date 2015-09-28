package com.mooreliu.controller;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.mooreliu.db.model.BaseModel;
import com.mooreliu.util.DataProviderHelper;
import com.mooreliu.util.LogUtil;

import java.util.List;

/**
 * Created by mooreliu on 2015/9/15.
 */
public class BaseController implements IBaseController {
  private static final String TAG = "BaseController";

  @Override
  public int insert(Context context, List<? extends BaseModel> model) {
    if (context == null && model.size() == 0) return 0;
    ContentValues values[] = DataProviderHelper.getContentValuesByModels(model);
    try {
      Uri uri = model.get(0).getContentUri();
      return context.getContentResolver().bulkInsert(uri, values);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public Uri insert(Context context, BaseModel model) {
    if (context == null || model == null) {
      LogUtil.e(TAG, "context == null || model == null");
      return null;
    }
    Uri uri = model.getContentUri();
    ContentValues cv = model.values();
    LogUtil.e(TAG, uri + "");
    try {
      return context.getContentResolver().insert(uri, cv);
    } catch (Exception e) {
      e.printStackTrace();
      LogUtil.e(TAG, e.getCause() + e.getMessage());
    }
    return null;

  }

  @Override
  public <T extends BaseModel> T query(Context context, int id, Class<T> tClass) {
    return null;
  }

  @Override
  public boolean deleteById(Context context, int id, Class<? extends BaseModel> model) {
    return true;
  }

  @Override
  public boolean updateById(Context context, int id, BaseModel model) {
    return true;
  }


}
