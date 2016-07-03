package com.myproject.vivi.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myproject.vivi.db.ViviOpenHelper;
import com.myproject.vivi.model.Content;

//对数据库操作分装起来
public class ViviDB {
	// 数据库名
	public static final String DB_NAME = "vivi_notebook";
	// 版本
	public static final int VERSION = 1;

	private static ViviDB viviDB;
	private SQLiteDatabase db;

	// 构造方法私有化
	private ViviDB(Context context) {
		ViviOpenHelper voh = new ViviOpenHelper(context, DB_NAME, null, VERSION);
		db = voh.getWritableDatabase();
	}

	// 获取数据库实例
	public synchronized static ViviDB getInstance(Context context) {
		if (viviDB == null) {
			viviDB = new ViviDB(context);
		}
		return viviDB;
	}

	// 将Content实例储存到数据库
	public void saveContent(Content content) {
		if (content != null) {
			ContentValues values = new ContentValues();
			values.put("content", content.getnContent());
			values.put("time_1", content.getnTime_1());
			values.put("time_2", content.getnTime_2());
			values.put("place", content.getnPlace());
			values.put("weather", content.getnWeather());
			values.put("alarmColock", content.getAlarmColock());
			values.put("selectTime", content.getSelectTime());
			db.insert("Content", null, values);
		}
	}

	// 从数据库读取所有Content实例
	public List<Content> loadContent() {
		List<Content> list = new ArrayList<Content>();
		Cursor cursor = db.query("Content", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Content content = new Content();
				content.setId(cursor.getInt(cursor.getColumnIndex("id")));
				content.setAlarmColock(cursor.getInt(cursor
						.getColumnIndex("alarmColock")));
				content.setnContent(cursor.getString(cursor
						.getColumnIndex("content")));
				content.setnPlace(cursor.getString(cursor
						.getColumnIndex("place")));
				content.setnTime_1(cursor.getString(cursor
						.getColumnIndex("time_1")));
				content.setnTime_2(cursor.getString(cursor
						.getColumnIndex("time_2")));
				content.setnWeather(cursor.getInt(cursor
						.getColumnIndex("weather")));
				content.setSelectTime(cursor.getString(cursor
						.getColumnIndex("selectTime")));
				list.add(content);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;

	}

	// 从数据库中删除某条内容
	public void deleteContent(int id) {
		db.delete("Content", "id=?", new String[] { String.valueOf(id) });
	}

	// 从数据库中修改某条内容
	public void updateContent(int id, Content content) {
		ContentValues values = new ContentValues();
		values.put("content", content.getnContent());
		values.put("time_1", content.getnTime_1());
		values.put("time_2", content.getnTime_2());
		values.put("place", content.getnPlace());
		values.put("weather", content.getnWeather());
		values.put("alarmColock", content.getAlarmColock());
		values.put("selectTime", content.getSelectTime());
		db.update("Content", values, "id=?",
				new String[] { String.valueOf(id) });
	}

}
