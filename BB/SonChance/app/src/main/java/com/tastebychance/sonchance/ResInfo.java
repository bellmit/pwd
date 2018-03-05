package com.tastebychance.sonchance;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class ResInfo {

	private int result;
	private String error_message;//错误返回描述
	private Object data;
	public static final String SUCCESS_CODE = "0";

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public <T> List<T> getDataList(Class<T> cla){
		String str = JSON.toJSONString(getData());
		List<T> list = JSON.parseArray(str, cla);
		if(list == null){
			list = new ArrayList<T>();
		}
		return list;
	}
	
}
