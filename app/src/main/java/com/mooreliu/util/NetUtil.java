package com.mooreliu.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mooreliu on 2015/9/27.
 */
public class NetUtil {
  private final String TAG = "NetUtil";
  private int inputStreamLength;

  /**
   * 从URL上下载数据并转换为String类型 @param urlStr @return
   */
  public synchronized String getStringFromURL(String urlStr) { /*        LogUtil.e(TAG, "urlStr" + urlStr); InputStream inputStream = getInputStreamFromURL(urlStr); String output = inputStream2String(inputStream); LogUtil.e(TAG, "inputStream2String output" + output); return output;*/
    return inputStream2String(getInputStreamFromURL(urlStr));
  }

  public synchronized InputStream getInputStreamFromURL(String urlStr) {
    URL url = null;
    HttpURLConnection conn = null;
    InputStream inputStream = null;
    try {
      url = new URL(urlStr);
      conn = (HttpURLConnection) url.openConnection();
      conn.setReadTimeout(5 * 1000);
      conn.setConnectTimeout(5 * 1000);
      conn.setRequestMethod("GET");
      conn.connect();
      if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
        //LogUtil.e(TAG, "download responseCode = HTTP_OK");
        inputStream = conn.getInputStream();
        inputStreamLength = conn.getContentLength();
        //LogUtil.e(TAG, "conn.getContentLength()"+conn.getContentLength());

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return inputStream;
  }

  public int getInputStreamLength() {
    return inputStreamLength;
  }

  public synchronized String inputStream2String(InputStream inputStream) {
    if (inputStream == null)
      return null;
    String outputString = "";
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int len = 0;
    byte[] data = new byte[1024];
    try {
      while ((len = inputStream.read(data)) != -1) {
        outputStream.write(data, 0, len);
      }
      outputString = new String(outputStream.toByteArray());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return outputString;
  }

}
