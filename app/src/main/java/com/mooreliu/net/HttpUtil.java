package com.mooreliu.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liuyi on 15/9/1.
 */
public class HttpUtil {
    public static String HttpGETResponseString(String url, int readTimeout, int connectTimeout) {
        URL Url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            Url = new URL(url);
            conn = (HttpURLConnection) Url.openConnection();
            conn.setReadTimeout(readTimeout);
            conn.setConnectTimeout(connectTimeout);
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                String tmp;
                while ((tmp = br.readLine()) != null) {
                    sb.append(tmp);
                }
                return sb.toString();
            }
            //return  null;

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (conn != null)
                conn.disconnect();
            try {
                if (is != null)
                    is.close();
                if (br != null)
                    br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void httpPOST() {

    }
}
