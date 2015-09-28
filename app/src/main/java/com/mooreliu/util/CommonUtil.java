package com.mooreliu.util;

import android.widget.Toast;

import com.mooreliu.AppContext;

/**
 * Created by mooreliu on 2015/9/7.
 */
public class CommonUtil {
    private static final String TAG = "CommonUtil";
    private static long lastClickTime;

    public static void toastMessage(String message) {
        Toast.makeText(AppContext.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isFastDoubleClick(int interval) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < interval) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


}
