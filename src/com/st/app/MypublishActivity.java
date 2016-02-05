package com.st.app;

import com.st.activity.BaseListViewActivity;  
import com.st.activity.MyListView;
import com.st.activity.MyListView.IXListViewListener;
import com.st.adapter.MypublishgoodsAdapter;
import com.st.asynctask.DeleteGoodsAsyncTask;
import com.st.asynctask.MyPublishAsyncTask;
import com.st.app.MineActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MypublishActivity extends BaseListViewActivity implements
		IXListViewListener {

	private MypublishgoodsAdapter adapter;
	private TextView mDataTip;

	private int pageNow=1;  //现在的页数
	private int pageSize=4;  //需要显示的数量
	
	private int m=2;
	
	private  Handler delhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
		
			new MyPublishAsyncTask(MypublishActivity.this, listView, mDataTip,
					adapter,pageNow,pageSize,dialoghandler).execute();
			
		};
		
		
	};
	
	private Handler dialoghandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			final String goodsid=(String) msg.obj;
			

			   //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
	        AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
	        //    设置Title的图标
	        builder.setIcon(R.drawable.ic_launcher);
	        //    设置Title的内容
	        builder.setTitle("警告");
	        //    设置Content来显示一个信息
	        builder.setMessage("确定删除"+goodsid+"吗？");
	        //    设置一个PositiveButton
	        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
	        {
	            public void onClick(DialogInterface dialog, int which)
	            {
	            	new DeleteGoodsAsyncTask(goodsid,delhandler).execute();
	            }
	        });
	        //    设置一个NegativeButton
	        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
	        {
	            public void onClick(DialogInterface dialog, int which)
	            {
	            	
	            }
	        });
	        builder.show();
			
			
			
		};
	};
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypublishgoodslayout);
	

		listView = (MyListView) findViewById(R.id.list_bou);

		mDataTip = (TextView) findViewById(R.id.datatip);
		listView.setXListViewListener(this);

		new MyPublishAsyncTask(MypublishActivity.this, listView, mDataTip,
				adapter,pageNow,pageSize,dialoghandler).execute();
		Exit.activityList.add(this);
	}

	public void onRefresh() {

		new MyPublishAsyncTask(MypublishActivity.this, listView, mDataTip,
				adapter,pageNow,pageSize,dialoghandler).execute();
	}

	public void onLoadMore() {
		mDataTip.setVisibility(View.GONE);
		listView.setPullLoadEnable(true, 0);
		// 加载更多 
		
		new MyPublishAsyncTask(MypublishActivity.this,listView,mDataTip,adapter,pageNow,pageSize*m,dialoghandler).execute();
		m++;
	}

}
