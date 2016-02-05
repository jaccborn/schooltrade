package com.st.adapter;

import java.util.List; 
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.CursorUtils.FindCacheSequence;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.st.app.R;
import com.st.bean.AssistBean;
import com.st.bean.Collection;
import com.st.util.BaseUrl;

public class MycollectstoregoodAdapter extends BaseAdapter {

	private Context context;
	private List<Collection> collectlist;
	

	public MycollectstoregoodAdapter(List<Collection> collectlist) {

		this.collectlist = collectlist;
	}

	public void updateData(List<Collection> collectlist) {

		this.collectlist = collectlist;
	}

	// private ImageLoadingListener animateFirstListener = new
	// AnimateFirstDisplayListener();

	static class ViewHolder {

		public ImageView collectstoregoodimage;
		public TextView collectstoregoodname;
	
	}

	public int getCount() {

		if (collectlist == null)
			return 0;
		return collectlist.size();
	}

	public Object getItem(int arg0) {
		return arg0;
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		context = parent.getContext();
		if (convertView == null) {

			convertView = getItemView(parent);
		}

		setViewData((ViewHolder) convertView.getTag(), collectlist.get(position),
				position);
		return convertView;
	}

	private View getItemView(ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(parent
				.getContext());

		View view = inflater
				.inflate(R.layout.mycollectstorelistview_item, null);

		ViewHolder holder = new ViewHolder();
		holder.collectstoregoodname = (TextView) view
				.findViewById(R.id.collectstoregoodname);
		holder.collectstoregoodimage = (ImageView) view
				.findViewById(R.id.collectstoregoodimage);
	
		
		

		view.setTag(holder);
		return view;

	}

	private void setViewData(final ViewHolder vh,
			final Collection collection, final int position) {

		if (vh == null || collection == null)
			return;

		String head = (String) collection.getGoodsImage();

		if (head.equals("null") || head.equals("")) {

			vh.collectstoregoodimage.setImageResource(R.drawable.ic_launcher);

		} else {
			ImageLoader.getInstance().loadImage(BaseUrl.basegoodsimageurl+head,
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
		 vh.collectstoregoodname.setText(collection.getGoodsName());
		
	}

	


}
