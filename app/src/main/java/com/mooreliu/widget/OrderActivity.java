package com.mooreliu.widget;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mooreliu.R;
import com.mooreliu.controller.ShoppingChartController;
import com.mooreliu.db.model.ShoppingChartModel;
import com.mooreliu.util.DateUtil;
import com.mooreliu.util.DiskLruCacheUtil;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.LruCacheUtil;
import com.mooreliu.view.RatioImageView;

import task.AddToShoppingChartTask;

/**
 * Created by liuyi on 15/9/3.
 */
public class OrderActivity extends  BaseActivity implements View.OnClickListener{
    public static final String TAG = "OrderActivity";
    public static final String IMAGE_KEY ="ImageKey";
    private String productImgKey;
    private RatioImageView mProductImage;
    private ImageView mAddBtn;
    private ImageView mSubBtn;
    private AutoCompleteTextView  mProductNumber;
    private Button mAddToShoppingList;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        if(onSavedInstanceState == null) {
            Bundle extras =getIntent().getExtras();
            if(extras == null) {
                LogUtil.e(TAG , "EXTRA is null");
            } else {
                productImgKey = extras.getString(IMAGE_KEY);

            }
        }else {
            productImgKey = (String) onSavedInstanceState.getSerializable(IMAGE_KEY);
        }

        setContentView(R.layout.activity_order);
    }
    @Override
    public void findView() {
        mProductImage = (RatioImageView) findViewById(R.id.orderpage_product_image);
        mAddBtn = (ImageView) findViewById(R.id.product_add_btn);
        mSubBtn = (ImageView) findViewById(R.id.product_minus_btn);
        mProductNumber = (AutoCompleteTextView) findViewById(R.id.product_num);
        mAddToShoppingList = (Button) findViewById(R.id.add_to_shoppinglist);
    }

    @Override
    public void initView() {
        setToolBarTitle("订单");
        Bitmap productImage = LruCacheUtil.get(productImgKey);
        if(productImage == null) {
            productImage = DiskLruCacheUtil.get(productImgKey);
            if(productImage != null) {
                mProductImage.setImageBitmap(productImage);
            }
        }else {
            mProductImage.setImageBitmap(productImage);
        }

        mProductNumber.setCursorVisible(false);
    }

    @Override
    public void setOnClick() {
        mAddBtn.setOnClickListener(this);
        mSubBtn.setOnClickListener(this);
        mAddToShoppingList.setOnClickListener(this);

    }
    private void doAddToShoppingList() {
        ShoppingChartController sc = new ShoppingChartController();
        ShoppingChartModel model = sc.newShoppingChartItem(1,1,Integer.valueOf(mProductNumber.getText().toString()));


        new AddToShoppingChartTask(this,sc) {
            @Override
            public void postExecute(Boolean isSuccess) {
                if(isSuccess)
                    mAddToShoppingList.setText("yep");
                else
                    mAddToShoppingList.setText("Nooo...");
            }
        }.execute(model);
    }
    @Override
    public void onClick(View view) {
        View v = view;
        switch (v.getId()) {
            case R.id.add_to_shoppinglist :
                doAddToShoppingList();
                Toast.makeText(this , "加入购物车" , Toast.LENGTH_SHORT).show();
                break;

            case R.id.product_add_btn://increase product number
                int productNumber = Integer.parseInt(mProductNumber.getText().toString());
                if(productNumber >=1 && productNumber<999)
                    productNumber++;
                mProductNumber.setText(String.valueOf(productNumber));
                break;

            case R.id.product_minus_btn:
                productNumber = Integer.parseInt(mProductNumber.getText().toString());
                if(productNumber>0)
                    productNumber--;
                mProductNumber.setText(String.valueOf(productNumber));

            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(IMAGE_KEY, productImgKey);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG , "onDestroy()");
    }
}
