package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.lzy.okgo.callback.StringCallback;
import com.netcompss.ffmpeg4android.GeneralUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
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
import rongshanghui.tastebychance.com.rongshanghui.bean.RegionRes;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.clsb.bean.FileBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.bean.IntroInfo;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.jcvideoplayer.JCVideoPlayerActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.jcvideoplayer.MyUserActionStandard;
import rongshanghui.tastebychance.com.rongshanghui.util.BitmapUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.FileSizeUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ImageCompress;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UiHelper;
import rongshanghui.tastebychance.com.rongshanghui.util.UriUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.util.network.OkHttpUtils;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.commonpopupwindow.CommonPopupWindow;

/**
 * 类描述：BMManagerJJActivity 我的-管理-简介编辑
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/28 14:35
 * 修改人：
 * 修改时间：2017/11/28 14:35
 * 修改备注：
 *
 * @version 1.0
 */
public class ManagerJJActivityNoCompress1 extends MyAppCompatActivity {


    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.mine_manager_jj_uploadpicbg_iv)
    ImageView mineManagerJjUploadpicbgIv;
    @BindView(R.id.mine_manager_jj_uploadpic_iv)
    ImageView mineManagerJjUploadpicIv;
    @BindView(R.id.mine_manager_jj_uploadpic_tv)
    TextView mineManagerJjUploadpicTv;
    @BindView(R.id.relativelayout)
    RelativeLayout relativelayout;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.mine_manager_jj_username_tv)
    TextView mineManagerJjUsernameTv;
    @BindView(R.id.mine_manager_jj_username_edt_tv)
    TextView mineManagerJjUsernameEdtTv;
    @BindView(R.id.relativelayout1)
    RelativeLayout relativelayout1;
    @BindView(R.id.mine_manager_jj_ascription_edt)
    EditText mineManagerJjAscriptionEdt;
    @BindView(R.id.relativelalyout1)
    LinearLayout relativelalyout1;
    @BindView(R.id.mine_manager_jj_phoneno_tv)
    TextView mineManagerJjPhonenoTv;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.mine_manager_jj_email_edt)
    EditText mineManagerJjEmailEdt;
    @BindView(R.id.linearLayout4)
    LinearLayout linearLayout4;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.mine_manager_jj_zhch1_edt)
    EditText mineManagerJjZhch1Edt;
    @BindView(R.id.mine_manager_jj_zhch1_name_edt)
    EditText mineManagerJjZhch1NameEdt;
    @BindView(R.id.mine_manager_jj_leaderinfo_ll1)
    LinearLayout mineManagerJjLeaderinfoLl1;
    @BindView(R.id.mine_manager_jj_zhch2_edt)
    EditText mineManagerJjZhch2Edt;
    @BindView(R.id.mine_manager_jj_zhch2_name_edt)
    EditText mineManagerJjZhch2NameEdt;
    @BindView(R.id.mine_manager_jj_leaderinfo_ll2)
    LinearLayout mineManagerJjLeaderinfoLl2;
    @BindView(R.id.mine_manager_jj_zhch3_edt)
    EditText mineManagerJjZhch3Edt;
    @BindView(R.id.mine_manager_jj_zhch3_name_edt)
    EditText mineManagerJjZhch3NameEdt;
    @BindView(R.id.mine_manager_jj_leaderinfo_ll3)
    LinearLayout mineManagerJjLeaderinfoLl3;
    @BindView(R.id.mine_manager_jj_zhch4_edt)
    EditText mineManagerJjZhch4Edt;
    @BindView(R.id.mine_manager_jj_zhch4_name_edt)
    EditText mineManagerJjZhch4NameEdt;
    @BindView(R.id.mine_manager_jj_leaderinfo_ll4)
    LinearLayout mineManagerJjLeaderinfoLl4;
    @BindView(R.id.linearLayout5)
    LinearLayout linearLayout5;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.mine_manager_jj_jj_edt)
    EditText mineManagerJjJjEdt;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.uploadvideo_iv)
    ImageView uploadvideoIv;
    @BindView(R.id.content_bmmanager_jj)
    RelativeLayout contentBmmanagerJj;
    @BindView(R.id.activity_manager_jj_rootlayout)
    CoordinatorLayout activityManagerJjRootlayout;
    @BindView(R.id.uploadvideo_pb)
    ProgressBar uploadvideoPb;
    @BindView(R.id.uploadvideo_ll)
    LinearLayout uploadvideoLl;
    @BindView(R.id.uploadvideo_pb_tv)
    TextView uploadvideoPbTv;
    @BindView(R.id.jc_video)
    JCVideoPlayerStandard jcVideo;

    private ViewGroup.LayoutParams mVideoViewLayoutParams;

    private String originAvatar;
    private String originUserName;
    private String originAddress;
    private String originAreaCode;
    private String originPhoneNo;
    private String originUserEmail;
    private String originIntro;

    private List<FileBean> files = new ArrayList<FileBean>();

    private IntroInfo introInfo;
    private final int LOAD_DATA_WHAT = 20;
    private final int POST_DATA_WHAT = 21;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            try {
                switch (msg.what) {
                    case LOAD_DATA_WHAT:

                        ResInfo resInfo = (ResInfo) msg.obj;
                        introInfo = resInfo.getClass(IntroInfo.class);
                        originAvatar = introInfo.getImage();
                        originUserName = introInfo.getUnit_name();
                        originAddress = introInfo.getAddress();
                        originAreaCode = introInfo.getArea_code();
                        originPhoneNo = introInfo.getMobile();
                        originUserEmail = introInfo.getEmail();
                        originIntro = introInfo.getIntro();

                        //视频
                        fileUrl = introInfo.getVideo();
                        if (StringUtil.isNotEmpty(fileUrl)) {
                            uploadvideoIv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                            jcVideo.setUp(fileUrl.replaceAll("\"", ""), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                            Picasso.with(ManagerJJActivityNoCompress1.this)
                                    .load(R.drawable.shiping)
                                    .into(jcVideo.thumbImageView);
                            JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
                        }

//                        PicassoUtils.getinstance().loadCircleImage(ManagerJJActivityNoCompress1.this, introInfo.getImage(), mineManagerJjUploadpicIv, 24);
                        if (StringUtil.isNotEmpty(introInfo.getImage())) {
                            PicassoUtils.getinstance().loadNormalByPath(ManagerJJActivityNoCompress1.this, introInfo.getImage(), mineManagerJjUploadpicbgIv);
                            mineManagerJjUploadpicIv.setVisibility(View.GONE);
                            mineManagerJjUploadpicTv.setVisibility(View.GONE);
                        }
                        mineManagerJjUsernameTv.setText(introInfo.getUnit_name());
                        mineManagerJjAscriptionEdt.setText(introInfo.getAddress());
                        if (StringUtil.isNotEmpty(introInfo.getArea_code()) && StringUtil.isNotEmpty(introInfo.getMobile())) {
                            mineManagerJjPhonenoTv.setText("(+" + introInfo.getArea_code() + ")" + introInfo.getMobile());
                        }
                        mineManagerJjEmailEdt.setText(introInfo.getEmail());

                        int size = introInfo.getLeader_info().size();
                        switch (size) {
                            case 4:
                                mineManagerJjZhch4Edt.setText(introInfo.getLeader_info().get(3).getFunc());
                                mineManagerJjZhch4NameEdt.setText(introInfo.getLeader_info().get(3).getName());
                            case 3:
                                mineManagerJjZhch3Edt.setText(introInfo.getLeader_info().get(2).getFunc());
                                mineManagerJjZhch3NameEdt.setText(introInfo.getLeader_info().get(2).getName());
                            case 2:
                                mineManagerJjZhch2Edt.setText(introInfo.getLeader_info().get(1).getFunc());
                                mineManagerJjZhch2NameEdt.setText(introInfo.getLeader_info().get(1).getName());
                            case 1:
                                mineManagerJjZhch1Edt.setText(introInfo.getLeader_info().get(0).getFunc());
                                mineManagerJjZhch1NameEdt.setText(introInfo.getLeader_info().get(0).getName());
                                break;
                        }

                        mineManagerJjJjEdt.setText(introInfo.getIntro());
                        break;
                    case POST_DATA_WHAT:
                        ManagerJJActivityNoCompress1.this.finish();
                        break;
                    case Constants.WHAT_CHOSEDFILE:
                        if (null != videoPopupWindow && videoPopupWindow.isShowing()) {
                            videoPopupWindow.dismiss();
                        }

                        if (null == files || files.size() <= 0) {
                            return;
                        }

                        fileUrl = null;
                        if (files.get(0).getStatus() == -1) {
                            uploadvideoLl.setVisibility(View.GONE);
                            ToastUtils.showOneToast(getApplicationContext(), "视频上传失败");
                        } else if (files.get(0).getStatus() == 1) {
                            uploadvideoLl.setVisibility(View.GONE);
                            ToastUtils.showOneToast(getApplicationContext(), "视频上传完成");

                            if (null == files || files.size() <= 0) {
                                return;
                            }

                            fileUrl = files.get(0).getReturnName();
                        } else {
                            uploadvideoLl.setVisibility(View.VISIBLE);
                            uploadvideoPbTv.setText((new DecimalFormat("######0.00").format((files.get(0).getCurrentSize() * 100 / files.get(0).getTotalSize()))) + "%");
                            uploadvideoPb.setProgress((int) (100 * files.get(0).getProgress()));
//                            ToastUtils.showOneToast(getApplicationContext(), "正在上传");
                        }

                        if (files.get(0).getStatus() == -2) {
                            uploadvideoLl.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneralUtils.checkForPermissionsMAndAbove(ManagerJJActivityNoCompress1.this, true);
        setContentView(R.layout.activity_bmmanager_jj);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setTitle();
        getData();

        initView();

        GeneralUtils.copyDemoVideoFromAssetsToSDIfNeeded(this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/videokit/");
    }

    private void initView() {

        if (null != uploadvideoLl) {
            uploadvideoLl.setVisibility(View.GONE);
        }
    }

    private void setTitle() {
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setText("简介编辑");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("保存");
        }
    }

    /**
     * 初始化页面数据
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_manager_jj_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_MINE_INTRO;
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = LOAD_DATA_WHAT;
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
        popupWindow.showAtLocation(this.findViewById(R.id.activity_manager_jj_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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

    private String areaCode, phoneNo;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Constants.USERNAME_CHANGE_RETURNCODE) {
            if (null != data) {
                mineManagerJjUsernameTv.setText(data.getStringExtra("userName"));
            }
        }
        if (requestCode == 2 && resultCode == Constants.GETAREACODE_PHONENO_RETURNCODE) {
            if (null != data) {
                areaCode = data.getStringExtra("areaCode");
                phoneNo = data.getStringExtra("phoneNo");
                mineManagerJjPhonenoTv.setText("(+" + areaCode + ")" + phoneNo);
            }
        }

        if (requestCode == PHOTO_REQUEST_GALLERY && resultCode == -1) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                uriImg = uri;
//                PicassoUtils.getinstance().loadCircleImageByUri(ManagerJJActivityNoCompress1.this, uriImg, mineManagerJjUploadpicIv, 24);
                if (null != mineManagerJjUploadpicbgIv && StringUtil.isNotEmpty(introInfo.getImage())) {
                    PicassoUtils.getinstance().loadNormalByUri(ManagerJJActivityNoCompress1.this, uriImg, mineManagerJjUploadpicbgIv);
                    mineManagerJjUploadpicIv.setVisibility(View.GONE);
                    mineManagerJjUploadpicTv.setVisibility(View.GONE);
                }
                if (StringUtil.isNotEmpty(uriImg + "")) {
                    uploadHeadPortrait(uriImg);
                }
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA && resultCode == -1) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                if (tempFile.exists()) {
                    uriImg = Uri.fromFile(tempFile);
//                    PicassoUtils.getinstance().loadCircleImageByUri(ManagerJJActivityNoCompress1.this, uriImg, mineManagerJjUploadpicIv, 24);
                    if (null != mineManagerJjUploadpicbgIv && StringUtil.isNotEmpty(introInfo.getImage())) {
                        PicassoUtils.getinstance().loadNormalByUri(ManagerJJActivityNoCompress1.this, uriImg, mineManagerJjUploadpicbgIv);
                        mineManagerJjUploadpicIv.setVisibility(View.GONE);
                        mineManagerJjUploadpicTv.setVisibility(View.GONE);
                    }
                    if (StringUtil.isNotEmpty(uriImg + "")) {

                        try {
                            Uri capturedImage = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), tempFile.getAbsolutePath(), null, null));

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
                    mineManagerJjUploadpicIv.setImageDrawable(getResources().getDrawable(R.drawable.person_defaultheadportrait));
                }
            } else {
                ToastUtils.showOneToast(ManagerJJActivityNoCompress1.this, "未找到存储卡，无法存储照片！");
            }
        }

        //上传视频
        if (requestCode == Constants.FILE_SELECT_CODE && resultCode == RESULT_OK) {
            videoPopupWindowCancel();

            // Get the Uri of the selected file
            Uri uri = data.getData();
            Log.d(Constants.TAG, "File Uri: " + uri.toString());

            // Get the path
            String path = null;
            try {
//                path = FileUtils.getPath(this, uri);
                path = UriUtils.getPath(this, uri);

                if (StringUtil.isEmpty(path)) {
                    ToastUtils.showOneToast(getApplicationContext(), "请选择要上传的文件");
                    return;
                }
                if (!path.endsWith(".mp4")) {
                    ToastUtils.showOneToast(getApplicationContext(), "请选择mp4格式的视频");
                    return;
                }
            } catch (Exception e) {
                Log.e("TAG", e.toString());
                //e.printStackTrace();
                path = "";
            }
//            Log.d(Constants.TAG, "File Path: " + path);
            // Get the file instance
            // File file = new File(path);
            // Initiate the upload

//            path = "/sdcard/videokit/in.mp4";
            compresed(path);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void compresed(String path) {
        if (StringUtil.isNotEmpty(path) && !path.endsWith(".mp4")) {
            ToastUtils.showOneToast(getApplicationContext(), "请选择mp4格式的视频");
            return;
        }

        System.out.println("视频文件大小为 = " + FileSizeUtil.getFileOrFilesSize(path, FileSizeUtil.SIZETYPE_MB) + "MB");
        if (FileSizeUtil.getFileOrFilesSize(path, FileSizeUtil.SIZETYPE_MB) > 50) {
            ToastUtils.showOneToast(getApplicationContext(), "请上传小于50MB的视频");
            return;
        }

        String filename = path.substring(path.lastIndexOf("/") + 1);

        if (null != files && files.size() > 0) {
            files.clear();
        }

        WeakReference<FileBean> wf = new WeakReference<FileBean>(new FileBean());
        wf.get().setFileName(filename);
//        wf.get().setFilePath(uri.toString());
        wf.get().setFilePath(path);
        wf.get().setStatus(-2);
        files.add(wf.get());

        Message msg = new Message();
        msg.what = Constants.WHAT_CHOSEDFILE;
        handler.sendMessage(msg);

        uploadFile(new File(path), "video");
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
        Bitmap bitmap = compress.compressFromUri(ManagerJJActivityNoCompress1.this, options);
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
            loadingBar = LoadingBar.make(findViewById(R.id.activity_manager_jj_rootlayout), new CustomLoadingFactory());
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
        mOkHttpClient.newCall(request).enqueue(new Callback() {
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = Constants.UPLOAD_SUCCES;
                        myHandler.sendMessage(msg);
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

    private boolean canSave() {
        /*if (!Validator.isEmail(mineManagerJjEmailEdt.getText().toString())){
            ToastUtils.showOneToast(getApplicationContext(), Constants.WARNING_FORMAT_EMAIL);
            return false;
        }*/
        return true;
    }

    /**
     * 我的-主体管理-简介修改提交
     * image 	否 	string 	简介顶部图片地址
     * unit_name 	否 	string 	单位名称（一个月只能改一次）
     * address 	否 	string 	单位地址
     * mobile 	否 	string 	单位电话
     * area_code 	否 	string 	区号
     * email 	否 	string 	单位邮箱
     * leader 	否 	string 	领导人信息 [{“func”:”局长”,”name”:”李明”},{“func”:”副局长”,”name”:”李明明”}]
     * intro 	否 	string 	简介文字
     * video 	否 	string 	视频地址
     * token 	是 	string 	token指
     */
    private void save() {

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_manager_jj_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        if (StringUtil.isEmpty(token)) {
            ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
            return;
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_MINE_INTROPOST;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", token);
        String nowAvatar = filePath;
        String nowUserName = mineManagerJjUsernameTv.getText().toString();
        String nowAddress = mineManagerJjAscriptionEdt.getText().toString();
        String nowAreaCode = areaCode;
        String nowPhoneNo = phoneNo;
        String nowUserEmail = mineManagerJjEmailEdt.getText().toString();
        String nowIntro = mineManagerJjJjEdt.getText().toString();

        if (StringUtil.isNotEmpty(filePath)) {
            builder.add("image", nowAvatar);
        }
        if (StringUtil.isNotEmpty(nowUserName) && SystemUtil.getInstance().hasChanged(nowUserName, originUserName)) {
            builder.add("unit_name", nowUserName);
        }
        if (StringUtil.isNotEmpty(nowAddress) && SystemUtil.getInstance().hasChanged(nowAddress, originAddress)) {
            builder.add("address", nowAddress);
        }
        if (StringUtil.isNotEmpty(nowAreaCode) && SystemUtil.getInstance().hasChanged(nowAreaCode, originAreaCode)) {
            builder.add("code", nowAreaCode);
        }
        if (StringUtil.isNotEmpty(nowPhoneNo) && SystemUtil.getInstance().hasChanged(nowPhoneNo, originPhoneNo)) {
            builder.add("mobile", nowPhoneNo);
        }
        if (StringUtil.isNotEmpty(nowUserEmail) && SystemUtil.getInstance().hasChanged(nowUserEmail, originUserEmail)) {
            builder.add("email", nowUserEmail);
        }
        if (StringUtil.isNotEmpty(nowIntro) && SystemUtil.getInstance().hasChanged(nowIntro, originIntro)) {
            builder.add("intro", nowIntro);
        }
        if (null != files && files.size() > 0 && StringUtil.isNotEmpty(files.get(0).getReturnName().replaceAll("\"", ""))) {
            builder.add("video", files.get(0).getReturnName().replaceAll("\"", ""));
        }

        StringBuilder leader = new StringBuilder();
        leader.append("[");
//        if (StringUtil.isNotEmpty(mineManagerJjZhch1Edt.getText().toString())){
        leader.append("{\"func\":\"");
        leader.append(mineManagerJjZhch1Edt.getText().toString());
        leader.append("\",\"name\":\"");
        leader.append(mineManagerJjZhch1NameEdt.getText().toString());
        leader.append("\"}");
//        }
//        if (StringUtil.isNotEmpty(mineManagerJjZhch2Edt.getText().toString())){
        leader.append(",{\"func\":\"");
        leader.append(mineManagerJjZhch2Edt.getText().toString());
        leader.append("\",\"name\":\"");
        leader.append(mineManagerJjZhch2NameEdt.getText().toString());
        leader.append("\"}");
//        }
//        if (StringUtil.isNotEmpty(mineManagerJjZhch3Edt.getText().toString())){
        leader.append(",{\"func\":\"");
        leader.append(mineManagerJjZhch3Edt.getText().toString());
        leader.append("\",\"name\":\"");
        leader.append(mineManagerJjZhch3NameEdt.getText().toString());
        leader.append("\"}");
//        }
//        if (StringUtil.isNotEmpty(mineManagerJjZhch4Edt.getText().toString())){
        leader.append(",{\"func\":\"");
        leader.append(mineManagerJjZhch4Edt.getText().toString());
        leader.append("\",\"name\":\"");
        leader.append(mineManagerJjZhch4NameEdt.getText().toString());
        leader.append("\"}");
//        }
        leader.append("]");

        builder.add("leader", leader.toString());

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
                                msg.what = POST_DATA_WHAT;
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

    /******************************************上传文件****************************************************/
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"), Constants.FILE_SELECT_CODE);
        } catch (ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            ToastUtils.showOneToast(this, "请安装文件管理器");
        }
    }

    /**
     * file    是  obj    文件对象（通过二进制数据流传输）
     * filetype  是  string     文件类型 video,audio,file
     */
    private void uploadFile(final File file, String filetype) {
        Map<String, String> map = new HashMap<>();
        map.put("filetype", filetype);

        try {
            OkHttpUtils.okGoUploadFile(this, map, file, new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    try {
                        System.out.println("str = " + s);
                        final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                        if (resInfo.getCode() == Constants.RESULT_SUCCESS) {
                            for (int i = 0; i < files.size(); i++) {
                                if (files.get(i).getFileName().equals(file.getName())) {
                                    files.get(i).setStatus(1);
                                    files.get(i).setReturnName(resInfo.getDataToStr());
                                }
                            }

                            Message msg = new Message();
                            msg.what = Constants.WHAT_CHOSEDFILE;
                            handler.sendMessage(msg);
                        } else {
                            uploadErrorUpdate(file);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                    //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    //                        mProgressBar.setProgress((int) (100 * progress));
                    //                        mTextView2.setText("已上传" + currentSize/1024/1024 + "MB, 共" + totalSize/1024/1024 + "MB;");
                    for (int i = 0; i < files.size(); i++) {
                        if (files.get(i).getFileName().equals(file.getName())) {
                            if (null != files.get(i)) {
                                files.get(i).setProgress(progress);
                                files.get(i).setStatus(0);
                                System.out.println("currentSize = " + currentSize + "---totalSize:" + totalSize);

                                files.get(i).setCurrentSize(currentSize);
                                files.get(i).setTotalSize(totalSize);
                            }
                        }
                    }

                    Message msg = new Message();
                    msg.what = Constants.WHAT_CHOSEDFILE;
                    handler.sendMessage(msg);
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    ToastUtils.showOneToast(MyApplication.getContext(), Constants.UPLOAD_FILETYPE_ERROR);
                    uploadErrorUpdate(file);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadErrorUpdate(File file) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getFileName().equals(file.getName())) {
                files.get(i).setStatus(-1);
            }
        }
        Message msg = new Message();
        msg.what = Constants.WHAT_CHOSEDFILE;
        handler.sendMessage(msg);
    }
    /******************************************上传文件****************************************************/

    /******************************************视频上传popupwindow****************************************************/
    private CommonPopupWindow videoPopupWindow;

    private String fileUrl;

    private void videouploadPopupWindow() {
        videoPopupWindow = new CommonPopupWindow.Builder(ManagerJJActivityNoCompress1.this)
                //设置popupwindow布局
                .setView(R.layout.item_jjedt_videoupload_popupwindow)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色，取值范围0.0f～1.0f， 值越小越暗，1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子view及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        TextView play = (TextView) view.findViewById(R.id.popup_videoplay_tv);
                        TextView chooce = (TextView) view.findViewById(R.id.popup_videochooce_tv);
                        TextView cancel = (TextView) view.findViewById(R.id.popup_video_cancel_tv);

                        chooce.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                videoPopupWindowCancel();
                                showFileChooser();
                            }
                        });

                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                videoPopupWindowCancel();
                                if (StringUtil.isEmpty(fileUrl)) {
                                    ToastUtils.showOneToast(ManagerJJActivityNoCompress1.this, "请选上传视频");
                                    return;
                                }

                                if (!fileUrl.startsWith("http:") && !fileUrl.startsWith("https:")) {
                                    ToastUtils.showOneToast(ManagerJJActivityNoCompress1.this, "请点击保存后再播放");
                                    return;
                                }
                                Intent intent = new Intent(ManagerJJActivityNoCompress1.this, JCVideoPlayerActivity.class);
                                intent.putExtra("filepath", fileUrl);
                                startActivity(intent);
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                videoPopupWindowCancel();
                            }
                        });
                    }
                })
                //设置外部师傅可以点击，默认是true
                .setOutsideTouchable(true)
                .create();
        setBackgroundAlpha(0.3f);
        videoPopupWindow.showAtLocation(this.findViewById(R.id.activity_manager_jj_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void videoPopupWindowCancel() {
        if (null != videoPopupWindow && videoPopupWindow.isShowing()) {
            videoPopupWindow.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /******************************************视频上传popupwindow****************************************************/


    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.mine_manager_jj_uploadpicbg_iv, R.id.mine_manager_jj_uploadpic_iv, R.id.mine_manager_jj_uploadpic_tv, R.id.mine_manager_jj_username_edt_tv, R.id.mine_manager_jj_phoneno_tv, R.id.uploadvideo_iv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                ManagerJJActivityNoCompress1.this.finish();
                break;
            case R.id.head_right_tv:
                if (canSave()) {
                    save();
                }
                break;
            case R.id.mine_manager_jj_uploadpicbg_iv:
            case R.id.mine_manager_jj_uploadpic_iv:
            case R.id.mine_manager_jj_uploadpic_tv:
                SoftInputUtil.hideSoftInput(ManagerJJActivityNoCompress1.this, mineManagerJjUploadpicbgIv);
                getPopupWindow();
                break;
            case R.id.mine_manager_jj_username_edt_tv:
                if (null != introInfo && introInfo.getCan_modi() == 1) {
                    intent = new Intent(ManagerJJActivityNoCompress1.this, ManagerJJUserNameActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    ToastUtils.showOneToast(getApplicationContext(), "名称一个月只能更改一次");
                }
                break;
            case R.id.mine_manager_jj_phoneno_tv:
                intent = new Intent(ManagerJJActivityNoCompress1.this, ManagerPhoneNoActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.uploadvideo_iv:
                videouploadPopupWindow();
                break;
        }
    }
}
