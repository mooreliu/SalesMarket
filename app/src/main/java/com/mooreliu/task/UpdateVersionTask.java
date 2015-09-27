package com.mooreliu.task;

//import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

import com.mooreliu.R;
import com.mooreliu.util.CommonUtil;
import com.mooreliu.util.DateUtil;
import com.mooreliu.util.FileUtil;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.NetUtil;

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
public class UpdateVersionTask extends AsyncTask<Void, Integer, Boolean>
 {
     private static final String TAG = "UpdateVersionTask";
     private Context mContext;
     private NotificationManager mNotificationManager;
     private Builder mBuilder;
     private int id = 1;
     private float apkSize;
     private String filePath;
     private String fileName;
     private NetUtil mNetUtil;

     public UpdateVersionTask(Context context) {
         mContext = context;
         mNetUtil = new NetUtil();
         mNotificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
         mBuilder = new NotificationCompat.Builder(mContext);
         mBuilder.setContentText(mContext.getString(R.string.do_download))
                 .setContentText(mContext.getString(R.string.update_version))
                 .setSmallIcon(R.drawable.icon);
     }

     @Override
     public Boolean doInBackground(Void ... params) {
         String urlstr = "http://ac-rzryaqf5.clouddn.com/05dd0937013fc59b.apk";
         filePath = "SaleMarketDownload";
         fileName = "SalesMarket"+ DateUtil.getCurrentTime()+".apk";
         mNotificationManager.notify(id, mBuilder.build());
         if(downloadFile(urlstr, filePath, fileName) != null)
            return true;
         else
            return false;
     }

     @Override
     public void onPostExecute(Boolean isDownloadSuccess) {
         if(isDownloadSuccess)
            installApk();
         else
             LogUtil.e(TAG, "download update apk file failed");
     }

     @Override
     public void onProgressUpdate(Integer ... percentDownloaded) {
        LogUtil.e(TAG, "onProgressUpdate lengthDownloaded ="+(int)percentDownloaded[0]);
        mBuilder.setProgress(100, percentDownloaded[0], false);
        mBuilder.setContentText(mContext.getString(R.string.update_version)+"("+percentDownloaded[0]+"%)");
        mNotificationManager.notify(id, mBuilder.build());
     }

     public File downloadFile(String urlStr, String path, String fileName) {
         InputStream inputStream = null;
         inputStream = mNetUtil.getInputStreamFromURL(urlStr);
         apkSize = mNetUtil.getInputStreamLength();
         File outputFile = writeFileToSDcard(path, fileName, inputStream);
         return outputFile;
     }

//     private InputStream getInputStreamFromURL(String urlStr) {
//         URL url = null;
//         HttpURLConnection conn = null;
//         InputStream inputStream = null;
//         try {
//             url = new URL(urlStr);
//             conn = (HttpURLConnection) url.openConnection();
//             conn.setReadTimeout(5 * 1000);
//             conn.setConnectTimeout(5 * 1000);
//             conn.setRequestMethod("GET");
//             conn.connect();
//             if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                 LogUtil.e(TAG, "download responseCode = HTTP_OK");
//                 inputStream = conn.getInputStream();
//                 apkSize = conn.getContentLength();
//                 LogUtil.e(TAG, "conn.getContentLength()"+conn.getContentLength());
//
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return inputStream;
//     }

     private File writeFileToSDcard(String filePath, String fileName, InputStream inputStream ) {
         if(filePath == null || fileName == null || inputStream == null) {
             return null;
         }
         File file = null;
         OutputStream output = null;
         FileUtil.createSDPath(filePath);
         try {
             file = FileUtil.createSDFile(filePath + File.separator + fileName);
             if (file != null)
                 output = new FileOutputStream(file);
             byte[] buffer = new byte[FileUtil.FILE_SIZE];
             int length;
             long lengthDownLoaded = 0;

             while((length = inputStream.read(buffer)) >0) {
                 output.write(buffer, 0, length);
                 lengthDownLoaded += length;
                 publishProgress((int)(lengthDownLoaded*100/apkSize));  //有个超出了int型范围的坑
             }
             publishProgress(100); //download over
             output.flush();
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             try {
                 if(output != null)
                     output.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         return file;
     }

     /**
      * 安装apk
      */
    private void installApk() {
        String fileAbsolutePath = null;
        File apkfile = null;
//        LogUtil.e(TAG, " Environment.getExternalStorageDirectory() = "+ Environment.getExternalStorageDirectory());
//        LogUtil.e(TAG, " Environment.getDownloadCacheDirectory() = "+ Environment.getDownloadCacheDirectory());
//        LogUtil.e(TAG, " Environment.getDataDirectory() = "+ Environment.getDataDirectory());

        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            fileAbsolutePath = Environment.getExternalStorageDirectory() +File.separator + filePath + File.separator + fileName;
            LogUtil.e(TAG, "MEDIA_MOUNTED + fileAbsolutePath = "+fileAbsolutePath);
        } else if (Environment.getDataDirectory() != null) {
            String dataDir = Environment.getDataDirectory().toString();
            fileAbsolutePath =dataDir + File.separator + fileName;
        } else if (Environment.getDownloadCacheDirectory() != null) {
            String downloadCacheDir = Environment.getDownloadCacheDirectory().toString();
            fileAbsolutePath =downloadCacheDir + File.separator + fileName;
        } else {
            LogUtil.e(TAG, mContext.getString(R.string.no_usable_storage));
        }


        //LogUtil.e(TAG, "fileAbsolutePath = " + fileAbsolutePath);
        if(fileAbsolutePath != null)
            apkfile = new File(fileAbsolutePath);
        if (apkfile != null && !apkfile.exists()) {
            return;
        }
       //LogUtil.e(TAG, "apkfile.getAbsolutePath() = " + apkfile.getAbsolutePath());
        Intent i = new Intent(Intent.ACTION_VIEW);
        // 安装，如果签名不一致，可能出现程序未安装提示
        i.setDataAndType(Uri.fromFile(new File(apkfile.getAbsolutePath())), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }



//     public File writeUrlToSDcard(String urlstr ,String filePath, String fileName) {
//         if(filePath == null || fileName == null || urlstr == null) {
//             return null;
//         }
//         File file = null;
//         OutputStream output = null;
//         FileUtil.createSDPath(filePath);
//         URL url = null;
//         HttpURLConnection conn = null;
//         InputStream inputStream = null;
//         float totalLength;
//         try {
//             file = FileUtil.createSDFile(filePath + File.separator + fileName);
//             if (file != null)
//                 output = new FileOutputStream(file);
//             byte[] buffer = new byte[FileUtil.FILE_SIZE];
//             int length;
//
//             url = new URL(urlstr);
//             conn = (HttpURLConnection) url.openConnection();
//             conn.setReadTimeout(5 * 1000);
//             conn.setConnectTimeout(5 * 1000);
//             conn.setRequestMethod("GET");
//             conn.connect();
//             if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                 LogUtil.e(TAG, "download responseCode = HTTP_OK");
//                 inputStream = conn.getInputStream();
//                 totalLength = conn.getContentLength();
//                 LogUtil.e(TAG, "totalLength = "+totalLength);
//                 LogUtil.e(TAG, "conn.getContentLength() = "+conn.getContentLength());
//                 int lengthDownloaded = 0;
//                 while((length = inputStream.read(buffer)) >0) {
//                     output.write(buffer, 0, length);
//                     lengthDownloaded += length;
//                     publishProgress(lengthDownloaded);
//                 }
//             }
//
//             output.flush();
//         } catch (IOException e) {
//             e.printStackTrace();
//         } finally {
//             try {
//                 if(output != null)
//                     output.close();
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//         }
//         return file;
//     }



}
