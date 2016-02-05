package com.st.app;


import com.st.activity.BaseListViewActivity; 
import com.st.activity.MyListView;
import com.st.activity.MyListView.IXListViewListener;
import com.st.adapter.MycollectstoregoodAdapter;
import com.st.asynctask.GoodsCollectionAsyncTask;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class CollectstoregoodsActivity extends BaseListViewActivity implements
IXListViewListener  {

	private MycollectstoregoodAdapter adapter;
	private TextView mDataTip;
	private int pageNow=1;  //现在的页数
	private int pageSize=4;  //需要显示的数量

	private int m=2;
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.collectstorelayout);
		Exit.activityList.add(this);

		listView = (MyListView) findViewById(R.id.list_bou);
		
		mDataTip=(TextView) findViewById(R.id.datatip);
		listView.setXListViewListener(this);
	
		
		new GoodsCollectionAsyncTask(CollectstoregoodsActivity.this,listView,mDataTip,adapter,pageNow,pageSize).execute();
	}

	public void onRefresh() {

		new GoodsCollectionAsyncTask(CollectstoregoodsActivity.this,listView,mDataTip,adapter,pageNow,pageSize).execute();
	}

	public void onLoadMore() {
		mDataTip.setVisibility(View.GONE);
		listView.setPullLoadEnable(true, 0);
		//加载更多 
	
		new GoodsCollectionAsyncTask(CollectstoregoodsActivity.this,listView,mDataTip,adapter,pageNow,pageSize*m).execute();
		m++;
	}

	

}
