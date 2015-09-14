package com.mooreliu.db.model;

import android.os.Parcel;

/**
 * Created by liuyi on 15/9/13.
 */
public class ProductModel extends BaseModel{

    private int productId;
    private String productName;
    private String productPrice;
    private String productDescription;
    private String productImageUrl;

    public ProductModel(String imageUrl) {
        this.productImageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest , int flag) {
        writeBase(dest);
        dest.writeString(productName);
        dest.writeString(productPrice);
        dest.writeString(productImageUrl);
        dest.writeString(productDescription);

    }

    public int getProductId() {
        return this.productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return this.productName;
    }
    public void setProductName(String productName) {
        this.productName =productName;
    }
    public String getProductPrice() {
        return  this.productPrice;
    }
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
    public String getProductImageUrl() {
        return this.productImageUrl;
    }
    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl =productImageUrl;
    }
    public String getProductDescription() {
        return  this.productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}

