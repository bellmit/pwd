package com.newland.wstdd.login;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//0  响应成功
public class RetMsg <T> implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private int code;
	private Integer result;
	private String msg;//用来提示   服务器返回内容是否有无数据
	private T obj;
	private List<T> list1;//获取列表数据
	private List<T> list2;//获取列表数据
	private List<T> list3;//获取列表数据
	
	private List keyList;
	private Map objMap;
	public RetMsg() {
		code = 0;
		objMap = new HashMap();
	}

	public List getKeyList() {
		return keyList;
	}

	public void setKeyList(List keyList) {
		this.keyList = keyList;
	}

	public Map getObjMap() {
		return objMap;
	}

	public void setObjMap(Map objMap) {
		this.objMap = objMap;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Object getObj() {
		return (T)obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public List<T> getList1() {
		return list1;
	}

	public void setList1(List<T> list1) {
		this.list1 = list1;
	}

	public List<T> getList2() {
		return list2;
	}

	public void setList2(List<T> list2) {
		this.list2 = list2;
	}

	public List<T> getList3() {
		return list3;
	}

	public void setList3(List<T> list3) {
		this.list3 = list3;
	}

	
}
