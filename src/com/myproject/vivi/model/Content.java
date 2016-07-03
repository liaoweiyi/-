package com.myproject.vivi.model;

import java.io.Serializable;

//该类为内容类,实现Serializable接口，方便数据传递
@SuppressWarnings("serial")
public class Content implements Serializable{
	public static final int READ=0;
	public static final int WHITE=1;
	public static final int WEATHER_SUN=2;
	public static final int WEATHER_RAIN=3;
	public static final int WEATHER_CLOUDY=4;
	public static final int WEATHER_MCLOUDY=5;
	public static final int WEATHER_MOON=6;
	//选择闹钟的时间
	private String selectTime;
	//该记事的id
	private int id;
	//内容
	private String nContent;
	//备忘时间
	private String nTime_1;
	//创建时间
	private String nTime_2;
	//地址
	private String nPlace;
	//天气
	private int nWeather;
	//是否设置闹钟
	private int alarmColock;
	
	public String getSelectTime() {
		return selectTime;
	}
	public void setSelectTime(String selectTime) {
		this.selectTime = selectTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getnContent() {
		return nContent;
	}
	public void setnContent(String nContent) {
		this.nContent = nContent;
	}
	public String getnTime_1() {
		return nTime_1;
	}
	public void setnTime_1(String nTime_1) {
		this.nTime_1 = nTime_1;
	}
	public String getnTime_2() {
		return nTime_2;
	}
	public void setnTime_2(String nTime_2) {
		this.nTime_2 = nTime_2;
	}
	public String getnPlace() {
		return nPlace;
	}
	public void setnPlace(String nPlace) {
		this.nPlace = nPlace;
	}
	public int getnWeather() {
		return nWeather;
	}
	public void setnWeather(int nWeather) {
		this.nWeather = nWeather;
	}
	public int getAlarmColock() {
		return alarmColock;
	}
	public void setAlarmColock(int alarmColock) {
		this.alarmColock = alarmColock;
	}
	

}
