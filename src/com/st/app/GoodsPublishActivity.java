package com.st.app;

import com.st.asynctask.GoodsPublishAsyncTask;
import com.st.asynctask.WheelViewClassAsyncTask;
import com.st.asynctask.WheelViewSortAsyncTask;
import com.st.custom.SelectPicPopupWindow;
import com.st.custom.WheelView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsPublishActivity extends Activity implements OnClickListener {
	private Context mContext;
	private Button funBtn, btclear;
	private ImageView picImg;
	private SelectPicPopupWindow menuWindow;

	private EditText goods_name, goods_price, goods_desc;

	private Uri photoUri;
	/** 使用照相机拍照获取图片 */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	/** 使用相册中的图片 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	/** 获取到的图片路径 */
	private String picPath = "";

	WheelView publishgoodsclass, publishgoodssort;

	String classitemname;
	String sortitemname;

	TextView goods_class, goods_sort;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.goodspublish_layout);

		mContext = GoodsPublishActivity.this;

		initView();

		Exit.activityList.add(this);
	}

	/**
	 * 初始化页面控件
	 */
	private void initView() {
		funBtn = (Button) findViewById(R.id.funBtn);
		btclear = (Button) findViewById(R.id.clear);
		picImg = (ImageView) findViewById(R.id.picImg);
		picImg.setTag("0");

		goods_name = (EditText) findViewById(R.id.publishgoodsname);
		goods_desc = (EditText) findViewById(R.id.publishgoodsdesc);
		goods_price = (EditText) findViewById(R.id.publishgoodsprice);
		publishgoodsclass = (WheelView) findViewById(R.id.publishgoodsclass);
		publishgoodssort = (WheelView) findViewById(R.id.publishgoodssort);

		goods_sort = (TextView) findViewById(R.id.goodssort);
		goods_class = (TextView) findViewById(R.id.goodsclass);

		funBtn.setText("发布");

		funBtn.setOnClickListener(this);
		btclear.setOnClickListener(this);
		picImg.setOnClickListener(this);
		goods_class.setOnClickListener(this);
		goods_sort.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.funBtn:
			String goodsname = goods_name.getText().toString();
			String goodsdesc = goods_desc.getText().toString();
			String goodsprice = goods_price.getText().toString();

			classitemname = goods_class.getText().toString();
			sortitemname = goods_sort.getText().toString();

			if (goodsname.length() == 0 && goodsname.isEmpty()) {
				Toast.makeText(getApplicationContext(), "商品名称不可为空！",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (goodsdesc.length() == 0 && goodsdesc.isEmpty()) {
				Toast.makeText(getApplicationContext(), "商品描述不可为空！",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (goodsprice.length() == 0 && goodsprice.isEmpty()) {
				Toast.makeText(getApplicationContext(), "商品价格不可为空！",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (classitemname.length() == 0 && classitemname.isEmpty()) {
				Toast.makeText(getApplicationContext(), "请选择一个大类别！",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (sortitemname.length() == 0 && sortitemname.isEmpty()) {
				Toast.makeText(getApplicationContext(), "请选择一个小类别！",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (picImg.getTag().equals("0")) {
				Toast.makeText(getApplicationContext(), "请选择一张图片！",
						Toast.LENGTH_SHORT).show();
				break;
			}

			if (goodsname.length() != 0 && !goodsname.isEmpty()
					&& goodsdesc.length() != 0 && !goodsdesc.isEmpty()
					&& goodsprice.length() != 0 && !goodsprice.isEmpty()
					&& !picImg.getTag().equals("0")
					&& classitemname.length() != 0 && !classitemname.isEmpty()
					&& sortitemname.length() != 0 && !sortitemname.isEmpty()) {

				new GoodsPublishAsyncTask(getApplicationContext(), goods_name,
						goods_desc, goods_price, picPath, classitemname,
						sortitemname, picImg,goods_class,goods_sort).execute();
				break;
			}

		case R.id.picImg:// 添加图片点击事件
			// 从页面底部弹出一个窗体，选择拍照还是从相册选择已有图片
			menuWindow = new SelectPicPopupWindow(mContext, itemsOnClick);
			menuWindow.showAtLocation(findViewById(R.id.uploadgoodsLayout),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			break;
		case R.id.goodsclass:

			new WheelViewClassAsyncTask(GoodsPublishActivity.this,
					publishgoodsclass, goods_class).execute();

			break;
		case R.id.goodssort:

			new WheelViewSortAsyncTask(GoodsPublishActivity.this,
					publishgoodssort, goods_sort).execute();
			break;
		case R.id.clear:

			picImg.setBackgroundResource(R.drawable.ic_add_pic);
			goods_name.setText("");
			goods_desc.setText("");
			goods_price.setText("");
			goods_class.setHint("请选择商品大类");
			goods_sort.setHint("请选择商品小类");
			break;
		default:
			break;
		}
	}

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 隐藏弹出窗口
			menuWindow.dismiss();

			switch (v.getId()) {
			case R.id.takePhotoBtn:// 拍照
				takePhoto();
				break;
			case R.id.pickPhotoBtn:// 相册选择图片
				pickPhoto();
				break;
			case R.id.cancelBtn:// 取消
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 拍照获取图片
	 */
	private void takePhoto() {
		// 执行拍照前，应该先判断SD卡是否存在
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			/***
			 * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
			 * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
			 */
			ContentValues values = new ContentValues();
			photoUri = getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
			startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
		} else {
			Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
		}
	}

	/***
	 * 从相册中取图片
	 */
	private void pickPhoto() {
		Intent intent = new Intent();
		// 如果要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 点击取消按钮
		if (resultCode == RESULT_CANCELED) {
			return;
		}

		// 可以使用同一个方法，这里分开写为了防止以后扩展不同的需求
		switch (requestCode) {
		case SELECT_PIC_BY_PICK_PHOTO:// 如果是直接从相册获取
			doPhoto(requestCode, data);
			break;
		case SELECT_PIC_BY_TACK_PHOTO:// 如果是调用相机拍照时
			doPhoto(requestCode, data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 选择图片后，获取图片的路径
	 * 
	 * @param requestCode
	 * @param data
	 */
	private void doPhoto(int requestCode, Intent data) {

		// 从相册取图片，有些手机有异常情况，请注意
		if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
			if (data == null) {
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
			photoUri = data.getData();
			System.out.println("photoUri:" + photoUri);
			if (photoUri == null) {
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
		}

		String[] pojo = { MediaStore.Images.Media.DATA };
		// The method managedQuery() from the type Activity is deprecated
		// Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
		Cursor cursor = mContext.getContentResolver().query(photoUri, pojo,
				null, null, null);
		if (cursor != null) {
			int columnIndex = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			picPath = cursor.getString(columnIndex);

			System.out.println("pojo:" + pojo.toString());
			System.out.println("columnIndex:" + columnIndex);
			System.out.println("picPath:" + picPath);
			// 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
			if (Integer.parseInt(Build.VERSION.SDK) < 14) {
				cursor.close();
			}
		}

		// 如果图片符合要求将其上传到服务器
		if (picPath != null
				&& (picPath.endsWith(".png") || picPath.endsWith(".PNG")
						|| picPath.endsWith(".jpg") || picPath.endsWith(".JPG")
						|| picPath.endsWith(".jpeg") || picPath
							.endsWith(".JPEG"))) {

			BitmapFactory.Options option = new BitmapFactory.Options();
			// 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图
			option.inSampleSize = 1;
			// 根据图片的SDCard路径读出Bitmap
			Bitmap bm = BitmapFactory.decodeFile(picPath, option);
			// 显示在图片控件上
			picImg.setImageBitmap(bm);
			picImg.setTag("1");

		} else {

			Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
		}

	}

}
