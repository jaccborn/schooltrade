package com.st.asynctask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.st.activity.MyListView;
import com.st.adapter.MycollectstoregoodAdapter;
import com.st.bean.Collection;
import com.st.util.DBOpenHelper;
import com.st.util.GetJsonData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GoodsCollectionAsyncTask extends
		AsyncTask<Void, Void, List<Collection>> {

	private Context context;
	private MyListView listView;
	private TextView mDataTip;
	private List<Collection> collectionlist = new ArrayList<Collection>();

	private String userid = null;

	private MycollectstoregoodAdapter adapter;
	
	private int pageNow;  //现在的页数
	private int pageSize;  //需要显示的数量

	public GoodsCollectionAsyncTask(Context context, MyListView listView,
			TextView mDataTip, MycollectstoregoodAdapter adapter,int pageNow,int pageSize) {
		this.context = context;
		this.listView = listView;
		this.mDataTip = mDataTip;
		this.adapter = adapter;
		this.pageNow=pageNow;
		this.pageSize=pageSize;
	}

	protected List<Collection> doInBackground(Void... params) {

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

		System.out.println("goodsclooection得到userid：" + userid);

		List<Collection> collectionlist = GetJsonData.querycollectgoods(userid,pageNow,pageSize);
		

		if (collectionlist.size() > 0 && !collectionlist.isEmpty()) {
			return collectionlist;
		} else {
			return null;
		}
	}

	protected void onPostExecute(final List<Collection> collectlist) {
		if (collectlist.size() > 0 && !collectlist.isEmpty()) {

			listView.setPullLoadEnable(true, 1);

			if (adapter == null) {
				adapter = new MycollectstoregoodAdapter(collectlist);
				listView.setAdapter(adapter);
			} else {
				adapter.updateData(collectlist);
				adapter.notifyDataSetChanged();
			}
			onLoad();

			if (collectlist.size() < 1) {
				mDataTip.setVisibility(View.VISIBLE);
			}

			mDataTip.setVisibility(View.GONE);
		} 
		else {
			mDataTip.setVisibility(View.VISIBLE);
		}
		

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String goodsId = collectlist.get(position-1).getGoodsId();
				new GoodsCollectionDetailAsyncTask(context,goodsId).execute();
				
			}
		});
		
		

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
