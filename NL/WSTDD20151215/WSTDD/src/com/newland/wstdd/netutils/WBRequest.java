package com.newland.wstdd.netutils;

import java.util.Calendar;

/**
 * 报文请求头
 * 
 * Object reqBody 表示不同的请求对象
 * 
 * @author Administrator
 * 
 */
public class WBRequest extends BaseMessage {
	private String userId;// userId
	private String platform;// 平台
	private String timestamp;// 时间戳
	private String _method;// 获取的请求方法
	private String params;// 参数集合
	private Object reqBody;

	public WBRequest() {
		timestamp = get_date_time();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String get_method() {
		return _method;
	}

	public void set_method(String _method) {
		this._method = _method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	
	private static String get_date_time() {
		int mYear;
		int mMonth;
		int mDay;
		int mHour;
		int mMinute;
		int mSecond;

		final Calendar c = Calendar.getInstance();

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH) + 1;
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		mSecond = c.get(Calendar.SECOND);

		String result = String.format("%04d%02d%02d%02d%02d%02d", mYear,
				mMonth, mDay, mHour, mMinute, mSecond);
		return result;
	}

	public Object getReqBody() {
		return reqBody;
	}

	public void setReqBody(Object reqBody) {
		this.reqBody = reqBody;
	}

}
