package com.newland.wstdd.mine.managerpage.beanrequest;
import com.newland.wstdd.common.bean.TddActivity;
import com.newland.wstdd.mine.beanresponse.TddUserCertificate;
 /** 34.	设置是否公开活动报名人数
 * @author Administrator
 *
 */
public class OpenActivityPeoplesReq {
	private TddActivity tddActivity;//activityId 和needPublicSigninfo不能为空，needPublicSigninfo：1 公开 2 关闭
	private TddUserCertificate  tddUserCertificate;
	public TddActivity getTddActivity() {
		return tddActivity;
	}
	public void setTddActivity(TddActivity tddActivity) {
		this.tddActivity = tddActivity;
	}
	public TddUserCertificate getTddUserCertificate() {
		return tddUserCertificate;
	}
	public void setTddUserCertificate(TddUserCertificate tddUserCertificate) {
		this.tddUserCertificate = tddUserCertificate;
	}
	
}
