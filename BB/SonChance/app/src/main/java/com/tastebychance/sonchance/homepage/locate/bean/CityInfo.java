package com.tastebychance.sonchance.homepage.locate.bean;

import java.io.Serializable;

public class CityInfo implements Serializable,Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1832913370615994149L;
	private String cityName;//城市名称
	private String cityFirstWord;//城市首字母
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityFirstWord() {
		return cityFirstWord;
	}
	public void setCityFirstWord(String cityFirstWord) {
		this.cityFirstWord = cityFirstWord;
	}
	@Override
	public int compareTo(Object another) {
		
		return cityFirstWord.compareTo(((CityInfo)another).getCityFirstWord());
	}
	
	
}
