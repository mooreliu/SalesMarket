package com.mooreliu.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liuyi on 15/8/14.
 */
public class BitmapUtil {
	private static final String TAG = "BitmapUtil";

	public static Bitmap bitmapDecode(Resources res, int resId, int reqHeight, int reqWidth) { /*Debug.startMethodTracing("bitmapDecode");*/
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		options.inSampleSize = calculateInSampleSize(options, reqHeight, reqWidth);
		options.inJustDecodeBounds = false; /*Debug.stopMethodTracing();*/
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static Bitmap bitmapFromUrl(String url, int reqHeight, int reqWidth, OutputStream outputStream) {
		Bitmap bitmap = null;
		HttpURLConnection conn = null;
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		try {
			URL Url = new URL(url);
			conn = (HttpURLConnection) Url.openConnection();
			conn.setReadTimeout(5 * 1000);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			conn.connect();
			LogUtil.e(TAG, String.valueOf(conn.getResponseCode()));
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				int readLength = conn.getContentLength();
				BufferedInputStream bis = new BufferedInputStream(is);
				bis.mark(readLength);
				// LogUtil.e(TAG, url + " bis.available(): " + bis.available());
				// LogUtil.e(TAG, "count : " + count);
				out = new BufferedOutputStream(outputStream, 8 * 1024);
				int b;
				int total = 0;
				while ((b = bis.read()) != -1) {
					out.write(b);
					total++;
				}
//                LogUtil.e(TAG,"total read "+total);
				bis.reset();
				bitmap = BitmapFactory.decodeStream(bis);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(TAG, "Error in http GET");
		} finally {
			if (conn != null)
				conn.disconnect();
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (bitmap != null) {
				LogUtil.e(TAG, "bitmap !=null");
				return bitmap;
			}
			LogUtil.e(TAG, "bitmap is null");
			return null;

		}
	}

	private static int calculateInSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth) {
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

}
