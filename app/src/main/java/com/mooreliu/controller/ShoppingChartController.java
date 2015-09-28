package com.mooreliu.controller;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mooreliu.db.model.ShoppingChartColumns;
import com.mooreliu.db.model.ShoppingChartModel;
import com.mooreliu.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mooreliu on 2015/9/16.
 */
public class ShoppingChartController extends BaseController {
	public static final String SHOPPING_CHART_USER_ID = "shopping_chart_user_id";/*该订单所*/
	public static final String SHOPPING_CHART_MERCHANDISE_ID = "shopping_chart_merchandise_id"; /*订单状态*/
	public static final String SHOPPING_CHART_MERCHANDISE_NUMBER = "shopping_chart_merchandise_number"; /* 订单商品ID*/
	private static final String TAG = "ShoppingChartController";

	public boolean insertToShoppingChartDb(Context context, int user_id, int merchandise_id, int merchandise_number) {
		Uri uri = ShoppingChartColumns.CONTENT_URI;
		return true;
	}

	public ShoppingChartModel newShoppingChartItem(int user_id, int merchandise_id, int merchandise_number) {
		ShoppingChartModel model = new ShoppingChartModel();
		String createTime = DateUtil.getCurrentTime();
		model.setshoppingChartMerchandiseId(merchandise_id);
		model.setShoppingChartMerchandiseNumber(Integer.valueOf(merchandise_number));
		model.setShoppingChartUserId(user_id);
		model.setCreateTime(createTime);
		model.setUpdateTime(createTime);
		return model;
	}

	public List<ShoppingChartModel> getShoppingChartGoodsByUserId(Context context, int userId) {
		Uri uri = ShoppingChartColumns.CONTENT_URI;
		String where = ShoppingChartColumns.SHOPPING_CHART_USER_ID + "=?";
		String whereArgs[] = new String[]{String.valueOf(userId)};
		Cursor cursor = context.getContentResolver().query(uri, null, where, whereArgs, null);
		List<ShoppingChartModel> list = new ArrayList<>();
		ShoppingChartModel model;
		if (cursor.moveToNext()) {
			model = new ShoppingChartModel().getModel(cursor);
			if (model != null)
				list.add(model);
		}
		return list;
	}

}
