package com.tastebychance.sonchance.homepage.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * 首页菜品列表接口
 * 
 * @author shenbh
 *
 *         2017年8月9日
 */
public class HomePageListRes /*extends RealmObject */implements Serializable {

//	@PrimaryKey
	private int id;;// 菜品ID
//	@Required
	private String name;// 菜品名称
	private String logo;// 菜品缩略图
	private int comment_count;// 评论总条数
	private int sail_count;// 已售出数量
	private int sort;// 菜品排序号
//	@Required
	private float price	;//	菜品价格
	private Date create_time;// 菜品创建时间
	private int good_comments;// 好评数
	private int bad_comments;// 差评数
	private List<Comments> comments;// 关于评论的数组
	private String promotion;//优惠：买多少送多少

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public int getSail_count() {
		return sail_count;
	}

	public void setSail_count(int sail_count) {
		this.sail_count = sail_count;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getGood_comments() {
		return good_comments;
	}

	public void setGood_comments(int good_comments) {
		this.good_comments = good_comments;
	}

	public int getBad_comments() {
		return bad_comments;
	}

	public void setBad_comments(int bad_comments) {
		this.bad_comments = bad_comments;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
}
