package com.mooreliu.db.model;

import android.os.Parcel;

import com.mooreliu.util.LogUtil;

/**
 * Created by liuyi on 15/9/13.
 */
public class MerchandiseModel extends BaseModel{

    private static final String TAG = "MerchandiseModel";
    private int merchandiseId;
    private String merchandiseName;
    private String merchandisePrice;
    private String merchandiseDescription;
    private String merchandiseImageUrl;

    public MerchandiseModel(String merchandiseImageUrl) {
        this.merchandiseImageUrl = merchandiseImageUrl;
        //LogUtil.e(TAG, merchandiseImageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest , int flag) {
        writeBase(dest);
        dest.writeInt(merchandiseId);
        dest.writeString(merchandiseName);
        dest.writeString(merchandisePrice);
        dest.writeString(merchandiseImageUrl);
        dest.writeString(merchandiseDescription);

    }

    public int getmerchandiseId() {
        return this.merchandiseId;
    }
    public void setmerchandiseId(int merchandiseId) {
        this.merchandiseId = merchandiseId;
    }
    public String getmerchandiseName() {
        return this.merchandiseName;
    }
    public void setmerchandiseName(String merchandiseName) {
        this.merchandiseName =merchandiseName;
    }
    public String getmerchandisePrice() {
        return  this.merchandisePrice;
    }
    public void setmerchandisePrice(String merchandisePrice) {
        this.merchandisePrice = merchandisePrice;
    }
    public String getmerchandiseImageUrl() {
        return this.merchandiseImageUrl;
    }
    public void setmerchandiseImageUrl(String merchandiseImageUrl) {
        this.merchandiseImageUrl =merchandiseImageUrl;
    }
    public String getmerchandiseDescription() {
        return  this.merchandiseDescription;
    }
    public void setmerchandiseDescription(String merchandiseDescription) {
        this.merchandiseDescription = merchandiseDescription;
    }
}

