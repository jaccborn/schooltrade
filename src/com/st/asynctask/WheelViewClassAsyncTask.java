package com.st.asynctask;

import java.util.ArrayList; 
import java.util.Arrays;
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

public class WheelViewClassAsyncTask extends AsyncTask<Void, Void, List<String>> {
	
	WheelView publishgoodsclass;
	LayoutInflater inflater;
	Context context;
	private TextView goods_class;
	
	Context mcontext;
	
	public WheelViewClassAsyncTask(Context context,WheelView publishgoodsclass,TextView goods_class){
		
		this.publishgoodsclass=publishgoodsclass;
		this.goods_class=goods_class;
		this.inflater=LayoutInflater.from(context);
		this.context=context;
		
		
	}

	protected List<String> doInBackground(Void... params) {
		List<String> classnamelist = new ArrayList<String>();
		List<GoodsClass> goodsclasslist = GetJsonData.getJsonAllGoodsClass();
		
		System.out.println("goodsclasslist:"+goodsclasslist);
		for (int i = 0; i < goodsclasslist.size(); i++) {
			String className = goodsclasslist.get(i).getClassName();
			classnamelist.add(className);
		}
		
		return classnamelist;
	}
	
	protected void onPostExecute(List<String> classnamelist) {

		
		  View outerView = inflater.inflate(R.layout.goodsclasswheelview, null);
          WheelView wv = (WheelView) outerView.findViewById(R.id.publishgoodsclass);
          wv.setOffset(2);
          wv.setItems(classnamelist);
          wv.setSeletion(3);
          wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
              @Override
              public void onSelected(int selectedIndex, String classitem) {

            	  goods_class.setText(classitem);
            	  
              }
          });

          new AlertDialog.Builder(context)
                  .setTitle("ClassWheelView")
                  .setView(outerView)
                  .setPositiveButton("确定", null)
                  .show();
		
		
		
		
		
		
		
	}
	
	
	

}
