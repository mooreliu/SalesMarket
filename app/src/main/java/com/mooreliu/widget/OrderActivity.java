package com.mooreliu.widget;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mooreliu.R;
import com.mooreliu.controller.ShoppingChartController;
import com.mooreliu.db.model.ShoppingChartModel;
import com.mooreliu.task.AddToShoppingChartTask;
import com.mooreliu.util.CommonUtil;
import com.mooreliu.util.Constants;
import com.mooreliu.util.DiskLruCacheUtil;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.LruCacheUtil;
import com.mooreliu.view.RatioImageView;

/**
 * Created by liuyi on 15/9/3.
 */
public class OrderActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "OrderActivity";
    private String mProductImgKey;
    private RatioImageView mMerchandiseImage;
    private ImageView mImgViewAddBtn;
    private ImageView mImgViewSubBtn;
    private AutoCompleteTextView mTextViewNum; // merchandise number
    private Button mBtnAddToShoppingList;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        if (onSavedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                LogUtil.e(TAG, TAG + " onCreate getExtras is null ");
            } else {
                mProductImgKey = extras.getString(Constants.EXTRA_IMAGE_KEY);
                /*  //putSerializable && getSerializable test
                    *String [] strArray = (String[])extras.getSerializable(Constants.EXTRA_IMAGE_KEY);
                    *mProductImgKey =strArray[0];
                    *LogUtil.e(TAG, TAG + "=strArray[0] =  " + strArray[0]);
                    *LogUtil.e(TAG, TAG + "=strArray[1] =  " + strArray[1]);
                */
            }
        } else {
            mProductImgKey = (String) onSavedInstanceState.getSerializable(Constants.BUNLDE_IMAGE_KEY);
        }
        setContentView(R.layout.activity_order);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        //bundle.putString(Constants.EXTRA_IMAGE_KEY, mProductImgKey);
        bundle.putSerializable(Constants.BUNLDE_IMAGE_KEY, mProductImgKey);
    }

    @Override
    public void findView() {
        mMerchandiseImage = (RatioImageView) findViewById(R.id.radio_image_orderpage_product_image);
        mImgViewAddBtn = (ImageView) findViewById(R.id.image_product_add_btn);
        mImgViewSubBtn = (ImageView) findViewById(R.id.text_product_minus_btn);
        mTextViewNum = (AutoCompleteTextView) findViewById(R.id.aotu_complete_textview_product_num);
        mBtnAddToShoppingList = (Button) findViewById(R.id.btn_add_to_shoppinglist);
    }

    @Override
    public void initView() {
        setToolBarTitle("订单");
        Bitmap productImage = LruCacheUtil.get(mProductImgKey);
        if (productImage == null) {
            productImage = DiskLruCacheUtil.get(mProductImgKey);
            if (productImage != null) {
                mMerchandiseImage.setImageBitmap(productImage);
            }
        } else {
            mMerchandiseImage.setImageBitmap(productImage);
        }
        mTextViewNum.setCursorVisible(false);
    }

    @Override
    public void setOnClick() {
        mImgViewAddBtn.setOnClickListener(this);
        mImgViewSubBtn.setOnClickListener(this);
        mBtnAddToShoppingList.setOnClickListener(this);
    }

    private void doAddToShoppingList() {
        ShoppingChartController sc = new ShoppingChartController();
        ShoppingChartModel model = sc.newShoppingChartItem(1, 1,
                Integer.valueOf(mTextViewNum.getText().toString()));
        new AddToShoppingChartTask(this, sc) {
            @Override
            public void postExecute(Boolean isSuccess) {
                if (isSuccess) {
                    //++;
                    mBtnAddToShoppingList.setText("yep");
                } else {
                    mBtnAddToShoppingList.setText("Nooo...");
                }
            }
        }.execute(model);
    }

    @Override
    public void onClick(View view) {
        View v = view;
        switch (v.getId()) {
            case R.id.btn_add_to_shoppinglist:
                doAddToShoppingList();
                Toast.makeText(this, "加入购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image_product_add_btn://increase product number
                int productNumber = Integer.parseInt(mTextViewNum.getText().toString());
                if (productNumber >= 1 && productNumber < 999)
                    productNumber++;
                mTextViewNum.setText(String.valueOf(productNumber));
                break;
            case R.id.text_product_minus_btn:
                productNumber = Integer.parseInt(mTextViewNum.getText().toString());
                if (productNumber > 0)
                    productNumber--;
                mTextViewNum.setText(String.valueOf(productNumber));
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (CommonUtil.isFastDoubleClick(2 * 1000))
            return false;
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_refresh) {
            Toast.makeText(this, "刷新", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
