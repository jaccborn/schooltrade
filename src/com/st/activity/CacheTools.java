package com.st.activity;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheTools {
	
	private static SharedPreferences USER_DATA;
	
	public static void initCache(Context context){
		USER_DATA = context.getSharedPreferences("userdata", 0);
	}
	
	public static void setUserData(String name,String val){
		SharedPreferences.Editor editor = USER_DATA.edit();
		editor.putString(name, val);
		editor.commit();
	}
	
	public static void clearUserData(String name){
		SharedPreferences.Editor editor = USER_DATA.edit();
		editor.remove(name);
		editor.commit();
	}
	
	public static String getUserData(String name){
		return USER_DATA.getString(name, null);
	}
}
