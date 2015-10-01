package com.mooreliu;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.mooreliu.service.ServiceCheckNetConnect;
import com.mooreliu.util.Config;
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
    private static final String TAG = "AppContext";
    private static DiskLruCache DiskLruCache;
    private static LruCache<String, Bitmap> LruBitmapCache;
    private PackageInfo mPackageInfo;
    private static String PACKAGE_NAME;
    private static String VERSION_NAME;
    private static int VERSION_CODE;
    private static Context context;
    private static HashMap<String, WeakReference<Activity>> mContext = new HashMap<String, WeakReference<Activity>>();
    private List<Activity> mListAcitivity = new ArrayList<Activity>();
    private RefWatcher mRefWatcher;
    private AppContext mInstance;

    public final static boolean DEBUG = BuildConfig.DEBUG;

    public static LruCache<String, Bitmap> getLruBitmapCache() {
        return LruBitmapCache;
    }

    public static DiskLruCache getDistLruCache() {
        return DiskLruCache;
    }

    public static RefWatcher getRefWatcher(Context context) {
        AppContext app = (AppContext) context.getApplicationContext();
        return app.mRefWatcher;
    }

    public static Context getContext() {
        return context;
    }

    public static synchronized void setActiveContext(Activity context) {
        WeakReference<Activity> reference = new WeakReference<Activity>(context);
        mContext.put(context.getClass().getSimpleName(), reference);
    }

    public static synchronized Activity getActiveContext(String className) {
        WeakReference<Activity> reference = mContext.get(className);
        if (reference == null) {
            return null;
        }

        final Activity context = reference.get();
        if (context == null) {
            mContext.remove(className);
            return null;
        }

        return context;
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
        initAVOS();
        beginTestService();
        mRefWatcher = LeakCanary.install(this);
    }

    private void initAVOS() {
        AVOSCloud.initialize(this, Config.APP_ID, Config.APP_KEY);
        AVAnalytics.enableCrashReport(this, true);
    }

    private void initLruCache() {
        int cacheSize = (int) Runtime.getRuntime().maxMemory() / 8;
        LruBitmapCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            public int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    private void initDiskLruCache() {
        try {
            File cacheDir = StorageUtil.getDiskCacheDir(context, "product");
            if (!cacheDir.exists())
                cacheDir.mkdir();
            DiskLruCache = DiskLruCache.open(cacheDir, AppContext.VERSION_CODE, 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void beginTestService() {
        Intent intent = new Intent(AppContext.getContext(), ServiceCheckNetConnect.class);
        startService(intent);
        LogUtil.e(TAG, "startService(intent);");
    }

    public AppContext getInstance() {
        return mInstance;
    }

    private void init() {
        mInstance = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initPackage() {
        PackageManager pm = getPackageManager();
        try {
            mPackageInfo = pm.getPackageInfo(getPackageName(), 0);
            PACKAGE_NAME = mPackageInfo.packageName;
            VERSION_CODE = mPackageInfo.versionCode;
            VERSION_NAME = mPackageInfo.versionName;
//            LogUtil.e("initPackge",VERSION_CODE+": "+VERSION_NAME);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addActivity(Activity activity) {
        mListAcitivity.add(activity);

    }

    public synchronized void removeActivity(Activity activity) {
        if (mListAcitivity.contains(activity))
            mListAcitivity.remove(activity);
    }

}
