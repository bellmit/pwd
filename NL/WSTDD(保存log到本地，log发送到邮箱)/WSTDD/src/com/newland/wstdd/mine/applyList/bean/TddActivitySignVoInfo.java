package com.newland.wstdd.mine.applyList.bean;

import java.io.Serializable;

import android.R.integer;

/**
 * 参加活动对象 
 */
public class TddActivitySignVoInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TddActivitySignVo tddActivitySignVo;
	private boolean isSelected;//是否选中
	public TddActivitySignVo getTddActivitySignVo() {
		return tddActivitySignVo;
	}
	public void setTddActivitySignVo(TddActivitySignVo tddActivitySignVo) {
		this.tddActivitySignVo = tddActivitySignVo;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	
}
