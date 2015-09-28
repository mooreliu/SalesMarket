package com.mooreliu.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mooreliu.db.model.MerchandiseModel;

import java.util.List;

/**
 * Created by mooreliu on 2015/9/27.
 */
public class GsonHelper {
	private static final String TAG = "GsonHelper";
	private Gson gson;

	public static List<MerchandiseModel> getMerchandiseListFromGsonString(String inputGsonStr) {
		Gson gson = new Gson();
		List<MerchandiseModel> gsonList = gson.fromJson(inputGsonStr,
						new TypeToken<List<MerchandiseModel>>() {
						}.getType());
//        // why jsonList size is 8   which is 1 bigger than the fact size
		// GSON 文件最后多了一个逗号
//        List<MerchandiseModel> retList = new ArrayList<MerchandiseModel>();
//        for(int i =0 ;i< gsonList.size()-1 ;i++) {
//            retList.add(new MerchandiseModel(gsonList.get(i).getmerchandiseImageUrl()));
//        }
		return gsonList;
	}

	public <T> List<T> getListFromGsonString(String inputGsonStr, Class<T> tClass) {
		gson = new Gson();
		List<T> retList = gson.fromJson(inputGsonStr, new TypeToken<List<T>>() {
		}.getType());
		return retList;
	}
}
