package com.mooreliu.widget;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mooreliu.R;
import com.mooreliu.db.model.ProductColumns;
import com.mooreliu.util.DateUtil;
import com.mooreliu.util.LogUtil;

//import android.database.Cursor;

/**
 * Created by liuyi on 15/8/29.
 */
public class MyPageFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "MyPageFragment";
    private View mView;
    private ListView mListView;
   // DataProvider mdataProvider;
    private SimpleCursorAdapter adapter;
    private ContentResolver mContentResolver;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_mypage, container, false);
        LogUtil.e(TAG, DateUtil.getCurrentTime());
        //mdataProvider = new DataProvider();
        mContentResolver = getActivity().getContentResolver();
        insertTextData();
        findViews();
        initViews();
        setOnclick();

        return mView;
    }
    private void insertTextData() {
        Uri uri = Uri.parse(ProductColumns.CONTENT_URI + "");
        LogUtil.e(TAG, "CONTENT_URI" + ProductColumns.CONTENT_URI+"");
        String[] projection = new String[] {ProductColumns.PRODUCT_NAME,ProductColumns.PRODUCT_PRICE};
        ContentValues cv1 = new ContentValues();
        String createTime = DateUtil.getCurrentTime();
        String updateTime = createTime;
        cv1.put(ProductColumns.PRODUCT_ID, 1);
        cv1.put(ProductColumns.PRODUCT_NAME, "Orange");
        cv1.put(ProductColumns.PRODUCT_PRICE, "1.1");
        cv1.put(ProductColumns.PRODUCT_DESCRIPTION, "Orange Desciption");
        cv1.put(ProductColumns.PRODUCT_IMAGE_URL, "1");
        cv1.put(ProductColumns.CREATE_TIME, createTime);
        cv1.put(ProductColumns.UPDATE_TIME, updateTime);
        ContentValues cv2 = new ContentValues();
        cv2.put(ProductColumns.PRODUCT_ID, 2);
        cv2.put(ProductColumns.PRODUCT_NAME, "Apple");
        cv2.put(ProductColumns.PRODUCT_PRICE, "1.2");
        cv2.put(ProductColumns.PRODUCT_DESCRIPTION, "Apple Desciption");
        cv2.put(ProductColumns.PRODUCT_IMAGE_URL, "2");
        cv2.put(ProductColumns.CREATE_TIME, createTime);
        cv2.put(ProductColumns.UPDATE_TIME, updateTime);
        ContentValues cv3 = new ContentValues();
        cv3.put(ProductColumns.PRODUCT_ID,3);
        cv3.put(ProductColumns.PRODUCT_NAME, "Pear");
        cv3.put(ProductColumns.PRODUCT_PRICE, "1.3");
        cv3.put(ProductColumns.PRODUCT_DESCRIPTION, "Pear Desciption");
        cv3.put(ProductColumns.PRODUCT_IMAGE_URL, "3");
        cv3.put(ProductColumns.CREATE_TIME, createTime);
        cv3.put(ProductColumns.UPDATE_TIME, updateTime);
        mContentResolver.insert(uri, cv1);
        mContentResolver.insert(uri, cv2);
        mContentResolver.insert(uri, cv3);

    }

    private void findViews() {
        mListView = (ListView) mView.findViewById(R.id.myPagelist);
    }

    private void initViews() {
        Uri uri = Uri.parse(ProductColumns.CONTENT_URI + "");
        String[] projection = new String[] {ProductColumns.PRODUCT_NAME,ProductColumns._ID};
        Cursor cursor = mContentResolver.query(uri, projection ,null , null, null);
        LogUtil.e(TAG, cursor.getColumnIndex(ProductColumns.PRODUCT_NAME)+"");
        String[] from = new String[] {ProductColumns.PRODUCT_NAME,ProductColumns._ID};
        int[] to = new int[] {R.id.product_name, R.id.product_id};
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.layout_item_product, null, from, to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mListView.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);

        registerForContextMenu(mListView);
    }

    private void setOnclick() {

    }

    private void deleteItem(int id) {
        Uri uri = Uri.parse(ProductColumns.CONTENT_URI + "");
        String where = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        mContentResolver.delete(uri, where ,whereArgs);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)
                        item.getMenuInfo();
                deleteItem((int)menuInfo.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.contact_item_menu, menu);
    }
    @Override public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);

    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[] {ProductColumns.PRODUCT_NAME,ProductColumns._ID};
        LogUtil.e(TAG, "CursorLoader 初始化 onCreateLoader");
        return new CursorLoader(getActivity(), ProductColumns.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        LogUtil.e(TAG, "数据发生变化 onLoadFinished");
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}

