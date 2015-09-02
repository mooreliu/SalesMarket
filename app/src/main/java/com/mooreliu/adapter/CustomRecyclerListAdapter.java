package com.mooreliu.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mooreliu.AppContext;
import com.mooreliu.R;
import com.mooreliu.model.ProductModel;
import com.mooreliu.util.BitmapUtil;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.StorageUtil;
import com.mooreliu.util.TextUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import libcore.io.DiskLruCache;

/**
 * Created by liuyi on 15/8/30.
 * recyclerView鏄墍鏈夊厓绱犲悓鏃跺埛鏂帮紝杩樻槸鎸夌収鍙鎯呭喌鏉ワ紵锛�
 */
public class CustomRecyclerListAdapter extends RecyclerView.Adapter<CustomRecyclerListAdapter.ViewHolder>{
    private static final String TAG = "CustomRecyclerListAdapter";
    private List<ProductModel> mProductList;
    private OnProductClickListener mOnProductClickListener;
    private Resources mResources;
    private LruCache<String ,Bitmap> mLruBitmapCache;
    private DiskLruCache mDiskLruCache;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public CustomRecyclerListAdapter(RecyclerView rv ,Context context , List<ProductModel> list , Resources resources) {
        mRecyclerView = rv;
        mProductList = list;
        mResources = resources;
        mContext = context;
        initLruCache();
        initDiskLruCache();


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
            File cacheDir = StorageUtil.getDiskCacheDir(mContext , "product");
            if (!cacheDir.exists())
                cacheDir.mkdir();
            mDiskLruCache =DiskLruCache.open(cacheDir , AppContext.versionCode ,1 ,10*1024*1024);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent ,int position) {
        //LogUtil.e(TAG,"onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_product_recyclerview_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder ,int postion) {
        ProductModel mProductModel = mProductList.get(postion);
        String key = TextUtil.hashKeyForDisk(mProductModel.getUrl());
       // LogUtil.e(TAG,"getResId :"+mProductModel.getResId());
       // viewHolder.imageView.setImageBitmap(
       //         BitmapUtil.bitmapDecode(mResources, mProductModel.getResId(), 400, 400));
        Bitmap mBitmap = getBitmapFromLruCache(key);
        DiskLruCache.Snapshot snapShot = null;
        try {
            if(mDiskLruCache != null)
                snapShot = mDiskLruCache.get(key);
        }catch (IOException e) {
            e.printStackTrace();
        }
        if(mBitmap !=null) {//在LruCache中查询
            viewHolder.imageView.setImageBitmap(mBitmap);
            LogUtil.e(TAG,"in LruCache");
            return;
        } else { //如果没有在LruCache中
            if(snapShot != null) { //在DiskLruCache中查询
                LogUtil.e(TAG,"in DiskLruCache");
                InputStream is = snapShot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                viewHolder.imageView.setImageBitmap(bitmap);
                //然后再放入到LruCache中
                addBitmapToLruCache(key,bitmap);
                return;
            } else {
                //如果都没有的话，就去URL下载
                viewHolder.imageView.setImageResource(R.drawable.placeholder);
                viewHolder.imageView.setTag(key);
                downloadBitmapTask downloadBitmap = new downloadBitmapTask();
                downloadBitmap.execute(mProductModel);
            }
        }

    }
    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.imageView != null) holder.imageView.setImageBitmap(null);
    }

    public void setOnProductClick(OnProductClickListener listener) {
        mOnProductClickListener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ProductModel productModel;
//        SimpleDraweeView imageView;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
//            imageView = (SimpleDraweeView)itemView.findViewById(R.id.product_image);
            imageView = (ImageView)itemView.findViewById(R.id.product_image);

            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnProductClickListener.onTouch(view,productModel);

        }
    }

    public Bitmap getBitmapFromLruCache(String key) {
        //url ==key
        if(key != null)
            return mLruBitmapCache.get(key);
        return null;
    }
    public void addBitmapToLruCache(String key ,Bitmap bitmap) {
        if (getBitmapFromLruCache( key) == null)
            mLruBitmapCache.put(key ,bitmap);

    }
    class  downloadBitmapTask extends AsyncTask<ProductModel, Void , Bitmap> {
        Bitmap mbitmap = null;
        String url = null;
        int resId = -1;
        @Override
        protected Bitmap doInBackground(ProductModel ...parms) {
            this.url = parms[0].getUrl();
            this.resId = parms[0].getResId();
            //LogUtil.e(TAG,"onInBackGround"+url);
            //mbitmap = BitmapUtil.bitmapFromUrl(this.url,40,40);
            LogUtil.e(TAG,"NO CACHE MUSH DOWNLOAD");
            String key = TextUtil.hashKeyForDisk(this.url);
            try {
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    mbitmap = BitmapUtil.bitmapFromUrl(this.url,40,40,outputStream);
                    if (mbitmap !=null) {
                        editor.commit();
                        LogUtil.e(TAG, "Editor.commit()");
                    } else {
                        LogUtil.e(TAG,"Editor.abort()");
                        editor.abort();
                    }
                }
                mDiskLruCache.flush();
                LogUtil.e(TAG, "mDiskLruCache.flush()");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(null !=mbitmap ){
                return mbitmap;
            }
            return null;
        }

        @Override
        public void onPostExecute(Bitmap bitmap) {
            String key = TextUtil.hashKeyForDisk(this.url);
           // ImageView imageView =(ImageView) mRecyclerView.findViewWithTag(this.resId);
            ImageView imageView =(ImageView) mRecyclerView.findViewWithTag(key);
//            SimpleDraweeView imageView =(SimpleDraweeView) mRecyclerView.findViewWithTag(key);
            if(imageView == null) {
                LogUtil.e(TAG,"findViewWithTag null****"+this.url);
            }
            if(bitmap == null) {
                LogUtil.e(TAG,"BITmap is NULL");
            }
            if(bitmap !=null && imageView != null) {
                imageView.setImageBitmap(bitmap);
                LogUtil.e(TAG,"addToLruCache "+key);
                addBitmapToLruCache(key , bitmap);

            } else{
                if(imageView != null)
                    imageView.setImageResource(R.drawable.placeholder);
            }
        }

    }


}
