package com.tastebychance.sfj.util;

import com.tastebychance.sfj.common.Constants;

/**
 * 访问服务器的Url地址配置
 *
 * @author Administrator
 */
public class UrlManager {

    //外网
    private static final String REQUESTURL = "http://rsh.bouncebank.com/";
    /**
     * 服务器 图片存放地方
     */
    public static final String REQUESTIMGURL = "http://scimg.bouncebank.com/data/upload/";

    private static final String REQUESTURL_LOCAL = "http://192.168.1.170/rsh/public/index.php?s=";

    //测试
    private static final String REQUESTURL_TEST = "http://debugsfj.bouncebank.com";
    /**
     * 头像专用
     */
    private static final String REQUESTURL_HEAD_TEST = "http://testscapp.bouncebank.com/data/upload/";

    private static final String REQUESTURL_PRE = Constants.IS_DEVELOPING ? (Constants.IS_LOCALDATA ? REQUESTURL_LOCAL : REQUESTURL_TEST) : REQUESTURL;

    /**
     * 首页：广告
     */
    public static final String URL_HOME_BANNER = REQUESTURL_PRE + "/api/index/getSlideList";
    /**
     * 首页-动态公告接口
     */
    public static final String URL_HOME_NOTICE = REQUESTURL_PRE + "/api/index/notice";

    /**
     * 我的-个人信息
     */
    public static final String URL_MINE_PERSONALINFO = REQUESTURL_PRE + "/api/index/personalInfo";
    /**
     * 我的-个人信息-编辑接口
     */
    public static final String URL_MINE_PERSONALEDIT = REQUESTURL_PRE + "/api/index/personalEdit";
    /**
     * 我的-通讯录-列表
     */
    public static final String URL_MINE_CONTACT = REQUESTURL_PRE + "/api/index/contact";

    /**
     * 获取验证码
     */
    public static final String URL_GETVERIFYCODE = REQUESTURL_PRE + "/api/index/sendVerify";
    /**
     * 登录
     */
    public static final String URL_LOGIN = REQUESTURL_PRE + "/api/index/login";
    /**
     * 忘记密码接口
     */
    public static final String URL_FORGETPASS = REQUESTURL_PRE + "/api/index/forgetPass";

    /**
     * 申请审核-申请
     */
    public static final String URL_APPLY = REQUESTURL_PRE + "/api/index/applyHoliday";
    /**
     * 申请审核-我的申请-列表
     */
    public static final String URL_MYAPPLY = REQUESTURL_PRE + "/api/index/myApply";
    /**
     * 申请审核-我的申请-详情
     */
    public static final String URL_APPLYDETAIL = REQUESTURL_PRE + "/api/index/applyDetail";
    /**
     * 申请审核-我的待办-列表
     */
    public static final String URL_MYJOB = REQUESTURL_PRE + "/api/index/myJob";
    /**
     * 申请审核-我的待办-审核
     */
    public static final String URL_CHECK = REQUESTURL_PRE + "/api/index/check";
    /**
     * 申请审核-我的待办-详情-提示板
     */
    public static final String URL_GETTIPS = REQUESTURL_PRE + "/api/index/getTips";
    /**
     * 申请审核-我的待办-获取审核权限类型
     */
    public static final String URL_RABC = REQUESTURL_PRE + "/api/index/rbac";
    /**
     * 申请审核-我的待办-变更审批人
     */
    public static final String URL_USERJSON = REQUESTURL_PRE + "/api/index/userJson";
    /**
     * 申请审核-我的待办-变更审批人
     */
    public static final String URL_CHANGEPROCESS = REQUESTURL_PRE + "/api/index/changeProcess";

    /**
     * 文件管理-收件箱-列表
     */
    public static final String URL_RECEIVED = REQUESTURL_PRE + "/api/index/received";
    /**
     * 文件管理-已发送-列表
     */
    public static final String URL_HASBEENSENT = REQUESTURL_PRE + "/api/index/hasBeenSent";
    /**
     * 文件管理-图片上传
     */
    public static final String URL_IMAGEUPLOAD = REQUESTURL_PRE + "/api/index/imageUpload";
    /**
     * 文件处理-写文件
     */
    public static final String URL_WRITEFILE = REQUESTURL_PRE + "/api/index/writeFile";
    /**
     * 文件管理-读信件
     */
    public static final String URL_READMAIL = REQUESTURL_PRE + "/api/index/readMail";
    /**
     * 申请审核-校验电子密码
     */
    public static final String URL_CHECKSIGNPASS = REQUESTURL_PRE + "/api/index/checkSignPass";


}
