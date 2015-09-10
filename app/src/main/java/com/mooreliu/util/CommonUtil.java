package com.mooreliu.util;

import android.widget.Toast;

import com.mooreliu.AppContext;

/**
 * Created by mooreliu on 2015/9/7.
 */
public class CommonUtil {
    private static final String TAG = "CommonUtil";

    public static void toastMessage(String message) {
        Toast.makeText(AppContext.getContext(), message ,Toast.LENGTH_SHORT).show();
    }

    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


}
