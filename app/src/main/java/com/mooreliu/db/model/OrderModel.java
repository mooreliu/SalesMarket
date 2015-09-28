package com.mooreliu.db.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;

import com.mooreliu.util.DataProviderHelper;

/**
 * Created by mooreliu on 2015/9/14.
 */
public class OrderModel extends BaseModel {
    private int orderUserId;
    private int orderMerchandiseId;
    private String orderState;
    private String orderExpressState;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        writeBase(dest);
        dest.writeInt(orderUserId);
        dest.writeInt(orderMerchandiseId);
        dest.writeString(orderState);
        dest.writeString(orderExpressState);
    }

    @Override
    public String getTable() {
        return OrderColumns.TABLE_NAME;
    }

    @Override
    public Uri getContentUri() {
        return OrderColumns.CONTENT_URI;
    }

    @Override
    public OrderModel getModel(Cursor cursor) {
        if (cursor == null) return null;
        OrderModel model = new OrderModel();
        model.id = DataProviderHelper.parseInt(cursor, OrderColumns._ID);
        model.orderUserId = DataProviderHelper.parseInt(cursor, OrderColumns.ORDER_USER_ID);
        model.orderMerchandiseId = DataProviderHelper.parseInt(cursor, OrderColumns.ORDER_MERCHANDISE_ID);
        model.orderExpressState = DataProviderHelper.parseString(cursor, OrderColumns.ORDER_EXPRESS_STATE);
        model.orderState = DataProviderHelper.parseString(cursor, OrderColumns.ORDER_STATE);
        return model;
    }

    @Override
    public ContentValues values() {
        ContentValues values = ModelBase();
        values.put(OrderColumns.ORDER_USER_ID, orderUserId);
        values.put(OrderColumns.ORDER_MERCHANDISE_ID, orderMerchandiseId);
        values.put(OrderColumns.ORDER_EXPRESS_STATE, orderExpressState);
        values.put(OrderColumns.ORDER_STATE, orderState);
        return values;

    }

    public int getOrderUserId() {
        return this.orderUserId;
    }

    public void setOrderUserId(int orderUserId) {
        this.orderUserId = orderUserId;
    }

    public int getOrderMerchandiseId() {
        return this.orderMerchandiseId;
    }

    public void setOrderMerchandiseId(int orderMerchandiseId) {
        this.orderMerchandiseId = orderMerchandiseId;
    }

    public String getOrderState() {
        return this.orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderExpressState() {
        return this.orderExpressState;
    }

    public void setOrderExpressState(String orderExpressState) {
        this.orderExpressState = orderExpressState;
    }

}
