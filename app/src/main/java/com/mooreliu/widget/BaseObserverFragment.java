package com.mooreliu.widget;

import android.os.Bundle;

import com.mooreliu.sync.EventType;
import com.mooreliu.sync.NotifyInfo;
import com.mooreliu.sync.imple.EventObservable;
import com.mooreliu.sync.imple.EventObserver;
import com.mooreliu.view.InternetOffLayout;

import java.lang.ref.WeakReference;

/**
 * Created by mooreliu on 2015/9/23.
 */
public abstract class BaseObserverFragment extends BaseFragment {
	private static final String TAG = "BaseObserverFragment";
	protected InternetOffLayout mInternetOffLayout;
	private FragmentObserver mFrgamentObserver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFrgamentObserver = new FragmentObserver(this);
		registerFragment(mFrgamentObserver);
	}

	@Override
	public void onDestroy() {
		unregisterFragment(mFrgamentObserver);
	}

	private void registerFragment(FragmentObserver observer) { /*LogUtil.e(TAG, "registerFragment observer = "+observer);*/
		String[] evenTypes = getObserverEventTypes();
		if (evenTypes != null && evenTypes.length != 0) {
			final EventObservable observable = EventObservable.getInstance();
			for (int i = 0; i < evenTypes.length; i++) {
				observable.registerObserver(observer, evenTypes[i]);
			}
		}
	}

	private void unregisterFragment(FragmentObserver observer) {
		String[] evenTypes = getObserverEventTypes();
		if (evenTypes != null && evenTypes.length != 0) {
			final EventObservable observable = EventObservable.getInstance();
			for (int i = 0; i < evenTypes.length; i++) {
				observable.unregisterObserver(observer, evenTypes[i]);
			}
		}
	}

	protected abstract void onUpdate(NotifyInfo notifyInfo);

	protected abstract String[] getObserverEventTypes();

	private class FragmentObserver extends EventObserver {

		private WeakReference<BaseObserverFragment> mFragment;

		public FragmentObserver(BaseObserverFragment fragment) {
			mFragment = new WeakReference<BaseObserverFragment>(fragment);
		}

		@Override
		public void onUpdate(NotifyInfo notifyInfo) {
			String eventType = notifyInfo.getEventType();
			BaseObserverFragment fragment = mFragment.get();
			if (EventType.contains(eventType))
				fragment.onUpdate(notifyInfo);
		}
	}
}
