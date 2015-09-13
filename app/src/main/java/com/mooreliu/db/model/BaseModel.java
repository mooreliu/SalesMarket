package com.mooreliu.db.model;

import android.os.Parcel;

/**
 * Created by liuyi on 15/9/13.
 */
public abstract class BaseModel implements  IModel {

    protected String id;
    protected String createTime;
    protected String updateTime;

    public void readBase(Parcel in) {
        id = in.readString();
        createTime = in.readString();
        updateTime = in.readString();
    }

    public void writeBase(Parcel dest) {
        dest.writeString(id);
        dest.writeString(createTime);
        dest.writeString(updateTime);
    }


}
