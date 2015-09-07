package com.mooreliu;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mooreliu.service.ServiceCheckNetConnect;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.StorageUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import libcore.io.DiskLruCache;
import timber.log.Timber;

/**
 * Created by liuyi on 15/8/28.
 */
public class AppContext extends Application {
    public static final String TAG ="AppContext";

    public final static boolean DEBUG= BuildConfig.DEBUG;
    private List<Activity> acitivityList = new ArrayList<Activity>();
    public static  HashMap<String ,WeakReference<Activity>> mContext =
            new HashMap<String, WeakReference<Activity>>();
    private RefWatcher refWatcher;
    public static DiskLruCache mDiskLruCache;
    public static LruCache<String ,Bitmap> mLruBitmapCache;
    private AppContext mInstance;

    public static PackageInfo mpackageInfo ;
    public static String packageName;
    public static String versionName;
    public static int versionCode;
    public static Context context;

    public static LruCache<String ,Bitmap> getLruBitmapCache() {
        return mLruBitmapCache;
    }
    public static DiskLruCache getDistLruCache() {
        return mDiskLruCache;
    }
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
        initDiskLruCache();
        initLruCache();
        refWatcher = LeakCanary.install(this);
    }

    private void initLruCache() {
        int cacheSize = (int)Runtime.getRuntime().maxMemory()/8;
        mLruBitmapCache = new LruCache<String ,Bitmap>(cacheSize) {
            @Override
            public int sizeOf(String key,Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }
    private void initDiskLruCache() {
        try {
            File cacheDir = StorageUtil.getDiskCacheDir(context, "product");
            if (!cacheDir.exists())
                cacheDir.mkdir();
            mDiskLruCache =DiskLruCache.open(cacheDir , AppContext.versionCode ,1 ,10*1024*1024);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void beginTestService() {
//        Intent intent = new Intent("com.mooreliu.subService");
        Intent intent = new Intent(AppContext.getContext(), ServiceCheckNetConnect.class);
        startService(intent);
        LogUtil.e(TAG, "startService(intent);");
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
