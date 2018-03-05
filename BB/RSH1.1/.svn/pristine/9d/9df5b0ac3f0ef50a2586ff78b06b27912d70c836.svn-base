package rongshanghui.tastebychance.com.rongshanghui.util;

import android.Manifest;
import android.os.Environment;

import java.io.File;

import rongshanghui.tastebychance.com.rongshanghui.R;

public class Constants {
    public static final long GETVERIFYCODE_MILLISINFUTURE = 60 * 1000;//获取验证码倒计时
    public static final long OKHTTPS_CONNECT_TIMEOUT = 2 * 60 * 1000;//链接超时时间
    public static final long OKHTTPS_READ_TIMEOUT = 2 * 60 * 1000;//读取超时时间
    public static final long OKHTTPS_WRITE_TIMEOUT = 2 * 60 * 1000;//写入超时时间

    public static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.VIBRATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.GET_TASKS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.VIBRATE
//            ,
//            Manifest.permission.READ_LOGS

    };
    public static final boolean IS_LOCALDATA = false;//是否是本地数据
    public static final boolean IS_DEVELOPING = true;//是否是开发阶段

    public static final boolean IS_LIANTIAO = false;//是否是联调模式

    public static final long LOGO_SHOWTIME = IS_DEVELOPING ? 1 * 100L : 2 * 1000L;//logo 展示时间
    public static final String TAG = "RSHTAG";//Log的标签
    public static final int PAGE_START_INDEX = 1;//分页加载默认的第一页页码数
    public static final int RESULT_SUCCESS = 0;//请求服务器成功

    public static float scale = ScreenUtils.getScreenWidth() / 375;

    //temp pic
    public static final String PIC1 = "http://www.n63.com/zutu/n63/?N=X2hiJTI2MS4lNUMlNURZMVklNUIlNUQtWTAxJTJBJTJCLSUyODAlNUIlMjklMkMlMjklNUMlNUUwJTVDJTVFLS0xJTJBJTVDJTVCJTJGJTI3JTVDJTVCJTJG&v=.jpg";
    public static final String PIC2 = "http://www.n63.com/zutu/n63/?N=X2hiJTI2LkYlNUJiTiUyQjAlMkYlMkYlMkElMkYuJTI4JTJDJTI5JTI3LjExJTI5&v=.jpg";

    public static final int GETAREACODE_RETURNCODE = 0x10000;//获取区号
    public static final int GETASCRIPTION_RETURNCODE = 0x10010;//获取归属地
    public static final int INDUSTRY_CHOOSE_RETURNCODE = 0x10011;//选择行业
    public static final int USERNAME_CHANGE_RETURNCODE = 0x11000;//更新名称返回码
    public static final int GETAREACODE_PHONENO_RETURNCODE = 0x11100;//更新名称返回码
    public static final int ADDCATEGORY_RETURNCODE = 0x11110;//指南管理添加新的类别返回码
    public static final int GETCATEGORY_RETURNCODE = 0x11111;//选择指南管理类别返回码
    public static final int FILE_SELECT_CODE = 10001;
    /**
     * 调用文件选择软件来选择文件
     **/


    //empty
    public static final int EMPTY_NULL_PIC = R.drawable.nothing;
    public static final String EMPTY_NULL_STR = "";

    //message的what
    public static final int WHAT_REFRESH = 0X00001;
    public static final int WHAT_LOADMORE = 0X00002;
    public static final int WHAT_GETDATA = 0X00003;
    public static final int WHAT_POSTDATA = 0X00004;
    public static final int WHAT_CHOSEDFILE = 200;
    public static final int DOMESTIC_WHAT = 2001;

    //	public static final String REGISTER_SUCCESS_ACTION = "REGISTER_SUCCESS_ACTION";//注册成功的action
    public static final String CLOSE_MEMBERLIST_ACTION = "CLOSE_MEMBERLIST_ACTION";//关闭会员管理-会员列表页面的action
    public static final String CHANGEACCOUNT_ACTION = "CHANGEACCOUNT_ACTION";//更换账户成功的action
    public static final String LOGOUT_ACTION = "LOGOUT_ACTION";//退出登录的action
    public static final String TOSYSNEWSTAB_ACTION = "TOSYSNEWSTAB_ACTION";//切换到系统消息tab的action

    public static final int GETREGISTERTYPE_RETURNCODE = 0x11000;//选择注册类型

    //跳转到哪个界面
    public static final String DO_NOTHING = "DO_NOTHING";//取消
    public static final String TO_CARDCLIP = "TO_CARDCLIP";//名片夹
    public static final String TO_NEWS = "TO_NEWS";//消息
    public static final String TO_SETTING = "TO_SETTING";
    public static final String TO_PERSONCENTER = "TO_PERSONCENTER";
    public static final String TO_MYFOLLOW = "TO_MYFOLLOW";
    public static final String TO_MYCOLLOECTION = "TO_MYCOLLOECTION";
    public static final String TO_OPINIONFEEDBACK = "TO_OPINIONFEEDBACK";
    public static final String TO_FILECACHE = "TO_FILECACHE";
    public static final String TO_SHMANAGER = "TO_SHMANAGER";
    public static final String TO_QYMANAGER = "TO_QYMANAGER";
    public static final String TO_JGMANAGER = "TO_JGMANAGER";
    public static final String TO_BMMANAGER = "TO_BMMANAGER";
    public static final String TO_ZJMANAGER = "TO_ZJMANAGER";
    public static final String TO_XXMANAGER = "TO_XXMANAGER";
    public static final String TO_YYMANAGER = "TO_YYMANAGER";
    public static final String TO_QTMANAGER = "TO_QTMANAGER";
    public static final String TO_HDLY_CKHF = "TO_HDLY_CKHF";
    public static final String TO_HDLY_LY = "TO_HDLY_LY";
    public static final String FROMYJZQMANAGERTOYJZQ = "FROMYJZQMANAGERTOYJZQ";

    public static final String CLICK_COLLECT = "clickCollect";//收藏
    public static final String CLICK_PRAISE = "clickPraise";//点赞

    //注册入口类型
    public static final String REGISTERENTRANCETYPE_SH = "REGISTERENTRANCETYPE_SH";//商会、企业
    public static final String REGISTERENTRANCETYPE_QY = "REGISTERENTRANCETYPE_QY";//商会、企业
    public static final String REGISTERENTRANCETYPE_JG = "REGISTERENTRANCETYPE_JG";//机构
    public static final String REGISTERENTRANCETYPE_XX = "REGISTERENTRANCETYPE_XX";//医院、学校、其他
    public static final String REGISTERENTRANCETYPE_YY = "REGISTERENTRANCETYPE_YY";//医院、学校、其他
    public static final String REGISTERENTRANCETYPE_QT = "REGISTERENTRANCETYPE_QT";//医院、学校、其他
    public static final String REGISTERENTRANCETYPE_BM = "REGISTERENTRANCETYPE_BM";//部门、镇街
    public static final String REGISTERENTRANCETYPE_ZJ = "REGISTERENTRANCETYPE_ZJ";//部门、镇街

    public static final String JG_TO_GRXYD = "JG_TO_GRXYD";//机构管理-跳转到个人信用贷链接
    public static final String JG_TO_XYK = "JG_TO_XYK";//机构管理-跳转到信用卡链接
    public static final String JG_TO_SHCY = "JG_TO_SHCY";//商会管理-会员管理-跳转到商会成员列表
    public static final String JG_TO_DSH = "JG_TO_DSH";//商会管理-会员管理-跳转到-待审核成员列表

    public static final int SHTSH_RHSQ_NORELATION = -1;//商会通-商会-入会申请状态-没有关系 //-1:一点关系都没有 0:待审核,1:审核通过,3.审核不通过,(只有商会才有该字段)
    public static final int SHTSH_RHSQ_WAITAUDITE = 0;//商会通-商会-入会申请状态-待审核 //-1:一点关系都没有 0:待审核,1:审核通过,3.审核不通过,(只有商会才有该字段)
    public static final int SHTSH_RHSQ_MEMBER = 1;//商会通-商会-入会申请状态-审核通过 //-1:一点关系都没有 0:待审核,1:审核通过,3.审核不通过,(只有商会才有该字段)
    public static final int SHTSH_RHSQ_REJECT = 3;//商会通-商会-入会申请状态-审核不通过 //-1:一点关系都没有 0:待审核,1:审核通过,3.审核不通过,(只有商会才有该字段)
    public static final int SHTSH_RHSQ_REMOVE = 2;//商会通-商会-入会过后被移除的状态

    //0:平台（后台）;1:融资项目;2:放贷信息;3:政策;4:资讯;5:指南;6:招商秀;7:商会秀;8:下载材料;9:企业风采,10:上报,11:意见征集
    public static final String PULISHTYPE_PT = "平台";
    public static final String PULISHTYPE_RZXM = "融资项目";
    public static final String PULISHTYPE_FDXX = "放贷信息";
    public static final String PULISHTYPE_ZC = "政策";
    public static final String PULISHTYPE_ZX = "资讯";
    public static final String PULISHTYPE_ZN = "指南";
    public static final String PULISHTYPE_ZSX = "招商秀";
    public static final String PULISHTYPE_SHX = "商会秀";
    public static final String PULISHTYPE_XZCL = "下载材料";
    public static final String PULISHTYPE_QYFC = "企业风采";
    public static final String PULISHTYPE_SB = "上报";
    public static final String PULISHTYPE_YJZJ = "意见征集";

    public static final String PUBLISHCATE_PT = "平台";
    public static final String PUBLISHCATE_SH = "商会";
    public static final String PUBLISHCATE_QY = "企业";
    public static final String PUBLISHCATE_JG = "机构";
    public static final String PUBLISHCATE_BM = "部门";
    public static final String PUBLISHCATE_GR = "个人";
    public static final String PUBLISHCATE_ZJ = "镇街";
    public static final String PUBLISHCATE_XX = "学校";
    public static final String PUBLISHCATE_YY = "医院";
    public static final String PUBLISHCATE_QT = "其他";

    public static final String REGISTER_FROM = "modifyRegister";//来自修改注册

    //注册类型
    public static final String REGISTERTYPE_BANK = "REGISTERTYPE_BANK";//银行
    public static final String REGISTERTYPE_NEGOTIABLESECURITIES = "REGISTERTYPE_NEGOTIABLESECURITIES";//证券
    public static final String REGISTERTYPE_INSURANCE = "REGISTERTYPE_INSURANCE";//保险
    public static final String REGISTERTYPE_TRUST = "REGISTERTYPE_TRUST";//信托
    public static final String REGISTERTYPE_FUND = "REGISTERTYPE_FUND";//基金


    // item view的类型总数。
    public static final int VIEW_TYPE_COUNT = 2;
    public static final String DATA = "data";
    public static final String TYPE = "type";
    public static final int GROUP = -2;
    public static final int ITEM = -3;

    //key
    public static final String KEY_ISACCOUNTCHANGED = "isAccountChanged";//账号变更
    public static final String KEY_SYSNEWSUNREADNUM = "KEY_SYSNEWSUNREADNUM";//未读系统消息数

    //文案
    public static final String LOGIN_INVALID = "您的登录信息已失效，请重新登录";
    public static final String UPLOAD_SUCCES = "上传成功";
    public static final String COMMIT_SUCCES = "提交成功";
    public static final String UPLOAD_FAIL = "上传失败，请重试";
    public static final String WARNING_FORMAT_EMAIL = "您输入的邮箱格式不正确";
    public static final String UPLOAD_FILETYPE_ERROR = "您选择的文件格式不正确，请选择文本文件";
    public static final String NODETAIL = "无置顶信息";

    //阿里云旺
    public static String APP_KEY = "24676516";//测试 23015524
    public static final String USER_ID = "testpro1";
//	public static final String PASSWORD = "taobao1234";
//	public final static String STORE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/openim/images/";

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_APPKEY = "appkey";
    //阿里云旺

    //微信
    public static final String WX_APP_ID = "wxc860f2fc1ab68d18";

    public static class ShowMsgActivity {
        public static final String STitle = "showmsg_title";
        public static final String SMessage = "showmsg_message";
        public static final String BAThumbData = "showmsg_thumb_data";
    }

    //微信
    //qq
    public static final String QQ_APP_ID = "1106538673";
    public static final String QQ_APP_KEY = "q2ZStogQwmXayw3a";
    //qq


    public static final int FILE_TYPE = 0;
    public static final int IMG_TYPE = 1;
    public static final int VIDEO_TYPE = 2;


    //压缩图片调整大小
    public static final int RESIZEBITMAP_WIDTH = 480;
    public static final int RESIZEBITMAP_HEIGHT = 800;
    //压缩后图片存储路径
    public static final String COMPRESSED_PIC_PATH = Environment.getExternalStorageDirectory() + File.separator + "RSHCOMPRESSPIC" + File.separator + "compressed.png";
}
