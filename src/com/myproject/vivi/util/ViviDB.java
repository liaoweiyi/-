package com.myproject.vivi.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myproject.vivi.db.ViviOpenHelper;
import com.myproject.vivi.model.Content;

//�����ݿ������װ����
public class ViviDB {
	// ���ݿ���
	public static final String DB_NAME = "vivi_notebook";
	// �汾
	public static final int VERSION = 1;

	private static ViviDB viviDB;
	private SQLiteDatabase db;

	// ���췽��˽�л�
	private ViviDB(Context context) {
		ViviOpenHelper voh = new ViviOpenHelper(context, DB_NAME, null, VERSION);
		db = voh.getWritableDatabase();
	}

	// ��ȡ���ݿ�ʵ��
	public synchronized static ViviDB getInstance(Context context) {
		if (viviDB == null) {
			viviDB = new ViviDB(context);
		}
		return viviDB;
	}

	// ��Contentʵ�����浽���ݿ�
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

	// �����ݿ��ȡ����Contentʵ��
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

	// �����ݿ���ɾ��ĳ������
	public void deleteContent(int id) {
		db.delete("Content", "id=?", new String[] { String.valueOf(id) });
	}

	// �����ݿ����޸�ĳ������
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
