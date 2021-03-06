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

    //?????????????????????
    /*public void intentToLoginActivity(Context context, String toActivity) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (StringUtil.isNotEmpty(toActivity)) {
            intent.putExtra("toActivity", toActivity);
        }
        context.startActivity(intent);
    }*/

    /**
     * ?????????web???????????????????????????????????????
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
     * ?????????????????????
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
        String finishStr = "????????????";
        WeakReference<CountDownTimerUtil.CountDownBean> wf = new WeakReference<CountDownTimerUtil.CountDownBean>(new CountDownTimerUtil.CountDownBean());
        wf.get().setmTextView(tv);//???????????????
        wf.get().setMillisInFuture(Constants.GETVERIFYCODE_MILLISINFUTURE);//????????????????????? The number of millis in the future from the call to {@link #start()} until the countdown is done and {@link #onFinish()} is called.
        wf.get().setCountDownInterval(1000);//????????????????????? The interval along the way to receive {@link #onTick(long)} callbacks.
        wf.get().setTextStyle(StringUtil.setTextSizeColor(finishStr, Color.BLACK, 0, finishStr.length(), 14));//????????????
        wf.get().setTimeType(null);//?????????????????????(??????????????????60???????????????????????????null)
        wf.get().setFinishStr(finishStr);//?????????????????????????????????

        wf.get().setCountDownDrawableId(R.drawable.circle5_graybg_style);//?????????????????????
        wf.get().setCountDownTextColor(R.color.white);//??????????????????????????????

        wf.get().setFinishDrawableId(R.drawable.circle5_darkbluebg_style);//??????????????????
        wf.get().setFinishTextColor(R.color.white);//???????????????????????????
        //????????????????????????
        CountDownTimerUtil countDownTimerUtil = new CountDownTimerUtil(wf.get());
        countDownTimerUtil.start();
    }

    /**
     * ????????????
     */
    public void contactUs(final Context context, final String phonenoStr) {
        if (StringUtil.isEmpty(phonenoStr)) {
            ToastUtils.showOneToast(context, "????????????,????????????");
            return;
        }

        //?????????????????????????????????
        new CommomDialog(context, R.style.dialog, "????????????" + phonenoStr, new CommomDialog.OnCloseListener() {
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
        }).setTitle("??????").show();
    }

    public void makeToastInfo(Context context) {
        Toast.makeText(context, "?????????????????????,????????????", Toast.LENGTH_SHORT).show();
    }


    /**
     * ???????????????????????????
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
     * ???????????????(???????????????)
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
        //??????????????????status_bar_height?????????ID
        int resourceId = MyApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //????????????ID????????????????????????
            height = MyApplication.getContext().getResources().getDimensionPixelSize(resourceId);
        }

        //??????????????????????????????????????????????????????????????????(??????R????????????)
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

        //?????????????????????????????????????????????25dp?????????????????????
        if (height == -1) {
            float density = MyApplication.getContext().getResources().getDisplayMetrics().density;
            height = Integer.valueOf((25 * density + 0.5) + "");
        }

        return height;
    }

    /**
     * 1 ???????????????2 ?????????????????????3???????????????4 ???????????????5???????????????6???????????????7???????????????8??????????????????9??????????????????10?????????????????????11???????????????12??????????????????13????????????????????????????????????????????????????????????
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
     * 1 ???????????????2 ?????????????????????3???????????????4 ???????????????5???????????????6???????????????7???????????????8??????????????????9??????????????????10?????????????????????11???????????????12??????????????????13????????????????????????????????????????????????????????????
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
     * 1:?????????2:?????????3:????????????4:?????????5????????????6????????????7????????????
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
     * 1:0.5??????2:1??????3:1.5???4:2???5:2.5???6:3???7:3.5???8:4???9:4.5,10:5,11:5.5,12:6?????????
     * @param dayType
     */
    public int castDayTypeToInt(String dayType){
        int retInt = 1;
        switch (dayType){
            case "0.5???":
                retInt = 1;
                break;
            case "1???":
                retInt = 2;
                break;
             case "1.5???":
                retInt = 3;
                break;
             case "2???":
                retInt = 4;
                break;
             case "2.5???":
                retInt = 5;
                break;
             case "3???":
                retInt = 6;
                break;
             case "3.5???":
                retInt = 7;
                break;
             case "4???":
                retInt = 8;
                break;
             case "4.5???":
                retInt = 9;
                break;
             case "5???":
                retInt = 10;
                break;
             case "5.5???":
                retInt = 11;
                break;
             case "6????????????":
                retInt = 12;
                break;
            default:break;
        }
        return retInt;
    }
}
