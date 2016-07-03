package com.myproject.vivi.activity;

import com.myproject.vivi.R;
import com.myproject.vivi.model.Content;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {
	private Content content;

	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		content = (Content) intent.getSerializableExtra("content");
		Intent it = new Intent(this, Alarm.class);
		it.putExtra("content", content);
		PendingIntent pi = PendingIntent.getActivity(this, 0, it,
				PendingIntent.FLAG_CANCEL_CURRENT);
		;
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Notification no = new Notification(R.drawable.ic_launcher, "时间轴有提醒！",
				System.currentTimeMillis());
		no.setLatestEventInfo(this, "注意！注意哦！", content.getnContent(), pi);
		no.defaults = Notification.DEFAULT_ALL;
		nm.notify(content.getId(), no);
		stopSelf();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
