package com.myproject.vivi.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.myproject.vivi.R;
import com.myproject.vivi.adapter.WeatherAdapter;
import com.myproject.vivi.model.Content;
import com.myproject.vivi.model.WeatherList;
import com.myproject.vivi.util.ActivityCollector;
import com.myproject.vivi.util.ViviDB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class AddEventActivity extends Activity {

	private TextView tv;
	private EditText et;
	private Button setClock;
	private Button setWeather;
	private LocationManager lm;
	private String provider;
	private Button done;
	private Button back;
	private Button place;
	private Content content = new Content();
	private Calendar c;
	private String placeText;
	private WeatherAdapter wa;
	// ѡ�������
	private int index;
	private List<WeatherList> list = new ArrayList<WeatherList>();
	public static final int SHOW_LOCATION = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_event);
		// ��ʼ����������
		initWeather();
		wa = new WeatherAdapter(this, R.layout.weather_list, list);
		tv = (TextView) findViewById(R.id.time_text_view);
		et = (EditText) findViewById(R.id.content_edit);
		setClock = (Button) findViewById(R.id.set_clock);
		back = (Button) findViewById(R.id.back_button);
		setWeather = (Button) findViewById(R.id.set_weather);
		done = (Button) findViewById(R.id.done_button);
		place = (Button) findViewById(R.id.set_place);
		c = Calendar.getInstance();
		setClock.setVisibility(View.GONE);
		// ��������
		setWeather.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(AddEventActivity.this)
						.setTitle("����")
						.setSingleChoiceItems(wa, 0,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										index = arg1;
										switch (arg1) {
										case 0:
											setWeather
													.setBackgroundResource(R.drawable.sun);
											break;
										case 1:
											setWeather
													.setBackgroundResource(R.drawable.cloudy);
											break;
										case 2:
											setWeather
													.setBackgroundResource(R.drawable.moon);
											break;
										case 3:
											setWeather
													.setBackgroundResource(R.drawable.rain);
											break;
										case 4:
											setWeather
													.setBackgroundResource(R.drawable.mcloudy);
											break;
										default:
											break;
										}
										arg0.dismiss();

									}
								}).show();

			}
		});
		// ����λ��
		place.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// ��ȡ�������
				lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				// ��ȡ���п��õ�λ���ṩ��
				List<String> pd = lm.getProviders(true);
				Location location = null;
				location = lm
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if (location == null) {
					location = lm
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				}
				if (location == null) {
					Toast.makeText(AddEventActivity.this, "û�п��õĵ���λ���ṩ��",
							Toast.LENGTH_SHORT).show();
				}
				if (location != null) {
					//showLocation(location);
				}

			}
		});
		// ����
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ���
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setWeather();
				content.setnTime_1(tv.getText().toString());
				content.setAlarmColock(Content.WHITE);
				content.setnPlace(placeText);
				content.setnContent(et.getText().toString());
				content.setnTime_2(DateFormat.getDateTimeInstance(
						DateFormat.FULL, DateFormat.SHORT).format(new Date()));
				ViviDB viviDB = ViviDB.getInstance(AddEventActivity.this);
				// �������ݿ�
				viviDB.saveContent(content);
				ActivityCollector.finishAll();
				Intent it = new Intent(AddEventActivity.this,
						MainActivity.class);
				startActivity(it);
				finish();

			}

		});
		tv.setText(c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1)
				+ "/" + c.get(Calendar.DAY_OF_MONTH));
		// ����ʱ��
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// ����һ��DatePickerDialog�Ի�ʵ����
				new DatePickerDialog(AddEventActivity.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker dp, int year,
									int month, int dayofMonth) {
								tv.setText(year + "/" + (month + 1) + "/"
										+ dayofMonth);

							}
						}
						// ���ó�ʼ����
						, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH)).show();

			}
		});

	}

	// ������Ϣ����
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
			content.setnWeather(Content.WEATHER_SUN);
			break;
		}

	}

	// ��ʼ����������
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

	// ����λ����Ϣ
	protected void showLocation(final Location location) {
		Toast.makeText(AddEventActivity.this, "����",
				Toast.LENGTH_SHORT).show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// ��װ����������Ľӿڵ�ַ
					StringBuilder url = new StringBuilder();
					url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
					url.append(location.getLatitude()).append(",");
					url.append(location.getLongitude());
					url.append("&sensor=false");
					HttpClient hc = new DefaultHttpClient();
					//HttpGet httpGet = new HttpGet(url.toString());
					HttpGet httpGet = new HttpGet("http://www.baidu.com");
					// ����Ϣͷ��ָ�����ԣ���֤�������᷵����������
					Log.d("1111111111", url.toString());
					httpGet.addHeader("Accept-Language", "zh-CN");
					HttpResponse hr = hc.execute(httpGet);
					Log.d("1111111111", "etesdfsf");
					if (hr.getStatusLine().getStatusCode() == 200) {
						HttpEntity he = hr.getEntity();
						String response = EntityUtils.toString(he, "utf-8");
						JSONObject jo = new JSONObject(response);
						// ��ȡresults�ڵ���λ����Ϣ
						JSONArray ja = jo.getJSONArray("results");
						if (ja.length() > 0) {
							JSONObject subObject = ja.getJSONObject(0);
							// ��ȡ��ʽ�����λ����Ϣ
							String address = subObject
									.getString("fromatted_address");
							Log.d("222", address);
							Message mess = new Message();
							mess.what = SHOW_LOCATION;
							mess.obj = address;
							handler.sendMessage(mess);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	// ���߳�
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_LOCATION:
				String cp = (String) msg.obj;
				placeText = cp;
				Toast.makeText(AddEventActivity.this, "��ǰλ�ã�" + cp,
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};


}
