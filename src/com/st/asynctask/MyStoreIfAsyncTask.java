package com.st.asynctask;

import java.util.List;

import com.st.bean.Store;
import com.st.util.DBOpenHelper;
import com.st.util.GetJsonData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.TextView;

public class MyStoreIfAsyncTask extends AsyncTask<Void,Void,List<Store>> {
	
	Context context;

	private TextView storename;
	private TextView storeowner;
	private TextView storetel;
	private TextView storeplace;
	private TextView storeregistertime;
	
	public  MyStoreIfAsyncTask(Context context,TextView storename,TextView storeowner,TextView storetel,TextView storeplace,TextView storeregistertime){
		
		this.context=context;
		this.storename=storename;
		this.storeowner=storeowner;
		this.storeplace=storeplace;
		this.storeregistertime=storeregistertime;
		this.storetel=storetel;
		
	}
	

	protected List<Store> doInBackground(Void... params) {

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
		
		
		System.out.println("mystore得到userid："+userid);
		
		
		List<Store> storelist = GetJsonData.queryStoreInfoByUserId(userid);
		
		
		
		return storelist;
	}
	
	
	protected void onPostExecute(List<Store> storelist) {
		

		storename.setText(storelist.get(0).getStname());;
		storeowner.setText(storelist.get(0).getStowner());;
		storetel.setText(storelist.get(0).getSttelphone());;
		storeplace.setText(storelist.get(0).getStplace());;
		String time=storelist.get(0).getStregistertime();
		storeregistertime.setText(time.substring(0, 10));;
		
		
	}




}
