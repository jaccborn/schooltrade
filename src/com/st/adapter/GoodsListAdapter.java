package com.st.adapter;

import java.util.List;   

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.origamilabs.library.views.StaggeredGridView;
import com.st.app.R;
import com.st.asynctask.StaggeredGridViewImageLoader;
import com.st.bean.Goods;
import com.st.util.BaseUrl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
 
public class GoodsListAdapter extends BaseAdapter implements OnScrollListener{
	List<Goods> goodslist;
	LayoutInflater inflater;
	
	int mStart,mEnd;
	
	Boolean mFirstIn;
	StaggeredGridViewImageLoader mImageLoader;
	
	public static String[] urls;//用于保存所有图片的url

	public GoodsListAdapter(Context context,List<Goods> goodslist,StaggeredGridView gridView) {
		this.inflater=inflater.from(context);
		this.goodslist=goodslist;
		
		mImageLoader=new StaggeredGridViewImageLoader(gridView);// 这样就不用每次都new 只有一个lrucache
		
		mFirstIn=true;
		
		urls=new String[goodslist.size()];
		for(int i=0;i<goodslist.size();i++)
		{
			urls[i]=goodslist.get(i).goodsImage;
			
		}
	}

	public int getCount() {

		return goodslist.size();
	}

	public Object getItem(int position) {

		return null != goodslist?goodslist.get(position):null;
	}

	public long getItemId(int position) {

		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if(convertView==null)
		{
			viewHolder=new ViewHolder();
			convertView=inflater.inflate(R.layout.row_staggered_demo, null);
			viewHolder.imageView=(ImageView) convertView.findViewById(R.id.imageView1);
			viewHolder.title=(TextView) convertView.findViewById(R.id.goodstitle);
			//上架时间
			//viewHolder.publishtime=(TextView) convertView.findViewById(R.id.goodspublishtime);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder=(ViewHolder) convertView.getTag();
			
		}
		viewHolder.imageView.setImageResource(R.drawable.ic_launcher);
		viewHolder.imageView.setTag(BaseUrl.basegoodsimageurl+goodslist.get(position).goodsImage);
		//1、使用多线程方法

		//mImageLoader.showImageByThread(viewHolder.imageView, BaseUrl.baseimageurl+goodslist.get(position).goodsImage);
		
		//2、使用asynctask方法
		mImageLoader.ShowImageByAsyncTask(viewHolder.imageView, BaseUrl.basegoodsimageurl+goodslist.get(position).goodsImage);
		viewHolder.title.setText(goodslist.get(position).goodsName);
		//viewHolder.publishtime.setText(goodslist.get(position).goodsPublishTime);

		return convertView;
	}
	
	
	class ViewHolder{
		TextView title;
		//TextView publishtime;
		ImageView imageView;
		
	}


	//状态切换时调用
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//停止状态    要加载图片
		if(scrollState==SCROLL_STATE_IDLE)
		{
			mImageLoader.loadImages(mStart, mEnd);
		}
		else
		{
			//停止所有的加载任务
			mImageLoader.cancelAllTasks();
		}
		
	}

	//整个过程都调用
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		mStart=firstVisibleItem;
		mEnd=firstVisibleItem+visibleItemCount;
		//因为onScrollStateChanged方法不会一直调用 而onScroll一直调用 所以在这里判断是不是第一次启动
		if(mFirstIn&&visibleItemCount>0)
		{
		
			mImageLoader.loadImages(mStart, mEnd);
			mFirstIn=false;
		}
		
	}

}
