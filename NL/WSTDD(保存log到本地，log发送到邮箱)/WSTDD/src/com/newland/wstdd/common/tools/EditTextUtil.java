package com.newland.wstdd.common.tools;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.widget.EditText;

/**
 * 编辑控件的的工具，
 * 
 * @author Administrator
 * 
 */
public class EditTextUtil {
	//将edittext的光标显示在字符串的最后面
	public static void setEditTextCursorLocation(EditText editText) {
		 CharSequence text = editText.getText();
		 if (text instanceof Spannable) {
		 Spannable spanText = (Spannable) text;
		 Selection.setSelection(spanText, text.length());}
		 }
	
	 /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
     
    /**
     * 验证手机号码
     * @param mobiles
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber){
        boolean flag = false;
        try{	
                Pattern regex = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
                Matcher matcher = regex.matcher(mobileNumber);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
    
    /**
	 * 身份证号验证
	 * 
	 * @param sfzh
	 * @return true验证通过 false不通过
	 */
	public static boolean checkChinaIDCard(String sfzh) {
		try {
			int flag = 1;
			// 如果长度不是15和18，则返回错误
			if (sfzh.length() != 18 && sfzh.length() != 15) {
				flag = flag * 0;
			}
			if (sfzh.length() == 18) {
				String start = sfzh.substring(0, 6);
				int year = Integer.parseInt(sfzh.substring(6, 10));
				int month = Integer.parseInt(sfzh.substring(10, 12));
				int day = Integer.parseInt(sfzh.substring(12, 14));
				int nowYear = (new Date()).getYear() + 1900;
				Log.v("sfzh year is ", year + "");
				Log.v("sfzh month is ", month + "");
				Log.v("sfzh day is ", day + "");
				Log.v("now year is ", nowYear + "");
				if (year < 1900 || year > nowYear) {
					flag = flag * 0;
				}
				if (month < 1 || month > 12) {
					flag = flag * 0;
				}
				if (day < 1 || day > 31) {
					flag = flag * 0;
				}
				// 判断验证位
				String check = "";
				int a = Integer.parseInt(sfzh.substring(0, 1)) * 7 + Integer.parseInt(sfzh.substring(1, 2)) * 9 + Integer.parseInt(sfzh.substring(2, 3)) * 10 + Integer.parseInt(sfzh.substring(3, 4))
						* 5 + Integer.parseInt(sfzh.substring(4, 5)) * 8 + Integer.parseInt(sfzh.substring(5, 6)) * 4 + Integer.parseInt(sfzh.substring(6, 7)) * 2
						+ Integer.parseInt(sfzh.substring(7, 8)) * 1 + Integer.parseInt(sfzh.substring(8, 9)) * 6 + Integer.parseInt(sfzh.substring(9, 10)) * 3
						+ Integer.parseInt(sfzh.substring(10, 11)) * 7 + Integer.parseInt(sfzh.substring(11, 12)) * 9 + Integer.parseInt(sfzh.substring(12, 13)) * 10
						+ Integer.parseInt(sfzh.substring(13, 14)) * 5 + Integer.parseInt(sfzh.substring(14, 15)) * 8 + Integer.parseInt(sfzh.substring(15, 16)) * 4
						+ Integer.parseInt(sfzh.substring(16, 17)) * 2;
				int b = a % 11;
				switch (b) {
				case 0:
					check = "1";
					break;
				case 1:
					check = "0";
					break;
				case 2:
					check = "X";
					break;
				case 3:
					check = "9";
					break;
				case 4:
					check = "8";
					break;
				case 5:
					check = "7";
					break;
				case 6:
					check = "6";
					break;
				case 7:
					check = "5";
					break;
				case 8:
					check = "4";
					break;
				case 9:
					check = "3";
					break;
				case 10:
					check = "2";
					break;
				default:
					break;
				}
				Log.v("check num is ", check);
				if (!check.equals(sfzh.subSequence(17, 18))) {
					flag = flag * 0;
				}
			}
			Log.v("flag", flag + "");
			if (flag == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
