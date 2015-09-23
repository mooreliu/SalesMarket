package com.mooreliu.widget;

import android.app.Activity;
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
import com.mooreliu.db.model.MerchandiseColumns;
import com.mooreliu.sync.EventType;
import com.mooreliu.sync.Notify;

import com.mooreliu.listener.OnSwitchFragmentListener;
import com.mooreliu.sync.NotifyInfo;
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
    private SimpleCursorAdapter adapter;
    private ContentResolver mContentResolver;
//    @Override
//    public void onVisible() {

//    }
    private OnSwitchFragmentListener mOnSwitchFragmentListener;

    public MyPageFragment() {
        super();
        LogUtil.e(TAG, "MyPageFragment 构造函数");
    }
    public MyPageFragment(OnSwitchFragmentListener listener) {
        super();
        mOnSwitchFragmentListener =listener;
        LogUtil.e(TAG, "MyPageFragment 构造函数");
    }
    public static MyPageFragment newInstance() {
        MyPageFragment fragment = new MyPageFragment();
        return  fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_mypage, container, false);
        mContentResolver = getActivity().getContentResolver();
        findViews();
        initViews();
        setOnclick();
        return mView;
    }

    private void findViews() {
        mListView = (ListView) mView.findViewById(R.id.myPagelist);
    }

    private void initViews() {
        Uri uri = Uri.parse(MerchandiseColumns.CONTENT_URI + "");
        String[] projection = new String[] {MerchandiseColumns.MERCHANDISE_NAME, MerchandiseColumns._ID};
        Cursor cursor = mContentResolver.query(uri, projection ,null , null, null);
        LogUtil.e(TAG, cursor.getColumnIndex(MerchandiseColumns.MERCHANDISE_NAME)+"");
        String[] from = new String[] {MerchandiseColumns.MERCHANDISE_NAME, MerchandiseColumns._ID};
        int[] to = new int[] {R.id.product_name, R.id.product_id};
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.layout_item_product, null, from, to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mListView.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);

        registerForContextMenu(mListView);
    }

    private void setOnclick() {
        LogUtil.e(TAG, "setOnclick notify ");
        NotifyInfo notifyInfo = new NotifyInfo(EventType.TEST);
        Notify.getInstance().notifyFragment(notifyInfo);
    }

    private void deleteItem(int id) {
        Uri uri = Uri.parse(MerchandiseColumns.CONTENT_URI + "");
        String where = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        mContentResolver.delete(uri, where, whereArgs);
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
        String[] projection = new String[] {MerchandiseColumns.MERCHANDISE_NAME, MerchandiseColumns._ID};
        //LogUtil.e(TAG, "CursorLoader 初始化 onCreateLoader");
        return new CursorLoader(getActivity(), MerchandiseColumns.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //LogUtil.e(TAG, "数据发生变化 onLoadFinished");
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    @Override
    public void onAttach(Activity activity) {
        super.onDetach();
        LogUtil.e(TAG, "onAttach " + activity.toString());
    }
    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        LogUtil.e(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
//        LogUtil.e(TAG, "onPause");
//        if(!NetWorkUtil.isNetworkConnected())
//            Toast.makeText(getActivity(), getString(R.string.networkNotAvail), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e(TAG,"onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e(TAG,"onDetach");
    }
}

