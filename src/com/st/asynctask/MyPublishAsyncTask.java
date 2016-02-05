package com.st.asynctask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.st.activity.MyListView;
import com.st.adapter.MycollectstoregoodAdapter;
import com.st.adapter.MypublishgoodsAdapter;
import com.st.bean.Collection;
import com.st.bean.Goods;
import com.st.util.DBOpenHelper;
import com.st.util.GetJsonData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyPublishAsyncTask extends
		AsyncTask<Void, Void, List<Goods>> {

	private Context context;
	private MyListView listView;
	private TextView mDataTip;

	private String userid = null;

	private MypublishgoodsAdapter adapter;
	
	private Handler dialoghandler;
	
	
	private int pageNow;  //现在的页数
	private int pageSize;  //需要显示的数量
	
	private List<Goods> mypublishgoodslist=new ArrayList<Goods>();


	public MyPublishAsyncTask(Context context, MyListView listView,
			TextView mDataTip, MypublishgoodsAdapter adapter,int pageNow,int pageSize,Handler dialoghandler) {
		this.context = context;
		this.listView = listView;
		this.mDataTip = mDataTip;
		this.adapter = adapter;
		this.pageNow=pageNow;
		this.pageSize=pageSize;
		this.dialoghandler=dialoghandler;
	}

	protected List<Goods> doInBackground(Void... params) {

		// 这个userid来自所登录用户的
		DBOpenHelper dbuser = new DBOpenHelper(context, "userif.db");
		String sqlquery = "select * from userif";
		SQLiteDatabase db = dbuser.getWritableDatabase();
		Cursor cursor = db.rawQuery(sqlquery, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {

				userid = cursor.getString(cursor.getColumnIndex("userid"));

			}
		}

		dbuser.close();

		System.out.println("mypublishgoods得到userid：" + userid);

		mypublishgoodslist= GetJsonData.querypublishgoods(userid,pageNow,pageSize);

		if (mypublishgoodslist.size() > 0 && !mypublishgoodslist.isEmpty()) {
			return mypublishgoodslist;
		} else {
			return null;
		}
	}

	protected void onPostExecute(List<Goods> goodslist) {
		if (goodslist.size() > 0 && !goodslist.isEmpty()) {

			listView.setPullLoadEnable(true, 1);

			if (adapter == null) {
				adapter = new MypublishgoodsAdapter(goodslist,dialoghandler);
				listView.setAdapter(adapter);
			} else {
				adapter.updateData(goodslist,dialoghandler);
				adapter.notifyDataSetChanged();
			}
			onLoad();

			if (goodslist.size() < 1) {
				mDataTip.setVisibility(View.VISIBLE);
			}

			mDataTip.setVisibility(View.GONE);
		} 
		else {
			mDataTip.setVisibility(View.VISIBLE);
		}
		
	
		

	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String displayTime = sdf.format(new java.util.Date());
		listView.setRefreshTime(displayTime);
	}

	private String mytime(String timeStr) {
		StringBuffer sb = new StringBuffer();
		long t = Long.parseLong(timeStr);
		long time = System.currentTimeMillis() - (t * 1000);
		long mill = (long) Math.ceil(time / 1000);

		long minute = (long) Math.ceil(time / 60 / 1000.0f);

		long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);

		long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);

		if (day - 1 > 0) {
			sb.append(day + "天");
		} else if (hour - 1 > 0) {
			if (hour >= 24) {
				sb.append("1天");
			} else {
				sb.append(hour + "小时");
			}
		} else if (minute - 1 > 0) {
			if (minute == 60) {
				sb.append("1小时");
			} else {
				sb.append(minute + "分钟");
			}
		} else if (mill - 1 > 0) {
			if (mill == 60) {
				sb.append("分钟");
			} else {
				sb.append(mill + "秒");
			}
		} else {
			sb.append("刚刚");
		}
		if (!sb.toString().equals("刚刚")) {
			sb.append("前");
		}
		return sb.toString();

	}

}
