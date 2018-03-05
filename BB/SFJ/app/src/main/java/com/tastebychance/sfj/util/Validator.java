package com.tastebychance.sfj.util;

import android.text.TextUtils;
import android.util.Log;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * 校验器：利用正则表达式校验邮箱、手机号等
 * Created by shenbinghong on 2018/1/6.
 */

public class Validator {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }


    /**
     * 校验是否是中国身份证号
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
     * 校验是否是身份证
     */
    public static boolean isPid(String str) {

        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] arg = {str};

        // 17位加权因子，与身份证号前17位依次相乘。

        int w[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

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

        String sums[] = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
        if (sums[(sum % 11)].equals(arg[0].substring(arg[0].length() - 1, arg[0].length()))) {// 与身份证最后一位比较
            return true;

        } else {
            return false;
        }

    }

    /**
     * 校验是否是银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean isBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}
