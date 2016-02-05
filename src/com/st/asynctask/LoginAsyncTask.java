package com.st.asynctask;

import java.util.List;  

import com.st.activity.XZTApplication;
import com.st.app.HomePage_tabhostActivity;
import com.st.bean.UserIf;
import com.st.util.DBOpenHelper;
import com.st.util.GetJsonData;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class LoginAsyncTask extends AsyncTask<Void, Void, Boolean> {
	
	Context context;
	String username;
	String pwd;
	
	XZTApplication app;
	
	private Handler mHandler;
	
	public LoginAsyncTask(Context context,String username,String pwd,Handler mHandler)
	{
		this.context=context;
		this.username=username;
		this.pwd=pwd;
		this.mHandler=mHandler;
		
	}
	
	protected Boolean doInBackground(Void... params) {

		
			
		List<UserIf> loginlist = GetJsonData.CheckLoginInf(username, pwd);
		if(loginlist.size()>0&&!loginlist.isEmpty())
		{
		String userId = loginlist.get(0).userId;
		String regtime=loginlist.get(0).registerTime;
		String regimage=loginlist.get(0).registerImage;
		
		//将信息存入本地数据库
		DBOpenHelper userdb=new DBOpenHelper(context, "userif.db");
		SQLiteDatabase db = userdb.getWritableDatabase();
		String sqlinsert = "insert into userif(userid,username,userpwd,registertime,registerimage) values('"+userId+"'"+","+"'"+username+"'"+","+"'"+pwd+"'"+","+"'"+regtime+"'"+","+"'"+regimage+"'"+")";
		
		System.out.println(sqlinsert);
		String sqlquery = "select * from userif";
		Cursor cursor = db.rawQuery(sqlquery, null);

		if(cursor!=null)
		{
			db.execSQL(sqlinsert);
		}
		
		cursor.close();
		db.close();
		
		boolean flag = true;
		
		return flag;
		
		}
		else
		{
			toast( "账号或密码错误,请重新输入！");
			boolean flag=false;
			return flag;
	
			
		}
		
	}

		protected void onPostExecute(Boolean flag) {
			
		if(flag==true)
		{
			
				Intent intent = new Intent();
				intent.setClass(context, HomePage_tabhostActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				context.startActivity(intent);
				//给登录界面发消息 登录成功关闭它
				Message msg = new Message();
				msg.obj = "loginsuccess";
				mHandler.sendMessage(msg);
				
				
		}
		}

		

		public void toast(final String s)
	    {
	        Handler handler=new Handler(Looper.getMainLooper());
	        handler.post(
	                new Runnable() {
	                    @Override
	                    public void run() {
	                        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
	                    }
	                }
	        );


	    }



		

	}




