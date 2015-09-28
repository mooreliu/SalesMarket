package com.mooreliu.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mooreliu.AppContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import libcore.io.DiskLruCache;

/**
 * Created by liuyi on 15/9/3.
 */
public class DiskLruCacheUtil {
	private static final String TAG = "DistkLruCacheUtil"; /*    private static DiskLruCache mDiskLruCache;*/

	public static Bitmap addToDiskLruCache(String key, String url) {
		DiskLruCache mDiskLruCache = null;
		Bitmap mbitmap = null;
		mDiskLruCache = AppContext.getDistLruCache();
		try {
			DiskLruCache.Editor editor = mDiskLruCache.edit(key);
			if (editor != null) {
				OutputStream outputStream = editor.newOutputStream(0);
				mbitmap = BitmapUtil.bitmapFromUrl(url, 40, 40, outputStream);
				if (mbitmap != null) {
					editor.commit();
					// LogUtil.e(TAG, "Editor.commit()");
				} else {
					LogUtil.e(TAG, "Editor.abort()");
					editor.abort();
				}
			}
			mDiskLruCache.flush();
//            LogUtil.e(TAG, "mDiskLruCache.flush()");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (null != mbitmap) {
			return mbitmap;
		}
		return null;

	}

	public static Bitmap get(String key) {
		DiskLruCache mDiskLruCache = AppContext.getDistLruCache();
		DiskLruCache.Snapshot snapShot = null;
		if (key != null) {
			try {
				if (mDiskLruCache != null) {
					snapShot = mDiskLruCache.get(key);
					if (snapShot != null) { //在DiskLruCache中查询
//                        LogUtil.e(TAG, "in DiskLruCache "+key);
						InputStream is = snapShot.getInputStream(0);
						Bitmap bitmap = BitmapFactory.decodeStream(is);
//                        LogUtil.e(TAG , "IN DISK +"+key);
						return bitmap;
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//        LogUtil.e(TAG , "NOT IN DISK +"+key);
		return null;
	}
}
