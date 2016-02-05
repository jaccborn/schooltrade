package com.st.app;


import android.app.Activity;
import android.os.Bundle;

public class FujinActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fujin_layout);
		Exit.activityList.add(this);

	}

}
