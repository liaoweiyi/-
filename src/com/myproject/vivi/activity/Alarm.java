package com.myproject.vivi.activity;

import com.myproject.vivi.R;
import com.myproject.vivi.model.Content;
import com.myproject.vivi.util.ViviDB;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Alarm extends Activity {
	private Button finish;
	private Button shelve;
	private TextView tv1;
	private TextView tv2;
	private Content content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.clock);
		finish = (Button) findViewById(R.id.finish);
		shelve = (Button) findViewById(R.id.shelve);
		tv1 = (TextView) findViewById(R.id.clock_time);
		tv2 = (TextView) findViewById(R.id.clock_content);
		content = (Content) getIntent().getSerializableExtra("content");
		tv1.setText(content.getnTime_1());
		tv2.setText(content.getnContent().toString());
		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content.setAlarmColock(Content.WHITE);
				ViviDB.getInstance(Alarm.this).updateContent(content.getId(),
						content);
				NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				nm.cancel(content.getId());
				Intent it1=new Intent(Alarm.this,MainActivity.class);
				startActivity(it1);
				finish();

			}
		});
		shelve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();

			}
		});

	}

}
