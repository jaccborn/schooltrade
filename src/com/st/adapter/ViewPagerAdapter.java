package com.st.adapter;

import com.st.app.HomePageActivity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return HomePageActivity.imageResId.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub

		((ViewPager) container).removeView(HomePageActivity.imageViews
				.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(HomePageActivity.imageViews
				.get(position));
		return HomePageActivity.imageViews.get(position);
	}

}
