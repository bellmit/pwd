package rongshanghui.tastebychance.com.rongshanghui.mime.personalcenter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.mobileim.utility.IMPrefsTools;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.tencent.wxop.stat.StatConfig;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.AlibabaUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventTaobaoUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.RegionRes;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.AreaCodeActivity;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCodeInfo;
import rongshanghui.tastebychance.com.rongshanghui.login.bean.LoginRes;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.ManagerJJActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.personalcenter.bean.GetUserInfo;
import rongshanghui.tastebychance.com.rongshanghui.mime.personalcenter.bean.IndustryInfo;
import rongshanghui.tastebychance.com.rongshanghui.qqshare.BaseUiListener;
import rongshanghui.tastebychance.com.rongshanghui.register.AscriptionActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.BitmapUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.DialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.ImageCompress;
import rongshanghui.tastebychance.com.rongshanghui.util.ImageUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UiHelper;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.util.network.MyCallBack;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.RoundImageView;
import rongshanghui.tastebychance.com.rongshanghui.view.commonpopupwindow.CommonPopupWindow;
import rongshanghui.tastebychance.com.rongshanghui.wxshare.Util;

/**
 * 类描述：PersonalCenterActivity 个人中心
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/8 14:06
 * 修改人：
 * 修改时间：2017/12/8 14:06
 * 修改备注：
 *
 * @version 1.0
 */
public class PersonalCenterActivity extends MyBaseActivity {

    @BindView(R.id.mine_myfollow_underline)
    View mineMyfollowUnderline;
    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.includestatusbar_rl)
    RelativeLayout includestatusbarRl;
    @BindView(R.id.head_bottomline)
    View headBottomline;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.personalcenter_head_riv)
    RoundImageView personalcenterHeadRiv;
    @BindView(R.id.personalcenter_headindex_iv)
    ImageView personalcenterHeadindexIv;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.personalcenter_userinfotype_tv)
    TextView personalcenterUserinfotypeTv;
    @BindView(R.id.personalcenter_userinfo_share_tv)
    TextView personalcenterUserinfoShareTv;
    @BindView(R.id.view9)
    View view9;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.personalcenter_nicheng_edt)
    EditText personalcenterNichengEdt;
    @BindView(R.id.personalcenter_nicheng_copy_tv)
    TextView personalcenterNichengCopyTv;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.personalcenter_sex_tv)
    TextView personalcenterSexTv;
    @BindView(R.id.personalcenter_sex_change_tv)
    TextView personalcenterSexChangeTv;
    @BindView(R.id.personalcenter_sex_rl)
    RelativeLayout personalcenterSexRl;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.personalcenter_industry_tv)
    TextView personalcenterIndustryTv;
    @BindView(R.id.personalcenter_industry_change_tv)
    TextView personalcenterIndustryChangeTv;
    @BindView(R.id.personalcenter_industry_rl)
    RelativeLayout personalcenterIndustryRl;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.personalcenter_areacode_tv)
    TextView personalcenterAreacodeTv;
    @BindView(R.id.personalcenter_areacode_change_tv)
    TextView personalcenterAreacodeChangeTv;
    @BindView(R.id.personalcenter_areacode_rl)
    RelativeLayout personalcenterAreacodeRl;
    @BindView(R.id.textView12)
    TextView textView12;
    @BindView(R.id.personalcenter_phoneno_edt)
    EditText personalcenterPhonenoEdt;
    @BindView(R.id.personalcenter_phoneno_change_tv)
    TextView personalcenterPhonenoChangeTv;
    @BindView(R.id.textView13)
    TextView textView13;
    @BindView(R.id.personalcenter_email_edt)
    EditText personalcenterEmailEdt;
    @BindView(R.id.personalcenter_email_change_tv)
    TextView personalcenterEmailChangeTv;
    @BindView(R.id.textView14)
    TextView textView14;
    @BindView(R.id.personalcenter_city_tv)
    TextView personalcenterCityTv;
    @BindView(R.id.personalcenter_city_change_tv)
    TextView personalcenterCityChangeTv;
    @BindView(R.id.personalcenter_city_rl)
    RelativeLayout personalcenterCityRl;
    @BindView(R.id.textView15)
    TextView textView15;
    @BindView(R.id.personalcenter_companyname_tv)
    TextView personalcenterCompanynameTv;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.personalcenter_jiguan_tv)
    TextView personalcenterJiguanTv;
    @BindView(R.id.personalcenter_jiguan_rl)
    RelativeLayout personalcenterJiguanRl;
    @BindView(R.id.scrollView2)
    ScrollView scrollView2;
    @BindView(R.id.activity_personalcenter_rootlayout)
    LinearLayout activityPersonalcenterRootlayout;
    private String originSex;
    private String originNativePlace;
    private String originIndustry;
    private String originUserNickName;
    private String originCardMobile;
    private String originAreCode;
    private String originUserEmail;
    private String originCity;
    private String originAvatar;

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends Activity> extends Handler {
        private final WeakReference<T> mActivity;

        public MyHandler(T t) {
            this.mActivity = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(Message msg) {
            final T t = mActivity.get();
            if (null != t) {
                try {
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:
                            GetUserInfo getUserInfo = resInfo.getClass(GetUserInfo.class);
                            if (null != getUserInfo) {
                                PicassoUtils.getinstance().loadCircleHead(t, getUserInfo.getAvatar(), ((PersonalCenterActivity) t).personalcenterHeadRiv, 24);
                                ((PersonalCenterActivity) t).personalcenterUserinfotypeTv.setText("[" + getUserInfo.getSubject_type() + "]");
                                ((PersonalCenterActivity) t).personalcenterNichengEdt.setText(getUserInfo.getUser_nickname());
                                ((PersonalCenterActivity) t).personalcenterSexTv.setText(getUserInfo.getSex());
                                ((PersonalCenterActivity) t).personalcenterJiguanTv.setText(getUserInfo.getNative_place());
                                ((PersonalCenterActivity) t).personalcenterIndustryTv.setText(getUserInfo.getIndustry_str());
                                ((PersonalCenterActivity) t).personalcenterAreacodeTv.setText(getUserInfo.getArea_code());
                                ((PersonalCenterActivity) t).personalcenterPhonenoEdt.setText(getUserInfo.getCard_mobile());
                                ((PersonalCenterActivity) t).personalcenterEmailEdt.setText(getUserInfo.getUser_email());
                                ((PersonalCenterActivity) t).personalcenterCityTv.setText(getUserInfo.getCity());
                                ((PersonalCenterActivity) t).personalcenterCompanynameTv.setText(getUserInfo.getUnit_name());

                                ((PersonalCenterActivity) t).originSex = getUserInfo.getSex();
                                ((PersonalCenterActivity) t).originNativePlace = getUserInfo.getNative_place();
                                ((PersonalCenterActivity) t).originIndustry = getUserInfo.getIndustry_str();
                                ((PersonalCenterActivity) t).originUserNickName = getUserInfo.getUser_nickname();
                                ((PersonalCenterActivity) t).originCardMobile = getUserInfo.getCard_mobile();
                                ((PersonalCenterActivity) t).originAreCode = getUserInfo.getArea_code();
                                ((PersonalCenterActivity) t).originUserEmail = getUserInfo.getUser_email();
                                ((PersonalCenterActivity) t).originCity = getUserInfo.getCity();
                                ((PersonalCenterActivity) t).originAvatar = getUserInfo.getAvatar();

                                WeakReference<IndustryInfo> wf = new WeakReference<IndustryInfo>(new IndustryInfo());
                                wf.get().setId(getUserInfo.getIndustry());
                                wf.get().setName(getUserInfo.getIndustry_str());
                                ((PersonalCenterActivity) t).chosedIndustry = wf.get();

                                WeakReference<AreaCodeInfo> wf2 = new WeakReference<AreaCodeInfo>(new AreaCodeInfo());
                                wf2.get().setCode(getUserInfo.getArea_code());
                                ((PersonalCenterActivity) t).areaCodeInfo = wf2.get();

                                //分享链接
                                ((PersonalCenterActivity) t).showWebBean.setShareUrl(UrlManager.URL_WEB_PERSONALCARD + IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID));
                                ((PersonalCenterActivity) t).showWebBean.setShareImgUrl(getUserInfo.getAvatar());
                                ((PersonalCenterActivity) t).showWebBean.setShareTitle(getUserInfo.getUser_nickname());
                                ((PersonalCenterActivity) t).showWebBean.setShareDes(null);
                            }
                            break;
                        case Constants.WHAT_POSTDATA:
                            LoginRes.UserInfoBean userInfoBean = resInfo.getClass(LoginRes.UserInfoBean.class);
                            SystemUtil.getInstance().setUserInfo(userInfoBean);

                            SystemUtil.getInstance().taobaouser(2, new MyCallBack<AlibabaUser>() {
                                @Override
                                public void onLoadingBefore(Request request) {

                                }

                                @Override
                                public void onSuccess(Response response, AlibabaUser result) {
                                    t.finish();
                                }

                                @Override
                                public void onFailure(Request request, Exception e) {
                                    EventBusUtils.post(new EventTaobaoUser());
                                }

                                @Override
                                public void onError(Response response) {

                                }
                            });
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private ShowWebBean showWebBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_personal_center);
        ButterKnife.bind(this);
        setTitle();

        registerToWX();
        initQQShare();

        showWebBean = new ShowWebBean();
        getData();
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("信息");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("保存");
        }
    }

    /*--------------------------------上传头像-------------------------------*/
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static String PHOTO_FILE_NAME = "temp_photo.png";//头像名称
    private Uri uriImg;// 获取到了图片的Uri
    private File tempFile;
    private Uri imgUri = null;
    private PopupWindow popupWindow;

    public void getPopupWindow() {
        if (null != popupWindow) {
            //如果popupWindow正在显示，接下来隐藏
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                //产生背景变暗效果
                setBackgroundAlpha(0.3f);
                popupWindow.showAtLocation(this.findViewById(R.id.activity_personalcenter_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        } else {
            initPopupWindow();
        }
    }

    private void initPopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupWindow_view = inflater.inflate(R.layout.person_information_chooseheadportrait, null, false);
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow_view.setFocusable(true);
        popupWindow_view.setFocusableInTouchMode(true);

        TextView person_information_changeheadportrait_photo_tv = (TextView) popupWindow_view.findViewById(R.id.person_information_changeheadportrait_photo_tv);
        TextView person_information_changeheadportrait_fromalbum_tv = (TextView) popupWindow_view.findViewById(R.id.person_information_changeheadportrait_fromalbum_tv);
        TextView person_information_changeheadportrait_cancel_tv = (TextView) popupWindow_view.findViewById(R.id.person_information_changeheadportrait_cancel_tv);

        //拍照
        person_information_changeheadportrait_photo_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromCameraCapture();
                popupWindow.dismiss();
            }
        });

        //从相册中选择
        person_information_changeheadportrait_fromalbum_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
                popupWindow.dismiss();
            }
        });

        person_information_changeheadportrait_cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        popupWindow_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    return true;
                }
                return false;
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
                popupWindow = null;
            }
        });


        setBackgroundAlpha(0.3f);
        popupWindow.showAtLocation(this.findViewById(R.id.activity_personalcenter_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    // 启动手机相机拍摄照片作为头像
    /* 头像文件 */
    private final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    private void choseHeadImageFromCameraCapture() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            PHOTO_FILE_NAME = SystemUtil.getInstance().generateImgName();
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
            System.err.println();
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);

    }

    // 设置透明度
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        this.getWindow().setAttributes(layoutParams);
    }

    private RegionRes.DataBean chosedCity;
    private RegionRes.DataBean chosedJiguan;
    private IndustryInfo chosedIndustry;

    private AreaCodeInfo areaCodeInfo;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //区号选择
        if (requestCode == 4 && resultCode == Constants.GETAREACODE_RETURNCODE) {
            if (null != personalcenterAreacodeTv && data != null) {
                areaCodeInfo = (AreaCodeInfo) data.getSerializableExtra("selectedAreaData");
                if (areaCodeInfo != null) {
                    personalcenterAreacodeTv.setText(areaCodeInfo.getCode());
                }
            }
        }

        //归属地选择
        if (requestCode == 5 && resultCode == Constants.GETASCRIPTION_RETURNCODE) {
            if (null != personalcenterAreacodeTv && data != null) {
                chosedCity = (RegionRes.DataBean) data.getSerializableExtra("ascription");
                if (chosedCity != null) {
                    personalcenterCityTv.setText(chosedCity.getRegion_name());
                }
            }
        }

        //籍贯选择
        if (requestCode == 7 && resultCode == Constants.GETASCRIPTION_RETURNCODE) {
            if (null != personalcenterAreacodeTv && data != null) {
                chosedJiguan = (RegionRes.DataBean) data.getSerializableExtra("ascription");
                if (chosedJiguan != null) {
                    personalcenterJiguanTv.setText(chosedJiguan.getRegion_name());
                }
            }
        }

        //行业选择
        if (requestCode == 6 && resultCode == Constants.INDUSTRY_CHOOSE_RETURNCODE) {
            chosedIndustry = (IndustryInfo) data.getSerializableExtra("selectedIndustryData");
            if (null != data && personalcenterIndustryTv != null) {
                personalcenterIndustryTv.setText(chosedIndustry.getName());
            }
        }

        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                uriImg = uri;

              /*  Picasso.with(PersonalInformationActivity.this)
                        .load(uriImg)
                        .resize(48,48)
                        .centerCrop()
                        .into(person_information_headportrait_riv);*/

                PicassoUtils.getinstance().loadCircleImageByUri(PersonalCenterActivity.this, uriImg, personalcenterHeadRiv, 31);

                if (StringUtil.isNotEmpty(uriImg + "")) {
                    uploadHeadPortrait(uriImg);
                }
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA && resultCode == -1) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                if (tempFile.exists()) {
                    uriImg = Uri.fromFile(tempFile);

/*                    Picasso.with(PersonalInformationActivity.this)
                            .load(uriImg)
                            .resize(48,48)
                            .centerCrop()
                            .into(person_information_headportrait_riv);*/

                    PicassoUtils.getinstance().loadCircleImageByUri(PersonalCenterActivity.this, uriImg, personalcenterHeadRiv, 31);

                    if (StringUtil.isNotEmpty(uriImg + "")) {

                        try {
                            Uri capturedImage = Uri.parse(
                                    MediaStore.Images.Media.insertImage(
                                            getContentResolver(),
                                            tempFile.getAbsolutePath(), null, null));

                            uploadHeadPortrait(capturedImage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (tempFile != null && tempFile.exists()) {
                        tempFile.delete();
                    }
                    // "未执行拍照操作!");
                    personalcenterHeadRiv.setImageDrawable(getResources().getDrawable(R.drawable.person_defaultheadportrait));
                }
            } else {
                Toast.makeText(PersonalCenterActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 上传图片
     */
    private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private String filePath = "";

    private void uploadHeadPortrait(Uri imgUri) {
        compressPic(imgUri);
    }

    private void compressPic(Uri uri) {
        ImageCompress compress = new ImageCompress();
        ImageCompress.CompressOptions options = new ImageCompress.CompressOptions();
//        options.uri = Uri.fromFile(new File(sourcePath));
        options.uri = uri;
        options.maxWidth = Constants.RESIZEBITMAP_WIDTH;
        options.maxHeight = Constants.RESIZEBITMAP_HEIGHT;
        Bitmap bitmap = compress.compressFromUri(PersonalCenterActivity.this, options);
        saveCompressedPic(bitmap, uri);
    }

    private void saveCompressedPic(Bitmap bitmap, Uri uri) {
        boolean isSaved = BitmapUtils.setBitmapToFile(bitmap, Constants.COMPRESSED_PIC_PATH);
        if (isSaved) {
            Uri uri1 = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "compress.jpg"));
            uploadPic(uri1);
        } else {
            uploadPic(uri);
        }
    }

    private void uploadPic(Uri imgUri) {

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_personalcenter_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }


        final String filepath;
        if (imgUri.toString().startsWith("file")) {
            filepath = imgUri.getPath();
        } else {
            filepath = UiHelper.getFilePathFromContentUri(imgUri, getContentResolver());
        }

        //采用okhttp3来进行网络请求
        String url = UrlManager.URL_IMAGEUPLOAD;
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Constants.OKHTTPS_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(Constants.OKHTTPS_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(Constants.OKHTTPS_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("title", "Square Logo")
                .addFormDataPart("file", "logo-square.png", RequestBody.create(MEDIA_TYPE_PNG, new File(filepath))).build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                dialogCancel();

                Message msg = new Message();
                msg.what = INFO_WHAT;
                msg.obj = Constants.UPLOAD_FAIL;
                myHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialogCancel();
                String str = response.body().string();
                Log.i(Constants.TAG, str);

                JSONObject jsonObject = JSONObject.parseObject(str);

                filePath = (String) jsonObject.get("data");

                String url = (String) jsonObject.get("data");
                if (null != url) {
                    url = url.replaceAll("/upload", "").replaceAll("/data", "");
                    /*if (null != userInfo){
                        WeakReference<UserInfo> wf = new WeakReference<UserInfo>(userInfo);
                        wf.get().setAvatar(url);
                        SharedPreferencesUtils.saveToShared(MyApplication.getContext(),"userInfo",wf.get());
                    }*/
                }

                final String finalUrl = url;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = Constants.UPLOAD_SUCCES;
                        myHandler.sendMessage(msg);

                        /*if (null != registerUsercardclipIv){
                            if (null != registerUsercardclipIv){

                                imgPath = finalUrl;*/

//                                String imgUrl;
//                                if (finalUrl != null && finalUrl.contains("http:") || finalUrl.contains("https")){
//                                    imgUrl = finalUrl;
//                                }else{
//                                    imgUrl = (Constants.IS_DEVELOPING? UrlManager.REQUESTURL_HEAD_TEST : UrlManager.REQUESTIMGURL) + finalUrl;
//                                }
//
//                                PicassoUtils.getinstance().loadNormalByPath(RegisterJGActivity.this,imgUrl,registerUsercardclipIv);
                          /*  }
                        }*/
                    }
                });
            }

        });
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    private static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
    /*--------------------------------上传头像-------------------------------*/

    //
    private boolean canSave() {
        if (StringUtil.isEmpty(personalcenterCityTv.getText().toString())) {
            ToastUtils.showOneToast(PersonalCenterActivity.this, "所在城市不能为空");
            return false;
        }
        return true;
    }

    private boolean hasAvatarChanged = false;
    private boolean hasNickNameChanged = false;
    private boolean hasSexChanged = false;
    private boolean hasJiGuanChanged = false;
    private boolean hasIndustryChanged = false;
    private boolean hasAreaCodeChanged = false;
    private boolean hasPhoneNoChanged = false;
    private boolean hasEmailChanged = false;
    private boolean hasCityChanged = false;

    /**
     * 检查所有控件是否有修改过
     */
    private void checkAllHasChanged() {
        hasSexChanged = SystemUtil.getInstance().hasChanged(personalcenterSexTv.getText().toString(), originSex);
//        hasJiGuanChanged = SystemUtil.getInstance().hasChanged(personalcenterJiguanTv.getText().toString(), originNativePlace);
        if (null != chosedIndustry && StringUtil.isNotEmpty(chosedIndustry.getName())) {
            hasIndustryChanged = SystemUtil.getInstance().hasChanged(chosedIndustry.getName(), originIndustry);
        }
        hasNickNameChanged = SystemUtil.getInstance().hasChanged(personalcenterNichengEdt.getText().toString(), originUserNickName);
        hasPhoneNoChanged = SystemUtil.getInstance().hasChanged(personalcenterPhonenoEdt.getText().toString(), originCardMobile);
        if (null != areaCodeInfo && StringUtil.isNotEmpty(areaCodeInfo.getCode())) {
            hasAreaCodeChanged = SystemUtil.getInstance().hasChanged(areaCodeInfo.getCode(), originAreCode);
        }
        hasEmailChanged = SystemUtil.getInstance().hasChanged(personalcenterEmailEdt.getText().toString(), originUserEmail);
        hasCityChanged = SystemUtil.getInstance().hasChanged(personalcenterCityTv.getText().toString(), originCity);

        if (StringUtil.isNotEmpty(filePath)) {
            hasAvatarChanged = SystemUtil.getInstance().hasChanged(filePath, originAvatar);
        }
    }

    /**
     * 保存
     * token 	是 	string 	token 值
     * sex 	否 	int 	1 男 2 女
     * native_place 	否 	string 	籍贯
     * industry 	否 	int 	行业id
     * user_nickname 	否 	string 	昵称
     * card_mobile 	否 	string 	电话
     * area_code 	否 	string 	区号
     * user_email 	否 	string 	邮箱
     * city 	是 	string 	城市
     * avatar 	否 	string 	头像地址
     */
    private void save() {
        checkAllHasChanged();

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_personalcenter_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_MINE_USERINFOPOST;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", token);

        String sex = personalcenterSexTv.getText().toString();
        if (StringUtil.isNotEmpty(sex) && hasSexChanged) {
            builder.add("sex", sex.equals("女") ? "2" : "1");//1 男 2 女
        }

        String jiguan = personalcenterJiguanTv.getText().toString();
        if (StringUtil.isNotEmpty(jiguan) && hasJiGuanChanged) {
            builder.add("native_place", jiguan);
        }

        if (StringUtil.isNotEmpty(personalcenterIndustryTv.getText().toString())) {
            builder.add("industry", chosedIndustry.getId() + "");//行业id
        }
        String nicheng = personalcenterNichengEdt.getText().toString();
        if (StringUtil.isNotEmpty(nicheng) && hasNickNameChanged) {
            builder.add("user_nickname", nicheng);
        }
        String phoneno = personalcenterPhonenoEdt.getText().toString();
        if (StringUtil.isNotEmpty(phoneno) && hasPhoneNoChanged) {
            builder.add("card_mobile", phoneno);
        }
        String areaCode = areaCodeInfo.getCode();
        if (StringUtil.isNotEmpty(areaCode) && hasAreaCodeChanged) {
            builder.add("area_code", areaCode);
        }
        String userEmail = personalcenterEmailEdt.getText().toString();
        if (StringUtil.isNotEmpty(userEmail) && hasEmailChanged) {
            builder.add("user_email", userEmail);
        }
        String city = personalcenterCityTv.getText().toString();
        if (StringUtil.isNotEmpty(city) && hasCityChanged) {
            builder.add("city", city);
        }
        String avatar = filePath;
        if (StringUtil.isNotEmpty(avatar) && hasAvatarChanged) {
            builder.add("avatar", avatar);
        }

        RequestBody formBody = builder.build();
        if (null == formBody) {
            return;
        }
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                dialogCancel();
                ToastUtils.showOneToast(PersonalCenterActivity.this, "保存失败，请重试");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialogCancel();
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = Constants.WHAT_POSTDATA;
                                msg.obj = resInfo;
                                handler.sendMessage(msg);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    /**
     * 获取信息
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_personalcenter_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_MINE_ME;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token).build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                dialogCancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialogCancel();
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {
                        /**Looper.prepare();
                         UiHelper.ShowOneToast(getApplicationContext(),"数据请求成功");
                         Looper.loop();*/

//                        goodsReceiptInfos = resInfo.getDataList(GoodsReceiptInfo.class);

                        //AfterPayInfo afterPayInfo = JSONObject.parseObject(resInfo.getData().toString(),AfterPayInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                message.what = Constants.WHAT_GETDATA;
                                message.obj = resInfo;
                                handler.sendMessage(message);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    /**
     * 询问未保存是否退出
     */
    private void askNoSaveToExit() {
        checkAllHasChanged();

        if (hasAvatarChanged ||
                hasNickNameChanged ||
                hasSexChanged ||
                hasJiGuanChanged ||
                hasIndustryChanged ||
                hasAreaCodeChanged ||
                hasPhoneNoChanged ||
                hasEmailChanged ||
                hasCityChanged) {
            DialogUtils.getInstance().AlertMessage(PersonalCenterActivity.this, "温馨提示", "是否直接返回?您编辑的信息会丢失哦!", "返回", "确定", null, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PersonalCenterActivity.this.finish();
                }
            });
        } else {
            PersonalCenterActivity.this.finish();
        }

    }

    /**
     * 性别下拉框
     */
    private CommonPopupWindow sexPopupWindow;

    private void showSexPopupWindow() {
        sexPopupWindow = new CommonPopupWindow.Builder(PersonalCenterActivity.this)
                //设置Popupwindow布局
                .setView(R.layout.item_personalcenter_sexpopupwindow)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色,取值范围0.0f-1.0f 值越小越暗 1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {

                    @Override
                    public void getChildView(View view, int layoutResId) {
                        TextView male = (TextView) view.findViewById(R.id.personalcenter_sexpopupwindow_male_tv);
                        TextView female = (TextView) view.findViewById(R.id.personalcenter_sexpopupwindow_female_tv);
                        male.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                personalcenterSexTv.setText("男");
                                if (sexPopupWindow != null) {
                                    sexPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                            }
                        });

                        female.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                personalcenterSexTv.setText("女");
                                if (sexPopupWindow != null) {
                                    sexPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                            }
                        });
                    }
                })
                //设置外部是否可以点击，默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        setBackgroundAlpha(0.3f);
        sexPopupWindow.showAtLocation(this.findViewById(R.id.activity_personalcenter_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /*-------------------------------------------------分享---------------------------------------------------------*/
    //IWXAPI是第三方app和微信通信的openapi接口
    private IWXAPI iwxapi;

    private void registerToWX() {
        //通过WXAPIFactory工厂，获取IWXAPI的实例
        iwxapi = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, true);
        //将应用的appId注册到微信
        iwxapi.registerApp(Constants.WX_APP_ID);
    }

    private Tencent mTencent;

    private void initQQShare() {
        StatConfig.setDebugEnable(true);
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(Constants.QQ_APP_ID, this.getApplicationContext());
        // 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
        // 初始化视图
//        initViews();
    }

    private PopupWindow sharePopupWindow;

    private void getSharePopupWindow() {
        if (null != sharePopupWindow) {
            //如果sharePopupWindow正在显示，接下来隐藏
            if (sharePopupWindow.isShowing()) {
                sharePopupWindow.dismiss();
            } else {
                //产生背景变暗效果
                setBackgroundAlpha(0.3f);
                sharePopupWindow.showAtLocation(this.findViewById(R.id.activity_personalcenter_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            }
        } else {
            initSharePopupWindow();
        }
    }

    private void initSharePopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupWindow_view = inflater.inflate(R.layout.web_detail_share, null, false);
//        sharePopupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sharePopupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, 180);
        sharePopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        sharePopupWindow.setBackgroundDrawable(new PaintDrawable());
        sharePopupWindow.setOutsideTouchable(true);
        sharePopupWindow.setFocusable(true);
        popupWindow_view.setFocusable(true);
        popupWindow_view.setFocusableInTouchMode(true);

        ImageView wxShareIv = (ImageView) popupWindow_view.findViewById(R.id.web_detail_share_wx_iv);
        ImageView wxpyqShareIv = (ImageView) popupWindow_view.findViewById(R.id.web_detail_share_wxpyq_iv);
        ImageView qqShareIv = (ImageView) popupWindow_view.findViewById(R.id.web_detail_share_qq_iv);

        wxShareIv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                wechatShare(true);
                if (null != sharePopupWindow && sharePopupWindow.isShowing()) {
                    sharePopupWindow.dismiss();
                }
            }
        });

        wxpyqShareIv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                wechatShare(false);
                if (null != sharePopupWindow && sharePopupWindow.isShowing()) {
                    sharePopupWindow.dismiss();
                }
            }
        });

        qqShareIv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                qqShare();
                if (null != sharePopupWindow && sharePopupWindow.isShowing()) {
                    sharePopupWindow.dismiss();
                }
            }
        });

        popupWindow_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    sharePopupWindow.dismiss();
                    sharePopupWindow = null;
                    return true;
                }
                return false;
            }
        });

        sharePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
                sharePopupWindow = null;
            }
        });


        setBackgroundAlpha(0.3f);
        sharePopupWindow.showAtLocation(this.findViewById(R.id.activity_personalcenter_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void showPopUp(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);

        sharePopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] - sharePopupWindow.getHeight());
    }

    private static final int THUMB_SIZE = 150;

    private void wechatShare(final boolean isWx) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bm = null;
                try {
                    URL iconUrl = new URL(showWebBean.getShareImgUrl());
                    URLConnection conn = iconUrl.openConnection();
                    HttpURLConnection http = (HttpURLConnection) conn;

                    int length = http.getContentLength();

                    conn.connect();
                    // 获得图像的字符流
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is, length);
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();// 关闭流
                } catch (Exception e) {
                    e.printStackTrace();
                }

                wechatShare(bm, isWx);
            }
        }).start();
    }

    private void wechatShare(Bitmap bmp, boolean isWx) {
        //初始化一个WXWebpageObject对象，填写url
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = StringUtil.isNotEmpty(showWebBean.getShareUrl()) ? showWebBean.getShareUrl() : showWebBean.getUrl();

        //用WXWebpageObject对象实例化一个WXMediaMessage对象，填写标题、描述
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = StringUtil.isNotEmpty(showWebBean.getShareTitle()) ? showWebBean.getShareTitle() : showWebBean.getTitle();
        if (StringUtil.isNotEmpty(showWebBean.getShareDes())) {
            msg.description = showWebBean.getShareDes();
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;  //图片宽高都为原来的二分之一，即图片为原来的四分一 //以上代码可以优化内存溢出，但它只是改变图片大小，并不能彻底解决内存溢出。

        /*if (bmp == null){*/
        bmp = ImageUtils.getInstance().readBitmap(getApplicationContext(), R.drawable.login_logo);
        /*}*/
        if (null != bmp) {
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
            bmp.recycle();
            msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        }

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");//transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = isWx ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;

        //调用api接口发送数据到微信
        iwxapi.sendReq(req);
    }

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void qqShare() {
        if (showWebBean == null) {
            return;
        }
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, StringUtil.isNotEmpty(showWebBean.getShareTitle()) ? showWebBean.getShareTitle() : showWebBean.getTitle());
        if (StringUtil.isNotEmpty(showWebBean.getShareDes())) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, showWebBean.getShareDes());
        }
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, StringUtil.isNotEmpty(showWebBean.getShareUrl()) ? showWebBean.getShareUrl() : showWebBean.getUrl());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, showWebBean.getShareImgUrl());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "融商汇");
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
        mTencent.shareToQQ(PersonalCenterActivity.this, params, new BaseUiListener());
    }
    /*-------------------------------------------------分享---------------------------------------------------------*/


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void taobaoUser(@NonNull EventTaobaoUser eventTaobaoUser) {
        SystemUtil.getInstance().taobaouser(1, new MyCallBack<AlibabaUser>() {
            @Override
            public void onLoadingBefore(Request request) {

            }

            @Override
            public void onSuccess(Response response, AlibabaUser result) {
                PersonalCenterActivity.this.finish();
            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onError(Response response) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBusUtils.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    @Override
    public void finish() {
        hasAvatarChanged = false;
        hasNickNameChanged = false;
        hasSexChanged = false;
        hasJiGuanChanged = false;
        hasIndustryChanged = false;
        hasAreaCodeChanged = false;
        hasPhoneNoChanged = false;
        hasEmailChanged = false;
        hasCityChanged = false;
        super.finish();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //双击退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            askNoSaveToExit();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.personalcenter_head_riv, R.id.textView8,
            R.id.personalcenter_userinfo_share_tv, R.id.personalcenter_nicheng_copy_tv, R.id.personalcenter_sex_rl,
            R.id.personalcenter_sex_change_tv, R.id.personalcenter_industry_rl, R.id.personalcenter_industry_change_tv,
            R.id.personalcenter_areacode_rl, R.id.personalcenter_areacode_change_tv, R.id.personalcenter_phoneno_change_tv, R.id.personalcenter_email_change_tv,
            R.id.personalcenter_city_rl, R.id.personalcenter_city_change_tv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                askNoSaveToExit();
                break;
            case R.id.head_right_tv:
                if (canSave()) {
                    save();
                }
                break;
            case R.id.personalcenter_head_riv:
            case R.id.textView8:
                getPopupWindow();
                break;
            case R.id.personalcenter_userinfo_share_tv:
                getSharePopupWindow();
                break;
            case R.id.personalcenter_nicheng_copy_tv:
                personalcenterNichengEdt.requestFocus();
                SoftInputUtil.showSoftInput(PersonalCenterActivity.this, personalcenterNichengEdt);
                break;
            case R.id.personalcenter_sex_rl:
            case R.id.personalcenter_sex_change_tv:
                showSexPopupWindow();
                break;
            case R.id.personalcenter_industry_rl:
            case R.id.personalcenter_industry_change_tv:
                intent = new Intent(PersonalCenterActivity.this, IndustryChooseActivity.class);
                startActivityForResult(intent, 6);
                break;
            case R.id.personalcenter_areacode_rl:
            case R.id.personalcenter_areacode_change_tv:
                intent = new Intent(PersonalCenterActivity.this, AreaCodeActivity.class);
                startActivityForResult(intent, 4);
                break;
            case R.id.personalcenter_phoneno_change_tv:
                personalcenterPhonenoEdt.requestFocus();
                SoftInputUtil.showSoftInput(PersonalCenterActivity.this, personalcenterPhonenoEdt);
                break;
            case R.id.personalcenter_email_change_tv:
                personalcenterEmailEdt.requestFocus();
                SoftInputUtil.showSoftInput(PersonalCenterActivity.this, personalcenterEmailEdt);
                break;
            case R.id.personalcenter_city_rl:
            case R.id.personalcenter_city_change_tv:
                intent = new Intent(PersonalCenterActivity.this, AscriptionActivity.class);
                startActivityForResult(intent, 5);
                break;
        }
    }
}
