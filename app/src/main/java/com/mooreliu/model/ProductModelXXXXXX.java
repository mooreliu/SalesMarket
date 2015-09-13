package com.mooreliu.model;

/**
 * Created by liuyi on 15/8/30.
 */
public class ProductModelXXXXXX {
    public int resId;
    public String url = null;
    public ProductModelXXXXXX(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public ProductModelXXXXXX(String url, int resId) {
        this.url = url;
        this.resId = resId;
    }

    public ProductModelXXXXXX(int resId) {
        this.resId = resId;
    }

    public void setResId( int resId) {
        this.resId = resId;
    }
    public int getResId() {
        return  this.resId;
    }
}
