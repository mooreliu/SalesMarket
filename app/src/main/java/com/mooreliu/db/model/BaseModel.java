package com.mooreliu.db.model;

import android.content.ContentValues;
import android.os.Parcel;

/**
 * Created by liuyi on 15/9/13.
 */
public abstract class BaseModel implements IModel {
  protected int id;
  protected String createTime;
  protected String updateTime;

  public ContentValues ModelBase() {
    ContentValues cv = new ContentValues(); /*cv.put(BaseColumns._ID, id);*/
    cv.put(BaseColumns.CREATE_TIME, createTime);
    cv.put(BaseColumns.UPDATE_TIME, updateTime);
    return cv;
  }

  public void readBase(Parcel in) {
    id = in.readInt();
    createTime = in.readString();
    updateTime = in.readString();
  }

  public void writeBase(Parcel dest) {
    dest.writeInt(id);
    dest.writeString(createTime);
    dest.writeString(updateTime);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getUpdateTime() {
    return this.updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }
}
