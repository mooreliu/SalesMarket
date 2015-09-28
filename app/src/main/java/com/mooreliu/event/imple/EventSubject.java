package com.mooreliu.event.imple;

import com.mooreliu.event.IEventSubject;
import com.mooreliu.event.NotifyInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * FIXME @author zhiwu_yan @version 1.0 @since 2015-06-30  14:36
 */
public class EventSubject implements IEventSubject {
	private static volatile EventSubject mEventSubject;
	private Map<String, ArrayList<EventObserver>> mEventObservers = new HashMap<String, ArrayList<EventObserver>>();

	private EventSubject() {
	}

	public synchronized static EventSubject getInstance() {
		if (mEventSubject == null) mEventSubject = new EventSubject();
		return mEventSubject;
	}

	@Override
	public void registerObserver(String eventType, EventObserver observer) {
		synchronized (mEventObservers) {
			ArrayList<EventObserver> eventObservers = mEventObservers.get(eventType);
			if (eventObservers == null) {
				eventObservers = new ArrayList<EventObserver>();

				mEventObservers.put(eventType, eventObservers);
			}
			if (eventObservers.contains(observer)) {
				return;
			}
			eventObservers.add(observer);

		}

	}

	@Override
	public void removeObserver(String eventType, EventObserver observer) {
		synchronized (mEventObservers) {
			int index = mEventObservers.get(eventType).indexOf(observer);
			if (index >= 0) {
				mEventObservers.remove(observer);
			}
		}
	}

	@Override
	public void notifyObserver(NotifyInfo notifyInfo) {
		if (mEventObservers != null && mEventObservers.size() > 0 && notifyInfo != null) {
			ArrayList<EventObserver> eventObservers = mEventObservers.get(notifyInfo.getEventType());
			for (EventObserver observer : eventObservers) {
				observer.dispatchChange(notifyInfo);
			}
		}

	}
}
