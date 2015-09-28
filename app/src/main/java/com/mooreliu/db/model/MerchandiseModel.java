package com.mooreliu.db.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;

import com.mooreliu.util.DataProviderHelper;

/**
 * Created by liuyi on 15/9/13.
 */
public class MerchandiseModel extends BaseModel {
	private static final String TAG = "MerchandiseModel";
	private int merchandiseId;
	private String merchandiseName;
	private String merchandisePrice;
	private String merchandiseDescription;
	private String merchandiseImageUrl;

	public MerchandiseModel() {
	}

	public MerchandiseModel(String merchandiseImageUrl) {
		this.merchandiseImageUrl = merchandiseImageUrl;
	}

	@Override
	public String getTable() {
		return MerchandiseColumns.TABLE_NAME;
	}

	@Override
	public Uri getContentUri() {
		return MerchandiseColumns.CONTENT_URI;
	}

	@Override
	public MerchandiseModel getModel(Cursor cursor) {
		if (cursor == null) return null;
		MerchandiseModel model = new MerchandiseModel();
		model.id = DataProviderHelper.parseInt(cursor, MerchandiseColumns._ID);
		model.merchandiseId = DataProviderHelper.parseInt(cursor, MerchandiseColumns.MERCHANDISE_ID);
		model.merchandiseName = DataProviderHelper.parseString(cursor, MerchandiseColumns.MERCHANDISE_NAME);
		model.merchandiseDescription = DataProviderHelper.parseString(cursor, MerchandiseColumns.MERCHANDISE_DESCRIPTION);
		model.merchandisePrice = DataProviderHelper.parseString(cursor, MerchandiseColumns.MERCHANDISE_PRICE);
		model.merchandiseImageUrl = DataProviderHelper.parseString(cursor, MerchandiseColumns.MERCHANDISE_IMAGE_URL);
		return model;
	}

	@Override
	public ContentValues values() {
		ContentValues values = ModelBase();
		values.put(MerchandiseColumns.MERCHANDISE_ID, merchandiseId);
		values.put(MerchandiseColumns.MERCHANDISE_NAME, merchandiseName);
		values.put(MerchandiseColumns.MERCHANDISE_DESCRIPTION, merchandiseDescription);
		values.put(MerchandiseColumns.MERCHANDISE_PRICE, merchandisePrice);
		values.put(MerchandiseColumns.MERCHANDISE_IMAGE_URL, merchandiseImageUrl);
		return values;

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flag) {
		writeBase(dest);
		dest.writeInt(merchandiseId);
		dest.writeString(merchandiseName);
		dest.writeString(merchandisePrice);
		dest.writeString(merchandiseImageUrl);
		dest.writeString(merchandiseDescription);

	}

	public int getmerchandiseId() {
		return this.merchandiseId;
	}

	public void setmerchandiseId(int merchandiseId) {
		this.merchandiseId = merchandiseId;
	}

	public String getmerchandiseName() {
		return this.merchandiseName;
	}

	public void setmerchandiseName(String merchandiseName) {
		this.merchandiseName = merchandiseName;
	}

	public String getmerchandisePrice() {
		return this.merchandisePrice;
	}

	public void setmerchandisePrice(String merchandisePrice) {
		this.merchandisePrice = merchandisePrice;
	}

	public String getmerchandiseImageUrl() {
		return this.merchandiseImageUrl;
	}

	public void setmerchandiseImageUrl(String merchandiseImageUrl) {
		this.merchandiseImageUrl = merchandiseImageUrl;
	}

	public String getmerchandiseDescription() {
		return this.merchandiseDescription;
	}

	public void setmerchandiseDescription(String merchandiseDescription) {
		this.merchandiseDescription = merchandiseDescription;
	}
}

