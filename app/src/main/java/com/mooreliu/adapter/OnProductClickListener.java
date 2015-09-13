package com.mooreliu.adapter;

import android.view.View;

import com.mooreliu.db.model.ProductModel;

/**
 * Created by liuyi on 15/8/30.
 */
public interface OnProductClickListener {
    public void onTouch(View v,ProductModel model);
}
