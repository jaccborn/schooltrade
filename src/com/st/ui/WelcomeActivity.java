package com.st.ui;

import com.st.app.HomePage_tabhostActivity;
import com.st.app.LoginActivity;
import com.st.app.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class WelcomeActivity extends Activity {

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcom_layout);
		mContext = this;
		handler.sendEmptyMessageDelayed(1,3000);

	}

	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			startActivity(new Intent(mContext, LoginActivity.class));
			finish();
		};
	};
}
