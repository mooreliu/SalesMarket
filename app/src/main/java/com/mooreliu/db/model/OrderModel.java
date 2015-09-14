package com.mooreliu.db.model;

import android.os.Parcel;

/**
 * Created by mooreliu on 2015/9/14.
 */
public class OrderModel extends BaseModel{

    private String orderProductId;
    private String orderState;
    private String orderExpressState;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest , int flag) {
        writeBase(dest);
        dest.writeString(orderProductId);
        dest.writeString(orderState);
        dest.writeString(orderExpressState);
    }
    public String getOrderProductId() {
        return this.orderProductId;
    }
    public void setOrderProductId(String orderProductId) {
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
