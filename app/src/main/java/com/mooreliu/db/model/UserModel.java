package com.mooreliu.db.model;

import android.os.Parcel;

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
