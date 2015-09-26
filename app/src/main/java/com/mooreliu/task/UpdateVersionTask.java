package com.mooreliu.task;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by liuyi on 15/9/26.
 */
public class UpdateVersionTask extends AsyncTask<Context, Void, Boolean>
 {
     private static final String TAG = "UpdateVersionTask";
     private Context mContext;

     @Override
     public Boolean doInBackground(Context ... context) {

         return true;
     }

     @Override
     public void onPostExecute(Boolean isDownloadSuccess) {

     }

}
