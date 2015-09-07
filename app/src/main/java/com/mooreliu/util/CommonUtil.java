package com.mooreliu.util;

import android.widget.Toast;

import com.mooreliu.AppContext;

/**
 * Created by mooreliu on 2015/9/7.
 */
public class CommonUtil {

    public static void toastMessage(String message) {
        Toast.makeText(AppContext.getContext(), message ,Toast.LENGTH_SHORT).show();
    }

}
