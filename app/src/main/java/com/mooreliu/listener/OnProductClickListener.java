package com.mooreliu.listener;

import android.view.View;

import com.mooreliu.db.model.MerchandiseModel;

/**
 * Created by liuyi on 15/8/30.
 */
public interface OnProductClickListener {
  public void onTouch(View v, MerchandiseModel model);
}
