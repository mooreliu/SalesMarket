package com.mooreliu.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mooreliu.AppContext;

/**
 * Created by mooreliu on 2015/9/2.
 */
public class NetWorkUtil {
	public static boolean isNetworkConnected() {
		ConnectivityManager manager =
						(ConnectivityManager) AppContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) {
			if (info.isAvailable())
				return true;
			//Toast.makeText(AppContext.getContext(), R.string.networkNotAvail, Toast.LENGTH_SHORT);

		}
		return false;
	}

}
