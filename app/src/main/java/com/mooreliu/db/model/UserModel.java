package com.mooreliu.db.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;

import com.mooreliu.util.DataProviderHelper;

/**
 * Created by mooreliu on 2015/9/14.
 */
public class UserModel extends BaseModel{

    private String userName;
    private String userPassword;
    private String userPhoneNumber;
    private String userMailingAddress;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest , int flag) {
        writeBase(dest);
        dest.writeString(userName);
        dest.writeString(userPassword);
        dest.writeString(userPhoneNumber);
        dest.writeString(userMailingAddress);
    }
    @Override
    public String getTable() {
        return UserColumns.TABLE_NAME;
    }

    @Override
    public Uri getContentUri() {
        return UserColumns.CONTENT_URI;
    }

    @Override
    public UserModel getModel(Cursor cursor) {
        if(cursor == null) {
            return null;
        }
        UserModel model = new UserModel();
        model.id = DataProviderHelper.parseInt(cursor, UserColumns._ID);
        model.userName = DataProviderHelper.parseString(cursor, UserColumns.USER_NAME);
        model.userPassword = DataProviderHelper.parseString(cursor, UserColumns.USER_PASSWORD);
        model.userPhoneNumber = DataProviderHelper.parseString(cursor, UserColumns.USER_PHONE_NUMBER);
        model.userMailingAddress = DataProviderHelper.parseString(cursor, UserColumns.USER_MAILING_ADDRESS);

        return model;
    }

    @Override
    public ContentValues values() {
        ContentValues values = ModelBase();
        values.put(UserColumns.USER_NAME, userName);
        values.put(UserColumns.USER_PASSWORD, userPassword);
        values.put(UserColumns.USER_PHONE_NUMBER, userPhoneNumber);
        values.put(UserColumns.USER_MAILING_ADDRESS, userMailingAddress);
        return values;

    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return this.userPassword;
    }
    public void settUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserPhoneNumber() {
        return this.userPhoneNumber;
    }
    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
    public String getUserMailingAddress() {
        return this.userMailingAddress;
    }
    public void setUserMailingAddress(String userMailingAddress) {
        this.userMailingAddress = userMailingAddress;
    }

}
