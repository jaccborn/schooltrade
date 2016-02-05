package com.st.app;

import java.io.DataOutputStream; 
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.st.asynctask.RegisterAsyncTask;
import com.st.custom.CircleImg;
import com.st.custom.SelectPicPopupWindow;
import com.st.util.BaseUrl;
import com.st.util.FileUtil;
import com.st.util.GetDate;
import com.st.util.NetUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {

	private Button register, back;
	private TextView imageurl;
	private EditText username,passwd,st_name,st_tel,st_place;
	
	private Handler mHandler;
	
	private Context mContext;
	private CircleImg headimage;// 头像图片
	private SelectPicPopupWindow menuWindow; // 自定义的头像编辑弹出框
	// 上传服务器的路径【一般不硬编码到程序中】
    private static final String IMAGE_FILE_NAME = "headImage.jpg";// 头像文件名称
    private String urlpath;			// 图片本地路径
    private static final int REQUESTCODE_PICK = 0;		// 相册选图标记
    private static final int REQUESTCODE_TAKE = 1;		// 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2;	// 图片裁切标记

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);

		
		mContext = RegisterActivity.this;
		initView();
		
		Exit.activityList.add(this);
		
		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// 接收消息
				if (msg.obj == "registersuccess") 
				{
					menuWindow.dismiss();
					
					finish();
				}
			}
		};

	}

	private void initView() {
		register = (Button) findViewById(R.id.bt_reg_reg);
		back = (Button) findViewById(R.id.bt_reg_back);
		
		username=(EditText) findViewById(R.id.et_username);
		passwd=(EditText) findViewById(R.id.et_password);
		st_name=(EditText) findViewById(R.id.st_name);
		st_tel=(EditText) findViewById(R.id.st_tel);
		st_place=(EditText) findViewById(R.id.st_place);

		headimage = (CircleImg) findViewById(R.id.headimage);
		
		imageurl = (TextView) findViewById(R.id.img_url);

		register.setOnClickListener(this);
		back.setOnClickListener(this);
		headimage.setOnClickListener(this);
		
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_reg_back:
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.bt_reg_reg:
			//注册功能  先查一下是否注册过 
			String user=username.getText().toString();
			String pwd=passwd.getText().toString();
			String url=imageurl.getText().toString();
			String stname=st_name.getText().toString();
			String sttel=st_tel.getText().toString();
			String stplace=st_place.getText().toString();
			if (user.length()==0&&user.isEmpty()) 
			{
				Toast.makeText(getApplicationContext(), "账号不能为空噢！", Toast.LENGTH_SHORT).show();
				break;
			}
			if (pwd.length()==0&&pwd.isEmpty()) 
			{
				Toast.makeText(getApplicationContext(), "密码不能为空噢！", Toast.LENGTH_SHORT).show();
				break;
			}
			if (stname.length()==0&&stname.isEmpty()) 
			{
				Toast.makeText(getApplicationContext(), "店名不能为空噢！", Toast.LENGTH_SHORT).show();
				break;
			}
			if (sttel.length()==0&&sttel.isEmpty()) 
			{
				Toast.makeText(getApplicationContext(), "联系方式不能为空噢！", Toast.LENGTH_SHORT).show();
				break;
			}
			if (stplace.length()==0&&stplace.isEmpty()) 
			{
				Toast.makeText(getApplicationContext(), "地址不能为空噢！", Toast.LENGTH_SHORT).show();
				break;
			}
			if(user.length()!=0&&!user.isEmpty()&&pwd.length()!=0&&!pwd.isEmpty()&&stname.length()!=0&&!stname.isEmpty()&&sttel.length()!=0&&!sttel.isEmpty()&&stplace.length()!=0&&!stplace.isEmpty())
			{
				//这里处理一下图片的url 也就是说 如果用户没有选择 则url可以为空
				new RegisterAsyncTask(RegisterActivity.this, user, pwd, url+"",stname,sttel,stplace,mHandler).execute();
			}


			//选择一张图片作为用户头像
			case R.id.headimage:
			
			menuWindow = new SelectPicPopupWindow(mContext, itemsOnClick);  
			menuWindow.showAtLocation(findViewById(R.id.registerLayout),Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
			break;
		default:
			break;
		}
	}
	
	//为弹出窗口实现监听类  
			private OnClickListener itemsOnClick = new OnClickListener() {
				public void onClick(View v) {
					
					menuWindow.dismiss();
					
					switch (v.getId()) {
					// 拍照
					case R.id.takePhotoBtn:
						Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						//下面这句指定调用相机拍照后的照片存储的路径
						takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, 
								Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
						startActivityForResult(takeIntent, REQUESTCODE_TAKE);
						break;
					// 相册选择图片
					case R.id.pickPhotoBtn:
						Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
						// 如果要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
						pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
						startActivityForResult(pickIntent, REQUESTCODE_PICK);
						break;
					default:
						break;
					}
				}
			}; 
		
		
			public void onActivityResult(int requestCode, int resultCode, Intent data) {
				
				switch (requestCode) {
				case REQUESTCODE_PICK:// 直接从相册获取
					try {
						startPhotoZoom(data.getData());
					} catch (NullPointerException e) {
						e.printStackTrace();// 用户点击取消操作
					}
					break;
				case REQUESTCODE_TAKE:// 调用相机拍照
					File temp = new File(Environment.getExternalStorageDirectory() + "/SchoolTrade/headimage/" + IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(temp));
					break;
				case REQUESTCODE_CUTTING:// 取得裁剪后的图片
					if (data != null) {
						setPicToView(data);
					}
					break;
				}
				super.onActivityResult(requestCode, resultCode, data);
			}
		
		
			/**
			 * 裁剪图片方法实现
			 * @param uri
			 */
			public void startPhotoZoom(Uri uri) {
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(uri, "image/*");
				// crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
				intent.putExtra("crop", "true");
				// aspectX aspectY 是宽高的比例
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				// outputX outputY 是裁剪图片宽高
				intent.putExtra("outputX", 300);
				intent.putExtra("outputY", 300);
				intent.putExtra("return-data", true);
				startActivityForResult(intent, REQUESTCODE_CUTTING);
			}
		
			/**
			 * 保存裁剪之后的图片数据
			 * @param picdata
			 */
			private void setPicToView(Intent picdata) {
				Bundle extras = picdata.getExtras();
				if (extras != null) {
					// 取得SDCard图片路径做显示
					Bitmap photo = extras.getParcelable("data");
					Drawable drawable = new BitmapDrawable(null, photo);
					String currenttime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					urlpath = FileUtil.saveFile(mContext, currenttime+"temphead.jpg", photo);
					
					System.out.println("urlpath:"+urlpath);
					
					imageurl.setText(urlpath);
					headimage.setImageDrawable(drawable);
					

				
				}
			}
			
			
		
	
	
	
	
	
	
	
	
	
	
	
	

}
