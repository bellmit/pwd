package com.newland.wstdd.netutils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.net.ParseException;

public class DateUtil {

	 @SuppressLint("SimpleDateFormat")
	public static String getIncreaseDateStr(String day,int dayAddNum) {  
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	        Date nowDate = null;  
	        try {  
	            try {
					nowDate = df.parse(day);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        }  
	        Date newDate2 = new Date(nowDate.getTime() + dayAddNum * 24 * 60 * 60 * 1000);  
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	        String dateOk = simpleDateFormat.format(newDate2);  
	        return dateOk;  
	    }  
	 
	 @SuppressLint("SimpleDateFormat")
	public static String getDecreaseDateStr(String day,int dayDecNum) {  
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	        Date nowDate = null;  
	        try {  
	            try {
					nowDate = df.parse(day);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        }  
	        Date newDate2 = new Date(nowDate.getTime() - dayDecNum * 24 * 60 * 60 * 1000);  
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	        String dateOk = simpleDateFormat.format(newDate2);  
	        return dateOk;  
	    }  
	 //获取当前的时间  格式2015-10-10
	 public static String getDateNowString(){
		 java.util.Date today=new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.format(today);	
	 }
	 
	 //获取当前的时间  格式2015-10-10
	 public static String getDateNowSecondString(){
		 java.util.Date today=new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			return formatter.format(today);	
	 }
	 
	 //月份   日   添加0的封装函数
	 public static String getDayFormatString(int date ){
	
			String tempDay;
			if(date<10)
				tempDay="0"+(date);
			else {
				tempDay=date+"";
			}
			return tempDay;
	 }
	 
	 
	
}
