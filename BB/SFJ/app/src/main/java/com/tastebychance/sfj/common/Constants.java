package com.tastebychance.sfj.common;

import android.Manifest;

import com.tastebychance.sfj.util.ScreenUtils;

/**
 * Created by shenbinghong on 2018/1/30.
 */

public class Constants {
    /**
     * 是否是开发阶段
     */
    public static final boolean IS_DEVELOPING = true;
    /**
     * 是否是本地数据
     */
    public static final boolean IS_LOCALDATA = false;

    /**
     * 询问权限的请求码
     */
    public static final int PERMISSION_REQUEST_CODE = 0;
    /**
     * 所需的全部权限
     */
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.KILL_BACKGROUND_PROCESSES,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE
    };

    /**
     * 获取验证码倒计时
     */
    public static final long GETVERIFYCODE_MILLISINFUTURE = 60 * 1000;
    /**
     * 链接超时时间
     */
    public static final long OKHTTPS_CONNECT_TIMEOUT = 2 * 60 * 1000;
    /**
     * 读取超时时间
     */
    public static final long OKHTTPS_READ_TIMEOUT = 2 * 60 * 1000;
    /**
     * 写入超时时间
     */
    public static final long OKHTTPS_WRITE_TIMEOUT = 2 * 60 * 1000;

    /**
     * Log的标签
     */
    public static final String TAG = "RSHTAG";
    public static float scale = ScreenUtils.getScreenWidth() / 375;

    /**
     * 请求服务器成功
     */
    public static final int NET_RETURNCODE_SUC = 0;
    /**
     * 分页加载默认的第一页页码数
     */
    public static final int PAGE_START_INDEX = 1;

    /**
     * message的what
     */
    public static final int WHAT_REFRESH = 0X00001;
    public static final int WHAT_LOADMORE = 0X00002;
    public static final int WHAT_GETDATA = 0X00003;
    public static final int WHAT_GETDATA2 = 0X00005;
    public static final int WHAT_POSTDATA = 0X00004;
    public static final int WHAT_CHOSEDFILE = 200;
    public static final int WHAT_LOADING_SHOW = 2001;
    public static final int WHAT_LOADING_HIDE = 2002;

    /**
     * 文案
     */
    public static final String LOGIN_INVALID = "您的登录信息已失效，请重新登录";
    public static final String UPLOAD_SUCCES = "上传成功";
    public static final String COMMIT_SUCCES = "提交成功";
    public static final String MODIFY_SUCCES = "修改成功";
    public static final String GET_SUCCES = "获取成功";
    public static final String UPLOAD_FAIL = "上传失败，请重试";
    public static final String EMAIL_FORMAT_ERROR = "您输入的邮箱格式不正确";
    public static final String UPLOAD_FILETYPE_ERROR = "您选择的文件格式不正确，请选择文本文件";
    public static final String LOGIN_NULL = "请输入账号与密码";
    public static final String INPUT_HAS_EMPTY = "请填全信息";
    public static final String INPUT_PHONE = "请输入手机号码";
    public static final String PWD_NOTEQUAL = "两次输入的密码不一致";
    public static final String LOADING = "正在加载，请稍后";
    public static final String GET_VERIFYCODE_SUC = "验证码已发送";

    public static final String VALIDATOR_MOBILE = "手机格式不正确，请输入正确的手机号";
    public static final String NULL_RECEIVER = "请选择收件人";
    public static final String NULL_TITLE = "请输入标题";
    public static final String NULL_CONTENT = "请输入内容";
    public static final String NULL_PIC = "请选择要上传的图片";


    /**
     * 联系人
     */
    public static final String CONTACTS_NULL = "请选择一个联系人";
    public static final String KEY_CHOOCECONTACT = "KEY_CHOOCECONTACT";
    public static final int RET_CHOOCECONTACT = 0X00001;

    public static final String FROM_WRITEFILE = "FROM_WRITEFILE";//从文件处理过来的

    public static final String EXTRA_CONTENT = "content";


    //action
    /**
     * 退出登录的action
     */
    public static final String LOGOUT_ACTION = "LOGOUT_ACTION";

    //key
    /**
     * 账号
     */
    public static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    /**
     * 我的待办事件id
     */
    public static final String KEY_AFFAIRID = "KEY_AFFAIRID";
    /**
     * 审核类型
     */
    public static final String KEY_EXAMINETYPE = "KEY_EXAMINETYPE";
    /**
     * 登录状态
     */
    public static final String KEY_LOGINSTATE = "KEY_LOGINSTATE";
    /**
     * Token值
     */
    public static final String KEY_TOKEN = "KEY_TOKEN";
    /**
     * 用户信息
     */
    public static final String KEY_USERINFO = "KEY_USERINFO";
    public static final String KEY_FROM = "KEY_FROM";
    public static final String KEY_TOMYAPPLYDETAIL = "KEY_TOMYAPPLYDETAIL";

    /**
     * 登出
     */
    public static final String KEY_LOGOUT = "KEY_LOGOUT";

    public static final String POLICSSTATUS_ZGDY = "中共党员";
    public static final String POLICSSTATUS_ZGYBDY = "中共预备党员";
    public static final String POLICSSTATUS_GQTY = "共青团员";
    public static final String POLICSSTATUS_MGDY = "民革党员";
    public static final String POLICSSTATUS_MMMY = "民盟盟员";
    public static final String POLICSSTATUS_MJIANHY = "民建会员";
    public static final String POLICSSTATUS_MJINHY = "民进会员";
    public static final String POLICSSTATUS_NGDDY = "农工党党员";
    public static final String POLICSSTATUS_ZGDDY = "致公党党员";
    public static final String POLICSSTATUS_JSXSSY = "九三学社社员";
    public static final String POLICSSTATUS_TMMY = "台盟盟员";
    public static final String POLICSSTATUS_WDPRS = "无党派人士";
    public static final String POLICSSTATUS_QZ = "群众";

    /**
     * leaveType
     */
    public static final String LEAVETYPE_SHIJIA = "事假";
    public static final String LEAVETYPE_BINGJIA = "病假";
    public static final String LEAVETYPE_NIANXIUJIA = "年休假";
    public static final String LEAVETYPE_SHANGJIA = "丧假";
    public static final String LEAVETYPE_HUNJIA = "婚假";
    public static final String LEAVETYPE_CHANJIA = "产假";
    public static final String LEAVETYPE_TANQINJIA = "探亲假";

    public static final String APPLY_MYAPPLY = "我的申请";
    public static final String APPLY_MYJOB = "我的待办";
    public static final String FILEDEAL_RECEIVED = "收件箱";
    public static final String FILEDEAL_HASBEENSENT = "已发送";


    /**
     * 返回内容中一些key
     */
    public static final String PASS = "通过";
    public static final String REJECT = "驳回";
    public static final String PROCESS = "process";

    public static final int EXAMINE_REJECT = 3;
    public static final int EXAMINE_SUCCESS = 1;
}
