package com.mooreliu.task;

import android.os.AsyncTask;

import com.mooreliu.util.CommonUtil;
import com.mooreliu.util.Constants;
import com.mooreliu.util.GsonHelper;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.NetUtil;

/**
 * Created by mooreliu on 2015/9/27.
 */
public abstract class TaskGetMerchandiseListFromServer extends AsyncTask<Void, Void , Void> {

    private static final String TAG = "TaskGetMerchandiseListFromServer";
    //private boolean isSuccess;
    private String response;
    @Override
    public Void doInBackground(Void... parms) {
        //String getUrl = "http://192.168.1.115:8080/user";
        NetUtil netUtil = new NetUtil();
        response = netUtil.getStringFromURL(Constants.MerchandiseServerUrl);
        if (response != null) {
            //LogUtil.e(TAG, "response = "+ response);
            //return response;
        }
        response = Constants.jsonProductList;
        return null;
    }

    @Override
    public void onPostExecute(Void params) {
        //LogUtil.e(TAG, "onPostExecute()");
        //LogUtil.e(TAG, "onPostExecute response = "+ response);
        postExecute(response);
    }

    public abstract void postExecute(String response);

}

