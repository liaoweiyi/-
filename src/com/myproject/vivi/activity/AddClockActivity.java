package com.myproject.vivi.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import com.myproject.vivi.R;
import com.myproject.vivi.model.Content;

public class AddClockActivity extends Activity {
	private Button back;
	private Button done;
	private Switch sw;
	private LinearLayout wd;
	private TextView tv;
	private TextView selectTime;
	private TextView clockTime;
	private int isClock;
	private Content content;
	private CalendarView cv;
	private Calendar c1;
	private Calendar c2;
	private String time;
	private String time1;
	private String time2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_clock);
		sw = (Switch) findViewById(R.id.clock_switch);
		back = (Button) findViewById(R.id.back_button);
		done = (Button) findViewById(R.id.done_button);
		tv = (TextView) findViewById(R.id.clock_text);
		wd = (LinearLayout) findViewById(R.id.warn_day);
		cv = (CalendarView) findViewById(R.id.calendarview);
		selectTime = (TextView) findViewById(R.id.select_time);
		clockTime = (TextView) findViewById(R.id.select_time_2);
		content = (Content) getIntent().getSerializableExtra("content");
		isClock = content.getAlarmColock();
		time = content.getSelectTime();
		// 初始化
		initView();
		c1 = Calendar.getInstance();
		c1.setTimeInMillis(System.currentTimeMillis());
		c2 = Calendar.getInstance();
		// 设置初始时间
		time1 = c2.get(Calendar.YEAR) + "/" + c2.get(Calendar.MONTH) + "&";
		time2 = c2.get(Calendar.HOUR_OF_DAY) + ":" + c2.get(Calendar.MINUTE);

		// 监听日历
		cv.setOnDateChangeListener(new OnDateChangeListener() {

			@Override
			public void onSelectedDayChange(CalendarView arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				c1.set(Calendar.YEAR, arg1);
				c1.set(Calendar.MONTH, arg2);
				c1.set(Calendar.DATE, arg3);
				time1 = arg1 + "/" + arg2 + "/" + arg3 + "&";
			}
		});
		// 设置时间
		selectTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new TimePickerDialog(AddClockActivity.this,
						new OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker arg0, int arg1,
									int arg2) {
								// TODO Auto-generated method stub
								if (arg1 != 0) {
									c1.set(Calendar.HOUR_OF_DAY, arg1);
								}
								if (arg2 != 0) {
									c1.set(Calendar.MINUTE, arg2);
								}
								c1.set(Calendar.SECOND, 0);
								selectTime.setText(arg1 + ":" + arg2);
								time2 = arg1 + ":" + arg2;
							}
						}, c2.get(Calendar.HOUR_OF_DAY), c2
								.get(Calendar.MINUTE), true).show();

			}
		});
		// 监听确定
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				time = time1 + time2;
				setClock();
				// 返回数据给上一个活动
				Intent it3 = new Intent();
				it3.putExtra("add_clock", isClock);
				it3.putExtra("selectTime", time);
				setResult(RESULT_OK, it3);
				finish();
			}
		});
		// 监听返回
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 监听开关
		android.widget.CompoundButton.OnCheckedChangeListener listener = new android.widget.CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					isClock = Content.READ;
					tv.setText("通知");
					wd.setVisibility(View.VISIBLE);
				} else {
					isClock = Content.WHITE;
					tv.setText("不通知");
					wd.setVisibility(View.INVISIBLE);
				}

			}
		};
		sw.setOnCheckedChangeListener(listener);

	}

	// 初始化
	private void initView() {
		// TODO Auto-generated method stub
		if (isClock == Content.READ) {
			sw.setChecked(true);
			tv.setText("通知");
			wd.setVisibility(View.VISIBLE);
		} else if (isClock == Content.WHITE) {
			sw.setChecked(false);
			tv.setText("不通知");
			wd.setVisibility(View.INVISIBLE);
		}
		if (time != null) {
			clockTime.setText(time);
		}

	}

	protected void setClock() {
		if (isClock == Content.READ) {
			Intent intent = new Intent(AddClockActivity.this,
					AlarmReceiver.class);
			intent.putExtra("content", content);
			PendingIntent pi = PendingIntent.getBroadcast(
					AddClockActivity.this, content.getId(), intent,
					PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			// alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
			// 20 * 1000 + SystemClock.elapsedRealtime(), pi);
			alarmManager.set(AlarmManager.RTC_WAKEUP, c1.getTimeInMillis(), pi);
		} else if (isClock == Content.WHITE) {
			Intent intent = new Intent(AddClockActivity.this,
					AlarmReceiver.class);
			intent.putExtra("content", content);
			PendingIntent pi = PendingIntent.getBroadcast(
					AddClockActivity.this, content.getId(), intent,
					PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarmManager.cancel(pi);
		}

	}
}
