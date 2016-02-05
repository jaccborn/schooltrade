package com.st.app;


import com.st.asynctask.LoadHeadImage;
import com.st.custom.CircleImg;
import com.st.util.BaseUrl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class OtherActivity extends Activity {

	private RelativeLayout relativeLayout;
	private Button button; 
	
	private CircleImg headimage;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.other_layout);

		button = (Button) findViewById(R.id.extbtn);
		headimage=(CircleImg) findViewById(R.id.headimage);
		
		new LoadHeadImage(getApplicationContext()).ShowImageByAsyncTask(headimage, BaseUrl.baseheadimageurl);
		
		
		relativeLayout = (RelativeLayout) findViewById(R.id.banbengengxin);
		relativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new Builder(OtherActivity.this);

				builder.setMessage("已是最新版本")
						.setCancelable(false)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										dialog.cancel();

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alertDialog = builder.create();
				alertDialog.show();

			}
		});
	
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Exit.exitClient(OtherActivity.this);
				new AlertDialog.Builder(OtherActivity.this)
						.setTitle("确认退出吗？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 点击“确认”后的操作
										OtherActivity.this.finish();

									}
								})
						.setNegativeButton("返回",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 点击“返回”后的操作,这里不设置没有任何操作
									}
								}).show();
				// super.onBackPressed();
			}

		});
		
	}
}