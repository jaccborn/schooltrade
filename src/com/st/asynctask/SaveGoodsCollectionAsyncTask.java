package com.st.asynctask;

import java.util.List;  

import com.st.activity.XZTApplication;
import com.st.app.HomePage_tabhostActivity;
import com.st.app.LoginActivity;
import com.st.app.R;
import com.st.bean.UserIf;
import com.st.util.DBOpenHelper;
import com.st.util.GetJsonData;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class SaveGoodsCollectionAsyncTask extends AsyncTask<Void, Void, Boolean> {
	
	Context context;
	String goodsid;

	
	public SaveGoodsCollectionAsyncTask(Context context,String goodsid) {
		
		this.context=context;
		this.goodsid=goodsid;
		
		
	}

	protected Boolean doInBackground(Void... params) {

		//这个userid来自所登录用户的
		String userid = null;
		DBOpenHelper dbuser=new DBOpenHelper(context, "userif.db");
		String sqlquery = "select * from userif";
		SQLiteDatabase db = dbuser.getWritableDatabase();
		Cursor cursor = db.rawQuery(sqlquery, null);
		
		if(cursor!=null)
		{
			while(cursor.moveToNext())
			{
			
				userid=cursor.getString(cursor.getColumnIndex("userid"));
			
			}
		}
		
		dbuser.close();
		
		
		System.out.println("goodsclooection得到userid："+userid);
		
		String str = GetJsonData.findcollectionif(userid,goodsid);
		if(str.equals("exist"))
		{
			Boolean flag=true;
			return flag;
		}
		else
		{
			Boolean flag=false;
			
			GetJsonData.saveCollection(userid,goodsid);
			
			return flag;
		}
	
	}


		protected void onPostExecute(Boolean flag) {
			
			
			
			if(flag==true)
			{
				Log.i("collect", "已经收藏过了");
			}
			if(flag==false)
			{
				Log.i("collect", "成功收藏");
			}
			
		}






		

	}




