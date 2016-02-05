package com.st.app;

import java.util.ArrayList;
import java.util.List;

import com.st.adapter.GoodsDetailAdapter;
import com.st.asynctask.SaveGoodsCollectionAsyncTask;
import com.st.bean.Goods;
import com.st.util.GetJsonData;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

public class GoodsDetailActivity extends Activity {
	private Button btback;
	private Button goodsdetailcollect;

	private Handler mhandler;

	private String goodsid;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.goodsdetail);

		ListView lv = (ListView) findViewById(R.id.goods_lv);

		goodsdetailcollect = (Button) findViewById(R.id.goodsdetailcollect);
		btback = (Button) findViewById(R.id.goodsdetailback);
		btback.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {


				// 这里如果标记是收藏的话 就存到服务器 要先查一下有没有 没有的话 再存

				mhandler = new Handler() {

					public void handleMessage(Message msg) {
						if (msg.what == 1)
							goodsid = (String) msg.obj;
						new SaveGoodsCollectionAsyncTask(
								getApplicationContext(), goodsid);
					}
				};

				finish();
			}
		});

		// 为了图片缩放取手机屏幕宽高然后做计算缩放图片 可以使用setScaleType(ScaleType.FIT_XY);
		WindowManager wm = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		int mScreenWidth = outMetrics.widthPixels;

		Bundle bundle = getIntent().getExtras();
		Goods goods = (Goods) bundle.getSerializable("goodsdetail");
		List<Goods> list = new ArrayList<Goods>();
		list.add(goods);
		System.out.println(list.get(0).goodsName);
		GoodsDetailAdapter detailAdapter = new GoodsDetailAdapter(
				GoodsDetailActivity.this, list, mScreenWidth, mhandler);

		lv.setAdapter(detailAdapter);
		
		detailAdapter.notifyDataSetChanged();

		Exit.activityList.add(this);

	}

}
