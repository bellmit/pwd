package com.tastebychance.sfj.util;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.tastebychance.sfj.MyApplication;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.web.ShowWebInfoActivity;
import com.tastebychance.sfj.common.web.bean.ShowWebBean;

import java.lang.ref.WeakReference;
import java.util.UUID;

public class SystemUtil {

    public static SystemUtil getInstance() {
        return SingeltonHolder.INSTANCE;
    }

    private static class SingeltonHolder {
        private static final SystemUtil INSTANCE = new SystemUtil();
    }

    private SystemUtil() {
    }

    public void saveIntToSP(@NonNull String key, @NonNull int value) {
        SharedPreferencesUtils.setConfigInt(MyApplication.getContext(), key, value);
    }

    public int getIntFromSP(@NonNull String key) {
        try {
            return SharedPreferencesUtils.getConfigInt(MyApplication.getContext(), key);
        } catch (Exception e) {
            e.printStackTrace();

            return -1;
        }
    }

    public void saveStrToSP(@NonNull String key, @NonNull String str) {
        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), key, str);
    }

    public String getStrFromSP(@NonNull String key) {
        try {
            return SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveBooleanToSP(@NonNull String key, @NonNull boolean booleanValue) {
        SharedPreferencesUtils.setConfigBoolean(MyApplication.getContext(), key, booleanValue);
    }

    public boolean getBooleanFromSP(@NonNull String key) {
        try {
            return SharedPreferencesUtils.getConfigBoolean(MyApplication.getContext(), key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveObjectToSP(@NonNull String key, @NonNull Object object){
        SharedPreferencesUtils.saveToShared(MyApplication.getContext(), key, object);
    }

    public Object getObjectFromSP(@NonNull String key){
        try {
            return SharedPreferencesUtils.queryForSharedToObject(MyApplication.getContext(), key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //跳转到登录界面
    /*public void intentToLoginActivity(Context context, String toActivity) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (StringUtil.isNotEmpty(toActivity)) {
            intent.putExtra("toActivity", toActivity);
        }
        context.startActivity(intent);
    }*/

    /**
     * 跳转到web页面（不带收藏点赞的页面）
     *
     * @param context
     * @param showWebBean
     */
    public void intentToWebInfoActivity(Context context, ShowWebBean showWebBean) {
        Intent intent = new Intent(context, ShowWebInfoActivity.class);
        intent.putExtra("showWebBean", showWebBean);
        context.startActivity(intent);
    }

    /**
     * 调用系统浏览器
     *
     * @param context
     * @param url
     */
    public void intentToSystemBrowser(Context context, String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
        }
    }

    public void countDownTime(TextView tv) {
        String finishStr = "重新发送";
        WeakReference<CountDownTimerUtil.CountDownBean> wf = new WeakReference<CountDownTimerUtil.CountDownBean>(new CountDownTimerUtil.CountDownBean());
        wf.get().setmTextView(tv);//倒计时控件
        wf.get().setMillisInFuture(Constants.GETVERIFYCODE_MILLISINFUTURE);//倒计时持续时间 The number of millis in the future from the call to {@link #start()} until the countdown is done and {@link #onFinish()} is called.
        wf.get().setCountDownInterval(1000);//倒计时时间间隔 The interval along the way to receive {@link #onTick(long)} callbacks.
        wf.get().setTextStyle(StringUtil.setTextSizeColor(finishStr, Color.BLACK, 0, finishStr.length(), 14));//文字格式
        wf.get().setTimeType(null);//倒计时时钟格式(如果要显示“60秒”样式，则直接传null)
        wf.get().setFinishStr(finishStr);//倒计时结束时显示的内容

        wf.get().setCountDownDrawableId(R.drawable.circle5_graybg_style);//倒计时时的背景
        wf.get().setCountDownTextColor(R.color.white);//倒计时时的文字的颜色

        wf.get().setFinishDrawableId(R.drawable.circle5_darkbluebg_style);//恢复时的背景
        wf.get().setFinishTextColor(R.color.white);//恢复时的文字的颜色
        //获取验证码倒计时
        CountDownTimerUtil countDownTimerUtil = new CountDownTimerUtil(wf.get());
        countDownTimerUtil.start();
    }

    /**
     * 联系我们
     */
    public void contactUs(final Context context, final String phonenoStr) {
        if (StringUtil.isEmpty(phonenoStr)) {
            ToastUtils.showOneToast(context, "号码为空,无法拨打");
            return;
        }

        //弹出框提示是否拨打电话
        new CommomDialog(context, R.style.dialog, "是否拨打" + phonenoStr, new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenoStr)));
                    dialog.dismiss();
                }
            }
        }).setTitle("提示").show();
    }

    public void makeToastInfo(Context context) {
        Toast.makeText(context, "该功能暂未开放,敬请期待", Toast.LENGTH_SHORT).show();
    }


    /**
     * 返回当前程序版本名
     */
    public String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            int versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取版本号(内部识别号)
     *
     * @param context
     * @return
     */
    public int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public void clearData() {
        SystemUtil.getInstance().saveObjectToSP(Constants.KEY_EXAMINETYPE, null);
        SystemUtil.getInstance().saveStrToSP(Constants.KEY_ACCOUNT, "");
        SystemUtil.getInstance().saveStrToSP(Constants.KEY_TOKEN, "");
        SystemUtil.getInstance().saveBooleanToSP(Constants.KEY_LOGINSTATE, false);
        SystemUtil.getInstance().saveObjectToSP(Constants.KEY_USERINFO, null);
        SystemUtil.getInstance().saveBooleanToSP(Constants.KEY_LOGOUT, false);
    }


    public int getStatusBarHeight() {
        int height = -1;
        //方法一：获取status_bar_height资源的ID
        int resourceId = MyApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            height = MyApplication.getContext().getResources().getDimensionPixelSize(resourceId);
        }

        //方法一未获取到状态栏高度，采用方法二进行获取(通过R类的反射)
        if (height == -1) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int tempHeight = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
                height = MyApplication.getContext().getResources().getDimensionPixelOffset(tempHeight);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        //如果都没有获取到那么采用默认的25dp当状态栏的高度
        if (height == -1) {
            float density = MyApplication.getContext().getResources().getDisplayMetrics().density;
            height = Integer.valueOf((25 * density + 0.5) + "");
        }

        return height;
    }

    /**
     * 1 中共党员，2 中共预备党员，3共青团员，4 民革党员，5民盟盟员，6民建会员，7民进会员，8农工党党员，9致公党党员，10九三学社社员，11台盟盟员，12无党派人士，13群众（现称普通居民，与居民身份证相对应）
     * @param politics_status
     * @return
     */
    public String castPolicsStatusToStr(int politics_status) {
        String retStr = "";
        switch (politics_status) {
            case 1:
                retStr = Constants.POLICSSTATUS_ZGDY;
                break;
            case 2:
                retStr = Constants.POLICSSTATUS_ZGYBDY;
                break;
            case 3:
                retStr = Constants.POLICSSTATUS_GQTY;
                break;
            case 4:
                retStr = Constants.POLICSSTATUS_MGDY;
                break;
            case 5:
                retStr = Constants.POLICSSTATUS_MMMY;
                break;
            case 6:
                retStr = Constants.POLICSSTATUS_MJIANHY;
                break;
            case 7:
                retStr = Constants.POLICSSTATUS_MJINHY;
                break;
            case 8:
                retStr = Constants.POLICSSTATUS_NGDDY;
                break;
            case 9:
                retStr = Constants.POLICSSTATUS_ZGDDY;
                break;
            case 10:
                retStr = Constants.POLICSSTATUS_JSXSSY;
                break;
            case 11:
                retStr = Constants.POLICSSTATUS_TMMY;
                break;
            case 12:
                retStr = Constants.POLICSSTATUS_WDPRS;
                break;
            case 13:
                retStr = Constants.POLICSSTATUS_QZ;
                break;
            default:break;
        }
        return retStr;
    }

    /**
     * 1 中共党员，2 中共预备党员，3共青团员，4 民革党员，5民盟盟员，6民建会员，7民进会员，8农工党党员，9致公党党员，10九三学社社员，11台盟盟员，12无党派人士，13群众（现称普通居民，与居民身份证相对应）
     * @param politics_status
     * @return
     */
    public int castPolicsStatusToInt(String politics_status) {
        int retInt = 0;
        switch (politics_status) {
            case Constants.POLICSSTATUS_ZGDY:
                retInt = 1;
                break;
            case Constants.POLICSSTATUS_ZGYBDY:
                retInt = 2;
                break;
            case Constants.POLICSSTATUS_GQTY:
                retInt = 3;
                break;
            case Constants.POLICSSTATUS_MGDY:
                retInt = 4;
                break;
            case Constants.POLICSSTATUS_MMMY:
                retInt = 5;
                break;
            case Constants.POLICSSTATUS_MJIANHY:
                retInt = 6;
                break;
            case Constants.POLICSSTATUS_MJINHY:
                retInt = 7;
                break;
            case  Constants.POLICSSTATUS_NGDDY:
                retInt = 8;
                break;
            case Constants.POLICSSTATUS_ZGDDY:
                retInt = 9;
                break;
            case Constants.POLICSSTATUS_JSXSSY:
                retInt = 10;
                break;
            case Constants.POLICSSTATUS_TMMY:
                retInt = 11;
                break;
            case Constants.POLICSSTATUS_WDPRS:
                retInt = 12;
                break;
            case Constants.POLICSSTATUS_QZ:
                retInt = 13;
                break;
            default:break;
        }
        return retInt;
    }

    /**
     * 1:事假，2:病假，3:年休假，4:丧假，5：婚假，6：产假，7：探亲假
     * @param leaveType
     * @return
     */
    public int castLeaveTypeToInt(String leaveType){
        int retInt = 1;
        switch (leaveType){
            case Constants.LEAVETYPE_SHIJIA:
                retInt = 1;
                break;
            case Constants.LEAVETYPE_BINGJIA:
                retInt = 2;
                break;
            case Constants.LEAVETYPE_NIANXIUJIA:
                retInt = 3;
                break;
            case Constants.LEAVETYPE_SHANGJIA:
                retInt = 4;
                break;
            case Constants.LEAVETYPE_HUNJIA:
                retInt = 5;
                break;
            case Constants.LEAVETYPE_CHANJIA:
                retInt = 6;
                break;
            case Constants.LEAVETYPE_TANQINJIA:
                retInt = 7;
                break;
            default:break;
        }
        return retInt;
    }

    /**
     * 1:0.5天，2:1天，3:1.5，4:2，5:2.5，6:3，7:3.5，8:4，9:4.5,10:5,11:5.5,12:6及以上
     * @param dayType
     */
    public int castDayTypeToInt(String dayType){
        int retInt = 1;
        switch (dayType){
            case "0.5天":
                retInt = 1;
                break;
            case "1天":
                retInt = 2;
                break;
             case "1.5天":
                retInt = 3;
                break;
             case "2天":
                retInt = 4;
                break;
             case "2.5天":
                retInt = 5;
                break;
             case "3天":
                retInt = 6;
                break;
             case "3.5天":
                retInt = 7;
                break;
             case "4天":
                retInt = 8;
                break;
             case "4.5天":
                retInt = 9;
                break;
             case "5天":
                retInt = 10;
                break;
             case "5.5天":
                retInt = 11;
                break;
             case "6天及以上":
                retInt = 12;
                break;
            default:break;
        }
        return retInt;
    }
}
