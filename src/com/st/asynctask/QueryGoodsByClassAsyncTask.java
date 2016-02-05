package com.st.asynctask;

import java.util.ArrayList; 
import java.util.List; 

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.st.app.GoodsDetailFragment;
import com.st.app.R;
import com.st.bean.Goods;
import com.st.util.GetJsonData;

public class  QueryGoodsByClassAsyncTask extends AsyncTask<String, Void, List<Goods>> {
	Context context;
	StaggeredGridView gridView;
	
	//为了展示详情页
	FragmentManager fragmentManager;

		public QueryGoodsByClassAsyncTask(Context context,StaggeredGridView gridView,FragmentManager fragmentManager) {
			this.context=context;
			this.gridView=gridView;
			this.fragmentManager=fragmentManager;
		}

		protected List<Goods> doInBackground(String... params) {

			return GetJsonData.getJsonGoodsByClassName(params[0]);
		}

		protected void onPostExecute( final List<Goods> goodslist) {


			GoodsListAdapter adapter = new GoodsListAdapter(context, goodslist, gridView);
			gridView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			
			gridView.setOnItemClickListener(new OnItemClickListener() {
				
				public void onItemClick(StaggeredGridView parent, View view, int position,
						long id) {
					
					Goods goods = goodslist.get(position);
					Toast.makeText(context, goods.getGoodsName(),
							Toast.LENGTH_SHORT).show();
					
					Intent intent = new Intent();
					intent.setClass(context, GoodsDetailActivity.class);
					Bundle bundle=new Bundle();
					bundle.putSerializable("goodsdetail",goods);
					intent.putExtras(bundle);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					context.startActivity(intent);
					
					
//					GoodsDetailFragment goodsDetailFragment=new GoodsDetailFragment();
//					Bundle bundle=new Bundle();
//					bundle.putString("goodsid", goodslist.get(position).getGoodsId());
//					goodsDetailFragment.setArguments(bundle);
//				
//					FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
//					
//					beginTransaction.add(R.id.id_type_layout, goodsDetailFragment);
//				
//					beginTransaction.commit();
					
				}
			});
			
		}

		

	}



