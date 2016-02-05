package com.st.asynctask;

import java.util.ArrayList; 
import java.util.List;

import com.origamilabs.library.views.StaggeredGridView;
import com.st.app.R;
import com.st.bean.Goods;
import com.st.bean.GoodsClass;
import com.st.util.BaseUrl;
import com.st.util.GetJsonData;

import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class GoodsClassAsyncTask extends AsyncTask<String, Void, List<GoodsClass>>
		implements OnItemSelectedListener {

	Spinner sp;
	Context context;
	StaggeredGridView gridView;
	FragmentManager fragmentManager;

	public GoodsClassAsyncTask(Context context) {
		this.context = context;

	}

	public GoodsClassAsyncTask(Spinner sp, Context context,StaggeredGridView gridView,FragmentManager fragmentManager) {
		this.context = context;
		this.sp = sp;
		this.gridView=gridView;
		this.fragmentManager=fragmentManager;
	}

	protected List<GoodsClass> doInBackground(String... params) {

		return GetJsonData.GetJsonByClass();
	}

	protected void onPostExecute(List<GoodsClass> list) {

		System.out.println("类别名称："+list);

		
		
		ArrayAdapter<GoodsClass> ar_adapter = new ArrayAdapter<GoodsClass>(context,R.layout.drop_list, list);
		ar_adapter.setDropDownViewResource(R.layout.drop_list_item);

		sp.setAdapter(ar_adapter);
		sp.setOnItemSelectedListener(this);

	}

	// spinner选择监听事件
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		String classes = (String) parent.getAdapter().getItem(position);
		//sptv.setText("你选择的类别是:" + classes);
		switch (classes) {
		case "所有类":
			new QueryAllGoodsAsyncTask(context, gridView).execute(BaseUrl.queryallgoodsurl);
			break;
		case "图书类":
			new QueryGoodsByClassAsyncTask(context, gridView,fragmentManager).execute("图书类");
			break;
		case "车类":
			new QueryGoodsByClassAsyncTask(context, gridView,fragmentManager).execute("车类");
			break;
		case "服饰类":
			new QueryGoodsByClassAsyncTask(context, gridView,fragmentManager).execute("服饰类");
			break;
		case "电子数码类":
			new QueryGoodsByClassAsyncTask(context, gridView,fragmentManager).execute("电子数码类");
			break;
		case "教育培训类":
			new QueryGoodsByClassAsyncTask(context, gridView,fragmentManager).execute("教育培训类");
			break;
		case "生活用品类":
			new QueryGoodsByClassAsyncTask(context, gridView,fragmentManager).execute("生活用品类");
			break;
		case "其他":
			new QueryGoodsByClassAsyncTask(context, gridView,fragmentManager).execute("其他");
			break;

		default:
			break;
		}
		

	}
	public void onNothingSelected(AdapterView<?> parent) {

	}
	
	
	private String GetClassIdByClassName(String classname) {

		
		String classid = null;
		List<GoodsClass> allGoodsclass = GetJsonData.getJsonAllGoodsClass();
		for(int i=0;i<allGoodsclass.size();i++)
		{
			if(classname.equals(allGoodsclass.get(i).getClassName()))
			{
				classid=allGoodsclass.get(i).getGoodsClassId();
			}
		}
		
		
		return classid;
	}
	
	

	
	
}
