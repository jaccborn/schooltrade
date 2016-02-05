package com.st.activity;

import org.apache.http.Header; 
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.st.app.R;
import com.st.http.JsonHttpResponseHandler;
import com.st.http.RequestParams;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

public class XZTApplication extends Application {

	public void onCreate() {
		super.onCreate();
		NetworkUtil.initNetwork(getApplicationContext());
		CacheTools.initCache(getApplicationContext());
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.gongneng)
				.showImageOnFail(R.drawable.ic_error)
				.resetViewBeforeLoading(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).build();
		ImageLoader.getInstance().init(config);

		// 查看用户是否已经有了userid

		updateVersionYesOrNot(this);
		
		

	}
	
	
	
	// 是否有版本更新
	public void updateVersionYesOrNot(final Context context) {
		PackageManager manager = this.getPackageManager();
		String appVersion = "";
		try {
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			appVersion = info.versionName;
		} catch (NameNotFoundException e) {// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestParams params = new RequestParams();
		params.add("client", "android");
		params.add("version", appVersion);

		NetworkUtil.post("/web/updateversion", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						// TODO Auto-generated method stub
						Log.i("appVersion", "path--000");
						try {
							int errorcode = response.getInt("errorcode");
							Log.i("appVersion", "path--12");
							if (errorcode == 0) {
								String path = response.getString("path");
								Log.i("appVersion", "path--" + path);
								int isnewversion = response
										.getInt("isnewversion");
								if (isnewversion > 0 && !path.equals("")) {
									Log.i("appVersion", "appVersion--1234");
									Intent intent = new Intent(context,
											UpdateVersionActivity.class);
									intent.putExtra("path", path);
									context.startActivity(intent);

								}

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						// TODO Auto-generated method stub
						super.onFailure(statusCode, headers, throwable,
								errorResponse);
					}
				});
	}

	//
	// private String randomNumber() {
	// // 产生随机的数字和字母的组合 
	// String[] readomHard = new String[20];
	// int readomWordIndex = (int) (Math.random() * 19);
	// for (int j = 0; j < 20; j++) {
	// int readomWordNum = (int) (Math.random() * 10);
	// char readomLetter = (char) (Math.random() * 26 + 'a');
	// if (readomWordNum % 2 == 0) {
	// readomHard[j] = readomWordNum + "";
	// } else {
	// readomHard[j] = String.valueOf(readomLetter);
	// }
	// }
	// String a = readomHard[readomWordIndex];
	//
	// return a;
	// }
	//

}
