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
	// ��Ӱ�ť
	private Button addButton;
	// ListView����Content����
	private List<Content> contentList = new ArrayList<Content>();
	// ������
	private ContentAdapter ca;
	// ������
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
		// ����������������
		queryContent();
		// �����¼�
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder ab = new AlertDialog.Builder(
						MainActivity.this);
				ab.setTitle("���棡")
						.setMessage("��ȷ��ɾ��������¼��")
						.setPositiveButton("ȷ��",
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
												"���������Ѿ�ɾ��", Toast.LENGTH_LONG)
												.show();

									}

								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
									}
								}).show();

				return true;
			}
		});
		// lv��ӵ���¼�
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
		// ����Ӱ�ť��ӵ���¼�
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(MainActivity.this,
						AddEventActivity.class);
				startActivity(it);
			}
		});

	}

	// �����ݿ��ȡ����
	private void queryContent() {
		ViviDB viviDB = ViviDB.getInstance(MainActivity.this);
		List<Content> cl = viviDB.loadContent();
		contentList.clear();
		contentList.addAll(cl);
		// ˢ��
		ca.notifyDataSetChanged();
		// ����궨λ�����һ��
		lv.setSelection(contentList.size());

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

	// �˳�
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
		ab.setTitle("��ʾ")
				.setMessage("����һ�����㣿")
				.setPositiveButton("һ��Ҫ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								finish();

							}

						})
				.setNegativeButton("����", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

					}
				}).show();
	}

}
