package com.st.asynctask;

import com.st.util.GetJsonData;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class DeleteGoodsAsyncTask extends AsyncTask<Void, Void, Boolean> {

	String goodsid;
	Handler delhandler;

	public DeleteGoodsAsyncTask(String goodsid,Handler delhandler) {

		this.goodsid = goodsid;
		this.delhandler=delhandler;
	}

	protected Boolean doInBackground(Void... params) {

		String str = GetJsonData.deletegoodsbyid(goodsid);
		if (str.length() > 0 && !str.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	protected void onPostExecute(Boolean flag) {
		if (flag==true) {
			
			Message msg=new Message();
			msg.what=123456;
			delhandler.sendMessage(msg);
		
		}
	};

}
