package com.st.asynctask;

import java.io.DataOutputStream; 
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.st.app.R;
import com.st.util.BaseUrl;
import com.st.util.DBOpenHelper;
import com.st.util.GetJsonData;
import com.st.util.NetUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsPublishAsyncTask extends AsyncTask<Void, Void, Void> {

	private String resultStr = ""; // 服务端返回结果集
	private String imgUrl = BaseUrl.uploadgoodsimageurl;
	
	Context mContext;
	private String picPath;
	private String goodsname,goodsdesc,goodsprice,classitemname,sortitemname;
	ImageView picImg;
	
	EditText goods_name;
	EditText goods_desc; 
	EditText goods_price;
	TextView goods_class;
	TextView goods_sort;
	
	public  GoodsPublishAsyncTask (Context mContext,EditText goods_name, EditText goods_desc, EditText goods_price, String picPath, String classitemname, String sortitemname, ImageView picImg,TextView goods_class,TextView goods_sort){
		
		this.mContext=mContext;
		this.picPath=picPath;
		this.goodsname=goods_name.getText().toString();
		this.goodsdesc=goods_desc.getText().toString();
		this.goodsprice=goods_price.getText().toString();
		this.classitemname=classitemname;
		this.sortitemname=sortitemname;
		this.picImg=picImg;
		this.goods_desc=goods_desc;
		this.goods_name=goods_name;
		this.goods_price=goods_price;
		this.goods_class=goods_class;
		this.goods_sort=goods_sort;
		
		
	}
	
	protected Void doInBackground(Void... params) {

		String userid = null;
		DBOpenHelper dbuser=new DBOpenHelper(mContext, "userif.db");
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
		
		
		
		
		String goodsimagesubstring = picPath.substring(picPath.lastIndexOf("/")+1);
		System.out.println("goodsimagesubstring:"+goodsimagesubstring);
		String str = GetJsonData.UploadGoods(goodsname,goodsdesc,goodsprice,classitemname,sortitemname,goodsimagesubstring,userid);
		if(str.equals("uploadgoodsuccess!"))
		{
		toast("正在上传物品图片，请稍候...");
		new Thread(uploadImageRunnable).start();}
		else
		{
			toast("上传物品失败！");
		}
		return null;
	}

	protected void onPostExecute(Void result) {

		 Handler handler=new Handler(Looper.getMainLooper()); 
	        handler.post(
	                new Runnable() {
	                    public void run() {
	                    	 picImg.setImageResource(R.drawable.ic_add_pic);
	                    	 goods_name.setText("");
	                    	 goods_desc.setText("");
	                    	 goods_price.setText("");
	                    	 goods_class.setText("");
	                    	 goods_sort.setText("");
	                    	 goods_class.setHint("请选择商品大类");
	             			 goods_sort.setHint("请选择商品小类");
	                    	
	                    	Toast.makeText(mContext, "发布物品成功！", Toast.LENGTH_SHORT).show();
	                    }
	                }
	                );
		
	}
	
	public void toast(final String s)
    {
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable() {
                    public void run() {
                        Toast.makeText(mContext,s,Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
	

	/**
	 * 使用HttpUrlConnection模拟post表单进行文件 上传平时很少使用，比较麻烦 原理是：
	 * 分析文件上传的数据格式，然后根据格式构造相应的发送给服务器的字符串。
	 */
	Runnable uploadImageRunnable = new Runnable() {
		@Override
		public void run() {

			if (TextUtils.isEmpty(imgUrl)) {

		        Handler handler=new Handler(Looper.getMainLooper());
		        handler.post(
		                new Runnable() {
		                    public void run() {
		                    	Toast.makeText(mContext, "还没有设置上传服务器的路径！", Toast.LENGTH_SHORT).show();
		                    }
		                }
		                );
				
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
				File file = new File(picPath);
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
					//Toast.makeText(mContext, "请求URL失败！", Toast.LENGTH_SHORT).show();
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
				System.out.println("返回的消息:"+resultStr);

			}
			return false;
		}
	});
	
	
	
	
	
	
	
	
	
}
