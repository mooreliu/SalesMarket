//package com.mooreliu.widget;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.ConnectivityManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.provider.Settings;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import com.mooreliu.R;
//import com.mooreliu.sync.EventType;
//import com.mooreliu.listener.OnLoadingOverListener;
//import com.mooreliu.listener.OnSwitchFragmentListener;
//import com.mooreliu.net.NetWorkUtil;
//import com.mooreliu.sync.NotifyInfo;
//import com.mooreliu.util.CommonUtil;
//import com.mooreliu.util.LogUtil;
//
///**
// * Created by mooreliu on 2015/9/11.
// */
//public class ReloadFragment extends BaseObserverFragment implements OnClickListener {
//
//    private static final String TAG = "ReloadFragment";
//    private BroadcastReceiver receiver;
//    private View rootView;
//    private Button mReloadButton;
//    private Button mGoToSetting;
//    private OnSwitchFragmentListener mOnSwitchFragmentListener;
//    private int fragmentId;
//    public static int count = 0;
//    public int pageIndexSelected ;
//    public static volatile ReloadFragment mReloadFragment;
//
//    private ReloadFragment() {
//
//    }
//
//    public static ReloadFragment getInstance() {
//        if(mReloadFragment == null)
//            return newInstance();
//        return mReloadFragment;
//    }
//    public void setPageIndexSelected(int pageIndexSelected) {
//        this.pageIndexSelected = pageIndexSelected;
//    }
//    @Override
//    public String[] getObserverEventTypes() {
//        return new String[] {
//            EventType.NETWORK_OK
//        };
//    }
//
//    @Override
//    public void onUpdate(NotifyInfo notifyInfo) {
//        LogUtil.e(TAG, "EventType ="+notifyInfo.getEventType());
//        switch (notifyInfo.getEventType()) {
//            case EventType.NETWORK_OK:
////                if(isVisible) {
////                    LogUtil.e(TAG, "isVisible == true framentID = "+fragmentId);
//                new Handler().post(new Runnable() {
//                    public void run() {
//                        replaceFragment(true);
//                    }
//                });
//
////                } else
////                    LogUtil.e(TAG, "isVisible == false  framentID = "+fragmentId);
//                break;
//            default:
//                LogUtil.e(TAG, "eventType error");
//        }
//
//    }
//    @Override
//    public int setUpLayout() {
//        return R.layout.layout_reload;
//    }
//    @Override
//    public void onVisible() {
//
//    }
//    public synchronized static ReloadFragment newInstance() {
//        if(mReloadFragment == null) {
//            mReloadFragment = new ReloadFragment();
//        }
//        return mReloadFragment;
////        ReloadFragment fragment = new ReloadFragment();
////        Bundle args = new Bundle();
//////        args.putInt("fragmentId", fragmentId);
//////        fragment.setArguments(args);
////        count++;
////        LogUtil.e(TAG,"ReloadFragment newInstance count="+count);
////        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle onSavedInstanceState) {
//        super.onCreate(onSavedInstanceState);
////        fragmentId = getArguments() != null? getArguments().getInt("fragmentId"):0;
//        LogUtil.e(TAG, "onCreate() fragmentId = " + fragmentId);
////        registerBroadcastReceiver();
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
////        if(r®getActivity().unregisterReceiver(receiver);
//    }
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        count--;
//        LogUtil.e(TAG,"onDetach count="+count);
////        if(r®getActivity().unregisterReceiver(receiver);
//    }
////    @Override
////    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        rootView = inflater.inflate(R.layout.layout_reload, container, false);
////        findView();
////        initView();
////        setOnClick();
////        return rootView;
////    }
//
////    private void registerBroadcastReceiver() {
////        IntentFilter filter = new IntentFilter();
////        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
////        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
////
////        receiver = new BroadcastReceiver() {
////            @Override
////            public void onReceive(Context context, Intent intent) {
////                //LogUtil.e(TAG, "receive an action in ReloadFragment ");
////                String action = intent.getAction();
////                if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
////                    LogUtil.e(TAG, " 网络状态发生变化 ");
////                    checkNetwork();
////                } else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
////                    LogUtil.e(TAG, " 开机完成 ");
////                    Intent newintent = new Intent(context, MainActivity.class);
////                    newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //注意，必须添加这个标记，否则启动会失败
////                    context.startActivity(newintent);
////                }
////            }
////        };
////        getActivity().registerReceiver(receiver, filter);
////
////    }
//    @Override
//    public void onActivityCreated(Bundle onSavedInstanceState) {
//        super.onActivityCreated(onSavedInstanceState);
//    }
//    @Override
//    protected void findViews() {
//        mReloadButton = (Button) rootView.findViewById(R.id.reload);
//        mGoToSetting = (Button) rootView.findViewById(R.id.goto_setting);
//    }
//    @Override
//    protected void initViews() {
//
//    }
//    @Override
//    protected void setOnClick() {
//        if(mReloadButton != null)
//            mReloadButton.setOnClickListener(this);
//        if(mGoToSetting !=null)
//            mGoToSetting.setOnClickListener(this);
//    }
//
//    public void checkNetwork() {
//        if(NetWorkUtil.isNetworkConnected()) {
//            new Handler().post(new Runnable() {
//                public void run() {
//                    replaceFragment(true);
//                }
//            });
//        }
//        else {
//            new Handler().post(new Runnable() {
//                public void run() {
//                    replaceFragment(false);
//                }
//            });
//        }
//    }
//    private void replaceFragment(boolean isNetworkConnected) {
//        if(getActivity() == null) {
//            LogUtil.e(TAG, "getActivity() == null  replaceFragment fragmentId ="+fragmentId);
//            return;
//        }
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        LogUtil.e(TAG, "replaceFragment fragmentId = "+fragmentId);
//        //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        if(isNetworkConnected) {
//            switch (fragmentId) {
//                case 0:
//                    Fragment mainPageFragment = new MainPageFragment();
//                    // Replace whatever is in the fragment_container view with this fragment,
//                    // and add the transaction to the back stack if needed
//                    transaction.replace(R.id.mainpage_fragment_root_id, mainPageFragment);
//                    //Create new fragment and transacnt);
//                    break;
//                case 1:
//                    Fragment shoppingListPageFragment = new ShoppingListPageFragment();
//                    transaction.replace(R.id.shoppinglist_fragment_root_id, shoppingListPageFragment);
//                    break;
//                case 2:
//                    Fragment myPageFragment = new MyPageFragment();
//                    transaction.replace(R.id.myPage_fragment_root_id, myPageFragment);
//                    break;
//                default:
//                    throw new IllegalArgumentException("illegal fragmentId exception");
//            }
//        }
//        transaction.addToBackStack(null);
//        // Commit the transaction
//        transaction.commit();
//    }
//
//    public void onClick(View view) {
//        int id = view.getId();
//
//        switch (id) {
//            case R.id.reload:
//                LogUtil.e(TAG,"reload button pressed");
//                checkNetwork();
//                break;
//            case R.id.goto_setting:
//                startActivity(new Intent(Settings.ACTION_SETTINGS));
//                break;
//            default:
//                break;
//        }
//    }
//
//
//}
