package com.st.asynctask;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.origamilabs.library.views.StaggeredGridView;
import com.origamilabs.library.views.StaggeredGridView.OnItemClickListener;
import com.st.adapter.GoodsListAdapter;
import com.st.app.GoodsDetailActivity;
import com.st.app.TypeActivity;
import com.st.bean.Goods;
import com.st.util.GetJsonData;

public class  QueryAllGoodsAsyncTask extends AsyncTask<String, Void, List<Goods>> {
	Context context;
	StaggeredGridView gridView;
		public QueryAllGoodsAsyncTask(Context context,StaggeredGridView gridView) {
			this.context=context;
			this.gridView=gridView;
		}

		protected List<Goods> doInBackground(String... params) {

			return GetJsonData.getJsonAllGoods(params[0]);
		}

		protected void onPostExecute(final List<Goods> goodslist) {

			super.onPostExecute(goodslist);

			GoodsListAdapter adapter = new GoodsListAdapter(context, goodslist, gridView);
			gridView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			
			gridView.setOnItemClickListener(new OnItemClickListener() {
				
				public void onItemClick(StaggeredGridView parent, View view, int position,
						long id) {

					
					Goods goods = goodslist.get(position);
					Toast.makeText(context, goods.getGoodsName(),
							Toast.LENGTH_SHORT).show();
					
					
					System.out.println("即将显示详情页");
					Intent intent = new Intent();
					intent.setClass(context, GoodsDetailActivity.class);
					Bundle bundle=new Bundle();
					bundle.putSerializable("goodsdetail",goods);
					intent.putExtras(bundle);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					context.startActivity(intent);
					
				}
			});
			
		}

		

	}



