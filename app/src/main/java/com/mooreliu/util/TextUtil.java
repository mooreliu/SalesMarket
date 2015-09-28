package com.mooreliu.util;

import com.facebook.common.internal.Preconditions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by liuyi on 15/8/28.
 */
public class TextUtil {
    private static final String TAG = "TextUtil";

    public static boolean isEmpty(String inputString) {
        if (inputString != null) return (inputString.length() == 0);
        return true;
    }

    public static String hashKeyForDisk(String key) {
        Preconditions.checkNotNull(key); /*LogUtil.e(TAG, key);*/
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
