package com.mooreliu.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mooreliu.R;
import com.mooreliu.controller.ShoppingChartController;
import com.mooreliu.db.model.ShoppingChartModel;

/**
 * Created by mooreliu on 2015/9/16.
 */
public abstract class AddToShoppingChartTask extends AsyncTask<ShoppingChartModel, Void, Boolean> {
	private static final String TAG = "AddToShoppingChartTask";
	private ProgressDialog mProgressDialog;
	private Context mContext;
	private ShoppingChartController sc;

	public AddToShoppingChartTask(Context context, ShoppingChartController sc) {
		this.mContext = context;
		this.sc = sc;
	}

	@Override
	public void onPreExecute() {
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setMessage(mContext.getResources().getString(R.string.add_to_shopping_chart));
		mProgressDialog.show();
	}

	@Override
	public Boolean doInBackground(ShoppingChartModel... params) {
		if (sc.insert(mContext, params[0]) != null)
			return true;
		return false;
	}

	@Override
	public void onPostExecute(Boolean vBoolean) {
		postExecute(vBoolean);
		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}

	public abstract void postExecute(Boolean vBoolean);

}
