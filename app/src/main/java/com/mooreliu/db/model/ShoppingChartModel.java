package com.mooreliu.db.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;

import com.mooreliu.util.DataProviderHelper;

/**
 * Created by mooreliu on 2015/9/14.
 */
public class ShoppingChartModel extends BaseModel {
  private int shoppingChartUserId;
  private int shoppingChartMerchandiseId;
  private int shoppingChartMerchandiseNumber;

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flag) {
    writeBase(dest);
    dest.writeInt(shoppingChartUserId);
    dest.writeInt(shoppingChartMerchandiseId);
    dest.writeInt(shoppingChartMerchandiseNumber);
  }

  @Override
  public String getTable() {
    return ShoppingChartColumns.TABLE_NAME;
  }

  @Override
  public Uri getContentUri() {
    return ShoppingChartColumns.CONTENT_URI;
  }

  @Override
  public ShoppingChartModel getModel(Cursor cursor) {
    if (cursor == null) {
      return null;
    }
    ShoppingChartModel model = new ShoppingChartModel();
    model.id = DataProviderHelper.parseInt(cursor, ShoppingChartColumns._ID);
    model.shoppingChartUserId = DataProviderHelper.parseInt(cursor, ShoppingChartColumns.SHOPPING_CHART_USER_ID);
    model.shoppingChartMerchandiseId = DataProviderHelper.parseInt(cursor, ShoppingChartColumns.SHOPPING_CHART_MERCHANDISE_ID);
    model.shoppingChartMerchandiseNumber = DataProviderHelper.parseInt(cursor, ShoppingChartColumns.SHOPPING_CHART_MERCHANDISE_NUMBER);
    return model;
  }

  @Override
  public ContentValues values() {
    ContentValues values = ModelBase();
    values.put(ShoppingChartColumns.SHOPPING_CHART_USER_ID, shoppingChartUserId);
    values.put(ShoppingChartColumns.SHOPPING_CHART_MERCHANDISE_ID, shoppingChartMerchandiseId);
    values.put(ShoppingChartColumns.SHOPPING_CHART_MERCHANDISE_NUMBER, shoppingChartMerchandiseNumber);
    return values;

  }

  public int getShoppingChartUserId() {
    return this.shoppingChartUserId;
  }

  public void setShoppingChartUserId(int shoppingChartUserId) {
    this.shoppingChartUserId = shoppingChartUserId;
  }

  public int getshoppingChartMerchandiseId() {
    return this.shoppingChartMerchandiseId;
  }

  public void setshoppingChartMerchandiseId(int shoppingChartMerchandiseId) {
    this.shoppingChartMerchandiseId = shoppingChartMerchandiseId;
  }

  public int getShoppingChartMerchandiseNumber() {
    return this.shoppingChartMerchandiseNumber;
  }

  public void setShoppingChartMerchandiseNumber(int shoppingChartMerchandiseNumber) {
    this.shoppingChartMerchandiseNumber = shoppingChartMerchandiseNumber;
  }

}
