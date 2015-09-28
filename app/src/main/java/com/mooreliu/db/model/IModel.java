package com.mooreliu.db.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;

/**
 * Created by liuyi on 15/9/13.
 */
public interface IModel extends Parcelable {
	public abstract Uri getContentUri();

	public abstract <T extends BaseModel> T getModel(Cursor cursor);

	public abstract String getTable();

	public abstract ContentValues values();

}

