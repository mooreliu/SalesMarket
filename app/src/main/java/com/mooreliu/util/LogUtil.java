package com.mooreliu.util;



import com.mooreliu.AppContext;
import timber.log.Timber;

/**
 * Created by liuyi on 15/8/28.
 */
public class LogUtil {
    private static final boolean DEBUG = AppContext.DEBUG;
    public static void e (String tag ,String debuginfo) {
        if( DEBUG && (!TextUtil.isEmpty(debuginfo))) {
            Timber.tag(tag);
            Timber.e(debuginfo);
        }
    }

    public static void w (String tag ,String debuginfo) {
        if( DEBUG && (!TextUtil.isEmpty(debuginfo))) {
            Timber.tag(tag);
            Timber.w(debuginfo);
        }
    }
    public static void i (String tag ,String debuginfo) {
        if( DEBUG && (!TextUtil.isEmpty(debuginfo))) {
            Timber.tag(tag);
            Timber.i(debuginfo);
        }
    }
    public static void v (String tag ,String debuginfo) {
        if( DEBUG && (!TextUtil.isEmpty(debuginfo))) {
            Timber.tag(tag);
            Timber.v(debuginfo);
        }
    }

}
