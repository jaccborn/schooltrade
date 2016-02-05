package com.st.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	
	public DBOpenHelper(Context context, String name) {
		super(context, name,null,1);

	}
	
	
	
	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

	}

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);

	}

	//首次创建数据库的时候调用 一般可以把建库建表的操作放在这里
	public void onCreate(SQLiteDatabase db) {

		String sqlcreate = "create table if not exists userif(_id integer primary key autoincrement,userid text not null,username text not null,userpwd text not null,registertime text not null,registerimage text )";

		//String sqlinsert = "insert into user3(name,age,sex) values('cccccc','108','Ů')";

		db.execSQL(sqlcreate);
		//db.execSQL(sqlinsert);
		
	}

	//当数据库的版本发生变化的时候会自动执行
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		
	}

}
