package com.newland.wstdd.mine.minesetting.beanresponse;

import com.newland.wstdd.mine.minesetting.beanrequest.TddAppVersion;

/**
 * 版本更新
 * @author Administrator
 *
 */
public class VersionRes {
	private TddAppVersion tddAppVersion;//版本对象信息

	public TddAppVersion getTddAppVersion() {
		return tddAppVersion;
	}

	public void setTddAppVersion(TddAppVersion tddAppVersion) {
		this.tddAppVersion = tddAppVersion;
	}
	
}
