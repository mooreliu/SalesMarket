package com.mooreliu.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import com.mooreliu.util.DiskLruCacheUtil;
import com.mooreliu.util.LogUtil;
import com.mooreliu.util.LruCacheUtil;
import com.mooreliu.util.TextUtil;

import java.util.List;

import libcore.io.DiskLruCache;

/**
 * Created by liuyi on 15/8/30.
 * recyclerView
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
        mLruBitmapCache = AppContext.getLruBitmapCache();//初始化LruCache
        mDiskLruCache = AppContext.getDistLruCache();//初始化DiskLruCache
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
        viewHolder.productModel = mProductModel;
        String key = TextUtil.hashKeyForDisk(mProductModel.getUrl());
        Bitmap mBitmap = LruCacheUtil.get(key);


        if(mBitmap !=null) {//在LruCache中查询
            viewHolder.imageView.setImageBitmap(mBitmap);
            LogUtil.e(TAG,"in LruCache"+key);
            return;
        } else { //如果没有在LruCache中
            Bitmap bitmap = DiskLruCacheUtil.get(key);
            viewHolder.imageView.setImageBitmap(bitmap);
            if(bitmap != null) {

                LruCacheUtil.add(key, bitmap);
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
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.product_image);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnProductClickListener.onTouch(view,productModel);

        }
    }

    class  downloadBitmapTask extends AsyncTask<ProductModel, Void , Bitmap> {
        String url = null;
        int resId = -1;
        @Override
        protected Bitmap doInBackground(ProductModel ...parms) {
            this.url = parms[0].getUrl();
            this.resId = parms[0].getResId();
//            LogUtil.e(TAG,"NO CACHE MUSH DOWNLOAD");
            String key = TextUtil.hashKeyForDisk(this.url);
            return DiskLruCacheUtil.addToDiskLruCache(key, this.url);
        }

        @Override
        public void onPostExecute(Bitmap bitmap) {
            String key = TextUtil.hashKeyForDisk(this.url);
            ImageView imageView =(ImageView) mRecyclerView.findViewWithTag(key);
            if(imageView == null) {
//                LogUtil.e(TAG,"findViewWithTag null****"+this.url);
            }
            if(bitmap == null) {
//                LogUtil.e(TAG,"BITmap is NULL");
            }
            if(bitmap !=null && imageView != null) {
                imageView.setImageBitmap(bitmap);
//                LogUtil.e(TAG,"addToLruCache "+key);
                LruCacheUtil.add(key, bitmap);

            } else{
                if(imageView != null)
                    imageView.setImageResource(R.drawable.placeholder);
            }
        }

    }


}
