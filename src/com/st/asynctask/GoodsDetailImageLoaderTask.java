package com.st.asynctask;

import java.io.File;

import com.st.adapter.GoodsDetailAdapter;
import com.st.util.HttpHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class GoodsDetailImageLoaderTask extends AsyncTask<String, Void, Bitmap> {

	private String mImageUrl;
	private StaggeredGridViewImageLoader imagerLoader;
	private ImageView iv;

	private int Width;
	private int Height;

	public GoodsDetailImageLoaderTask(int Width, int Height, ImageView imageview) {
		if (imagerLoader == null) {
			imagerLoader = StaggeredGridViewImageLoader.getInstance();
		}

		this.Width = Width;
		this.Height = Height;
		iv = imageview;

	}

	protected Bitmap doInBackground(String... params) {
		// 子线程中加载图片
		mImageUrl = params[0];
		System.out.println("imageurl:" + mImageUrl);
		return loadImage(mImageUrl);
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		// 显示图片
		System.out.println("即将显示图片");
		if (null != bitmap) {

			addImage(bitmap);

		}
	}

	private Bitmap loadImage(String imageUrl) {
		// 1.内存有没有图片
		Bitmap bitmap = imagerLoader.getBitmapFromCache(imageUrl);
		if (bitmap != null) {
			System.out.println("从内存中取图片");
			return bitmap;
		}
		// 2.SD卡里面有没有图片
		File imageFile = new File(HttpHelper.getImagePath(imageUrl));
		System.out.println("imageFile:" + imageFile);
		// 3.如果SD卡都没有图片，去网络加载
		if (!imageFile.exists()) {
			HttpHelper.downloadImage(imageUrl, Width, Height);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 去缓存里面获取图片
		if (null != imageUrl) {

			// bitmap = ImageLoader.decodeSampleBitmapFormResource(
			// imageFile.getPath(), Width);
			// if (null != bitmap) {
			// return bitmap;
			// }else{
			// bitmap = ImageLoader.decodeSampleBitmapFormResource(
			// imageFile.getPath(), Width);
			// return bitmap;
			// }
			// bitmap = ImageLoader.decodeBitmap(imageFile.getPath(), Width,
			// Height);
			// if (null != bitmap) {
			// return bitmap;
			// } else {
			// bitmap = ImageLoader.decodeBitmap(imageFile.getPath(), Width,
			// Height);
			// return bitmap;
			// }
			bitmap = BitmapFactory.decodeFile(imageFile.getPath());
			return bitmap;
		}
		return null;
	}

	public void addImage(Bitmap bitmap) {
		// 添加图片 -- 显示

		iv.setImageBitmap(bitmap);
		iv.setScaleType(ScaleType.FIT_XY); // 填充整个ImageView

	}

}
