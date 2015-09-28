package com.mooreliu.widget;

import android.app.Activity;
import android.os.Bundle;

import com.mooreliu.R;
import com.mooreliu.sync.EventType;
import com.mooreliu.sync.NotifyInfo;
import com.mooreliu.util.LogUtil;
import com.mooreliu.view.InternetOffLayout;

/**
 * Created by liuyi on 15/8/29.
 */
public class ShoppingListPageFragment extends BaseObserverFragment {
	private static final String TAG = "ShoppingListPageFragment";

	public ShoppingListPageFragment() {
		super();
		LogUtil.e(TAG, "ShoppingListPageFragment 构造函数");
	}

	public static ShoppingListPageFragment newInstance() {
		ShoppingListPageFragment fragment = new ShoppingListPageFragment();
		return fragment;
	}

	@Override
	public String[] getObserverEventTypes() {
		return new String[]{EventType.NETWORK_OK, EventType.NETWORK_NOT_OK};
	}

	@Override
	public void onUpdate(NotifyInfo notifyInfo) {
		switch (notifyInfo.getEventType()) {
			case EventType.NETWORK_OK:
				mInternetOffLayout.setInternetOnView();
				break;
			case EventType.NETWORK_NOT_OK:
				mInternetOffLayout.setInternetOffView();
				break;
			default:
				LogUtil.e(TAG, "default");
		}
	}

	@Override
	public int onSetUpLayout() {
		return R.layout.layout_shoppinglist;
	}

	public void onVisible() {
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	protected void findViews() {

	}

	@Override
	protected void initViews() {
		mInternetOffLayout = new InternetOffLayout(getActivity(), mRootView, R.id.shoppinglist_parent_view) {
			public void initData() {
				initViewData();
			}
		};
		mInternetOffLayout.showInternetOffLayout();
		if (mInternetOffLayout.checkNerworkForView())
			initViewData();
	}

	private void initViewData() {

	}

	@Override
	protected void setOnClick() {

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
	public void onResume() {
		super.onResume();
		LogUtil.e(TAG, "onResume");
		if (mInternetOffLayout.checkNerworkForView()) {
			initViewData();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtil.e(TAG, "onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtil.e(TAG, "onStop");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.e(TAG, "onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		LogUtil.e(TAG, "onDetach");
	}
}
