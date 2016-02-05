package com.st.asynctask;

import java.util.ArrayList;
import java.util.List;

import com.st.app.GoodsPublishActivity;
import com.st.app.R;
import com.st.bean.GoodsClass;
import com.st.bean.GoodsSort;
import com.st.custom.WheelView;
import com.st.util.GetJsonData;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class WheelViewSortAsyncTask extends AsyncTask<Void, Void, List<String>> {
	
	WheelView publishgoodssort;
	LayoutInflater inflater;
	Context context;
	private TextView goods_sort;
	
	public WheelViewSortAsyncTask(Context context,WheelView publishgoodssort,TextView goods_sort){
		
		this.publishgoodssort=publishgoodssort;
		this.goods_sort=goods_sort;
		this.context=context;
		this.inflater=LayoutInflater.from(context);
		
	}

	protected List<String> doInBackground(Void... params) {
		List<String> sortnamelist = new ArrayList<String>();
		List<GoodsSort> goodssortlist = GetJsonData.getJsonAllGoodsSort();
		
		for (int i = 0; i < goodssortlist.size(); i++) {
			String sortName = goodssortlist.get(i).getSortName();
			sortnamelist.add(sortName);
		}
		
		return sortnamelist;
	}
	
	protected void onPostExecute(List<String> sortnamelist) {

		
		
		View outerView = inflater.inflate(R.layout.goodssortwheelview, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.publishgoodssort);
        wv.setOffset(2);
        wv.setItems(sortnamelist);
        wv.setSeletion(3);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String sortitem) {
            	
            	
            	goods_sort.setText(sortitem);
            	
            }
        });

        new AlertDialog.Builder(context)
                .setTitle("SortWheelView")
                .setView(outerView)
                .setPositiveButton("确定", null)
                .show();
		
		
		
		
	}
	
	
	

}
