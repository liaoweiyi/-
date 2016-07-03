package com.myproject.vivi.activity;

import java.util.TimerTask;

import com.myproject.vivi.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start);

		Handler	handler = new Handler();
			TimerTask task = new TimerTask() {
				public void run() {
					Intent it=new Intent(StartActivity.this,MainActivity.class);
					startActivity(it);
					finish();
				
				}
			};

			// 2。5秒后执行TimerTask任务
			handler.postDelayed(task, 2500);
		
	}

}
