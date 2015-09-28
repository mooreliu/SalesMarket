package com.mooreliu.util;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.mooreliu.AppContext;

/**
 * Created by liuyi on 15/9/3.
 */
public class LruCacheUtil {
    public static void add(String key, Bitmap bitmap) {
        LruCache<String, Bitmap> mLruCache = AppContext.getLruBitmapCache();
        if (key != null && bitmap != null) {
            mLruCache.put(key, bitmap);
        }
    }

    public static Bitmap get(String key) {
        LruCache<String, Bitmap> mLruCache = AppContext.getLruBitmapCache();
        if (!TextUtil.isEmpty(key)) {
            return mLruCache.get(key);

        }
        return null;
    }
}
