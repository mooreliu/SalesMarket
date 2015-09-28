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
import com.mooreliu.db.model.MerchandiseModel;
import com.mooreliu.listener.OnProductClickListener;
import com.mooreliu.util.DiskLruCacheUtil;
import com.mooreliu.util.LruCacheUtil;
import com.mooreliu.util.TextUtil;

import java.util.List;

import libcore.io.DiskLruCache;

/**
 * Created by liuyi on 15/8/30.
 * recyclerView
 */
public class CustomRecyclerListAdapter extends RecyclerView.Adapter<CustomRecyclerListAdapter.ViewHolder> {
	private static final String TAG = "CustomRecyclerListAdapter";
	private List<MerchandiseModel> mProductList;
	private OnProductClickListener mOnProductClickListener;
	private LruCache<String, Bitmap> mLruBitmapCache;
	private DiskLruCache mDiskLruCache;
	private RecyclerView mRecyclerView;
	private Context mContext;
	private Resources mResources;

	public CustomRecyclerListAdapter(RecyclerView rv, Context context, List<MerchandiseModel> list, Resources resources) {
		mRecyclerView = rv;
		mProductList = list;
		mResources = resources;
		mContext = context;
		mLruBitmapCache = AppContext.getLruBitmapCache(); // 初始化LruCache
		mDiskLruCache = AppContext.getDistLruCache(); // 初始化DiskLruCache
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
		//LogUtil.e(TAG,"onCreateViewHolder");
		View v = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.layout_product_recyclerview_item, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public int getItemCount() {
		return mProductList.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int postion) {
		MerchandiseModel mMerchandiseModel = mProductList.get(postion);
		viewHolder.productModel = mMerchandiseModel;
		String key = TextUtil.hashKeyForDisk(mMerchandiseModel.getmerchandiseImageUrl());
		Bitmap mBitmap = LruCacheUtil.get(key);
		if (mBitmap != null) {// 在LruCache中查询
			viewHolder.imageView.setImageBitmap(mBitmap);
			return;
		} else { // 如果没有在LruCache中
			Bitmap bitmap = DiskLruCacheUtil.get(key);
			viewHolder.imageView.setImageBitmap(bitmap);
			if (bitmap != null) {
				LruCacheUtil.add(key, bitmap);
				return;
			} else {
				// 如果都没有的话，就去URL下载
				viewHolder.imageView.setImageResource(R.drawable.placeholder);
				viewHolder.imageView.setTag(key);
				downloadBitmapTask downloadBitmap = new downloadBitmapTask();
				downloadBitmap.execute(mMerchandiseModel);
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
		MerchandiseModel productModel;
		ImageView imageView;

		public ViewHolder(View itemView) {
			super(itemView);
			imageView = (ImageView) itemView.findViewById(R.id.product_image);
			imageView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			mOnProductClickListener.onTouch(view, productModel);

		}
	}

	class downloadBitmapTask extends AsyncTask<MerchandiseModel, Void, Bitmap> {
		String url = null;

		@Override
		protected Bitmap doInBackground(MerchandiseModel... parms) {
			this.url = parms[0].getmerchandiseImageUrl();
			String key = TextUtil.hashKeyForDisk(this.url);
			return DiskLruCacheUtil.addToDiskLruCache(key, this.url);
		}

		@Override
		public void onPostExecute(Bitmap bitmap) {
			String key = TextUtil.hashKeyForDisk(this.url);
			ImageView imageView = (ImageView) mRecyclerView.findViewWithTag(key);
			if (bitmap != null && imageView != null) {
				imageView.setImageBitmap(bitmap);
				LruCacheUtil.add(key, bitmap);
			} else {
				if (imageView != null)
					imageView.setImageResource(R.drawable.placeholder);
			}
		}

	}


}
