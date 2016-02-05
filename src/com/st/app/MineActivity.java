package com.st.app;


import com.st.asynctask.LoadHeadImage; 
import com.st.custom.CircleImg;
import com.st.util.BaseUrl;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MineActivity extends TabActivity implements OnTabChangeListener {

	private TabHost tabHost;
	private String[] TABSTRS;

	CircleImg headimage;

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_layout);

		
		headimage=(CircleImg) findViewById(R.id.headimage);
		

		
		initData();
		initView();
	
		new LoadHeadImage(getApplicationContext()).ShowImageByAsyncTask(headimage, BaseUrl.baseheadimageurl);
		Exit.activityList.add(this);
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initData() {
		TABSTRS = getResources().getStringArray(R.array.mine_tab_items);

	}

	private void initView() {
		tabHost = getTabHost();

		tabHost.setBackgroundResource(R.drawable.mine_bg);

		for (int i = 0, count = TABSTRS.length; i < count; i++) {
			TabSpec tabSpec = tabHost
					.newTabSpec(Mine_EnumTabInfo.getTabInfoByIndex(i).getTag())
					.setIndicator(getTabItemView(i))
					.setContent(getTabItemIntent(i));
			tabHost.addTab(tabSpec);
			tabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.selector_item_tabhome);
		}
		setTabItemSlectedShow(0);
		tabHost.setCurrentTab(0);
		tabHost.setOnTabChangedListener(this);

	}


	private View getTabItemView(int index) {
		View vItem = View.inflate(this, R.layout.item_tab_home, null);
		ImageView imgIcon = (ImageView) vItem
				.findViewById(R.id.itemtab_img_icon);
		TextView txtName = (TextView) vItem.findViewById(R.id.itemtab_txt_name);
		// imgIcon.setImageResource(Mine_EnumTabInfo.getTabInfoByIndex(index)
		// );
		txtName.setText(TABSTRS[index]);

		return vItem;
	}

	private Intent getTabItemIntent(int index) {
		Intent intent = new Intent();

		intent.setClass(this, Mine_EnumTabInfo.getTabInfoByIndex(index)
				.getClss());

		return intent;
	}

	private void setTabItemSlectedShow(int index) {

		for (int i = 0; i < TABSTRS.length; i++) {
			View vItem = tabHost.getTabWidget().getChildAt(i);
//			ImageView imgIcon = (ImageView) vItem
//					.findViewById(R.id.itemtab_img_icon);
			TextView txtName = (TextView) vItem
					.findViewById(R.id.itemtab_txt_name);
			if (i == index) {

				// imgIcon.setImageResource(Mine_EnumTabInfo.getTabInfoByIndex(i)
				// .getIconSelected());
				txtName.setTextColor(getResources().getColor(
						R.color.home_tab_txt_checked));
			} else {

				// imgIcon.setImageResource(Mine_EnumTabInfo.getTabInfoByIndex(i)
				// .getIcon());
				txtName.setTextColor(getResources().getColor(
						R.color.home_tab_txt));
			}

		}

	}

	public void onTabChanged(String tabId) {

		setTabItemSlectedShow(Mine_EnumTabInfo.getTabInfoByTag(tabId)
				.getIndex());
	}
}
