package rongshanghui.tastebychance.com.rongshanghui.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
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
import rongshanghui.tastebychance.com.rongshanghui.MyAppCompatActivity;
import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.AlibabaUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventRegisterSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventTaobaoUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.RegionRes;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.PermissionsActivity;
import rongshanghui.tastebychance.com.rongshanghui.login.bean.LoginRes;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.ManagerJJActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.BitmapUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.ImageCompress;
import rongshanghui.tastebychance.com.rongshanghui.util.MD5Util;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SharedPreferencesUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UiHelper;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.util.Validator;
import rongshanghui.tastebychance.com.rongshanghui.util.network.MyCallBack;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：RegisterSHQYActivity 信息填写-商会、企业
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/24 11:39
 * 修改人：
 * 修改时间：2017/11/24 11:39
 * 修改备注：
 *
 * @version 1.0
 */
public class RegisterSHQYActivity extends MyAppCompatActivity {


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
    @BindView(R.id.register_unitname_tv)
    TextView registerUnitnameTv;
    @BindView(R.id.register_unitname_edt)
    EditText registerUnitnameEdt;
    @BindView(R.id.register_unitemail_tv)
    TextView registerUnitemailTv;
    @BindView(R.id.register_unitemail_edt)
    EditText registerUnitemailEdt;
    @BindView(R.id.register_username_tv)
    TextView registerUsernameTv;
    @BindView(R.id.register_username_edt)
    EditText registerUsernameEdt;
    @BindView(R.id.register_ascription_tv)
    TextView registerAscriptionTv;
    @BindView(R.id.register_ascription_edt_tv)
    TextView registerAscriptionEdtTv;
    @BindView(R.id.register_usercardclip_tv)
    TextView registerUsercardclipTv;
    @BindView(R.id.register_usercardclip_iv)
    ImageView registerUsercardclipIv;
    @BindView(R.id.activity_register_xxyyqt)
    LinearLayout activityRegisterXxyyqt;
    private String imgPath = null;

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static String PHOTO_FILE_NAME = "temp_photo.png";//头像名称
    private Uri uriImg;// 获取到了图片的Uri
    private File tempFile;
    private Uri imgUri = null;

    private String registerType;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shqy);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            registerType = getIntent().getStringExtra("registerType");
            from = getIntent().getStringExtra("from");
        }
        setTitle();
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("信息填写");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("注册");
        }
    }

    /**
     * 注册
     * mobile	是	string	用户手机号
     * area_code	是	string	区号
     * unit_name	是	string	单位名称
     * card_image	是	string	名片图片路径
     * email	是	string	邮箱账号
     * user_nickname	是	string	使用人姓名
     * subject_type	是	int	主体类型 1:商会,2:企业，3：部门，4：机构，5:镇街，6：学校，7：医院,8:其他
     * organization_type	否	int	机构类型（1:银行,2:证券，3：保险，4：信托，5：基金）,只有机构才传
     * attribution	否	int	归属地id , 只有商会，企业才传
     */
    private void register() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_register_xxyyqt), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        if (null == chosedCity) {
            return;
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_REGISTER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", StringUtil.isNotEmpty(SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), "phoneno"))
                        ? SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), "phoneno")
                        : SystemUtil.getInstance().getUserInfo().getMobile())
                .add("area_code", SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), "areaCode"))
                .add("unit_name", registerUnitnameEdt.getText().toString())
                .add("card_image", imgPath)
                .add("email", registerUnitemailEdt.getText().toString())
                .add("user_nickname", registerUsernameEdt.getText().toString())
                .add("subject_type", (StringUtil.isNotEmpty(registerType) && registerType.equals(Constants.REGISTERENTRANCETYPE_SH) ?
                        "1" : "2"))
                .add("attribution", chosedCity.getRegion_id())
                .build();
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

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //清空上一个登录信息
                                SystemUtil.getInstance().clearData();

                                LoginRes loginRes = JSONObject.parseObject(resInfo.getData().toString(), LoginRes.class);
                                SystemUtil.getInstance().setToken(loginRes.getToken());
                                System.out.println("token = " + loginRes.getToken());
//                                SharedPreferencesUtils.saveToShared(MyApplication.getContext(),"userInfo",loginRes.getUser_info());
                                SystemUtil.getInstance().setUserInfo(loginRes.getUser_info());
                                SystemUtil.getInstance().setIsLogin(true);
                                ToastUtils.showOneToast(MyApplication.getContext(), "注册成功");

                                //注册成功返回
                                /*Intent intent = new Intent();
                                intent.setAction(Constants.REGISTER_SUCCESS_ACTION);
                                sendBroadcast(intent);*/

                                SystemUtil.getInstance().saveLoginInfoToLocal(loginRes.getUser_info().getUser_login(), MD5Util.md5Code(loginRes.getUser_info().getUser_login()), Constants.APP_KEY);
                                SystemUtil.getInstance().taobaouser(1, new MyCallBack<AlibabaUser>() {
                                    @Override
                                    public void onLoadingBefore(Request request) {

                                    }

                                    @Override
                                    public void onSuccess(Response response, AlibabaUser result) {
                                        //注册成功返回
                                        EventBusUtils.post(new EventRegisterSuc());
                                        SystemUtil.getInstance().alibabalogout();
                                        //注册成功返回
                                        RegisterSHQYActivity.this.finish();
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

    private boolean canRegister() {
        if (StringUtil.isEmpty(registerUnitnameEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "单位名称不能为空");
            return false;
        }
        if (StringUtil.isEmpty(registerUnitemailEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "单位邮箱不能为空");
            return false;
        }
        if (!Validator.isEmail(registerUnitemailEdt.getText().toString())) {
            ToastUtils.showOneToast(this, Constants.WARNING_FORMAT_EMAIL);
            return false;
        }
        if (StringUtil.isEmpty(registerUsernameEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "使用人名字不能为空");
            return false;
        }
        if (chosedCity == null || StringUtil.isEmpty(chosedCity.getRegion_id()) || StringUtil.isEmpty(registerAscriptionEdtTv.getText().toString())) {
            ToastUtils.showOneToast(this, "所属地不能为空");
            return false;
        }
        if (StringUtil.isEmpty(imgPath)) {
            ToastUtils.showOneToast(this, "请上传名片");
            return false;
        }
        return true;
    }

    /**
     * 上传使用者名片
     */
    private void uploadUserCardClip() {
        getPopupWindow();
    }

    private PopupWindow popupWindow;

    public void getPopupWindow() {
        if (null != popupWindow) {
            //如果popupWindow正在显示，接下来隐藏
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                //产生背景变暗效果
                setBackgroundAlpha(0.3f);
                popupWindow.showAtLocation(this.findViewById(R.id.activity_register_xxyyqt), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
        popupWindow.showAtLocation(this.findViewById(R.id.activity_register_xxyyqt), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
            System.out.println("PHOTO_FILE_NAME = " + PHOTO_FILE_NAME);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void taobaoUser(EventTaobaoUser eventTaobaoUser) {
        SystemUtil.getInstance().taobaouser(1, new MyCallBack<AlibabaUser>() {
            @Override
            public void onLoadingBefore(Request request) {

            }

            @Override
            public void onSuccess(Response response, AlibabaUser result) {
//注册成功返回
                EventBusUtils.post(new EventRegisterSuc());
                //注册成功返回
                RegisterSHQYActivity.this.finish();
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
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (Build.VERSION.SDK_INT >= 23 && permissionsChecker.lacksPermissions(Constants.PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == Constants.REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }

        if (requestCode == 4 && resultCode == Constants.GETASCRIPTION_RETURNCODE) {
            if (null != registerAscriptionEdtTv && data != null) {
                chosedCity = (RegionRes.DataBean) data.getSerializableExtra("ascription");
                if (chosedCity != null) {
                    registerAscriptionEdtTv.setText(chosedCity.getRegion_name());
                }
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

                PicassoUtils.getinstance().loadNormalByUri(RegisterSHQYActivity.this, uriImg, registerUsercardclipIv);

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

                    PicassoUtils.getinstance().loadNormalByUri(RegisterSHQYActivity.this, uriImg, registerUsercardclipIv);

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
                    registerUsercardclipIv.setImageDrawable(getResources().getDrawable(R.drawable.register_usercardclip_edt_bg));
                }
            } else {
                Toast.makeText(RegisterSHQYActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
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
        Bitmap bitmap = compress.compressFromUri(RegisterSHQYActivity.this, options);
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
            loadingBar = LoadingBar.make(findViewById(R.id.activity_register_xxyyqt), new CustomLoadingFactory());
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
//                .addFormDataPart("title","Square Logo")
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
                        if (null != registerUsercardclipIv) {
                            if (null != registerUsercardclipIv) {

                                imgPath = finalUrl;

                                Message msg = new Message();
                                msg.what = INFO_WHAT;
                                msg.obj = Constants.UPLOAD_SUCCES;
                                myHandler.sendMessage(msg);
//                                String imgUrl;
//                                if (finalUrl != null && finalUrl.contains("http:") || finalUrl.contains("https")){
//                                    imgUrl = finalUrl;
//                                }else{
//                                    imgUrl = (Constants.IS_DEVELOPING? UrlManager.REQUESTURL_HEAD_TEST : UrlManager.REQUESTIMGURL) + finalUrl;
//                                }
//
//                                PicassoUtils.getinstance().loadNormalByPath(RegisterJGActivity.this,imgUrl,registerUsercardclipIv);
                            }
                        }
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

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.register_ascription_edt_tv, R.id.register_usercardclip_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                RegisterSHQYActivity.this.finish();
                break;
            case R.id.head_right_tv:
                SoftInputUtil.hideSoftInput(RegisterSHQYActivity.this, headRightTv);
                if (canRegister()) {
                    register();
                }
                break;
            case R.id.register_ascription_edt_tv:
                Intent intent = new Intent(RegisterSHQYActivity.this, AscriptionActivity.class);
                startActivityForResult(intent, 4);
                break;
            case R.id.register_usercardclip_iv:
                uploadUserCardClip();
                break;
        }
    }

}
