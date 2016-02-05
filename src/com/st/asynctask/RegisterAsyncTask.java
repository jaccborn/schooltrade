package com.st.asynctask;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.st.activity.XZTApplication;
import com.st.app.GoodsDetailActivity;
import com.st.app.HomePage_tabhostActivity;
import com.st.app.LoginActivity;
import com.st.app.RegisterActivity;
import com.st.bean.UserIf;
import com.st.util.BaseUrl;
import com.st.util.DBOpenHelper;
import com.st.util.GetJsonData;
import com.st.util.NetUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.Toast;

public class RegisterAsyncTask extends AsyncTask<Void, Void, String> {

	private Context context;
	private String username;
	private String pwd;
	private String imageurl;
	private String stname;
	private String sttel;
	private String stplace;

	private Handler mHandler;
	
	private String imgUrl = BaseUrl.uploadheadimageurl;
	private String resultStr = ""; // 服务端返回结果集

	
	public RegisterAsyncTask(Context context, String username, String pwd,
			String imageurl, String stname, String sttel, String stplace,
			Handler mHandler) {
		this.context = context;
		this.username = username;
		this.pwd = pwd;
		this.imageurl = imageurl;
		this.stname = stname;
		this.sttel = sttel;
		this.stplace = stplace;
		this.mHandler = mHandler;

	}

	protected String doInBackground(Void... params) {

		// 这里判断一下 是否有注册过
		String flag = GetJsonData.CheckUsername(username);
		if (!flag.equals("exist")) {
			System.out.println(imageurl);
			String imagesubstring = imageurl.substring(imageurl
					.lastIndexOf("/") + 1);
			System.out.println("imagesubstring:" + imagesubstring);
			String str = GetJsonData
					.RegisterUser(username, pwd, imagesubstring);

			List<UserIf> list = GetJsonData.CheckLoginInf(username, pwd);
			String userId = list.get(0).getUserId();
			GetJsonData.addStore(stname, sttel, stplace, userId);
			String regtime = list.get(0).registerTime;
			String regimage = list.get(0).registerImage;

			// 将信息存入本地数据库
			DBOpenHelper userdb = new DBOpenHelper(context, "userif.db");
			SQLiteDatabase db = userdb.getWritableDatabase();
			String sqlinsert = "insert into userif(userid,username,userpwd,registertime,registerimage) values('"
					+ userId
					+ "'"
					+ ","
					+ "'"
					+ username
					+ "'"
					+ ","
					+ "'"
					+ pwd
					+ "'"
					+ ","
					+ "'"
					+ regtime
					+ "'"
					+ ","
					+ "'"
					+ regimage + "'" + ")";

			System.out.println(sqlinsert);
			String sqlquery = "select * from userif";
			Cursor cursor = db.rawQuery(sqlquery, null);

			if (cursor != null) {
				db.execSQL(sqlinsert);
			}

			cursor.close();
			db.close();

			toast("正在上传图片，请稍候...");
		

			
			
			new Thread(uploadImageRunnable).start();

			return str;

		} else {
			return "0";
		}

	}

	protected void onPostExecute(String str) {

		if (!str.equals("0")) {

			
			Toast.makeText(context, "注册成功！", Toast.LENGTH_SHORT).show();

			UserIf userif = new UserIf();
			userif.setUserName(username);

			// 注册成功后 直接跳转主界面 这里给主界面传一个userif 里面含有当前注册用户的信息
			Intent intent = new Intent();
			intent.setClass(context, HomePage_tabhostActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("userif", userif);
			intent.putExtras(bundle);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			// 给注册界面发消息 注册成功关闭它
			Message msg = new Message();
			msg.obj = "registersuccess";
			mHandler.sendMessage(msg);

		}

		else {
			toast("您已注册过 不要闹啦！");
			Intent intent = new Intent();
			intent.setClass(context, LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			// 给注册界面发消息 注册成功关闭它
			Message msg = new Message();
			msg.obj = "registersuccess";
			mHandler.sendMessage(msg);

		}

	}

	public void toast(final String s) {
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 使用HttpUrlConnection模拟post表单进行文件 上传平时很少使用，比较麻烦 原理是：
	 * 分析文件上传的数据格式，然后根据格式构造相应的发送给服务器的字符串。
	 */
	Runnable uploadImageRunnable = new Runnable() {
		public void run() {

			if (TextUtils.isEmpty(imgUrl)) {

				Handler handler = new Handler(Looper.getMainLooper());
				handler.post(new Runnable() {
					public void run() {
						Toast.makeText(context, "还没有设置上传服务器的路径！",
								Toast.LENGTH_SHORT).show();
					}
				});

				return;
			}

			Map<String, String> textParams = new HashMap<String, String>();
			Map<String, File> fileparams = new HashMap<String, File>();

			try {
				// 创建一个URL对象
				URL url = new URL(imgUrl);
				textParams = new HashMap<String, String>();
				fileparams = new HashMap<String, File>();
				// 要上传的图片文件
				File file = new File(imageurl);
				fileparams.put("image", file);
				// 利用HttpURLConnection对象从网络中获取网页数据
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				// 设置连接超时（记得设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作）
				conn.setConnectTimeout(5000);
				// 设置允许输出（发送POST请求必须设置允许输出）
				conn.setDoOutput(true);
				// 设置使用POST的方式发送
				conn.setRequestMethod("POST");
				// 设置不使用缓存（容易出现问题）
				conn.setUseCaches(false);
				conn.setRequestProperty("Charset", "UTF-8");// 设置编码
				// 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
				conn.setRequestProperty("ser-Agent", "Fiddler");
				// 设置contentType
				conn.setRequestProperty("Content-Type",
						"multipart/form-data; boundary=" + NetUtil.BOUNDARY);
				OutputStream os = conn.getOutputStream();
				DataOutputStream ds = new DataOutputStream(os);
				NetUtil.writeStringParams(textParams, ds);
				NetUtil.writeFileParams(fileparams, ds);
				NetUtil.paramsEnd(ds);
				// 对文件流操作完,要记得及时关闭
				os.close();
				// 服务器返回的响应吗
				int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
				// 对响应码进行判断
				if (code == 200) {// 返回的响应码200,是成功
					// 得到网络返回的输入流
					InputStream is = conn.getInputStream();
					resultStr = NetUtil.readString(is);
				} else {

					Handler handler = new Handler(Looper.getMainLooper());
					handler.post(new Runnable() {
						public void run() {
							//Toast.makeText(context, "请求不到返回结果！",Toast.LENGTH_SHORT).show();
						}
					});

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
		}
	};

	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// pd.dismiss();
				System.out.println("返回的消息:" + resultStr);
			}
			return false;
		}
	});

}
