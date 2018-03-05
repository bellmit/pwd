package com.newland.wstdd.originate.beanrequest;
/**
 * 单个活动的状态更新
 * @author Administrator
 *
 */
public class SingleActivityStateReq {
	private String status;//状态
	private String reason;//变更原因
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
