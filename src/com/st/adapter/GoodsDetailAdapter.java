package com.st.adapter;

import java.util.ArrayList;
import java.util.List;

import com.st.app.R;
import com.st.asynctask.QueryGoodsCollectionAsyncTask;
import com.st.asynctask.GoodsDetailImageLoaderTask;
import com.st.bean.Goods;
import com.st.util.BaseUrl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsDetailAdapter extends BaseAdapter {

	private List<Goods> list;
	private LayoutInflater inflater;
	private Context context;
	private Handler mhandler;
	private int flag;
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public GoodsDetailAdapter(Context context, List<Goods> list,int Width,Handler mhandler) {
		this.list=list;
		this.inflater=LayoutInflater.from(context);
		this.context=context;
		this.mhandler=mhandler;
				
	}
	
	public int getCount() {

		return list.size();
	}
	public Object getItem(int position) {

		return list.get(position);
	}
	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder viewHolder;
		if(convertView==null)
		{
			viewHolder=new ViewHolder();
			convertView=inflater.inflate(R.layout.goodsdetail_item, null);
			viewHolder.imageView=(ImageView) convertView.findViewById(R.id.imv);
			viewHolder.goodsname=(TextView) convertView.findViewById(R.id.goodsname);
			viewHolder.goodsdesc=(TextView) convertView.findViewById(R.id.goodspublishtime);
			viewHolder.goodsprice=(TextView) convertView.findViewById(R.id.goodsprice);
			viewHolder.goodspublishtime=(TextView) convertView.findViewById(R.id.goodspublishtime);
			viewHolder.goodspublisher=(TextView) convertView.findViewById(R.id.goodspublisher);
			viewHolder.goodscontacts=(TextView) convertView.findViewById(R.id.goodscontacts);
			
			viewHolder.goodsdetailcollect=(Button) convertView.findViewById(R.id.goodsdetailcollect);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder=(ViewHolder) convertView.getTag();
			
		}
		//加载数据
		
//		LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.lineardetail);
//		
//		int columnWidth=linearLayout.getWidth();
//		int columnHeight=linearLayout.getHeight();
//		System.out.println("columnWidth:"+columnWidth+"columnHeight:"+columnHeight);
		

				
		new GoodsDetailImageLoaderTask(350, 400,viewHolder.imageView).execute(BaseUrl.basegoodsimageurl+list.get(0).goodsImage);
		
		viewHolder.goodsname.setText(list.get(0).goodsName);
		viewHolder.goodsdesc.setText(list.get(0).goodsDesc);
		viewHolder.goodsprice.setText(list.get(0).goodsPrice.toString());
		viewHolder.goodspublishtime.setText(list.get(0).goodsPublishTime);
		viewHolder.goodspublisher.setText(list.get(0).goodsPublisher);
		viewHolder.goodscontacts.setText(list.get(0).contacts);
		
		
		
		//加载的时候就去查一下服务器是否有收藏该物品
		String goodsid=list.get(0).goodsId;
		new QueryGoodsCollectionAsyncTask(context, viewHolder.goodsdetailcollect,goodsid).execute();	
		

		Drawable background = viewHolder.goodsdetailcollect.getBackground();
		if(background.equals(R.drawable.clollect))
		{
			flag=1;
		}else
		{
			flag=0;
		}
		setFlag(flag);
		viewHolder.goodsdetailcollect.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				int flag = getFlag();
				if(flag==0)
				{
					viewHolder.goodsdetailcollect.setBackgroundResource(R.drawable.clollect);
					Toast.makeText(context, "已收藏！", Toast.LENGTH_SHORT).show();
					setFlag(1);
				}
				else 
				{
					viewHolder.goodsdetailcollect.setBackgroundResource(R.drawable.noclollect);
					Toast.makeText(context, "取消收藏！", Toast.LENGTH_SHORT).show();
					setFlag(0);
				}
				
				Message msg=new Message();
				msg.what=getFlag();
				msg.obj=list.get(0).goodsId;
				mhandler.sendMessage(msg);

			}
		});

		return convertView;
		
	}
	static class ViewHolder{
		TextView goodsname;
		TextView goodsdesc;
		TextView goodsprice;
		TextView goodspublishtime;
		TextView goodspublisher;
		TextView goodscontacts;
		ImageView imageView;
		Button goodsdetailcollect;
		
	}


}
