package com.newland.wstdd.mine.minesetting.feedbackandhelp.bean;

/**意见反馈 网络请求对象
 * @author Administrator
 * 2015-12-8
 */
public class FeedBackReq {
	private String adviceContent;//	建议内容
	private String mobliePhone;//	联系电话
	private String img;//	示意图
	public String getAdviceContent() {
		return adviceContent;
	}
	public void setAdviceContent(String adviceContent) {
		this.adviceContent = adviceContent;
	}
	public String getMobliePhone() {
		return mobliePhone;
	}
	public void setMobliePhone(String mobliePhone) {
		this.mobliePhone = mobliePhone;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

}
