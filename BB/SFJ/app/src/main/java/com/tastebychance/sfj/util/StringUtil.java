package com.tastebychance.sfj.util;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        StringBuilder hexValue = new StringBuilder();
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
        if (str == null || str.equals("") || "null".equals(str)) {
            return "";
        } else {
            return str.toString();
        }
    }

    /**
     * 如果源串为空或NULL，返回默认值
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static String noNull(Object str, String defaultValue) {
        if (str == null || str.equals("") || "null".equals(str)) {
            return defaultValue;
        }else{
            return str.toString();
        }
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
            StringBuilder sb = new StringBuilder();
            sb.append(s1);
            sb.append(" ");
            sb.append(s2);
            textView.setText(sb.toString());
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
     * 获取相对URL地址
     *
     * @param split 切割符
     * @param url
     * @return
     */
    public static final String splitSubUrl(String split, String url) {
        try {
            if (url != null && !"".equals(url)) {
                int pos = url.indexOf(split);
                if (pos <= -1) {
                    return "";
                } else {
                    return url.substring(pos);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**清除字符串中的空格字符
     * @param str
     * @return 设定文件
     */
    public static final String clearSpaces(String str) {
        StringTokenizer st = new StringTokenizer(str, " ", false);
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreElements()) {
            sb.append(st.nextElement());
        }
        return sb.toString();
    }

    /**如果为null,返回"" , 如果不是却掉前后空格
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

    public static final String secrecyPwd(String pwd) {
        if (null != pwd && pwd.length() == 11) {
            pwd = pwd.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return pwd;
    }

    /**
     * 取得指定参数的值
     *
     * @param key
     * @param keys
     * @param values
     * @return
     */
    public static String getKeyValue(String key, ArrayList<String> keys, ArrayList<String> values) {
        int len = keys.size();
        for (int i = 0; i < len; i++) {
            if (keys.get(i).equalsIgnoreCase(key)) {
                return values.get(i);
            }
        }
        return "";
    }

    /**
     * 去掉空格，回车符
     *
     * @param str
     * @return
     */
    private static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

    public static String replaceBlank(String str) {

        String dest = "";

        if (str != null) {
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }

        return dest;
    }

    /**
     * 检查Edit是否是空的 isEditTextIsEmpty(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @param editText
     * @return boolean
     * @throws
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

    /**
     * 给文本设置Style。可指定颜色、位置、大小
     *
     * @param text       要设置的文本
     * @param color      要设置的颜色
     * @param startIndex 指定变化的开始位置
     * @param endIndex   指定变化的结束的位置
     * @param textSize   文本的大小
     * @return
     */
    public static SpannableStringBuilder setTextSizeColor(String text, int color, int startIndex, int endIndex, int textSize) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(textSize, true);
        style.setSpan(absoluteSizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    /**
     * 设置指定位置的字符串的颜色
     *
     * @param text 字符串内容
     * @return
     */
    public static SpannableStringBuilder setStringGreenColor(String text) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(Color.GREEN), 0, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
        return style;
    }

    /**
     * 设置指定位置的字符串的颜色
     *
     * @param text  字符串内容
     * @param start 字符串变颜色开始位置
     * @param end   字符串变颜色结束位置
     * @return
     */
    public static SpannableStringBuilder setStringGreenColor(String text, int start, int end) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(Color.GREEN), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
        return style;
    }

    /**
     * 设置指定位置的字符串的颜色
     *
     * @param text 字符串内容
     * @return
     */
    public static SpannableStringBuilder setStringRedColor(String text) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(Color.RED), 0, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
        return style;
    }

    /**
     * 设置指定位置的字符串的颜色
     *
     * @param text  字符串内容
     * @param start 字符串变颜色开始位置
     * @param end   字符串变颜色结束位置
     * @return
     */
    public static SpannableStringBuilder setStringRedColor(String text, int start, int end) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
        return style;
    }

    //红色的字体
    public static String setRedColorString(String string) {
        return "<font color=" + Color.RED+ " size=" + 38 + ">" + string + "</font>";
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


	/*public static void main(String[] args) {
        String card = "6227007200120897790";
		System.out.println("      card: " + card);
		System.out.println("check code: " + getBankCardCheckCode(card));
		System.out.println("是否为银行卡:"+isBankCard(card));
    }*/
}
