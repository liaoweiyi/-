package com.myproject.vivi.adapter;

import java.util.List;

import com.myproject.vivi.R;
import com.myproject.vivi.model.Content;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//该类为ListView的适配器
public class ContentAdapter extends ArrayAdapter<Content> {
	private int resourceId;
	private ViewHolder vh;
	private Content content;

	public ContentAdapter(Context context, int resource, List<Content> objects) {
		super(context, resource, objects);
		resourceId = resource;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 获取当前Content实例
		content = getItem(position);
		View view;
		//如果布局没有加载过，则加载布局，并储存在vh中，如布局加载过，则直接从中取
		//convertView == null
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			vh = new ViewHolder();
			vh.weatherImage = (ImageView) view.findViewById(R.id.weather_image);
			vh.yearText = (TextView) view.findViewById(R.id.year_text);
			vh.timeText1 = (TextView) view.findViewById(R.id.time_text_1);
			vh.contentText = (TextView) view.findViewById(R.id.content_text);
			vh.placeText = (TextView) view.findViewById(R.id.place_text);
			vh.timeText2 = (TextView) view.findViewById(R.id.time_text_2);
			vh.place = (LinearLayout) view.findViewById(R.id.place);
			vh.clockImage = (ImageView) view.findViewById(R.id.clock_image);
			//将ViewHolder对象储存在View中
			view.setTag(vh);
		} else {
			view = convertView;
			vh = (ViewHolder) view.getTag();
		}
		//布局内容
		vh.contentText.setText(content.getnContent());
		//布局创建时间
		vh.timeText2.setText(content.getnTime_2());
		//布局备忘时间
		setTime();
		//布局天气类型
		setWeatherImage();
		//布局位置
		setPlace();
		//布局是否设置闹钟
		setClockImage();
		return view;
	}

	private void setClockImage() {
		switch (content.getAlarmColock()) {
		case Content.READ:
			vh.clockImage.setImageResource(R.drawable.read);
			break;
		case Content.WHITE:
			vh.clockImage.setImageResource(R.drawable.white);
			break;

		default:
			break;
		}

	}

	private void setPlace() {
		if (content.getnPlace() != null) {
			vh.placeText.setText(content.getnPlace());
			vh.place.setVisibility(View.VISIBLE);
		}

	}

	private void setWeatherImage() {
		switch (content.getnWeather()) {
		case Content.WEATHER_CLOUDY:
			vh.weatherImage.setImageResource(R.drawable.cloudy);
			break;
		case Content.WEATHER_MCLOUDY:
			vh.weatherImage.setImageResource(R.drawable.mcloudy);
			break;
		case Content.WEATHER_MOON:
			vh.weatherImage.setImageResource(R.drawable.moon);
			break;
		case Content.WEATHER_RAIN:
			vh.weatherImage.setImageResource(R.drawable.rain);
			break;
		case Content.WEATHER_SUN:
			vh.weatherImage.setImageResource(R.drawable.sun);
			break;

		default:
			break;
		}

	}

	private void setTime() {
		String[] time = content.getnTime_1().split("/");
		if (time != null && time.length > 0) {
			vh.yearText.setText(time[0]);
			vh.timeText1.setText("/" + time[1] + "/" + time[2]);
		}

	}
//该类将所有布局id储存起来
	class ViewHolder {
		ImageView weatherImage;
		// 日期年
		TextView yearText;
		// 日期 月，日
		TextView timeText1;
		// 内容
		TextView contentText;
		// 位置
		TextView placeText;
		// 创建时间
		TextView timeText2;
		// 位置布局
		LinearLayout place;
		// 闹钟图片
		ImageView clockImage;
	}

}
