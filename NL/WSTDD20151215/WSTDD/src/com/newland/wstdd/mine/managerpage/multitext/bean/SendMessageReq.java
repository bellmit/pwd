package com.newland.wstdd.mine.managerpage.multitext.bean;

import java.util.List;

/**群发消息 请求对象
 * @author Administrator
 * 2015-12-11
 */
public class SendMessageReq {

	private String message;//	消息内容	
	private String type	;//消息类型（可不填，默认“活动提醒”）	
	private String sponsor;//	发起人（可不填，默认当前用户nikename）	
	private List<String> receivers	;//接受者userId数组	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSponsor() {
		return sponsor;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	public List<String> getReceivers() {
		return receivers;
	}
	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}
	

}
