package com.myproject.vivi.adapter;

import java.util.List;

import com.myproject.vivi.R;
import com.myproject.vivi.model.WeatherList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class WeatherAdapter extends ArrayAdapter<WeatherList> {
	private int resourceId;

	public WeatherAdapter(Context context, int textViewResourceId,
			List<WeatherList> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		WeatherList wl = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		ImageView iv = (ImageView) view.findViewById(R.id.weather_id);
		iv.setImageResource(wl.getImageId());
		return view;

	}
}
