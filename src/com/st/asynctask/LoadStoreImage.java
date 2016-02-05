package com.st.asynctask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.st.custom.CircleImg;
import com.st.util.DBOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

public class LoadStoreImage {

	// 利用lrucache缓存图片可以节省流量以及提升listview滑动流畅性
	LruCache<String, Bitmap> mCache;

	String imagename;
	Context context;
	Handler mHandler ;

	public LoadStoreImage(Context context) {

		this.context = context;

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

	private void getimagename() {

		new Thread() {
			public void run() {

				DBOpenHelper dbuser = new DBOpenHelper(context, "userif.db");
				String sqlquery = "select * from userif";
				SQLiteDatabase db = dbuser.getWritableDatabase();
				Cursor cursor = db.rawQuery(sqlquery, null);

				if (cursor != null) {
					while (cursor.moveToNext()) {

						imagename = cursor.getString(cursor
								.getColumnIndex("registerimage"));

					}
				}

				dbuser.close();

				
				
				Message msg=new Message();
				msg.obj=imagename;
				mHandler.sendMessage(msg);
				
			}
		}.start();

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
		} 

		return null;
	}

	// 使用asynctask加载

	public void ShowImageByAsyncTask(ImageView  mystoreimage, String url) {

		getimagename();
		
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				// 接收消息
			
					
					imagename=(String) msg.obj;
					System.out.println("得到storeimagename：" + imagename);
				
			}
		};
		
		try {
			Thread.sleep(1000);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 从缓存中获取图片
		// 这里是一个拼接的url  因为每个人的头像不同所以要从服务器获取一下头像图片
		Bitmap bitmap = getBitmapFromCache(url+imagename);
		// 如果没有则 必须下载图片
		if (bitmap == null) {
			new ImageAsyncTask(mystoreimage).execute(url+imagename);
		} else {
			
			mystoreimage.setImageBitmap(bitmap);
		}

	}

	private class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
		ImageView storeimage;

		public ImageAsyncTask(ImageView storeimage) {
			this.storeimage = storeimage;
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

			if (storeimage != null && result != null) {

				storeimage.setImageBitmap(result);
			}

		}

	}

}
