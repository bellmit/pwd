package com.newland.wstdd.mine.myinterest;

import java.util.List;

public class DownloadShopFruitResponse {
	/**
	 * 商店水果更新下载
	 */
	private String result;
	private String resultdesp;
	private String shop_id;
	private List<FruitInfo> fruitlist;
	
	public String getResult(){
		return result;
	}
	public void setResult(String result){
		this.result=result;
	}
	public String getResultdesp(){
		return resultdesp;
	}
	public void  setResultdesp(String resultdesp){
	  this.resultdesp=resultdesp;
	}
	public String getShop_id(){
		return shop_id;
	} 
	public void setShop_id(String shop_id){
		this.shop_id=shop_id;
	}
	public List<FruitInfo> getFruitlist(){
		return fruitlist;
	}
	public void setFruitlist(List<FruitInfo> fruitlist){
		this.fruitlist=fruitlist;
	}
	
}
