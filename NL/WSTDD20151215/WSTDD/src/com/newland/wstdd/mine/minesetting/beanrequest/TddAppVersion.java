package com.newland.wstdd.mine.minesetting.beanrequest;
/**
 * 版本更新数据
 * @author Administrator
 *
 */
public class TddAppVersion {
	private String versionId;//版本主键
	private String versionNo;//版本号
	private String installUrl;//安卓地址
	private String platform;//安装平台
	private String createTime;//创建时间
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getInstallUrl() {
		return installUrl;
	}
	public void setInstallUrl(String installUrl) {
		this.installUrl = installUrl;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
