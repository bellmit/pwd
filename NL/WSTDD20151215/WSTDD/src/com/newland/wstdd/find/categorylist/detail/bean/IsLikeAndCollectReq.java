/**
 * @fields IsLikeAndCollectReq.java
 */
package com.newland.wstdd.find.categorylist.detail.bean;

/**活动是否点赞收藏 请求对象
 * @author H81 2015-11-26
 *
 */
public class IsLikeAndCollectReq {
	private  String targetId;//	活动标识

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
}
