package com.myproject.vivi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ViviOpenHelper extends SQLiteOpenHelper {

	// 内容表建表语句
	public static final String CREATE_CONTENT = "create table Content("
			+ "id integer primary key autoincrement," 
			+ "content text,"
			+ "time_1 text,"
			+ "time_2 text," 
			+ "place text," 
			+ "selectTime text,"
			+ "weather integer,"
			+ "alarmColock integer)";

	public ViviOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_CONTENT);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
