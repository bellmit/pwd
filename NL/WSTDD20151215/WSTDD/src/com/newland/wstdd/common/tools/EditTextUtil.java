package com.newland.wstdd.common.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Selection;
import android.text.Spannable;
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
                Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
                Matcher matcher = regex.matcher(mobileNumber);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
}
