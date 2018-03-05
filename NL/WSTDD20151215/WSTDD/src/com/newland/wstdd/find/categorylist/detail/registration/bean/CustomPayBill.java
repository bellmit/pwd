package com.newland.wstdd.find.categorylist.detail.registration.bean;

import java.io.Serializable;
import java.util.List;

public class CustomPayBill implements Serializable {
	private PayBill payBill;
	private List<PayBill> childPayBills;

	public PayBill getPayBill() {
		return payBill;
	}

	public void setPayBill(PayBill payBill) {
		this.payBill = payBill;
	}

	public List<PayBill> getChildPayBills() {
		return childPayBills;
	}

	public void setChildPayBills(List<PayBill> childPayBills) {
		this.childPayBills = childPayBills;
	}

}
