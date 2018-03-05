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

    //判断是否已经登录了  true为登录   false  为没有登录
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
     * 保存登录的用户名密码到本地
     *
     * @param userId
     * @param password
     */
    public void saveLoginInfoToLocal(String userId, String password, String appkey) {
        IMPrefsTools.setStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID, userId);
        IMPrefsTools.setStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD, password);
        IMPrefsTools.setStringPrefs(MyApplication.getContext(), Constants.KEY_APPKEY, appkey);
    }

    //跳转到登录界面
    public void intentToLoginActivity(Context context, String toActivity) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (StringUtil.isNotEmpty(toActivity)) {
            intent.putExtra("toActivity", toActivity);
        }
        context.startActivity(intent);
    }

    /**
     * 跳转到web页面（带收藏点赞的页面）
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
        String finishStr = "重新发送";
        WeakReference<CountDownTimerUtil.CountDownBean> wf = new WeakReference<CountDownTimerUtil.CountDownBean>(new CountDownTimerUtil.CountDownBean());
        wf.get().setmTextView(tv);//倒计时控件
        wf.get().setMillisInFuture(Constants.GETVERIFYCODE_MILLISINFUTURE);//倒计时持续时间 The number of millis in the future from the call to {@link #start()} until the countdown is done and {@link #onFinish()} is called.
        wf.get().setCountDownInterval(1000);//倒计时时间间隔 The interval along the way to receive {@link #onTick(long)} callbacks.
        wf.get().setTextStyle(StringUtil.setTextSizeColor(finishStr, Color.BLACK, 0, finishStr.length(), 14));//文字格式
        wf.get().setTimeType(null);//倒计时时钟格式(如果要显示“60秒”样式，则直接传null)
        wf.get().setFinishStr(finishStr);//倒计时结束时显示的内容

        wf.get().setCountDownDrawableId(R.drawable.rectangle_graybg_style);//倒计时时的背景
        wf.get().setCountDownTextColor(R.color.textgray);//倒计时时的文字的颜色

        wf.get().setFinishDrawableId(R.drawable.rectangle_graybg_style);//恢复时的背景
        wf.get().setFinishTextColor(R.color.font_blue);//恢复时的文字的颜色
        //获取验证码倒计时
        CountDownTimerUtil countDownTimerUtil = new CountDownTimerUtil(wf.get());
        countDownTimerUtil.start();
    }

    /**
     * 跳转到web页面（不带收藏点赞的页面）
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
     * 跳转到信息-列表页
     *
     * @param context
     * @param homeDetailListBean
     */
    public void intentToHomeDetailListActivity(Context context, HomeDetailListBean homeDetailListBean) {
        if (null == homeDetailListBean) {
            return;
        }
        Intent intent = null;
        switch (castPostListByCate(homeDetailListBean.getType())) {//0:平台;1:融资项目;2:放贷信息;3:政策;4:资讯;5:指南;6:招商秀;7:商会秀;8:下载材料;9:企业风采,10:上报
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
     * 跳转到信息综合页面
     *
     * @param context
     * @param toDetailBean
     */
    public void intentToDetail(Context context, ToDetailBean toDetailBean) {
        Intent intent = null;
        switch (toDetailBean.getToType()) {//1:商会,2:企业，3：部门，4：机构，5:镇街，6：学校，7：医院,8:其他
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

    /**
     * 跳转到可删除列表页面
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
     * 跳转到富文本编辑器界面
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
//		1:银行,2:证券，3：保险，4：信托，5：基金
        String returnStr = "1";
        switch (registerType) {
            case "银行":
                returnStr = "1";
                break;
            case "证券":
                returnStr = "2";
                break;
            case "保险":
                returnStr = "3";
                break;
            case "信托":
                returnStr = "4";
                break;
            case "基金":
                returnStr = "5";
                break;
        }

        return returnStr;
    }

    public String convertRegisterType2(int registerType) {
//		1:银行,2:证券，3：保险，4：信托，5：基金
        String returnStr = "银行";
        switch (registerType) {
            case 1:
                returnStr = "银行";
                break;
            case 2:
                returnStr = "证券";
                break;
            case 3:
                returnStr = "保险";
                break;
            case 4:
                returnStr = "信托";
                break;
            case 5:
                returnStr = "基金";
                break;
        }

        return returnStr;
    }

    /**
     * 类型转值
     * <p>
     * 1:商会,2:企业，3：部门，4：机构，5:镇街，6：学校，7：医院,8:其他
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
     * 值转类型
     * 1:商会,2:企业，3：部门，4：机构，5:镇街，6：学校，7：医院,8:其他
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

    //1:融商新闻-头条;2:融商新闻-融商风采;3:融商新闻-商会活动;4:融商新闻-文化寻根;5:招商宝-优惠政策;6:招商宝-资产交易;7:招商宝-创新创业,8:招商宝-招商秀（国际商机）9:融资易-金融资讯,10:融资易-融资融券，11：融资易-放贷信息，12：融资易-企业管理，15 招商宝-国内商机
    public String castType2(String type) {
        String typeStr = "1";
        switch (type) {
            case "头条":
                typeStr = "1";
                break;
            case "融商风采":
                typeStr = "2";
                break;
            case "商会活动":
                typeStr = "3";
                break;
            case "文化寻根":
                typeStr = "4";
                break;
            case "优惠政策":
                typeStr = "5";
                break;
            case "资产交易":
                typeStr = "6";
                break;
            case "创新创业":
                typeStr = "7";
                break;
            case "商机"://国际商机
                typeStr = "8";
                break;
            case "金融资讯":
                typeStr = "9";
                break;
            case "融资融券":
                typeStr = "10";
                break;
            case "放贷信息":
                typeStr = "11";
                break;
            case "企业管理":
                typeStr = "12";
                break;
            case "国内商机":
                typeStr = "15";
                break;
        }
        return typeStr;
    }

    /**
     * 文章列表-根据类别搜文章,类型转换
     *
     * @param type 0:平台（后台）;1:融资项目;2:放贷信息;3:政策;4:资讯;5:指南;6:招商秀;7:商会秀;8:下载材料;9:企业风采,10:上报’,11:意见征集
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
     * 发布平台类型转换
     * publish_cate 	是 	int 	0:平台（后台）;1:商会;2:企业;3:机构;4:部门（政府）;5:个人;6:镇街
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
     * 发布类型转换
     * publish_type 	是 	int 	0:平台（后台）;1:融资项目;2:放贷信息;3:政策;4:资讯;5:指南;6:招商秀;7:商会秀;8:下载材料;9:企业风采,10:上报,11:意见征集
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

    public boolean execShellCmd(String cmd) {

        try {
            System.out.println(cmd);
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
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
     * 获取当前手机所在省份城市名称
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
     * 获取网络类型
     *
     * @param context
     * @return 为空串时是没开启网络
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
                    // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
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
     * 获取IMEI
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
     * 获取手机号
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
     * 获取手机卡串号
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
     * 重启机子
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
     * 是否root过
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
     * 获取Android本机IP地址
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
     * 截屏
     *
     * @param activity
     * @return
     */
    public String captureScreen(Context activity) {

        // 获取屏幕大小：
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager WM = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = WM.getDefaultDisplay();
        display.getMetrics(metrics);
        int height = metrics.heightPixels; // 屏幕高
        int width = metrics.widthPixels; // 屏幕的宽

        // 获取显示方式
        int pixelformat = display.getPixelFormat();
        PixelFormat localPixelFormat1 = new PixelFormat();
        PixelFormat.getPixelFormatInfo(pixelformat, localPixelFormat1);
        int deepth = localPixelFormat1.bytesPerPixel;// 位深
        byte[] piex = new byte[height * width * deepth];
        try {
            Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "chmod 777 /dev/graphics/fb0"});
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // 获取fb0数据输入流
            InputStream stream = new FileInputStream(new File("/dev/graphics/fb0"));
            DataInputStream dStream = new DataInputStream(stream);
            dStream.readFully(piex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 保存图片
        int[] colors = new int[height * width];
        for (int m = 0; m < colors.length; m++) {
            int r = (piex[m * 4] & 0xFF);
            int g = (piex[m * 4 + 1] & 0xFF);
            int b = (piex[m * 4 + 2] & 0xFF);
            int a = (piex[m * 4 + 3] & 0xFF);
            colors[m] = (a << 24) + (r << 16) + (g << 8) + b;
        }

        // piex生成Bitmap
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
            // 获取输出流
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

    // 检测GPRS是否打开
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

    // 开启/关闭GPRS
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

    public void clearData() {
        //清空已经选中的城市
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
        map.put("type", type + "");//int 	1 导入用户 2 更新用户
        OkHttpUtils.getInstance().PostWithFormData(UrlManager.URL_TAOBAOUSER, map, myCallBack);
    }

    public void alibabalogout() {
        // openIM SDK提供的登录服务
        IYWLoginService mLoginService = LoginSampleHelper.getInstance().getIMKit().getLoginService();
        mLoginService.logout(new IWxCallback() {
            //此时logout已关闭所有基于IMBaseActivity的OpenIM相关Actiivity，s
            @Override
            public void onSuccess(Object... arg0) {
                YWLog.i(Constants.TAG, "退出成功");
                LoginSampleHelper.getInstance().setAutoLoginState(YWLoginState.idle);

                alibabaLogin();
            }

            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int arg0, String arg1) {
                YWLog.i(Constants.TAG, "退出失败,请重新登录");
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
                        //TODO：临时
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
            return "请选择一个正确格式的文件";
        }
        if (fileType == Constants.FILE_TYPE) {
            if (!path.endsWith(".txt") && !path.endsWith(".doc") && !path.endsWith(".docx") && !path.endsWith(".xlsx") && !path.endsWith(".xls")) {
                return "请选择txt、doc、docx、xlsx、xls格式的文件";
            }
        }

        if (fileType == Constants.IMG_TYPE) {
            if (!path.endsWith(".jpg") && !path.endsWith(".jpeg") && !path.endsWith(".png")) {
                return "jpg、jpeg、png格式的文件";
            }
        }

        if (fileType == Constants.VIDEO_TYPE) {
            if (!path.endsWith(".mp4")) {
                return "请选择mp4格式的文件";
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
