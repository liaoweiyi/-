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

//����ΪListView��������
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
		// ��ȡ��ǰContentʵ��
		content = getItem(position);
		View view;
		//�������û�м��ع�������ز��֣���������vh�У��粼�ּ��ع�����ֱ�Ӵ���ȡ
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
			//��ViewHolder���󴢴���View��
			view.setTag(vh);
		} else {
			view = convertView;
			vh = (ViewHolder) view.getTag();
		}
		//��������
		vh.contentText.setText(content.getnContent());
		//���ִ���ʱ��
		vh.timeText2.setText(content.getnTime_2());
		//���ֱ���ʱ��
		setTime();
		//������������
		setWeatherImage();
		//����λ��
		setPlace();
		//�����Ƿ���������
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
//���ཫ���в���id��������
	class ViewHolder {
		ImageView weatherImage;
		// ������
		TextView yearText;
		// ���� �£���
		TextView timeText1;
		// ����
		TextView contentText;
		// λ��
		TextView placeText;
		// ����ʱ��
		TextView timeText2;
		// λ�ò���
		LinearLayout place;
		// ����ͼƬ
		ImageView clockImage;
	}

}
