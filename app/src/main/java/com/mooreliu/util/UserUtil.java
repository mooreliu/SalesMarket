package com.mooreliu.util;

import com.avos.avoscloud.AVUser;

/**
 * Created by mooreliu on 2015/9/10.
 */
public class UserUtil {
	private static final String TAG = "UserUtil";

	public static boolean isLogined() {
		AVUser currentUser = AVUser.getCurrentUser();
		if (currentUser != null) {
			LogUtil.e(TAG, "允许用户使用应用");
			return true;
		} else {
			LogUtil.e(TAG, "缓存用户对象为空时， 可打开用户注册界面…");
			return false;
		}
	}

	public static final void loginout() {
		AVUser.logOut();             //清除缓存用户对象
	}

	public static final boolean isLoginoutSuccess() {
		AVUser currentUser = AVUser.getCurrentUser();
		if (currentUser == null)
			return true;
		return false;
	}

}
