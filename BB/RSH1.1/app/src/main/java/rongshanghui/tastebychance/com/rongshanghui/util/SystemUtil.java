package rongshanghui.tastebychance.com.rongshanghui.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.login.YWLoginState;
import com.alibaba.mobileim.utility.IMPrefsTools;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.Request;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.bean.AlibabaUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventSysNewsNum;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventTaobaoUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.web.ShowWeb2Activity;
import rongshanghui.tastebychance.com.rongshanghui.common.web.ShowWebActivity;
import rongshanghui.tastebychance.com.rongshanghui.common.web.ShowWebInfoActivity;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.HomeBSZNCKListActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.HomeDetailListActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.HomeDetailZCListActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeDetailListBean;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.SHTDepartmentDetailActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.SHTJGDetailActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.SHTQYDetailActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.SHTSHDetailActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.SHTXXDetailActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.home.zsb.detail.ZSBZJDetailActivity;
import rongshanghui.tastebychance.com.rongshanghui.login.LoginActivity;
import rongshanghui.tastebychance.com.rongshanghui.login.bean.LoginRes;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.fabu.FabuDeleteableActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.yijianzhengqiumanger.RichEditActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.network.MyCallBack;
import rongshanghui.tastebychance.com.rongshanghui.util.network.OkHttpUtils;


public class SystemUtil {

    public static SystemUtil getInstance() {
        return SingeltonHolder.mInstance;
    }

    private static class SingeltonHolder {
        private static final SystemUtil mInstance = new SystemUtil();
    }

    private SystemUtil() {
    }

    public String getToken() {
        try {
            String token = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), "token");
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setToken(String token) {
        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), "token", token);
    }

    public LoginRes.UserInfoBean getUserInfo() {
        LoginRes.UserInfoBean userInfo = null;

        try {
            userInfo = (LoginRes.UserInfoBean) SharedPreferencesUtils.queryForSharedToObject(MyApplication.getContext(), "userInfo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public void setUserInfo(LoginRes.UserInfoBean userInfo) {
        SharedPreferencesUtils.saveToShared(MyApplication.getContext(), "userInfo", userInfo);
    }

    //???????????????????????????  true?????????   false  ???????????????
    public boolean getIsLogin() {
        try {
            return SharedPreferencesUtils.getConfigBoolean(MyApplication.getContext(), "isLogin");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setIsLogin(boolean isLogin) {
        SharedPreferencesUtils.setConfigBoolean(MyApplication.getContext(), "isLogin", isLogin);
    }

    public void saveIntToSP(String key, int value) {
        SharedPreferencesUtils.setConfigInt(MyApplication.getContext(), key, value);
    }

    public int getIntFromSP(String key) {
        try {
            return SharedPreferencesUtils.getConfigInt(MyApplication.getContext(), key);
        } catch (Exception e) {
            e.printStackTrace();

            return -1;
        }
    }

    public void saveStrToSP(String key, String str) {
        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), key, str);
    }

    public String getStrFromSP(String key) {
        try {
            return SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveBooleanToSP(String key, boolean booleanValue) {
        SharedPreferencesUtils.setConfigBoolean(MyApplication.getContext(), key, booleanValue);
    }

    public boolean getBooleanFromSP(String key) {
        try {
            return SharedPreferencesUtils.getConfigBoolean(MyApplication.getContext(), key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @param userId
     * @param password
     */
    public void saveLoginInfoToLocal(String userId, String password, String appkey) {
        IMPrefsTools.setStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID, userId);
        IMPrefsTools.setStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD, password);
        IMPrefsTools.setStringPrefs(MyApplication.getContext(), Constants.KEY_APPKEY, appkey);
    }

    //?????????????????????
    public void intentToLoginActivity(Context context, String toActivity) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (StringUtil.isNotEmpty(toActivity)) {
            intent.putExtra("toActivity", toActivity);
        }
        context.startActivity(intent);
    }

    /**
     * ?????????web????????????????????????????????????
     *
     * @param context
     * @param showWebBean
     */
    public void intentToWebActivity(Context context, ShowWebBean showWebBean) {
        Intent intent = new Intent(context, ShowWebActivity.class);
        intent.putExtra("showWebBean", showWebBean);
        context.startActivity(intent);
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

        wf.get().setCountDownDrawableId(R.drawable.rectangle_graybg_style);//?????????????????????
        wf.get().setCountDownTextColor(R.color.textgray);//??????????????????????????????

        wf.get().setFinishDrawableId(R.drawable.rectangle_graybg_style);//??????????????????
        wf.get().setFinishTextColor(R.color.font_blue);//???????????????????????????
        //????????????????????????
        CountDownTimerUtil countDownTimerUtil = new CountDownTimerUtil(wf.get());
        countDownTimerUtil.start();
    }

    /**
     * ?????????web???????????????????????????????????????
     *
     * @param context
     * @param showWebBean
     */
    public void intentToWeb2Activity(Context context, ShowWebBean showWebBean) {
        Intent intent = new Intent(context, ShowWeb2Activity.class);
        intent.putExtra("showWebBean", showWebBean);
        context.startActivity(intent);
    }

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
     * ???????????????-?????????
     *
     * @param context
     * @param homeDetailListBean
     */
    public void intentToHomeDetailListActivity(Context context, HomeDetailListBean homeDetailListBean) {
        if (null == homeDetailListBean) {
            return;
        }
        Intent intent = null;
        switch (castPostListByCate(homeDetailListBean.getType())) {//0:??????;1:????????????;2:????????????;3:??????;4:??????;5:??????;6:?????????;7:?????????;8:????????????;9:????????????,10:??????
            case 0:

                break;
            case 3:
            case 10:
                intent = new Intent(context, HomeDetailZCListActivity.class);
                break;
            case 8:

                break;
            case 1:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
                intent = new Intent(context, HomeDetailListActivity.class);
                break;
            case 5:
                intent = new Intent(context, HomeBSZNCKListActivity.class);
                break;
        }
        if (null != intent) {
            intent.putExtra("homeDetailListBean", homeDetailListBean);
            context.startActivity(intent);
        }
    }

    /**
     * ???????????????????????????
     *
     * @param context
     * @param toDetailBean
     */
    public void intentToDetail(Context context, ToDetailBean toDetailBean) {
        Intent intent = null;
        switch (toDetailBean.getToType()) {//1:??????,2:?????????3????????????4????????????5:?????????6????????????7?????????,8:??????
            case 1:
                intent = new Intent(context, SHTSHDetailActivity.class);
                break;
            case 2:
                intent = new Intent(context, SHTQYDetailActivity.class);
                break;
            case 3:
                intent = new Intent(context, SHTDepartmentDetailActivity.class);
                break;
            case 4:
                intent = new Intent(context, SHTJGDetailActivity.class);
                break;
            case 5:
                intent = new Intent(context, ZSBZJDetailActivity.class);
                break;
            case 6:
            case 7:
            case 8:
                intent = new Intent(context, SHTXXDetailActivity.class);
                break;
        }
        if (null != intent) {
            intent.putExtra("toDetailBean", toDetailBean);
            context.startActivity(intent);
        }
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

    /**
     * ??????????????????????????????
     *
     * @param context
     * @param toDetailBean
     */
    public void intentToFabuDeleteableActivity(Context context, ToDetailBean toDetailBean) {
        Intent intent = new Intent(context, FabuDeleteableActivity.class);
        intent.putExtra("toDetailBean", toDetailBean);
        context.startActivity(intent);
    }

    /**
     * ?????????????????????????????????
     *
     * @param context
     * @param toDetailBean
     */
    public void intentToRichEditActivity(Context context, ToDetailBean toDetailBean) {
        Intent intent = new Intent(context, RichEditActivity.class);
        intent.putExtra("toDetailBean", toDetailBean);
        context.startActivity(intent);
    }

    public String convertRegisterType(String registerType) {
//		1:??????,2:?????????3????????????4????????????5?????????
        String returnStr = "1";
        switch (registerType) {
            case "??????":
                returnStr = "1";
                break;
            case "??????":
                returnStr = "2";
                break;
            case "??????":
                returnStr = "3";
                break;
            case "??????":
                returnStr = "4";
                break;
            case "??????":
                returnStr = "5";
                break;
        }

        return returnStr;
    }

    public String convertRegisterType2(int registerType) {
//		1:??????,2:?????????3????????????4????????????5?????????
        String returnStr = "??????";
        switch (registerType) {
            case 1:
                returnStr = "??????";
                break;
            case 2:
                returnStr = "??????";
                break;
            case 3:
                returnStr = "??????";
                break;
            case 4:
                returnStr = "??????";
                break;
            case 5:
                returnStr = "??????";
                break;
        }

        return returnStr;
    }

    /**
     * ????????????
     * <p>
     * 1:??????,2:?????????3????????????4????????????5:?????????6????????????7?????????,8:??????
     */
    public int castTypeToValue(String type) {
        int typeValue = 1;
        switch (type) {
            case Constants.PUBLISHCATE_SH:
                typeValue = 1;
                break;
            case Constants.PUBLISHCATE_QY:
                typeValue = 2;
                break;
            case Constants.PUBLISHCATE_BM:
                typeValue = 3;
                break;
            case Constants.PUBLISHCATE_JG:
                typeValue = 4;
                break;
            case Constants.PUBLISHCATE_ZJ:
                typeValue = 5;
                break;
            case Constants.PUBLISHCATE_XX:
                typeValue = 6;
                break;
            case Constants.PUBLISHCATE_YY:
                typeValue = 7;
                break;
            case Constants.PUBLISHCATE_QT:
                typeValue = 8;
                break;
        }
        return typeValue;
    }

    /**
     * ????????????
     * 1:??????,2:?????????3????????????4????????????5:?????????6????????????7?????????,8:??????
     */
    public String castValueToType(int typeValue) {
        String type = Constants.PUBLISHCATE_SH;
        switch (typeValue) {
            case 1:
                type = Constants.PUBLISHCATE_SH;
                break;
            case 2:
                type = Constants.PUBLISHCATE_QY;
                break;
            case 3:
                type = Constants.PUBLISHCATE_BM;
                break;
            case 4:
                type = Constants.PUBLISHCATE_JG;
                break;
            case 5:
                type = Constants.PUBLISHCATE_ZJ;
                break;
            case 6:
                type = Constants.PUBLISHCATE_XX;
                break;
            case 7:
                type = Constants.PUBLISHCATE_YY;
                break;
            case 8:
                type = Constants.PUBLISHCATE_QT;
                break;
        }
        return type;
    }

    //1:????????????-??????;2:????????????-????????????;3:????????????-????????????;4:????????????-????????????;5:?????????-????????????;6:?????????-????????????;7:?????????-????????????,8:?????????-???????????????????????????9:?????????-????????????,10:?????????-???????????????11????????????-???????????????12????????????-???????????????15 ?????????-????????????
    public String castType2(String type) {
        String typeStr = "1";
        switch (type) {
            case "??????":
                typeStr = "1";
                break;
            case "????????????":
                typeStr = "2";
                break;
            case "????????????":
                typeStr = "3";
                break;
            case "????????????":
                typeStr = "4";
                break;
            case "????????????":
                typeStr = "5";
                break;
            case "????????????":
                typeStr = "6";
                break;
            case "????????????":
                typeStr = "7";
                break;
            case "??????"://????????????
                typeStr = "8";
                break;
            case "????????????":
                typeStr = "9";
                break;
            case "????????????":
                typeStr = "10";
                break;
            case "????????????":
                typeStr = "11";
                break;
            case "????????????":
                typeStr = "12";
                break;
            case "????????????":
                typeStr = "15";
                break;
        }
        return typeStr;
    }

    /**
     * ????????????-?????????????????????,????????????
     *
     * @param type 0:??????????????????;1:????????????;2:????????????;3:??????;4:??????;5:??????;6:?????????;7:?????????;8:????????????;9:????????????,10:?????????,11:????????????
     * @return
     */
    public int castPostListByCate(String type) {
        int returnInt = 0;
        switch (type) {
            case Constants.PULISHTYPE_PT:
                returnInt = 0;
                break;
            case Constants.PULISHTYPE_RZXM:
                returnInt = 1;
                break;
            case Constants.PULISHTYPE_FDXX:
                returnInt = 2;
                break;
            case Constants.PULISHTYPE_ZC:
                returnInt = 3;
                break;
            case Constants.PULISHTYPE_ZX:
                returnInt = 4;
                break;
            case Constants.PULISHTYPE_ZN:
                returnInt = 5;
                break;
            case Constants.PULISHTYPE_ZSX:
                returnInt = 6;
                break;
            case Constants.PULISHTYPE_SHX:
                returnInt = 7;
                break;
            case Constants.PULISHTYPE_XZCL:
                returnInt = 8;
                break;
            case Constants.PULISHTYPE_QYFC:
                returnInt = 9;
                break;
            case Constants.PULISHTYPE_SB:
                returnInt = 10;
                break;
            case Constants.PULISHTYPE_YJZJ:
                returnInt = 11;
                break;
        }
        return returnInt;
    }

    /**
     * ????????????????????????
     * publish_cate 	??? 	int 	0:??????????????????;1:??????;2:??????;3:??????;4:??????????????????;5:??????;6:??????
     */
    public int castPulishCate(String str) {
        int returnInt = 0;
        switch (str) {
            case Constants.PUBLISHCATE_PT:
                returnInt = 0;
                break;
            case Constants.PUBLISHCATE_SH:
                returnInt = 1;
                break;
            case Constants.PUBLISHCATE_QY:
                returnInt = 2;
                break;
            case Constants.PUBLISHCATE_JG:
                returnInt = 3;
                break;
            case Constants.PUBLISHCATE_BM:
                returnInt = 4;
                break;
            case Constants.PUBLISHCATE_GR:
                returnInt = 5;
                break;
            case Constants.PUBLISHCATE_ZJ:
                returnInt = 6;
                break;
        }
        return returnInt;
    }

    /**
     * ??????????????????
     * publish_type 	??? 	int 	0:??????????????????;1:????????????;2:????????????;3:??????;4:??????;5:??????;6:?????????;7:?????????;8:????????????;9:????????????,10:??????,11:????????????
     *
     * @param type
     * @return
     */
    public int castPublishType(String type) {
        int returnInt = 0;
        switch (type) {
            case Constants.PULISHTYPE_PT:
                returnInt = 0;
                break;
            case Constants.PULISHTYPE_RZXM:
                returnInt = 1;
                break;
            case Constants.PULISHTYPE_FDXX:
                returnInt = 2;
                break;
            case Constants.PULISHTYPE_ZC:
                returnInt = 3;
                break;
            case Constants.PULISHTYPE_ZX:
                returnInt = 4;
                break;
            case Constants.PULISHTYPE_ZN:
                returnInt = 5;
                break;
            case Constants.PULISHTYPE_ZSX:
                returnInt = 6;
                break;
            case Constants.PULISHTYPE_SHX:
                returnInt = 7;
                break;
            case Constants.PULISHTYPE_XZCL:
                returnInt = 8;
                break;
            case Constants.PULISHTYPE_QYFC:
                returnInt = 9;
                break;
            case Constants.PULISHTYPE_SB:
                returnInt = 10;
                break;
            case Constants.PULISHTYPE_YJZJ:
                return returnInt = 11;
        }
        return returnInt;
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

    public boolean execShellCmd(String cmd) {

        try {
            System.out.println(cmd);
            // ????????????root???????????????????????????????????????????????????
            Process process = Runtime.getRuntime().exec("su");
            // ???????????????
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
            String ls = null;
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "gbk"));
            while ((ls = bufferedReader.readLine()) != null) {
                Log.v("CMD", ls);
                if ("INSTRUMENTATION_STATUS_CODE: 1".equals(ls)) {
                    return true;
                }
            }

            bufferedReader.close();
            process.getOutputStream().close();

        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @return
     */
    public String getLocationName() {
        String locateName = null;

		/*LocationUtil locationUtil = new LocationUtil(MyApplication.getContext());
        locateName = locationUtil.getAddressName();
*/
        return locateName;
    }

    /**
     * ??????????????????
     *
     * @param context
     * @return ??????????????????????????????
     */
    public String GetNetworkType(Context context) {
        String strNetworkType = "";

        ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo networkInfo = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mWifi != null && mWifi.isConnected()) {
            strNetworkType = "WIFI";
            return strNetworkType;
        }

        if (networkInfo != null && networkInfo.isConnected()) {

            String _strSubTypeName = networkInfo.getSubtypeName();

            Log.e("cocos2d-x", "Network getSubtypeName : " + _strSubTypeName);

            // TD-SCDMA networkType is 17
            int networkType = networkInfo.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by 11
                    strNetworkType = "2G";
                    break;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by 14
                case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by 12
                case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by 15
                    strNetworkType = "3G";
                    break;
                case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by 13
                    strNetworkType = "4G";
                    break;
                default:
                    // http://baike.baidu.com/item/TD-SCDMA ???????????? ?????? ?????? ??????3G??????
                    if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA")
                            || _strSubTypeName.equalsIgnoreCase("WCDMA")
                            || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                        strNetworkType = "3G";
                    } else {
                        strNetworkType = _strSubTypeName;
                    }

                    break;
            }

            Log.e("cocos2d-x",
                    "Network getSubtype : "
                            + Integer.valueOf(networkType).toString());

        }

        Log.e("cocos2d-x", "Network Type : " + strNetworkType);

        return strNetworkType;
    }

    /**
     * ??????IMEI
     *
     * @param context
     * @return
     */
    public String getIMEI(Context context) {
        // TelephonyManager telephonyManager = (TelephonyManager) context
        // .getSystemService(Context.TELEPHONY_SERVICE);
        //
        // String imei = telephonyManager.getDeviceId();
        // return imei;
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    /**
     * ???????????????
     *
     * @param context
     * @return
     */
    public String getPhoneNo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") String phoneNo = telephonyManager.getLine1Number();
        return phoneNo;
    }

    /**
     * ?????????????????????
     *
     * @param context
     * @return
     */
    public String getImsiNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imsi = tm.getSubscriberId();
        return imsi;
    }

    /**
     * ????????????
     *
     * @param context
     */
    public void rebootPhone(Context context) {
        // Intent restartIntent = new Intent(context, MainActivity.class);
        // int pendingId = 1;
        // PendingIntent pendingIntent = PendingIntent.getActivity(context,
        // pendingId, restartIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        // AlarmManager mgr =
        // (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        // mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 2000,
        // pendingIntent);
        String cmd = "su -c reboot";
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            new AlertDialog.Builder(context).setTitle("Error").setMessage(e.getMessage()).setPositiveButton("OK", null).show();
        }

    }

    /**
     * ??????root???
     *
     * @return
     */
    public boolean isRoot() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            process.getOutputStream().write("exit\n".getBytes());
            process.getOutputStream().flush();
            int i = process.waitFor();
            if (0 == i) {
                process = Runtime.getRuntime().exec("su");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * ??????Android??????IP??????
     *
     * @return
     */
    public String getLocalIPAddress() throws SocketException {
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
            NetworkInterface intf = en.nextElement();
            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                InetAddress inetAddress = enumIpAddr.nextElement();
                if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                    return inetAddress.getHostAddress().toString();
                }
            }
        }
        return null;
    }

    /**
     * ??????
     *
     * @param activity
     * @return
     */
    public String captureScreen(Context activity) {

        // ?????????????????????
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager WM = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = WM.getDefaultDisplay();
        display.getMetrics(metrics);
        int height = metrics.heightPixels; // ?????????
        int width = metrics.widthPixels; // ????????????

        // ??????????????????
        int pixelformat = display.getPixelFormat();
        PixelFormat localPixelFormat1 = new PixelFormat();
        PixelFormat.getPixelFormatInfo(pixelformat, localPixelFormat1);
        int deepth = localPixelFormat1.bytesPerPixel;// ??????
        byte[] piex = new byte[height * width * deepth];
        try {
            Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "chmod 777 /dev/graphics/fb0"});
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // ??????fb0???????????????
            InputStream stream = new FileInputStream(new File("/dev/graphics/fb0"));
            DataInputStream dStream = new DataInputStream(stream);
            dStream.readFully(piex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ????????????
        int[] colors = new int[height * width];
        for (int m = 0; m < colors.length; m++) {
            int r = (piex[m * 4] & 0xFF);
            int g = (piex[m * 4 + 1] & 0xFF);
            int b = (piex[m * 4 + 2] & 0xFF);
            int a = (piex[m * 4 + 3] & 0xFF);
            colors[m] = (a << 24) + (r << 16) + (g << 8) + b;
        }

        // piex??????Bitmap
        Bitmap bitmap = Bitmap.createBitmap(colors, width, height, Bitmap.Config.ARGB_8888);
        File f = null;
        try {
            Bitmap mBitmap = bitmap;
            f = new File(Environment.getExternalStorageDirectory() + "/mypic.png");
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return f.getAbsolutePath();
    }

    public void killPorcess(String processId) {
        try {
            String cmd = "kill -9 " + processId;
            Process process = Runtime.getRuntime().exec("su");
            // ???????????????
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopProcess(String processName) {
        String pscmd = "ps | grep " + processName;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(pscmd);
            InputStream inputstream = proc.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            String line = "";
            StringBuilder sb = new StringBuilder(line);
            while ((line = bufferedreader.readLine()) != null) {
                // System.out.println(line);
                sb.append(line);
                sb.append('\n');
            }
            proc.waitFor();
            String[] l = sb.toString().split("root");
            System.out.print(sb.toString());
            for (String s : l) {
                if (s.contains(processName)) {
                    String[] ls = s.split(" ");
                    for (String p : ls) {
                        if (p.length() > 0) {
                            killPorcess(p);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final Pattern pattern = Pattern.compile("\\S*[?]\\S*");

    public void toggleWiFi(Context context, boolean enabled) {
        /*WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wm.setWifiEnabled(enabled);*/
//		try {
//			Thread.sleep(2500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    // ??????GPRS????????????
    private boolean gprsIsOpenMethod(ConnectivityManager mCM, String methodName) {
        Class cmClass = mCM.getClass();
        Class[] argClasses = null;
        Object[] argObject = null;

        Boolean isOpen = false;
        try {
            Method method = cmClass.getMethod(methodName, argClasses);

            isOpen = (Boolean) method.invoke(mCM, argObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isOpen;
    }

    // ??????/??????GPRS
    private void setGprsEnabled(ConnectivityManager mCM, boolean isEnable) {
        Class cmClass = mCM.getClass();
        Class[] argClasses = new Class[1];
        argClasses[0] = boolean.class;

        try {
            Method method = cmClass.getMethod("setMobileDataEnabled", argClasses);
            method.invoke(mCM, isEnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final static String COMMAND_L_ON = "svc data enable\n ";
    private final static String COMMAND_L_OFF = "svc data disable\n ";
    private final static String COMMAND_SU = "su";

    public void setGprsEnabled(Context context, boolean enable) {

        String command;
        if (enable)
            command = COMMAND_L_ON;
        else
            command = COMMAND_L_OFF;

        try {
            Process su = Runtime.getRuntime().exec(COMMAND_SU);
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            outputStream.writeBytes(command);
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            try {
                su.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean gprsEnabled(Context context, boolean bEnable) {
        if (Build.VERSION.SDK_INT >= 21) {
            setGprsEnabled(context, bEnable);
        } else {
            Object[] argObjects = null;
            ConnectivityManager mCM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            setGprsEnabled(mCM, bEnable);
        }
        return bEnable;
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

    public void clearData() {
        //???????????????????????????
        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), "chosedCity", "");
        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), "city", "");
        SystemUtil.getInstance().setToken(null);
        SystemUtil.getInstance().setIsLogin(false);
        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), "token", "");
        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), "phoneno", "");
        SharedPreferencesUtils.saveToShared(MyApplication.getContext(), "userInfo", null);
        SystemUtil.getInstance().saveStrToSP("alibaba_userid", null);

        SystemUtil.getInstance().saveIntToSP(Constants.KEY_SYSNEWSUNREADNUM, 0);
    }

    public boolean hasChanged(String str1, String originStr) {
        if ((StringUtil.isEmpty(str1) && StringUtil.isNotEmpty(originStr) || (StringUtil.isNotEmpty(str1) && StringUtil.isEmpty(originStr)))) {
            return true;
        }

        if (str1.equals(originStr)) {
            return false;
        }
        return true;
    }

    public RelativeLayout.LayoutParams getRLLparams(View view, int viewWidth, int viewHeight) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.width = viewWidth * ScreenUtils.getScreenWidth() / 375;
        lp.height = viewHeight * ScreenUtils.getScreenHeight() / 667;
        return lp;
    }

    public void taobaouser(int type, MyCallBack myCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("token", SystemUtil.getInstance().getToken());
        map.put("type", type + "");//int 	1 ???????????? 2 ????????????
        OkHttpUtils.getInstance().PostWithFormData(UrlManager.URL_TAOBAOUSER, map, myCallBack);
    }

    public void alibabalogout() {
        // openIM SDK?????????????????????
        IYWLoginService mLoginService = LoginSampleHelper.getInstance().getIMKit().getLoginService();
        mLoginService.logout(new IWxCallback() {
            //??????logout?????????????????????IMBaseActivity???OpenIM??????Actiivity???s
            @Override
            public void onSuccess(Object... arg0) {
                YWLog.i(Constants.TAG, "????????????");
                LoginSampleHelper.getInstance().setAutoLoginState(YWLoginState.idle);

                alibabaLogin();
            }

            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int arg0, String arg1) {
                YWLog.i(Constants.TAG, "????????????,???????????????");
                alibabaLogin();
            }
        });
    }

    public void alibabaLogin() {
        LoginSampleHelper.getInstance().login_Sample(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID),
                IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD), Constants.APP_KEY, new IWxCallback() {
                    @Override
                    public void onSuccess(Object... arg0) {
                        SystemUtil.getInstance().saveLoginInfoToLocal(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID),
                                IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD), Constants.APP_KEY);

                        SystemUtil.getInstance().saveBooleanToSP(Constants.KEY_ISACCOUNTCHANGED, true);
                    }

                    @Override
                    public void onProgress(int arg0) {

                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        SystemUtil.getInstance().taobaouser(1, new MyCallBack<AlibabaUser>() {
                            @Override
                            public void onLoadingBefore(Request request) {

                            }

                            @Override
                            public void onSuccess(Response response, AlibabaUser result) {
                                SystemUtil.getInstance().saveLoginInfoToLocal(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID),
                                        IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD), Constants.APP_KEY);
                            }

                            @Override
                            public void onFailure(Request request, Exception e) {
                                EventBusUtils.post(new EventTaobaoUser());
                            }

                            @Override
                            public void onError(Response response) {

                            }
                        });
                    }
                });
    }

    public void hasNewNotify() {
        Map<String, String> map = new HashMap<>();
        map.put("token", SystemUtil.getInstance().getToken());
        OkHttpUtils.getInstance().PostWithFormData(UrlManager.URL_NEWS_HASNEWNOTIFY, map, new MyCallBack<ResInfo>() {
            @Override
            public void onLoadingBefore(Request request) {
                System.out.println("request = " + request);
            }

            @Override
            public void onSuccess(Response response, ResInfo result) {
                if (null == result) {
                    return;
                }

                if (result.getCode() == Constants.REQUEST_CODE) {
                    try {
                        int systemUnreadNum = Integer.valueOf(result.getDataToStr());
                        //TODO?????????
                        SystemUtil.getInstance().saveIntToSP(Constants.KEY_SYSNEWSUNREADNUM, systemUnreadNum);
                        WeakReference<EventSysNewsNum> wf = new WeakReference<EventSysNewsNum>(new EventSysNewsNum());
                        wf.get().setSysNewsNum(systemUnreadNum);
                        EventBusUtils.post(wf.get());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                System.out.println("e = " + e);
            }

            @Override
            public void onError(Response response) {
                System.out.println("response = " + response);
            }
        });
    }

    public String generateImgName() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".png";
    }

    public String checkFileType(String path, int fileType) {
        if (StringUtil.isEmpty(path)) {
            return "????????????????????????????????????";
        }
        if (fileType == Constants.FILE_TYPE) {
            if (!path.endsWith(".txt") && !path.endsWith(".doc") && !path.endsWith(".docx") && !path.endsWith(".xlsx") && !path.endsWith(".xls")) {
                return "?????????txt???doc???docx???xlsx???xls???????????????";
            }
        }

        if (fileType == Constants.IMG_TYPE) {
            if (!path.endsWith(".jpg") && !path.endsWith(".jpeg") && !path.endsWith(".png")) {
                return "jpg???jpeg???png???????????????";
            }
        }

        if (fileType == Constants.VIDEO_TYPE) {
            if (!path.endsWith(".mp4")) {
                return "?????????mp4???????????????";
            }
        }

        return null;
    }

    public String getJJVideoType(String video) {
//		return StringUtil.isNotEmpty(video) ? UrlManager.URL_ANDROID_JJ : "";
        return UrlManager.URL_ANDROID_JJ;
    }

	/*public static void main(String[] args){
		int height = -1;
		float density = 3f;
		height = (int)(25 * density + 0.5 );
		System.out.println(height);
	}*/

}
