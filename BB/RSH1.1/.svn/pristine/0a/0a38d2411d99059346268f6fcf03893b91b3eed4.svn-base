package rongshanghui.tastebychance.com.rongshanghui.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shenbh on 2017/8/29.
 */

public class TimeOrDateUtil {
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYY_MM_DD_HH_DD = "yyyy-MM-dd HH:mm";// 分钟
    public static String YYYY_MM = "yyyy-MM";
    public static String YYYY = "yyyy";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    //	public static int WEEK_TIME=java.text.DateFormat.WEEK_OF_MONTH_FIELD;
    public static String MM_SS = "mm:ss";

    /**
     * 获取当前时间
     *
     * @param type
     * @return
     */
    public static String getNowTime(String type) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(type);// 可以方便地修改日期格式
        return dateFormat.format(now);
    }


    /**
     * 传入时间，返回指定格式的时间字符串
     *
     * @param type
     * @return
     */
    public static String formatDate(Date date, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(type);
        return dateFormat.format(date);
    }

    /**
     * 字符串时间转成Date型时间
     *
     * @param dateStr
     * @param type
     * @return
     */
    public static Date StringToDate(String dateStr, String type) {
        Date date = null;
        try {
            date = new SimpleDateFormat(type).parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 传入时间跟时间格式返回long型时间
     *
     * @param time
     * @param type
     * @return
     */
    public static long getLongTime(String time, String type) {
        long timeL = 0;
        try {
            timeL = new SimpleDateFormat(type).parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeL;
    }

    /**
     * 传入long型返回对应格式的时间
     */
    public static String long2FormatTime(long timeL, String type) {
        String formatTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date dt = new Date(timeL);
        formatTime = sdf.format(dt);
        return formatTime;
    }

    /**
     * 传入时间（格式为HH:mm）返回long型时间
     *
     * @param time
     * @return
     */
    public static long getLongTime(String time) {
        long longTime = 0;
        String nowDate = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(new Date());
        nowDate = nowDate.substring(0, 10);
        time = nowDate + " " + time + ":00";
        try {
            longTime = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return longTime;
    }

    /**
     * 获取昨天时间
     *
     * @return
     */
    public static String getLastTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat(YYYY_MM_DD).format(cal.getTime());
        return yesterday;
    }

    /**
     * 获取上月
     *
     * @return
     */
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String day = new SimpleDateFormat(YYYY_MM).format(cal.getTime());
        return day;
    }

    /**
     * 获取当年
     *
     * @return
     */
    public static String getLastYear() {
        Calendar cal = Calendar.getInstance();
        // cal.add(Calendar.YEAR, 0);//获取上一年
        cal.add(Calendar.YEAR, 0);
        String day = new SimpleDateFormat(YYYY).format(cal.getTime());
        return day;
    }

    /**
     * 计算两个时间天数差
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int calculateDays(Date fromDate, Date toDate) {
        if (null == fromDate || null == toDate) {
            throw new NullPointerException();
        }

        if (fromDate.before(toDate)) {
            return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
        }
        return (int) ((fromDate.getTime() - toDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 计算小时差
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int calculateHours(Date fromDate, Date toDate) {
        if (null == fromDate || null == toDate) {
            throw new NullPointerException();
        }

        if (fromDate.before(toDate)) {
            return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60));
        }
        return (int) ((fromDate.getTime() - toDate.getTime()) / (1000 * 60 * 60));
    }

    /**
     * 计算分钟差
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int calculateMinutes(Date fromDate, Date toDate) {
        if (null == fromDate || null == toDate) {
            throw new NullPointerException();
        }

        if (fromDate.before(toDate)) {
            return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60));
        }
        return (int) ((fromDate.getTime() - toDate.getTime()) / (1000 * 60));
    }
}
