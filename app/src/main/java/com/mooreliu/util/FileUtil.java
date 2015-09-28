package com.mooreliu.util;

import android.os.Environment;

import com.mooreliu.AppContext;
import com.mooreliu.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liuyi on 15/9/26.
 */
public class FileUtil {
    public static final int FILE_SIZE = 35 * 1024 * 1024;
    private static final String TAG = "FileUtil";
    private static String SDCARD_PATH;

    public static void initSDCardPath() {
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            SDCARD_PATH = Environment.getExternalStorageDirectory() + File.separator;
        else if (Environment.getDataDirectory() != null)
            SDCARD_PATH = Environment.getDataDirectory().toString() + File.separator;
        else if (Environment.getDownloadCacheDirectory() != null)
            SDCARD_PATH = Environment.getDownloadCacheDirectory().toString() + File.separator;
        else LogUtil.e(TAG, AppContext.getContext().getString(R.string.no_usable_storage));
    }

    /**
     * 在SD卡上创建文件路劲-文件夹 @param pathName @return
     */
    public static File createSDPath(String pathName) {
        initSDCardPath();
        File file = new File(SDCARD_PATH + pathName);
        if (!file.exists()) file.mkdir();
        return file;
    }

    /**
     * 在SD卡上创建文件 @param filePathName @return @throws IOException
     */
    public static File createSDFile(String filePathName) throws IOException {
        initSDCardPath();
        File file = new File(SDCARD_PATH + filePathName);
        if (!file.exists()) file.createNewFile();
        return file;
    }

    /**
     * 将网络资源下载到SD卡中 @param urlstr 网络url @param filePath 存储的文件路径 @param fileName 存储的文件名 @return
     */
    public static File writeUrlToSDcard(String urlstr, String filePath, String fileName) {
        if (filePath == null || fileName == null || urlstr == null) {
            return null;
        }
        File file = null;
        OutputStream output = null;
        createSDPath(filePath);
        URL url = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        long totalLength;
        try {
            file = createSDFile(filePath + File.separator + fileName);
            if (file != null)
                output = new FileOutputStream(file);
            byte[] buffer = new byte[FILE_SIZE];
            int length;

            url = new URL(urlstr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                LogUtil.e(TAG, "download responseCode = HTTP_OK");
                inputStream = conn.getInputStream();
                while ((length = inputStream.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
            }

            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File writeFileToSDcard(String filePath, String fileName, InputStream inputStream) {
        if (filePath == null || fileName == null || inputStream == null) {
            return null;
        }
        File file = null;
        OutputStream output = null;
        createSDPath(filePath);
        try {
            file = createSDFile(filePath + File.separator + fileName);
            if (file != null)
                output = new FileOutputStream(file);
            byte[] buffer = new byte[FILE_SIZE];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
