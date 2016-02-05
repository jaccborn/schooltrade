package com.st.app;


import android.app.Activity;
import android.os.Bundle;

public class ShoucangActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.shoucang_layout);
		Exit.activityList.add(this);

	}

}
