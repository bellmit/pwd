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
import android.support.annotation.NonNull;
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
import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.AlibabaUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventRegisterSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventTaobaoUser;
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
 * ????????????RegisterXXYYQTActivity ?????????????????????????????????????????????
 * ?????????: shenbh Email:shenbh@qq.com
 * ??????????????? 2017/11/23 20:52
 * ????????????
 * ???????????????2017/11/23 20:52
 * ???????????????
 *
 * @version 1.0
 */
public class RegisterXXYYQTActivity extends MyBaseActivity {


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
    @BindView(R.id.register_usercardclip_tv)
    TextView registerUsercardclipTv;
    @BindView(R.id.register_usercardclip_iv)
    ImageView registerUsercardclipIv;
    @BindView(R.id.activity_register_xxyyqt)
    LinearLayout activityRegisterXxyyqt;
    private String imgPath = null;

    private static final int PHOTO_REQUEST_CAMERA = 1;// ??????
    private static final int PHOTO_REQUEST_GALLERY = 2;// ??????????????????
    private static String PHOTO_FILE_NAME = "temp_photo.png";//????????????
    private Uri uriImg;// ?????????????????????Uri
    private File tempFile;
    private Uri imgUri = null;

    private String registerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_xxyyqt);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            registerType = getIntent().getStringExtra("registerType");
        }
        setTitle();
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("????????????");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("??????");
        }
    }

    /**
     * ??????
     * mobile	???	string	???????????????
     * area_code	???	string	??????
     * unit_name	???	string	????????????
     * card_image	???	string	??????????????????
     * email	???	string	????????????
     * user_nickname	???	string	???????????????
     * subject_type	???	int	???????????? 1:??????,2:?????????3????????????4????????????5:?????????6????????????7?????????,8:??????
     * organization_type	???	int	???????????????1:??????,2:?????????3????????????4????????????5????????????,??????????????????
     * attribution	???	int	?????????id , ???????????????????????????
     */
    private void register() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_register_xxyyqt), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //??????okhttp3?????????????????????
        final String url = UrlManager.URL_REGISTER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), "phoneno"))
                .add("area_code", SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), "areaCode"))
                .add("unit_name", registerUnitnameEdt.getText().toString())
                .add("card_image", imgPath)
                .add("email", registerUnitemailEdt.getText().toString())
                .add("user_nickname", registerUsernameEdt.getText().toString())
                .add("subject_type", getRegisterTypeValue())
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
                                //???????????????????????????
                                SystemUtil.getInstance().clearData();

                                LoginRes loginRes = JSONObject.parseObject(resInfo.getData().toString(), LoginRes.class);
                                SystemUtil.getInstance().setToken(loginRes.getToken());
                                System.out.println("token = " + loginRes.getToken());
//                                SharedPreferencesUtils.saveToShared(MyApplication.getContext(),"userInfo",loginRes.getUser_info());
                                SystemUtil.getInstance().setUserInfo(loginRes.getUser_info());
                                SystemUtil.getInstance().setIsLogin(true);
                                ToastUtils.showOneToast(MyApplication.getContext(), "????????????");
                                SystemUtil.getInstance().saveLoginInfoToLocal(loginRes.getUser_info().getUser_login(), (MD5Util.md5Code(loginRes.getUser_info().getUser_login())), Constants.APP_KEY);


                                SystemUtil.getInstance().taobaouser(1, new MyCallBack<AlibabaUser>() {
                                    @Override
                                    public void onLoadingBefore(Request request) {

                                    }

                                    @Override
                                    public void onSuccess(Response response, AlibabaUser result) {
                                        //??????????????????
                                        /*Intent intent = new Intent();
                                        intent.setAction(Constants.REGISTER_SUCCESS_ACTION);
                                        sendBroadcast(intent);*/
                                        EventBusUtils.post(new EventRegisterSuc());
                                        SystemUtil.getInstance().alibabalogout();
                                        //??????????????????
                                        RegisterXXYYQTActivity.this.finish();
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
                    CrashHandler.getInstance().handlerError("??????" + url + " ???????????????????????????");
                }
            }
        });
    }

    //subject_type	???	int	???????????? 1:??????,2:?????????3????????????4????????????5:?????????6????????????7?????????,8:??????
    private String getRegisterTypeValue() {
        if (registerType.equals(Constants.REGISTERENTRANCETYPE_YY)) {
            return "7";
        } else if (registerType.equals(Constants.REGISTERENTRANCETYPE_QT)) {
            return "8";
        }

        return "6";
    }

    private boolean canRegister() {
        if (StringUtil.isEmpty(registerUnitnameEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "????????????????????????");
            return false;
        }
        if (StringUtil.isEmpty(registerUnitemailEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "????????????????????????");
            return false;
        }
        if (!Validator.isEmail(registerUnitemailEdt.getText().toString())) {
            ToastUtils.showOneToast(this, Constants.WARNING_FORMAT_EMAIL);
            return false;
        }
        if (StringUtil.isEmpty(registerUsernameEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "???????????????????????????");
            return false;
        }
        if (StringUtil.isEmpty(imgPath)) {
            ToastUtils.showOneToast(this, "???????????????");
            return false;
        }
        return true;
    }

    /**
     * ?????????????????????
     */
    private void uploadUserCardClip() {
        getPopupWindow();
    }

    private PopupWindow popupWindow;

    public void getPopupWindow() {
        if (null != popupWindow) {
            //??????popupWindow??????????????????????????????
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                //????????????????????????
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

        //??????
        person_information_changeheadportrait_photo_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromCameraCapture();
                popupWindow.dismiss();
            }
        });

        //??????????????????
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

    // ???????????????????????????????????????
    private void choseHeadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    // ??????????????????????????????????????????
    /* ???????????? */
    private final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    private void choseHeadImageFromCameraCapture() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // ???????????????????????????????????????????????????
        if (hasSdcard()) {
            PHOTO_FILE_NAME = SystemUtil.getInstance().generateImgName();
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
            System.err.println();
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);

    }

    // ???????????????
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        this.getWindow().setAttributes(layoutParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void taobaoUser(@NonNull EventTaobaoUser eventTaobaoUser) {
        SystemUtil.getInstance().taobaouser(1, new MyCallBack<AlibabaUser>() {
            @Override
            public void onLoadingBefore(Request request) {

            }

            @Override
            public void onSuccess(Response response, AlibabaUser result) {
//??????????????????
                                        /*Intent intent = new Intent();
                                        intent.setAction(Constants.REGISTER_SUCCESS_ACTION);
                                        sendBroadcast(intent);*/
                EventBusUtils.post(new EventRegisterSuc());

                //??????????????????
                RegisterXXYYQTActivity.this.finish();
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
    protected void onResume() {
        super.onResume();
        // ???????????????, ????????????????????????
        if (Build.VERSION.SDK_INT >= 23 && permissionsChecker.lacksPermissions(Constants.PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // ?????????, ????????????, ??????????????????, ????????????
        if (requestCode == Constants.REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }

        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // ????????????????????????
                Uri uri = data.getData();
                uriImg = uri;

              /*  Picasso.with(PersonalInformationActivity.this)
                        .load(uriImg)
                        .resize(48,48)
                        .centerCrop()
                        .into(person_information_headportrait_riv);*/

                PicassoUtils.getinstance().loadNormalByUri(RegisterXXYYQTActivity.this, uriImg, registerUsercardclipIv);

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

                    PicassoUtils.getinstance().loadNormalByUri(RegisterXXYYQTActivity.this, uriImg, registerUsercardclipIv);

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
                    // "?????????????????????!");
                    registerUsercardclipIv.setImageDrawable(getResources().getDrawable(R.drawable.register_usercardclip_edt_bg));
                }
            } else {
                Toast.makeText(RegisterXXYYQTActivity.this, "??????????????????????????????????????????", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * ????????????
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
        Bitmap bitmap = compress.compressFromUri(RegisterXXYYQTActivity.this, options);
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

        //??????okhttp3?????????????????????
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
     * ????????????????????????SDCard???????????????
     */
    private static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // ????????????SDCard
            return true;
        } else {
            return false;
        }
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.register_usercardclip_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                RegisterXXYYQTActivity.this.finish();
                break;
            case R.id.head_right_tv:
                SoftInputUtil.hideSoftInput(RegisterXXYYQTActivity.this, headRightTv);
                if (canRegister()) {
                    register();
                }
                break;
            case R.id.register_usercardclip_iv:
                uploadUserCardClip();
                break;
        }
    }
}
