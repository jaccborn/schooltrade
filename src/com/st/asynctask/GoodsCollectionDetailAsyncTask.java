package com.st.asynctask;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.st.app.GoodsDetailActivity;
import com.st.bean.Collection;
import com.st.bean.Goods;
import com.st.util.GetJsonData;

public class GoodsCollectionDetailAsyncTask extends
		AsyncTask<Void, Void, List<Goods>> {

	private Context context;
	private String goodsId;

	public GoodsCollectionDetailAsyncTask(Context context, String goodsId) {
		this.context = context;
		this.goodsId = goodsId;

	}

	protected List<Goods> doInBackground(Void... params) {

		List<Goods> goodlist = GetJsonData.queryGoodsByGoodsId(goodsId);
		return goodlist;
	}

	protected void onPostExecute(List<Goods> list) {

		Goods goods = list.get(0);

		Intent intent = new Intent();
		intent.setClass(context, GoodsDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("goodsdetail", goods);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

}
