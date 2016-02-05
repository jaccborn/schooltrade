package com.st.activity;

import java.io.File; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



import com.st.app.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

public class UpdateVersionActivity extends Activity {
   
	private ProgressBar mProgress;
	private boolean mInterceptFlag = false;
	private AlertDialog mDownloadDialog;
	private String mNewVersionUrl="";
	private int mProgressnum;
	private Handler mUIHandler;
	protected static final int DOWN_APK = 0;
	protected static final int DOWN_APK_OVER = 1;
	private static final int MESSAGE_NEW_VERSION = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String path = intent.getStringExtra("path");
		if(!path.equals("")){
			mUIHandler = new UIHandler(null);
			mUIHandler.sendEmptyMessage(MESSAGE_NEW_VERSION);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		return true;
	}
	
	

	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("软件版本更新");
		final LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.updateprogress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);
		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				mInterceptFlag = true;
			}
		});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();

		downloadApk();
	}


	private void downloadApk() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(mNewVersionUrl);

					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					int length = conn.getContentLength();
					InputStream is = conn.getInputStream();

					File file = new File("/sdcard/download/");
					if (!file.exists()) {
						file.mkdir();
					}
					String apkFile = "/sdcard/download/XZT.apk";
					File ApkFile = new File(apkFile);
					FileOutputStream fos = new FileOutputStream(ApkFile);

					int count = 0;
					byte buf[] = new byte[1024];

					do {
						int numread = is.read(buf);
						count += numread;
						mProgressnum = (int) (((float) count / length) * 100);
						// 更新进度
						mUIHandler.sendEmptyMessage(DOWN_APK);
						if (numread <= 0) {
							// 下载完成通知安装
							mUIHandler.sendEmptyMessage(DOWN_APK_OVER);
							break;
						}
						fos.write(buf, 0, numread);
					} while (!mInterceptFlag);// 点击取消就停止下载

					fos.close();
					is.close();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	/**
	 * 安装apk
	 */
	
	private void installApk() {
		File apkfile = new File("/sdcard/download/XZT.apk");
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		startActivity(i);
	}
	
	private class UIHandler extends Handler {

		public UIHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_NEW_VERSION:
				new AlertDialog.Builder(UpdateVersionActivity.this).setTitle("提示")
						.setMessage("发现新的版本，是否立即更新到新版本？")
						.setPositiveButton("是", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								showDownloadDialog();
							}
						}).setNegativeButton("否", null).show();
				break;
			case DOWN_APK:
				mProgress.setProgress(mProgressnum);
				break;
			case DOWN_APK_OVER:
				installApk();
				break;
			}
		}
	}


}
