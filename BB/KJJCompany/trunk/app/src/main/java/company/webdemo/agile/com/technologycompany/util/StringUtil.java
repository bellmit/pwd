package company.webdemo.agile.com.technologycompany.util;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import company.webdemo.agile.com.technologycompany.MyApplication;
import company.webdemo.agile.com.technologycompany.R;

public class StringUtil {

	/**
	 * MD5加密验证
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String noNull(Object str) {
		if (str == null || str.equals("") || "null".equals(str))
			return "";
		else
			return str.toString();
	}

	/**
	 * 如果源串为空或NULL，返回默认值
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String noNull(Object str, String defaultValue) {
		if (str == null || str.equals("") || "null".equals(str))
			return defaultValue;
		else
			return str.toString();
	}

	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0) || str.equals("null") || str.equals("");
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static void filtNull(TextView textView, String s) {
		if (s != null) {
			textView.setText(s);
		} else {
			textView.setText(filtNull(s));
		}
	}

	public static void filtNull(TextView textView, String s1, String s2) {
		if (s1 != null && s2 != null) {
			textView.setText(s1 + " " + s2);
		} else {
			textView.setText(filtNull(s1) + filtNull(s2));
		}

	}

	//判断过滤单个string为null
	public static String filtNull(String s) {
		if (s != null) {
			return s;
		} else {
			s = "null";
		}
		return s;
	}


	//判断单个对象不为null
	public static boolean filtObjectNull(Object object) {
		if (object != null) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 身份证号验证
	 * 
	 * @param sfzh
	 * @return true验证通过 false不通过
	 */
	public static boolean isChinaIDCard(String sfzh) {
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

	/**
	 * 
	 * 获取相对URL地址
	 * 
	 * @param split
	 *            切割符
	 * @param url
	 * 
	 * @return
	 */
	public static final String splitSubUrl(String split, String url) {
		try {
			if (url != null && !url.equals("")) {
				int pos = url.indexOf(split);
				if (pos <= -1) {
					return "";
				} else {
					String temp = url.substring(pos);
					return temp;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * @Title: clearSpaces
	 * @Description: 清除字符串中的空格字符
	 * @param str
	 * @return 设定文件
	 */
	public static final String clearSpaces(String str) {
		StringTokenizer st = new StringTokenizer(str, " ", false);
		String t = "";
		while (st.hasMoreElements()) {
			t += st.nextElement();
		}
		return t;
	}

	/**
	 * 
	 * @Title: formatStr
	 * @Description:如果为null,返回"" ,如果不是却掉前后空格
	 * @param str
	 * @return 设定文件
	 */
	public static final String formatStr(String str) {
		if (str == null) {
			return "";
		} else {
			return str.trim();
		}
	}

	public static final String secrecyPwd(String pwd){
		if (null != pwd && pwd.length() == 11){
			pwd = pwd.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
		}
		return pwd;
	}

	/**
	 * 取得指定参数的值
	 * 
	 * @param key
	 * @param Keys
	 * @param Values
	 * @return
	 */
	public static String getKeyValue(String key, ArrayList<String> Keys, ArrayList<String> Values) {
		int len = Keys.size();
		for (int i = 0; i < len; i++) {
			if (Keys.get(i).equalsIgnoreCase(key))
				return Values.get(i);
		}
		return "";
	}

	/**
	 * 去掉空格，回车符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {

		String dest = "";

		if (str != null) {

			Pattern p = Pattern.compile("\\s*|\t|\r|\n");

			Matcher m = p.matcher(str);

			dest = m.replaceAll("");

		}

		return dest;

	}

	// 验证手机号是否为正确手机号  
	public static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
	  
	    return m.matches();  
	}
	
	/**
	 * 判断是否是身份证
	 */
	public static boolean isPid(String str) {

		if (isEmpty(str)) {
			return false;
		}
		String[] arg = { str };

		// 17位加权因子，与身份证号前17位依次相乘。

		int w[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

		int sum = 0;// 保存级数和

		try {
			for (int i = 0; i < arg[0].length() - 1; i++) {
				sum += new Integer(arg[0].substring(i, i + 1)) * w[i];
			}
		} catch (Exception e) {
			return false;

		}

		/**
		 * 校验结果，上一步计算得出的结果与11取模，得到的结果相对应的字符就是身份证最后一位 ，也就是校验位。例如：0对应下面数组第一个元素，
		 * 以此类推。
		 */

		String sums[] = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		if (sums[(sum % 11)].equals(arg[0].substring(arg[0].length() - 1, arg[0].length()))) {// 与身份证最后一位比较
			return true;

		} else {
			return false;
		}

	}

	/**
	 * 校验银行卡卡号
	 * @param cardId
	 * @return
	 */
	public static boolean isBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
		if(bit == 'N'){
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * @param nonCheckCodeCardId
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId){
		if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			//如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if(j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
	}

	/**
	 * 检查Edit是否是空的 isEditTextIsEmpty(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param editText
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean isEditTextIsEmpty(EditText editText) {
		String str = editText.getText().toString().trim();
		if ("".equals(str)) {
			return true;
		}
		return false;

	}

	public static String formateNum(String date) {
		if ("1".equals(date)) {
			date = "01";
			return date;
		} else if ("2".equals(date)) {
			date = "02";
			return date;
		} else if ("3".equals(date)) {
			date = "03";
			return date;
		} else if ("4".equals(date)) {
			date = "04";
			return date;
		} else if ("5".equals(date)) {
			date = "05";
			return date;
		} else if ("6".equals(date)) {
			date = "06";
			return date;
		} else if ("7".equals(date)) {
			date = "07";
			return date;
		} else if ("8".equals(date)) {
			date = "08";
			return date;
		} else if ("9".equals(date)) {
			date = "09";
			return date;
		} else {
			return date;
		}
	}

	//设置购物车总价格富文本
	public static SpannableStringBuilder setShoppingcartTotalMoney(String text){
		//购物车：总共多少钱
		String totalMoneyIncludeIcon = "￥"+ text;
		SpannableStringBuilder style = new SpannableStringBuilder(totalMoneyIncludeIcon);
		AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(8,true);
		AbsoluteSizeSpan absoluteSizeSpan1 = new AbsoluteSizeSpan(18,true);
		style.setSpan(absoluteSizeSpan,0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		style.setSpan(absoluteSizeSpan1,1, totalMoneyIncludeIcon.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		style.setSpan(new ForegroundColorSpan(Color.RED), 0, totalMoneyIncludeIcon.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
		return style;
	}

	//设置购物车总价格富文本
	public static SpannableStringBuilder setMoneySize(String text, int iconSize, int textSize){
		//购物车：总共多少钱
		String totalMoneyIncludeIcon = "￥"+ text;
		SpannableStringBuilder style = new SpannableStringBuilder(totalMoneyIncludeIcon);
		AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(iconSize,true);
		AbsoluteSizeSpan absoluteSizeSpan1 = new AbsoluteSizeSpan(textSize,true);
		style.setSpan(absoluteSizeSpan,0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		style.setSpan(absoluteSizeSpan1,1, totalMoneyIncludeIcon.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//		style.setSpan(new ForegroundColorSpan(Color.RED), 0, totalMoneyIncludeIcon.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
		return style;
	}

	public static SpannableStringBuilder setMoneySizeAndColor(String text, int color, int iconSize, int textSize){
		//购物车：总共多少钱
		String totalMoneyIncludeIcon = "￥"+ text;
		SpannableStringBuilder style = new SpannableStringBuilder(totalMoneyIncludeIcon);
		AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(iconSize,true);
		AbsoluteSizeSpan absoluteSizeSpan1 = new AbsoluteSizeSpan(textSize,true);
		style.setSpan(absoluteSizeSpan,0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		style.setSpan(absoluteSizeSpan1,1, totalMoneyIncludeIcon.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		style.setSpan(new ForegroundColorSpan(color), 0, totalMoneyIncludeIcon.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
		return style;
	}

	/**给文本设置Style。可指定颜色、位置、大小
	 *
	 * @param text 要设置的文本
	 * @param color 要设置的颜色
	 * @param startIndex 指定变化的开始位置
	 * @param endIndex 指定变化的结束的位置
	 * @param textSize 文本的大小
     * @return
     */
	public static SpannableStringBuilder setTextSizeColor(String text, int color, int startIndex, int endIndex, int textSize){
		SpannableStringBuilder style = new SpannableStringBuilder(text);
		AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(textSize,true);
		style.setSpan(absoluteSizeSpan,startIndex,endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		style.setSpan(new ForegroundColorSpan(color),startIndex,endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		return style;
	}

	/**设置指定位置的字符串的颜色
	 * @param text 字符串内容
	 * @return
	 */
	public static SpannableStringBuilder setStringGreenColor(String text) {
		SpannableStringBuilder style = new SpannableStringBuilder(text);
		style.setSpan(new ForegroundColorSpan(Color.GREEN), 0, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
		return style;
	}
	
	/**设置指定位置的字符串的颜色
	 * @param text 字符串内容
	 * @param start 字符串变颜色开始位置
	 * @param end 字符串变颜色结束位置
	 * @return
	 */
	public static SpannableStringBuilder setStringGreenColor(String text, int start, int end) {
		SpannableStringBuilder style = new SpannableStringBuilder(text);
		style.setSpan(new ForegroundColorSpan(Color.GREEN), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
		return style;
	}

	/**设置指定位置的字符串的颜色
	 * @param text 字符串内容
	 * @return
	 */
	public static SpannableStringBuilder setStringRedColor(String text) {
		SpannableStringBuilder style = new SpannableStringBuilder(text);
		style.setSpan(new ForegroundColorSpan(Color.RED), 0, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
		return style;
	}

	/**设置指定位置的字符串的颜色
	 * @param text 字符串内容
	 * @param start 字符串变颜色开始位置
	 * @param end 字符串变颜色结束位置
	 * @return
	 */
	public static SpannableStringBuilder setStringRedColor(String text, int start, int end) {
		SpannableStringBuilder style = new SpannableStringBuilder(text);
		style.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
		return style;
	}

	//红色的字体
	public static String setRedColorString(String string) {
		return "<font color=" + MyApplication.getContext().getResources().getColor(R.color.text_red) +" size="+ 38 + ">" + string + "</font>";
	}


	/*public static void main(String[] args) {
		String card = "6227007200120897790";
		System.out.println("      card: " + card);
		System.out.println("check code: " + getBankCardCheckCode(card));
		System.out.println("是否为银行卡:"+isBankCard(card));
	}*/

	/**
	 * 订单状态 0:等待支付 1：等待送达 2：订单已完成 3：订单已评价 4：订单已取消
	 * @param status
	 * @return
     */
	public static String statusToDescribe(int status) {
		if (status == 0) {
			return "等待支付";
		} else if (status == 1) {
			return "等待送达";
		} else if (status == 2) {
			return "订单已完成";
		} else if (status == 3) {
			return "订单已评价";
		} else if (status == 4) {
			return "订单已取消";
		}
		return "等待支付";
	}

	/**
	 * 下划线
	 *
	 * @param textView
	 */
	private void addButtomLine(TextView textView) {
		textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	}

	/**
	 * 移除线
	 *
	 * @param textView
	 */
	private void removeLine(TextView textView) {
		textView.getPaint().setFlags(0); // 取消设置的的划线

	}

	/**
	 * 设置中划线并加清晰
	 *
	 * @param textView
	 */
	private void addLine(TextView textView) {
		textView.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰

	}

	/**
	 * 中划线
	 *
	 * @param textView
	 */
	private void addCenterLine(TextView textView) {
		textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中划线
	}

	/**
	 * 抗锯齿
	 *
	 * @param textView
	 */
	private void addjuchiLine(TextView textView) {
		textView.getPaint().setAntiAlias(true);// 抗锯齿
	}
}
