package com.st.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsDetailFragment extends Fragment {


	//每次创建都会绘制fragment的view组件时回调该方法
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		//resource:Fragment需要加载的布局文件
		//root:加载layout的父ViewGroup
		//attachToRoot:false 不返回父ViewGroup
		View view = inflater.inflate(R.layout.goodsdetail, container, false);
		
		TextView tv=(TextView) view.findViewById(R.id.goodsname);
		
		Bundle arguments = getArguments();
		String text = arguments.get("goodsid")+"";
		tv.setText(text);
		
		Toast.makeText(getActivity(), "已成功接收到"+text, Toast.LENGTH_LONG).show();
		
		return view;
	}

}
