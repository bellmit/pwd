package com.tastebychance.sfj.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 项目名称：RongShangHui2
 * 类描述：日期&时间选择控件工具类
 * 创建人：Administrator
 * 创建时间： 2017/12/4 15:56
 * 修改人：Administrator
 * 修改时间：2017/12/4 15:56
 * 修改备注：
 */

public class DateTimepickDialogUtils {
    private Activity activity;
    private boolean isHourLimited = false;//整时限制

    public void setHourlimited(boolean hourlimited) {
        isHourLimited = hourlimited;
    }

    public DateTimepickDialogUtils(Activity activity) {
        this.activity = activity;
    }

    public interface DateTimeChangerListener {
        void onDateTimeChange();
    }

    private DateTimeChangerListener listener;

    public void setDateChangeListener(DateTimeChangerListener l) {
        this.listener = l;
    }

    /**
     * 日期格式
     */
    private String mDateFormat = "yyyy-MM-dd";

    public void setDateFormat(String format) {
        mDateFormat = format;
    }

    private String mTimeFormat = "HH:00";

    public void setTimeFormat(String format) {
        mTimeFormat = format;
    }

    public enum TYPE {
        DATE, TIME
    }

    private final int[] THEMES = {
            AlertDialog.THEME_TRADITIONAL,
            AlertDialog.THEME_HOLO_DARK,
            AlertDialog.THEME_HOLO_LIGHT,
            AlertDialog.THEME_DEVICE_DEFAULT_DARK,
            AlertDialog.THEME_DEVICE_DEFAULT_LIGHT};

    private int mTheme;

    public void setmTheme(int theme) {
        this.mTheme = THEMES[theme];
    }

    /**
     * 弹出日期时间选择框
     *
     * @param dateTimeTextEdite
     * @param type
     * @return
     */
    public Dialog dateTimePickDialog(final TextView dateTimeTextEdite, TYPE type) {
        Dialog dialog = null;

        Calendar c = Calendar.getInstance();
        switch (type) {
            case DATE:
                dialog = new DatePickerDialog(activity, mTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                        SimpleDateFormat sdf = new SimpleDateFormat(mDateFormat);

                        String beforeText = dateTimeTextEdite.getText().toString();
                        dateTimeTextEdite.setText(sdf.format(calendar.getTime()));
                        if (listener != null && !beforeText.equals(dateTimeTextEdite.getText().toString())) {
                            listener.onDateTimeChange();
                        }
                    }
                },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DATE));
                dialog.show();
                break;
            case TIME:
                dialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        if (isHourLimited) {
                            calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hourOfDay, 0);
                            if (minute > 0) {
                                Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hourOfDay, minute);
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat(mTimeFormat);
                        String beforeText = dateTimeTextEdite.getText().toString();
                        dateTimeTextEdite.setText(sdf.format(calendar.getTime()));
                        if (listener != null && !beforeText.equals(dateTimeTextEdite.getText().toString())) {
                            listener.onDateTimeChange();
                        }
                    }
                },
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        true);
                dialog.show();
                break;
            default:break;
        }

        return dialog;
    }
}
