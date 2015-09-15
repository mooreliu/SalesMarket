package com.mooreliu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.facebook.common.internal.Preconditions;
import com.mooreliu.db.model.OrderColumns;
import com.mooreliu.db.model.MerchandiseColumns;
import com.mooreliu.db.model.UserColumns;
import com.mooreliu.util.DateUtil;
import com.mooreliu.util.LogUtil;

/**
 * Created by liuyi on 15/9/13.
 */
public class DbHelper extends SQLiteOpenHelper{

    public static final String TAG = "DbHelper";

    public static final String DATABASE_NAME = "mooreliu.db";
    public static final int DATABASE_VERSION = 1;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        LogUtil.e(TAG, "DbHelper Construction");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.e(TAG, "onCreate");
        db.execSQL("DROP TABLE IF EXISTS " + MerchandiseColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OrderColumns.TABLE_NAME);
        db.execSQL(MerchandiseColumns.CREATE_TABLE);
        db.execSQL(UserColumns.CREATE_TABLE);
        db.execSQL(OrderColumns.CREATE_TABLE);
        initMerchandiseTable(db);
        initUserTable(db);
        initOrderTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db ,int oldVersion , int newVersion) {
        LogUtil.e(TAG, "SQLite database version changed");
        db.execSQL("DROP TABLE IF EXISTS " + MerchandiseColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OrderColumns.TABLE_NAME);
        onCreate(db);
    }

    public void insertUserItem(SQLiteDatabase db, ContentValues value) {
        Preconditions.checkNotNull(db);
        db.insert(UserColumns.TABLE_NAME, null, value);
    }
    public void insertMerchandiseItem(SQLiteDatabase db, ContentValues value) {
        Preconditions.checkNotNull(db);
        db.insert(MerchandiseColumns.TABLE_NAME, null, value);
    }
    public void insertOrderItem(SQLiteDatabase db, ContentValues value) {
        Preconditions.checkNotNull(db);
        db.insert(OrderColumns.TABLE_NAME, null, value);
    }

    /**
     * 插入订单order数据库Item
     * @param db
     * @param orderUserId
     * @param orderProductId
     * @param orderState
     * @param orderExpressState
     */
    public static void addOrder(SQLiteDatabase db,
                                int orderUserId ,int orderProductId ,String orderState, String orderExpressState) {
        Preconditions.checkNotNull(db);
        Preconditions.checkNotNull(orderState);
        Preconditions.checkNotNull(orderExpressState);

        ContentValues insertValue = new ContentValues();
        insertValue.put(OrderColumns.ORDER_USER_ID, orderUserId);
        insertValue.put(OrderColumns.ORDER_MERCHANDISE_ID, orderProductId);
        insertValue.put(OrderColumns.ORDER_STATE, orderState);
        insertValue.put(OrderColumns.ORDER_EXPRESS_STATE, orderExpressState);

        db.insert(OrderColumns.TABLE_NAME, null, insertValue);
    }

    /**
     * 插入商品merchandise数据库Item
     * @param db
     * @param productId
     * @param productName
     * @param productPrice
     * @param productDescription
     * @param productImageUrl
     */
    public void addProduct(SQLiteDatabase db, int productId, String productName,
                           String productPrice, String productDescription, String productImageUrl) {
        Preconditions.checkNotNull(db);
        Preconditions.checkNotNull(productName);
        Preconditions.checkNotNull(productPrice);
        Preconditions.checkNotNull(productDescription);
        Preconditions.checkNotNull(productImageUrl);

        ContentValues insertValue = new ContentValues();

        insertValue.put(MerchandiseColumns.MERCHANDISE_ID, productId);
        insertValue.put(MerchandiseColumns.MERCHANDISE_NAME, productName);
        insertValue.put(MerchandiseColumns.MERCHANDISE_PRICE, productPrice);
        insertValue.put(MerchandiseColumns.MERCHANDISE_DESCRIPTION, productDescription);
        insertValue.put(MerchandiseColumns.MERCHANDISE_IMAGE_URL, productImageUrl);
        db.insert(MerchandiseColumns.TABLE_NAME, null, insertValue);
    }

    /**
     * 插入用户User数据库Item
     * @param db
     * @param userName
     * @param userPassword
     * @param userPhoneNumber
     * @param userMailingAddress
     */
    public static void addUser(SQLiteDatabase db, String userName, String userPassword,
                               String userPhoneNumber, String userMailingAddress) {
        Preconditions.checkNotNull(db);
        Preconditions.checkNotNull(userName);
        Preconditions.checkNotNull(userPassword);
        Preconditions.checkNotNull(userPhoneNumber);
        Preconditions.checkNotNull(userMailingAddress);

        ContentValues insertValue = new ContentValues();

        insertValue.put(UserColumns.USER_NAME, userName);
        insertValue.put(UserColumns.USER_PASSWORD, userPassword);
        insertValue.put(UserColumns.USER_PHONE_NUMBER, userPhoneNumber);
        insertValue.put(UserColumns.USER_MAILING_ADDRESS, userMailingAddress);
        db.insert(MerchandiseColumns.TABLE_NAME, null, insertValue);

    }

    public  void initUserTable(SQLiteDatabase db) {
        Preconditions.checkNotNull(db);
        ContentValues user1 = new ContentValues();
        String createTime = DateUtil.getCurrentTime();
        String updateTime = createTime;
        user1.put(UserColumns.USER_NAME, "john");
        user1.put(UserColumns.USER_PASSWORD, "Oranges");
        user1.put(UserColumns.USER_PHONE_NUMBER, "13504084901");
        user1.put(UserColumns.USER_MAILING_ADDRESS, "SHEN ZHEN");
        user1.put(UserColumns.CREATE_TIME, createTime);
        user1.put(UserColumns.UPDATE_TIME, updateTime);
        ContentValues user2 = new ContentValues();
        user2.put(UserColumns.USER_NAME, "moore");
        user2.put(UserColumns.USER_PASSWORD, "Apple");
        user2.put(UserColumns.USER_PHONE_NUMBER, "15304011901");
        user2.put(UserColumns.USER_MAILING_ADDRESS, "Beijing");
        user2.put(UserColumns.CREATE_TIME, createTime);
        user2.put(UserColumns.UPDATE_TIME, updateTime);
        ContentValues user3 = new ContentValues();
        user3.put(UserColumns.USER_NAME, "michal");
        user3.put(UserColumns.USER_PASSWORD, "pear");
        user3.put(UserColumns.USER_PHONE_NUMBER, "18822448921");
        user3.put(UserColumns.USER_MAILING_ADDRESS, "ShangHai");
        user3.put(UserColumns.CREATE_TIME, createTime);
        user3.put(UserColumns.UPDATE_TIME, updateTime);
        insertUserItem(db, user1);
        insertUserItem(db, user2);
        insertUserItem(db, user3);
    }

    public  final void initMerchandiseTable(SQLiteDatabase db) {
        Preconditions.checkNotNull(db);
        ContentValues merchandise1 = new ContentValues();
        String createTime = DateUtil.getCurrentTime();
        String updateTime = createTime;
        merchandise1.put(MerchandiseColumns.MERCHANDISE_ID, 1);
        merchandise1.put(MerchandiseColumns.MERCHANDISE_NAME, "Oranges");
        merchandise1.put(MerchandiseColumns.MERCHANDISE_PRICE, "1.1");
        merchandise1.put(MerchandiseColumns.MERCHANDISE_DESCRIPTION, "Orange Description");
        merchandise1.put(MerchandiseColumns.MERCHANDISE_IMAGE_URL, "1");
        merchandise1.put(MerchandiseColumns.CREATE_TIME, createTime);
        merchandise1.put(MerchandiseColumns.UPDATE_TIME, updateTime);
        ContentValues merchandise2 = new ContentValues();
        merchandise2.put(MerchandiseColumns.MERCHANDISE_ID, 2);
        merchandise2.put(MerchandiseColumns.MERCHANDISE_NAME, "Apples");
        merchandise2.put(MerchandiseColumns.MERCHANDISE_PRICE, "1.2");
        merchandise2.put(MerchandiseColumns.MERCHANDISE_DESCRIPTION, "Apple Description");
        merchandise2.put(MerchandiseColumns.MERCHANDISE_IMAGE_URL, "2");
        merchandise2.put(MerchandiseColumns.CREATE_TIME, createTime);
        merchandise2.put(MerchandiseColumns.UPDATE_TIME, updateTime);
        ContentValues merchandise3 = new ContentValues();
        merchandise3.put(MerchandiseColumns.MERCHANDISE_ID,3);
        merchandise3.put(MerchandiseColumns.MERCHANDISE_NAME, "Pears");
        merchandise3.put(MerchandiseColumns.MERCHANDISE_PRICE, "1.3");
        merchandise3.put(MerchandiseColumns.MERCHANDISE_DESCRIPTION, "Pear Description");
        merchandise3.put(MerchandiseColumns.MERCHANDISE_IMAGE_URL, "3");
        merchandise3.put(MerchandiseColumns.CREATE_TIME, createTime);
        merchandise3.put(MerchandiseColumns.UPDATE_TIME, updateTime);
        insertMerchandiseItem(db, merchandise1);
        insertMerchandiseItem(db, merchandise2);
        insertMerchandiseItem(db, merchandise3);
    }

    public final void initOrderTable(SQLiteDatabase db) {
        Preconditions.checkNotNull(db);
        ContentValues order1 = new ContentValues();
        String createTime = DateUtil.getCurrentTime();
        String updateTime = createTime;
        order1.put(OrderColumns.ORDER_USER_ID, 1);
        order1.put(OrderColumns.ORDER_MERCHANDISE_ID, 1);
        order1.put(OrderColumns.ORDER_STATE, "To be pay");
        order1.put(OrderColumns.ORDER_EXPRESS_STATE, "To be send");
        order1.put(OrderColumns.CREATE_TIME, createTime);
        order1.put(OrderColumns.UPDATE_TIME, updateTime);
        ContentValues order2 = new ContentValues();
        order2.put(OrderColumns.ORDER_USER_ID, 3);
        order2.put(OrderColumns.ORDER_MERCHANDISE_ID, 2);
        order2.put(OrderColumns.ORDER_STATE, "been paied");
        order2.put(OrderColumns.ORDER_EXPRESS_STATE, "To be send");
        order2.put(OrderColumns.CREATE_TIME, createTime);
        order2.put(OrderColumns.UPDATE_TIME, updateTime);

        insertOrderItem(db, order1);
        insertOrderItem(db, order2);
    }
}
