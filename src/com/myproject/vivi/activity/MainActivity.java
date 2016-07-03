package com.myproject.vivi.activity;

import java.util.ArrayList;
import java.util.List;
import com.myproject.vivi.R;
import com.myproject.vivi.adapter.ContentAdapter;
import com.myproject.vivi.model.Content;
import com.myproject.vivi.util.ActivityCollector;
import com.myproject.vivi.util.ViviDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// list
	private ListView lv;
	// 添加按钮
	private Button addButton;
	// ListView适配Content集合
	private List<Content> contentList = new ArrayList<Content>();
	// 适配器
	private ContentAdapter ca;
	// 请求码
	public static final int REQUEST_CODE_1 = 7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mybackground);
		ActivityCollector.addActivity(this);
		lv = (ListView) findViewById(R.id.list_view);
		addButton = (Button) findViewById(R.id.add_button);
		ca = new ContentAdapter(MainActivity.this, R.layout.list_item,
				contentList);
		lv.setAdapter(ca);
		// 加载已有数据数据
		queryContent();
		// 长按事件
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder ab = new AlertDialog.Builder(
						MainActivity.this);
				ab.setTitle("警告！")
						.setMessage("你确定删除这条记录吗")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stu
										Content ci2 = contentList.get(arg2);
										ViviDB viviDB = ViviDB
												.getInstance(MainActivity.this);
										viviDB.deleteContent(ci2.getId());
										lv.setAdapter(ca);
										queryContent();
										Toast.makeText(MainActivity.this,
												"这条内容已经删除", Toast.LENGTH_LONG)
												.show();

									}

								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
									}
								}).show();

				return true;
			}
		});
		// lv添加点击事件
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Content ci = contentList.get(arg2);
				Intent it = new Intent(MainActivity.this, LookActivity.class);
				it.putExtra("content", ci);
				startActivity(it);

			}
		});
		// 给添加按钮添加点击事件
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(MainActivity.this,
						AddEventActivity.class);
				startActivity(it);
			}
		});

	}

	// 从数据库获取数据
	private void queryContent() {
		ViviDB viviDB = ViviDB.getInstance(MainActivity.this);
		List<Content> cl = viviDB.loadContent();
		contentList.clear();
		contentList.addAll(cl);
		// 刷新
		ca.notifyDataSetChanged();
		// 将光标定位到最后一行
		lv.setSelection(contentList.size());

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

	// 退出
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
		ab.setTitle("提示")
				.setMessage("不留一下下咩？")
				.setPositiveButton("一定要走",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								finish();

							}

						})
				.setNegativeButton("留下", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

					}
				}).show();
	}

}
