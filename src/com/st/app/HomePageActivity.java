package com.st.app;

import java.util.ArrayList; 
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.st.adapter.ViewPagerAdapter;

/**
 * 
 * 首页
 * 
 * @author Administrator
 * 
 */
public class HomePageActivity extends Activity {
	private ViewPager viewPager; // android-support-v4中的滑动组件
	public static List<ImageView> imageViews; // 滑动的图片集合

	public static int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点
	private int currentItem = 0; // 当前图片的索引号
	private Intent intent;

	public ImageView fujin_imageView;

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.homepage_layout);
		Exit.activityList.add(this);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		imageResId = new int[] { R.drawable.viewpager1, R.drawable.viewpager2,
				R.drawable.viewpager3, R.drawable.viewpager4,
				R.drawable.viewpager5 };

		imageViews = new ArrayList<ImageView>();

		for (int i = 0; i < imageResId.length; i++) {

			ImageView imageView = new ImageView(this);
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);

		}

		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.v_dot0));
		dots.add(findViewById(R.id.v_dot1));
		dots.add(findViewById(R.id.v_dot2));
		dots.add(findViewById(R.id.v_dot3));
		dots.add(findViewById(R.id.v_dot4));

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new ViewPagerAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListener());

		fujin();
		VIP();
		dianpu();
		shoucang();
		remen();

	}

	// 附近监听事件
	private void fujin() {

		fujin_imageView = (ImageView) findViewById(R.id.fujin_imgview);
		fujin_imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				intent = new Intent();
				intent.setClass(HomePageActivity.this, FujinActivity.class);
				startActivity(intent);

			}
		});

	}

	// VIP监听事件
	private void VIP() {

		fujin_imageView = (ImageView) findViewById(R.id.vip_imgview);
		fujin_imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				intent = new Intent();
				intent.setClass(HomePageActivity.this, VIPActivity.class);
				startActivity(intent);

			}
		});

	}

	// 附近监听事件
	private void dianpu() {

		fujin_imageView = (ImageView) findViewById(R.id.dianpu_imgview);
		fujin_imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				intent = new Intent();
				intent.setClass(HomePageActivity.this, DianpuActivity.class);
				startActivity(intent);

			}
		});

	}

	// 热门
	private void remen() {

		fujin_imageView = (ImageView) findViewById(R.id.remen_imgview);
		fujin_imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				intent = new Intent();
				intent.setClass(HomePageActivity.this, RemenActivity.class);
				startActivity(intent);

			}
		});

	}

	// 附近监听事件
	private void shoucang() {

		fujin_imageView = (ImageView) findViewById(R.id.shoucang_imgview);
		fujin_imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				intent = new Intent();
				intent.setClass(HomePageActivity.this, ShoucangActivity.class);
				startActivity(intent);

			}
		});

	}

	// 登录
	public void onAction(View v) {

		intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	protected void onStop() {
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				//System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

}
