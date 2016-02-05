package com.st.app;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class HomePage_tabhostActivity extends TabActivity implements OnTabChangeListener {
	

	static TabHost tabHost;
	private String[] TABSTRS;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tabhost_home_page);
		Exit.activityList.add(this);

		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		initData();
		initView();
	}

	
	private void initData() {
		TABSTRS = getResources().getStringArray(R.array.tab_items);
	
	}

	private void initView() {
		tabHost = getTabHost();

		for (int i = 0, count = TABSTRS.length; i < count; i++) {
			TabSpec tabSpec = tabHost
					.newTabSpec(EnumTabInfo.getTabInfoByIndex(i).getTag())
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
		imgIcon.setImageResource(EnumTabInfo.getTabInfoByIndex(index).getIcon());
		txtName.setText(TABSTRS[index]);

		return vItem;
	}

	/**
	 * 得到Tabitem的Intent
	 * 
	 * @param index
	 * @return
	 */
	private Intent getTabItemIntent(int index) {
		Intent intent = new Intent();

		intent.setClass(this, EnumTabInfo.getTabInfoByIndex(index).getClss());

		return intent;
	}

	private void setTabItemSlectedShow(int index) {

		for (int i = 0; i < TABSTRS.length; i++) {
			View vItem = tabHost.getTabWidget().getChildAt(i);
			ImageView imgIcon = (ImageView) vItem
					.findViewById(R.id.itemtab_img_icon);
			TextView txtName = (TextView) vItem
					.findViewById(R.id.itemtab_txt_name);
			if (i == index) {
				imgIcon.setImageResource(EnumTabInfo.getTabInfoByIndex(i)
						.getIconSelected());
				txtName.setTextColor(getResources().getColor(
						R.color.home_tab_txt_checked));
			} else {
				imgIcon.setImageResource(EnumTabInfo.getTabInfoByIndex(i)
						.getIcon());
				txtName.setTextColor(getResources().getColor(
						R.color.home_tab_txt));
			}

		}

	}

	@Override
	public void onTabChanged(String tabId) {
		
		setTabItemSlectedShow(EnumTabInfo.getTabInfoByTag(tabId).getIndex());
	}
}

