package com.st.app;


import android.app.Activity;
import android.os.Bundle;

public class DianpuActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dianpu_layout);
		Exit.activityList.add(this);

	}

}
