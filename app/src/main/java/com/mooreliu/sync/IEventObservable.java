package com.mooreliu.sync;

import com.mooreliu.sync.imple.EventObserver;

/**
 * Created by mooreliu on 2015/9/23.
 */
public interface IEventObservable {
  /** */
  public void notifyObservers(NotifyInfo notifyInfo);

  /**
   * @param observer
   * @param eventType
   */
  public void registerObserver(EventObserver observer, String eventType);

  /**
   * @param observer
   * @param eventType
   */
  public void unregisterObserver(EventObserver observer, String eventType);


}
