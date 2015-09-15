package com.mooreliu.db.model;

import android.os.Parcel;

/**
 * Created by mooreliu on 2015/9/14.
 */
public class OrderModel extends BaseModel{

    private int orderUserId;
    private int orderProductId;
    private String orderState;
    private String orderExpressState;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest , int flag) {
        writeBase(dest);
        dest.writeInt(orderUserId);
        dest.writeInt(orderProductId);
        dest.writeString(orderState);
        dest.writeString(orderExpressState);
    }
    public int getOrderUserId() {
        return this.orderUserId;
    }
    public void setOrderUserId(int orderUserId) {
        this.orderUserId = orderUserId;
    }
    public int getOrderProductId() {
        return this.orderProductId;
    }
    public void setOrderProductId(int orderProductId) {
        this.orderProductId = orderProductId;
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
