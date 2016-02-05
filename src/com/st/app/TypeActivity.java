package com.st.app;
 
import java.util.List; 

import com.origamilabs.library.views.StaggeredGridView;
import com.origamilabs.library.views.StaggeredGridView.OnItemClickListener;
import com.st.adapter.GoodsListAdapter;
import com.st.asynctask.GoodsClassAsyncTask;
import com.st.bean.Goods;
import com.st.bean.GoodsClass;
import com.st.util.BaseUrl;
import com.st.util.GetJsonData;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TypeActivity extends Activity {

	StaggeredGridView gridView;
	
	//spinner相关
	ArrayAdapter<GoodsClass> arr_adapter;
	Spinner sp;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.type_layout);


		
		gridView= (StaggeredGridView) findViewById(R.id.staggeredGridView1);
		
		sp=(Spinner) findViewById(R.id.spinner);
		
		//为了展示详情页
		FragmentManager fragmentManager=getFragmentManager();

		GoodsClassAsyncTask goodsclassAsyncTask = new GoodsClassAsyncTask(sp,getApplicationContext(),gridView,fragmentManager);
		goodsclassAsyncTask.execute();
			
	
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);
		
		gridView.setItemMargin(margin); // set the GridView margin
		
		gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well 
		



		Exit.activityList.add(this);

	}

	// 实现网络的异步访问
	 class GoodsAsyncTask extends AsyncTask<String, Void, List<Goods>> {

		protected List<Goods> doInBackground(String... params) {

			return GetJsonData.getJsonAllGoods(params[0]);
		}

		protected void onPostExecute(final List<Goods> goodslist) {

			super.onPostExecute(goodslist);

			GoodsListAdapter adapter = new GoodsListAdapter(getApplicationContext(), goodslist, gridView);
			gridView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			
			gridView.setOnItemClickListener(new OnItemClickListener() {
				
				public void onItemClick(StaggeredGridView parent, View view, int position,
						long id) {

					
					Goods goods = goodslist.get(position);
					Toast.makeText(TypeActivity.this, goods.getGoodsName(),
							Toast.LENGTH_SHORT).show();
					
				}
			});
			
		}

		

	}


}
