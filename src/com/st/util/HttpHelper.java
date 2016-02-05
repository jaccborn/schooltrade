package com.st.util;


import java.io.File; 

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.st.asynctask.StaggeredGridViewImageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class HttpHelper {
	private static File imageFile;

	public static boolean hasSDCard(){
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageDirectory());
	}
	
	/**
	 * 下载图片到SDcard缓存起来
	 * @param imageUrl
	 * @param columnWidth
	 */
	public static void downloadImage(final String imageUrl,final int Width,final int Height){
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Log.d("MainActivity", "monted sdcard");
		}else{
			Log.d("MainActivity", "has no sdcard");
		}
		HttpUtils httpUtils=new HttpUtils(10000);
		int lastSlashIndex=imageUrl.lastIndexOf("/");
		String imageName=imageUrl.substring(lastSlashIndex+1);
		String target=Environment.getExternalStorageDirectory().getPath()+"/SchoolTrade/"+imageName;
		httpUtils.download(imageUrl, target, new RequestCallBack<File>() {
			
			@Override
			public void onSuccess(ResponseInfo<File> responseInfo) {
				if (null != responseInfo) {
					imageFile=responseInfo.result;
					if (imageFile != null) {
						//1、根据宽度缩放图片
						//Bitmap bmp=ImageLoader.decodeSampleBitmapFormResource(imageFile.getPath(), columnWidth);
						
//						2、根据宽高缩放图片
						Bitmap bmp=ImageUtil.decodeBitmap(imageFile.getPath(), Width, Height);
//						BitmapFactory.Options options=new BitmapFactory.Options();
//						Bitmap bmp=BitmapFactory.decodeFile(imageFile.getPath(), options);
						if (bmp != null) {
							StaggeredGridViewImageLoader imagerLoader=StaggeredGridViewImageLoader.getInstance();
							imagerLoader.addBitmapToCache(imageUrl, bmp);
						}
					}
				}
			}
			
			@Override
			public void onFailure(HttpException e, String msg) {
				Log.e("MainActivity", "====error===="+e);
			}
		});
		
	}
	
	/**
	 * 获取图片的本地存储路径
	 * @param imageUrl
	 * @return
	 */
	public static String getImagePath(String imageUrl){
		int lastSlashIndex=imageUrl.lastIndexOf("/");
		String imageName=imageUrl.substring(lastSlashIndex+1);
		String imageDir=Environment.getExternalStorageDirectory().getPath()+"/SchoolTrade/";
		File file=new File(imageDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		String imagePath=imageDir+imageName;
		return imagePath;
	}
}
