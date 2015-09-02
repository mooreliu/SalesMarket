package com.mooreliu;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

/**
 * Created by liuyi on 15/8/28.
 */
public class AppContext extends Application {
    public final static boolean DEBUG= BuildConfig.DEBUG;
    private List<Activity> acitivityList = new ArrayList<Activity>();
    public static  HashMap<String ,WeakReference<Activity>> mContext =
            new HashMap<String, WeakReference<Activity>>();
    private RefWatcher refWatcher;
    private AppContext mInstance;

    public static PackageInfo mpackageInfo ;
    public static String packageName;
    public static String versionName;
    public static int versionCode;
    public static Context context;


    public static RefWatcher getRefWatcher(Context context) {
        AppContext  app = (AppContext) context.getApplicationContext();
        return app.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Fresco.initialize(context);
        init();
        initPackage();
        refWatcher = LeakCanary.install(this);
    }

    public static Context getContext() {
        return  context;
    }

    public AppContext getInstance() {
        return mInstance;

    }

    private void init() {
        mInstance = this;
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initPackage() {
        PackageManager pm = getPackageManager();
        try {
            mpackageInfo = pm.getPackageInfo(getPackageName(), 0);
            packageName = mpackageInfo.packageName;
            versionCode = mpackageInfo.versionCode;
            versionName = mpackageInfo.versionName;
//            LogUtil.e("initPackge",versionCode+": "+versionName);
        } catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static synchronized void setActiveContext(Activity context) {
        WeakReference<Activity> reference  = new WeakReference<Activity>(context);
        mContext.put(context.getClass().getSimpleName() ,reference);
    }

    public static synchronized Activity getActiveContext(String className) {
        WeakReference<Activity> reference = mContext.get(className);
        if(reference == null) {
            return null;
        }

        final Activity context = reference.get();
        if(context == null) {
            mContext.remove(className);
            return null;
        }

        return  context;
    }

    public synchronized void addActivity(Activity activity) {
        acitivityList.add(activity);

    }

    public synchronized void removeActivity(Activity activity) {
        if(acitivityList.contains(activity))
            acitivityList.remove(activity);
    }

}
