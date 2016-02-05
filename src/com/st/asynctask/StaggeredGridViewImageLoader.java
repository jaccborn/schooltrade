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

	// ����lrucache����ͼƬ���Խ�ʡ�����Լ�����listview����������
	LruCache<String, Bitmap> mCache;

	Set<ImageAsyncTask> mtTask;
	static StaggeredGridView mgridView;
	static StaggeredGridViewImageLoader mImagerLoader;

	public StaggeredGridViewImageLoader(StaggeredGridView gridView) {

		mgridView = gridView;
		mtTask = new HashSet<>();

		// ��һ�����ڴ�ռ�ת��Ϊ����ռ�
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cachesize = maxMemory / 4;
		mCache = new LruCache<String, Bitmap>(cachesize) {
			protected int sizeOf(String key, Bitmap value) {
				// ÿ�δ��뻺���ʱ�����
				return value.getByteCount();
			};

		};

	}

	// ���ӵ�����
	public void addBitmapToCache(String url, Bitmap bitmap) {
		if (getBitmapFromCache(url) == null) {
			mCache.put(url, bitmap);
		}

	}

	// �ӻ����л�ȡ����
	public Bitmap getBitmapFromCache(String url) {

		return mCache.get(url);
	}

	// ���ش�start��end������е�����ͼƬ
	public void loadImages(int start, int end) {
		for (int i = start; i < end; i++) {
			String url = GoodsListAdapter.urls[i];
			// �ӻ����л�ȡͼƬ
			Bitmap bitmap = getBitmapFromCache(url);
			// ���û���� ��������ͼƬ
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

	// ʹ��asynctask����

	public void ShowImageByAsyncTask(ImageView imageView, String url) {

		// �ӻ����л�ȡͼƬ
		Bitmap bitmap = getBitmapFromCache(url);
		// ���û���� ��������ͼƬ
		if (bitmap == null)
			new ImageAsyncTask(url).execute(url); // new
													// NewsAsyncTask(url).execute(url)
													// ����ȥgetView��ʱ������ͼƬ
													// �����ڹ�����ʱ���ȡ
		// imageView.setImageResource(R.drawable.ic_launcher);
		else
			imageView.setImageBitmap(bitmap);

	}

	
	/**
	 * ��ȡImageLoader��ʵ��
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
			// �������ȡͼƬ
			Bitmap bitmap = getBitmapfromURL(params[0]);
			if (bitmap != null) {
				// �����ڻ����ͼƬ���뻺��
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
