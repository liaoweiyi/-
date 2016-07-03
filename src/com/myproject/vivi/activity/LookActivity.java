package com.myproject.vivi.activity;

import com.myproject.vivi.R;
import com.myproject.vivi.model.Content;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LookActivity extends Activity {
	private Button edit;
	private Button back;
	private ImageView iv;
	private TextView time1;
	private TextView time2;
	private TextView place;
	private TextView content;
	private Content content2;
	private LinearLayout ly;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.look_up_event);
		back = (Button) findViewById(R.id.back_button_2);
		edit = (Button) findViewById(R.id.edit_button);
		time2 = (TextView) findViewById(R.id.time_text_view_3);
		time1 = (TextView) findViewById(R.id.clock_text_view);
		place = (TextView) findViewById(R.id.place_text_view);
		content = (TextView) findViewById(R.id.content_text_2);
		iv = (ImageView) findViewById(R.id.is_weather_image);
		ly = (LinearLayout) findViewById(R.id.view_place);
		Intent it = getIntent();
		content2 = (Content) it.getSerializableExtra("content");
		isWeather(content2.getnWeather());
		time1.setText(content2.getnTime_1());
		time2.setText(content2.getnTime_2());
		content.setText(content2.getnContent());
		if (content2.getnPlace() != null) {
			content.setText(content2.getnPlace());
			ly.setVisibility(View.VISIBLE);

		}
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it2 = new Intent(LookActivity.this, EditActivity.class);
				it2.putExtra("content", content2);
				startActivityForResult(it2, 9);
				finish();

			}
		});
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

}
