package com.mooreliu.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mooreliu.R;

/**
 * 开机闪屏
 * Created by liuyi on 15/8/29.
 */
public class FlashActivity extends Activity{

    @Override
    public void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);
        setContentView(R.layout.activity_flash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FlashActivity.this , MainActivity.class);
                startActivity(intent);
                FlashActivity.this.finish();

            }
        },1000);
    }


}
