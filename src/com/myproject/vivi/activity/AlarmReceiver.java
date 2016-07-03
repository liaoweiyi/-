package com.myproject.vivi.activity;

import com.myproject.vivi.model.Content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Content content = (Content) arg1.getSerializableExtra("content");
		
		Intent it= new Intent(arg0,AlarmService.class);
		it.putExtra("content", content);
		arg0.startService(it);

	}

}
