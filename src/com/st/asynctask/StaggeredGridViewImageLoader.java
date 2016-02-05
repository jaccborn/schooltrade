package com.st.asynctask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.origamilabs.library.views.StaggeredGridView;
import com.st.adapter.GoodsListAdapter;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

public class StaggeredGridViewImageLoader {

	ImageView mimageView;
	String murl;

	// 利用lrucache缓存图片可以节省流量以及提升listview滑动流畅性
	LruCache<String, Bitmap> mCache;

	Set<ImageAsyncTask> mtTask;
	static StaggeredGridView mgridView;
	static StaggeredGridViewImageLoader mImagerLoader;

	public StaggeredGridViewImageLoader(StaggeredGridView gridView) {

		mgridView = gridView;
		mtTask = new HashSet<>();

		// 将一部分内存空间转换为缓存空间
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cachesize = maxMemory / 4;
		mCache = new LruCache<String, Bitmap>(cachesize) {
			protected int sizeOf(String key, Bitmap value) {
				// 每次存入缓存的时候调用
				return value.getByteCount();
			};

		};

	}

	// 增加到缓存
	public void addBitmapToCache(String url, Bitmap bitmap) {
		if (getBitmapFromCache(url) == null) {
			mCache.put(url, bitmap);
		}

	}

	// 从缓存中获取数据
	public Bitmap getBitmapFromCache(String url) {

		return mCache.get(url);
	}

	// 加载从start到end这个序列的所有图片
	public void loadImages(int start, int end) {
		for (int i = start; i < end; i++) {
			String url = GoodsListAdapter.urls[i];
			// 从缓存中获取图片
			Bitmap bitmap = getBitmapFromCache(url);
			// 如果没有则 必须下载图片
			if (bitmap == null) {
				ImageAsyncTask task = new ImageAsyncTask(url);
				task.execute(url);
				mtTask.add(task);
			} else {
				ImageView imageView = (ImageView) mgridView
						.findViewWithTag(url);
				imageView.setImageBitmap(bitmap);
			}

		}

	}

	public void cancelAllTasks() {

		if (mtTask != null) {
			for (ImageAsyncTask task : mtTask) {
				task.cancel(true);
			}
		}

	}

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (mimageView.getTag().equals(murl)) {
				mimageView.setImageBitmap((Bitmap) msg.obj);
			}
		}
	};

	public void showImageByThread(ImageView imageView, final String url) {
		mimageView = imageView;
		murl = url;

		new Thread() {

			public void run() {
				if (mimageView.getTag() == url) {
					Bitmap bitmap = getBitmapfromURL(url);

					Message msg = Message.obtain();
					msg.obj = bitmap;
					handler.sendMessage(msg);
				}

			}

		}.start();

	}

	public Bitmap getBitmapfromURL(String urlstring) {
		Bitmap bitmap;
		InputStream is = null;
		try {
			URL url = new URL(urlstring);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			is = new BufferedInputStream(httpURLConnection.getInputStream());
			bitmap = BitmapFactory.decodeStream(is);

			httpURLConnection.disconnect();

			return bitmap;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return null;
	}

	// 使用asynctask加载

	public void ShowImageByAsyncTask(ImageView imageView, String url) {

		// 从缓存中获取图片
		Bitmap bitmap = getBitmapFromCache(url);
		// 如果没有则 必须下载图片
		if (bitmap == null)
			new ImageAsyncTask(url).execute(url); // new
													// NewsAsyncTask(url).execute(url)
													// 不再去getView的时候下载图片
													// 而是在滚动的时候获取
		// imageView.setImageResource(R.drawable.ic_launcher);
		else
			imageView.setImageBitmap(bitmap);

	}

	
	/**
	 * 获取ImageLoader的实例
	 * 
	 * @return
	 */
	public static StaggeredGridViewImageLoader getInstance() {
		if (mImagerLoader == null) {
			mImagerLoader = new StaggeredGridViewImageLoader(mgridView);
		}
		return mImagerLoader;
	}

	

	private class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
		ImageView mimageView;
		String murl;

		public ImageAsyncTask(String url) {

			// mimageView = imageView;
			murl = url;
		}

		protected Bitmap doInBackground(String... params) {
			// 从网络获取图片
			Bitmap bitmap = getBitmapfromURL(params[0]);
			if (bitmap != null) {
				// 将不在缓存的图片加入缓存
				addBitmapToCache(params[0], bitmap);
			}
			return bitmap;
		}

		protected void onPostExecute(Bitmap result) {

			super.onPostExecute(result);
			// if (mimageView.getTag().equals(murl)) {
			// mimageView.setImageBitmap(result);
			// }

			ImageView imageView = (ImageView) mgridView.findViewWithTag(murl);
			if (imageView != null && result != null) {
				imageView.setImageBitmap(result);
			}

			mtTask.remove(this);
		}

	}
	
	

}
