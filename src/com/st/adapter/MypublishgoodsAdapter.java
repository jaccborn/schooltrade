package com.st.adapter;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.st.app.MypublishActivity;
import com.st.app.R;
import com.st.bean.AssistBean;
import com.st.bean.Goods;
import com.st.util.BaseUrl;

public class MypublishgoodsAdapter extends BaseAdapter {

	private List<Goods> Goodslist;

	private Handler dialoghandler;

	private int position;

	public MypublishgoodsAdapter(List<Goods> Goodslist,Handler dialoghandler) {

		this.Goodslist = Goodslist; 
		this.dialoghandler=dialoghandler;
	}

	public void updateData(List<Goods> Goodslist,Handler dialoghandler) {

		this.Goodslist = Goodslist;
		this.dialoghandler=dialoghandler;
	}

	static class ViewHolder {

		public ImageView collectstoregoodimage;
		public TextView collectstoregoodname;
		public ImageView minus;
	}

	public int getCount() {

		if (Goodslist == null)
			return 0;
		return Goodslist.size();
	}

	public Object getItem(int arg0) {
		return arg0;
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			convertView = getItemView(position,parent);
		}

		setViewData((ViewHolder) convertView.getTag(), Goodslist.get(position),
				position);
		return convertView;
	}

	private View getItemView(final int position,ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(parent
				.getContext());

		View view = inflater
				.inflate(R.layout.mypublishgoodslistview_item, null);

		ViewHolder holder = new ViewHolder();
		holder.collectstoregoodname = (TextView) view
				.findViewById(R.id.mypublishgoodsname);
		holder.collectstoregoodimage = (ImageView) view
				.findViewById(R.id.mypublishgoodsimage);

		holder.minus = (ImageView) view.findViewById(R.id.minus);

		holder.minus.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				String goodsid = Goodslist.get(position).getGoodsId();

				System.out.println("goodsid:" + goodsid);
				
				Message msg = new Message();
				msg.obj = goodsid;
				dialoghandler.sendMessage(msg);
				
			}
		});

		view.setTag(holder);
		return view;

	}

	private void setViewData(final ViewHolder vh, final Goods goods,
			 int position) {

		if (vh == null || goods == null)
			return;

		String head = (String) goods.getGoodsImage();

		if (head.equals("null") || head.equals("")) {

			vh.collectstoregoodimage.setImageResource(R.drawable.ic_launcher);

		} else {
			ImageLoader.getInstance().loadImage(
					BaseUrl.basegoodsimageurl + head,
					new ImageLoadingListener() {

						public void onLoadingStarted(String arg0, View arg1) {

						}

						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {

						}

						public void onLoadingComplete(String arg0, View arg1,
								Bitmap bit) {

							Bitmap bitmap = AssistBean
									.getRoundedCornerBitmap(bit);
							vh.collectstoregoodimage.setImageBitmap(bitmap);
						}

						public void onLoadingCancelled(String arg0, View arg1) {

						}
					});

		}
		vh.collectstoregoodname.setText(goods.getGoodsName());
	

	}


}
