package com.st.app;



import com.st.asynctask.LoginAsyncTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private Button btlogin, btback;
	private TextView btregister;
	private EditText username, passwd;

	private Handler mHandler;
	
	

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);

		initView();

		Exit.activityList.add(this);

		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// 接收消息
				if (msg.obj == "loginsuccess") 
				{
					finish();
				}
			}
		};

	}

	private void initView() {
		btlogin = (Button) this.findViewById(R.id.bt_login);
		btback = (Button) this.findViewById(R.id.bt_back);
		btregister = (TextView) this.findViewById(R.id.bt_register);

		username = (EditText) findViewById(R.id.et_username);
		passwd = (EditText) findViewById(R.id.et_password);

		btlogin.setOnClickListener(this);
		btback.setOnClickListener(this);
		btregister.setOnClickListener(this);
	
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_back:
			finish();
			break;
		case R.id.bt_login:
			String user = username.getText().toString();
			String pwd = passwd.getText().toString();
			if (user.length()==0&&user.isEmpty()) {
				Toast.makeText(getApplicationContext(), "账号不可为空！",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (pwd.length()==0&&pwd.isEmpty()) {
				Toast.makeText(getApplicationContext(), "密码不可为空！",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (user.length()!=0&&!user.isEmpty()&&pwd.length()!=0&&!pwd.isEmpty()) {
				new LoginAsyncTask(getApplicationContext(), user, pwd,mHandler)
						.execute();

				break;
			}

		case R.id.bt_register:
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			finish();
			break;
		
		default:
			break;
		}

	}
	
	
		
		
		
		
		
	
	

}
