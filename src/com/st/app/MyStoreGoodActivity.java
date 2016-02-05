package com.st.app;


import com.st.asynctask.LoadHeadImage;
import com.st.asynctask.LoadStoreImage;
import com.st.asynctask.MyStoreIfAsyncTask;
import com.st.util.BaseUrl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyStoreGoodActivity extends Activity {
	
	
	private TextView storename;
	private TextView storeowner;
	private TextView storetel;
	private TextView storeplace;
	private TextView storeregistertime;
	
	private ImageView imageView;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mystorelayout);
		
		storename = (TextView) findViewById(R.id.mystore_name);
		storeowner = (TextView) findViewById(R.id.mystore_owner);
		storetel = (TextView) findViewById(R.id.mystore_tel);
		storeplace = (TextView) findViewById(R.id.mystore_place);
		storeregistertime = (TextView) findViewById(R.id.mystore_registertime);
		
		imageView=(ImageView) findViewById(R.id.mystore_image);
		
		
		//加载店铺图片  这里暂且先和登录头像一致
		new LoadStoreImage(getApplicationContext()).ShowImageByAsyncTask(imageView, BaseUrl.baseheadimageurl);
		//加载店铺的信息
		new MyStoreIfAsyncTask(getApplicationContext(),storename,storeowner,storetel,storeplace,storeregistertime).execute();
	

		Exit.activityList.add(this);
	}

}
