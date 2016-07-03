package com.myproject.vivi.activity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.myproject.vivi.R;
import com.myproject.vivi.adapter.WeatherAdapter;
import com.myproject.vivi.model.Content;
import com.myproject.vivi.model.WeatherList;
import com.myproject.vivi.util.ActivityCollector;
import com.myproject.vivi.util.ViviDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends Activity {
	private TextView tv;
	private EditText et;
	private Button setClock;
	private Button back;
	private Button iv;
	private Button done;
	private Button place;
	private Content content;
	private int isClock;
	private Calendar c;
	private WeatherAdapter wa;
	private String selectTime;
	private List<WeatherList> list = new ArrayList<WeatherList>();
	// 选择的天气
	private int index=-1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_event);
		// 初始化天气数据
		initWeather();
		wa = new WeatherAdapter(this, R.layout.weather_list, list);
		tv = (TextView) findViewById(R.id.time_text_view);
		et = (EditText) findViewById(R.id.content_edit);
		setClock = (Button) findViewById(R.id.set_clock);
		back = (Button) findViewById(R.id.back_button);
		iv = (Button) findViewById(R.id.set_weather);
		done = (Button) findViewById(R.id.done_button);
		place = (Button) findViewById(R.id.set_place);
		content = (Content) getIntent().getSerializableExtra("content");
		isClock=content.getAlarmColock();
		isWeather(content.getnWeather());
		et.setText(content.getnContent());
		tv.setText(content.getnTime_1());
		c = Calendar.getInstance();
		// 闹钟设置
		setClock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				 Intent it2 = new Intent(EditActivity.this,
				 AddClockActivity.class); 
				 it2.putExtra("content", content);
				 startActivityForResult(it2,1);
				 

			}
		});
		// 天气
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(EditActivity.this)
						.setTitle("天气")
						.setSingleChoiceItems(wa, -1,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										index = arg1;
										switch (arg1) {
										case 0:
											iv.setBackgroundResource(R.drawable.sun);
											break;
										case 1:
											iv.setBackgroundResource(R.drawable.cloudy);
											break;
										case 2:
											iv.setBackgroundResource(R.drawable.moon);
											break;
										case 3:
											iv.setBackgroundResource(R.drawable.rain);
											break;
										case 4:
											iv.setBackgroundResource(R.drawable.mcloudy);
											break;
										default:
											break;
										}
										arg0.dismiss();

									}
								}).show();

			}
		});

		// 完成
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setWeather();
				content.setAlarmColock(isClock);
				content.setSelectTime(selectTime);
				content.setnTime_1(tv.getText().toString());
				content.setnContent(et.getText().toString());
				content.setnTime_2(DateFormat.getDateTimeInstance(
						DateFormat.FULL, DateFormat.SHORT).format(new Date()));
				ViviDB.getInstance(EditActivity.this).updateContent(
						content.getId(), content);
				ActivityCollector.finishAll();
				Intent it = new Intent(EditActivity.this, MainActivity.class);
				startActivity(it);
				finish();

			}

		});

		// 重新设置时间
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// 创建一个DatePickerDialog对话实例框
				new DatePickerDialog(EditActivity.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker dp, int year,
									int month, int dayofMonth) {
								tv.setText(year + "/" + (month + 1) + "/"
										+ dayofMonth);

							}
						}
						// 设置初始日期
						, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH)).show();

			}
		});

		// 返回
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	// 天气返回
	protected void setWeather() {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			content.setnWeather(Content.WEATHER_SUN);

			break;
		case 1:
			content.setnWeather(Content.WEATHER_CLOUDY);
			break;
		case 2:
			content.setnWeather(Content.WEATHER_MOON);
			break;
		case 3:
			content.setnWeather(Content.WEATHER_RAIN);
			break;
		case 4:
			content.setnWeather(Content.WEATHER_MCLOUDY);
			break;
		default:
			content.setnWeather(content.getnWeather());
			break;
		}

	}

	// 初始化天气数据
	private void initWeather() {
		// TODO Auto-generated method stub
		WeatherList sun = new WeatherList(R.drawable.sun);
		list.add(sun);
		WeatherList cloudy = new WeatherList(R.drawable.cloudy);
		list.add(cloudy);
		WeatherList moon = new WeatherList(R.drawable.moon);
		list.add(moon);
		WeatherList rain = new WeatherList(R.drawable.rain);
		list.add(rain);
		WeatherList mcloudy = new WeatherList(R.drawable.mcloudy);
		list.add(mcloudy);

	}

	private void isWeather(int getnWeather) {
		// TODO Auto-generated method stub

		switch (getnWeather) {
		case Content.WEATHER_SUN:
			iv.setBackgroundResource(R.drawable.sun);
			break;
		case Content.WEATHER_CLOUDY:
			iv.setBackgroundResource(R.drawable.cloudy);
			break;
		case Content.WEATHER_MOON:
			iv.setBackgroundResource(R.drawable.moon);
			break;
		case Content.WEATHER_RAIN:
			iv.setBackgroundResource(R.drawable.rain);
			break;
		case Content.WEATHER_MCLOUDY:
			iv.setBackgroundResource(R.drawable.mcloudy);
			break;
		default:
			iv.setBackgroundResource(R.drawable.sun);
			break;

		}
	}
	// 接收下个活动返回的数据
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			switch (requestCode) {
			case 1:
				if (resultCode == RESULT_OK) {
					isClock = data.getIntExtra("add_clock", Content.WHITE);
					selectTime=data.getStringExtra("selectTime");
				}
				break;

			default:
				break;
			}
		}


}
