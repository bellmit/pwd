package rongshanghui.tastebychance.com.rongshanghui.home.sht.detail;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
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
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.RegionRes;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.web.ShowWeb2Activity;
import rongshanghui.tastebychance.com.rongshanghui.common.web.ShowWebInfoActivity;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToRHSQBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.ManagerJJActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.ManagerPhoneNoActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager.bean.MemberInfoBean;
import rongshanghui.tastebychance.com.rongshanghui.register.AscriptionActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.BitmapUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.DateTimepickDialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.DateUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.DialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.ImageCompress;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.TextClickable;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UiHelper;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.wheel.WheelView;

/**
 * 类描述：RHSQActivity 首页-商会通-入会申请
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/4 14:09
 * 修改人：
 * 修改时间：2017/12/4 14:09
 * 修改备注：
 *
 * @version 1.0
 */
public class RHSQActivity extends MyAppCompatActivity {


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
    @BindView(R.id.sht_rhsq_name_edt)
    EditText shtRhsqNameEdt;
    @BindView(R.id.sht_rhsq_sex_man_tv)
    TextView shtRhsqSexManTv;
    @BindView(R.id.sht_rhsq_sex_lady_tv)
    TextView shtRhsqSexLadyTv;
    @BindView(R.id.sht_rhsq_birth_tv)
    TextView shtRhsqBirthTv;
    @BindView(R.id.sht_rhsq_jiguan_tv)
    TextView shtRhsqJiguanTv;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.sht_rhsq_minzu_tv)
    TextView shtRhsqMinzuTv;
    @BindView(R.id.sht_rhsq_zhuanye_edt)
    EditText shtRhsqZhuanyeEdt;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.sht_rhsq_address_edt)
    EditText shtRhsqAddressEdt;
    @BindView(R.id.sht_rhsq_phone_tv)
    TextView shtRhsqPhoneTv;
    @BindView(R.id.sht_rhsq_zhiwu_edt)
    EditText shtRhsqZhiwuEdt;
    @BindView(R.id.sht_rhsq_photo_iv)
    ImageView shtRhsqPhotoIv;
    @BindView(R.id.sht_rhsq_rhxz_cb)
    CheckBox shtRhsqRhxzCb;
    @BindView(R.id.sht_rhsq_rhxz_tv)
    TextView shtRhsqRhxzTv;
    @BindView(R.id.content_rhsq)
    RelativeLayout contentRhsq;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_home_sht_rhsq_rootlayout)
    CoordinatorLayout activityHomeShtRhsqRootlayout;
    private ToRHSQBean toRHSQBean;
    private String initStartDateTime = ""; // 日期时间控件初始化开始时间

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends Activity> extends Handler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t) {
                try {
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:
                            ResInfo resInfo = (ResInfo) msg.obj;
                            if (null != resInfo) {
                                MemberInfoBean memberInfoBean = resInfo.getClass(MemberInfoBean.class);
                                if (null != memberInfoBean) {
                                    ((RHSQActivity) t).shtRhsqNameEdt.setText(memberInfoBean.getApply_data().getName());
                                    if (memberInfoBean.getApply_data().getSex() == 1) {
//                                    shtRhsqSexRg.check(R.id.sht_rhsq_sex_man_rbt);
                                        ((RHSQActivity) t).setSexManChosed();
                                    } else {
//                                    shtRhsqSexRg.check(R.id.sht_rhsq_sex_lady_rbt);
                                        ((RHSQActivity) t).setSexLadyChosed();
                                    }
                                    ((RHSQActivity) t).shtRhsqBirthTv.setText(memberInfoBean.getApply_data().getBirthday());
                                    ((RHSQActivity) t).shtRhsqJiguanTv.setText(memberInfoBean.getApply_data().getNative_place());
                                    ((RHSQActivity) t).shtRhsqMinzuTv.setText(memberInfoBean.getApply_data().getNation());
                                    ((RHSQActivity) t).shtRhsqZhuanyeEdt.setText(memberInfoBean.getApply_data().getCollege());
                                    ((RHSQActivity) t).shtRhsqAddressEdt.setText(memberInfoBean.getApply_data().getAddress());
                                    ((RHSQActivity) t).shtRhsqPhoneTv.setText(memberInfoBean.getApply_data().getMobile());
                                    ((RHSQActivity) t).shtRhsqZhiwuEdt.setText(memberInfoBean.getApply_data().getFunc());
                                    PicassoUtils.getinstance().loadNormalByPath(t, memberInfoBean.getApply_data().getAvatar(), ((RHSQActivity) t).shtRhsqPhotoIv);

                                    if (null == ((RHSQActivity) t).chosedJiguan) {
                                        ((RHSQActivity) t).chosedJiguan = new RegionRes.DataBean();
                                    }
                                    ((RHSQActivity) t).chosedJiguan.setRegion_name(memberInfoBean.getApply_data().getNative_place());
                                    ((RHSQActivity) t).filePath = memberInfoBean.getApply_data().getAvatar();
                                }
                            }
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_sht_rhsq);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (null != getIntent()) {
            toRHSQBean = (ToRHSQBean) getIntent().getSerializableExtra("toRHSQBean");
        }

        if (null != toRHSQBean) {
            setTitle();
        }
        initStartDateTime = DateUtil.getDateNowString();// 获取当前时间格式为 2013年9月3日

        if (null != toRHSQBean) {
            initView();
        }

        if (null != toRHSQBean && Constants.SHTSH_RHSQ_NORELATION != toRHSQBean.getStatus()) {
            getData();
        }

        setSexManChosed();
    }

    private void initView() {
        //如果是拒绝的情况，那么弹框提示审核不通过的原因
        if (toRHSQBean.getStatus() == Constants.SHTSH_RHSQ_REJECT) {
            DialogUtils.getInstance().AlertMessage(RHSQActivity.this,
                    "审核未通过", "原因：" + toRHSQBean.getReason(), "退出", "修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RHSQActivity.this.finish();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            headRightTv.setText("提交");
                        }
                    });
        }

        shtRhsqRhxzCb.setChecked(true);

        //入会须知
        String khxzStr = "我已阅读并同意《入会须知》";
        SpannableString spannableInfo = new SpannableString(khxzStr);

        final Intent intent = new Intent(RHSQActivity.this, ShowWebInfoActivity.class);
        WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
        wf.get().setTitle("入会须知");
        wf.get().setUrl(UrlManager.URL_WEB_NOTICE);
        intent.putExtra("showWebBean", wf.get());

        spannableInfo.setSpan(new TextClickable(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        }), (khxzStr.length() - 6), khxzStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        shtRhsqRhxzTv.setText(spannableInfo);
        shtRhsqRhxzTv.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        shtRhsqRhxzTv.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("入会申请");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);

            if (Constants.SHTSH_RHSQ_REJECT == toRHSQBean.getStatus()) {
                headRightTv.setText("修改");
            } else if (Constants.SHTSH_RHSQ_NORELATION == toRHSQBean.getStatus()) {
                headRightTv.setText("提交");
            }
        }
    }


    private boolean canCommit() {
        if (null == toRHSQBean) {
            //TODO:流程异常
            CrashHandler.getInstance().handlerError("进入入会申请页面的参数为空");
        }

        if (!SystemUtil.getInstance().getIsLogin()) {
            ToastUtils.showOneToast(getApplicationContext(), "登陆信息失效");
            return false;
        }
        if (StringUtil.isEmpty(shtRhsqNameEdt.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "名字不能为空");
            return false;
        }
        if (chosedJiguan == null || StringUtil.isEmpty(shtRhsqJiguanTv.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "籍贯不能为空");
            return false;
        }
        if (StringUtil.isEmpty(shtRhsqMinzuTv.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "民族不能为空");
            return false;
        }
        if (StringUtil.isEmpty(shtRhsqAddressEdt.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "通讯地址不能为空");
            return false;
        }
        if (StringUtil.isEmpty(shtRhsqPhoneTv.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "联系电话不能为空");
            return false;
        }
        if (StringUtil.isEmpty(shtRhsqZhiwuEdt.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "社会职务不能为空");
            return false;
        }
        if (StringUtil.isEmpty(filePath)) {
            ToastUtils.showOneToast(getApplicationContext(), "头像不能为空或上传失败");
            return false;
        }
        if (!shtRhsqRhxzCb.isChecked()) {
            ToastUtils.showOneToast(getApplicationContext(), "请勾选入会须知");
            return false;
        }
        return true;
    }

    /**
     * token 	是 	string 	token值
     * user_id 	是 	int 	商会用户id号
     * name 	是 	string 	名称
     * sex 	是 	int 	性别 1 男 2 女
     * birthday 	是 	string 	生日
     * native_place 	是 	string 	籍贯
     * nation 	是 	string 	民族
     * college 	否 	string 	毕业院校
     * address 	是 	string 	通讯地址
     * mobile 	是 	string 	联系电话
     * func 	是 	string 	社会职务
     * avatar 	是 	string 	头像
     */
    private void commit() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_home_sht_rhsq_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_APPLYINTOMEMBER;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        builder.add("user_id", toRHSQBean.getId() + "");
        builder.add("name", shtRhsqNameEdt.getText().toString());
//        String sex = ((RadioButton) findViewById(shtRhsqSexRg.getCheckedRadioButtonId())).getText().toString().equals("男") ? "1" : "2";
        builder.add("sex", sexChosed);
        builder.add("birthday", shtRhsqBirthTv.getText().toString().replace("-", ""));
        builder.add("native_place", chosedJiguan == null ? "" : chosedJiguan.getRegion_name());
        builder.add("nation", shtRhsqMinzuTv.getText().toString());
        if (StringUtil.isNotEmpty(shtRhsqZhuanyeEdt.getText().toString())) {
            builder.add("college", shtRhsqZhuanyeEdt.getText().toString());
        }
        builder.add("address", shtRhsqAddressEdt.getText().toString());
        builder.add("mobile", shtRhsqPhoneTv.getText().toString().replaceAll("\\(+", "").replaceAll("\\)", ""));
        builder.add("func", shtRhsqZhiwuEdt.getText().toString());
        builder.add("avatar", filePath);

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

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = INFO_WHAT;
                                msg.obj = "申请成功";
                                myHandler.sendMessage(msg);
                                RHSQActivity.this.finish();
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

    /*****************************************
     * 上传图片
     ***************************************/
    private static final int PHOTO_REQUEST_CAMERA = 1000;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2000;// 从相册中选择
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
                popupWindow.showAtLocation(this.findViewById(R.id.activity_home_sht_rhsq_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
        popupWindow.showAtLocation(this.findViewById(R.id.activity_home_sht_rhsq_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Constants.GETASCRIPTION_RETURNCODE) {
            if (null != shtRhsqJiguanTv && data != null) {
                chosedJiguan = (RegionRes.DataBean) data.getSerializableExtra("ascription");
                if (chosedJiguan != null) {
                    shtRhsqJiguanTv.setText(chosedJiguan.getRegion_name());
                }
            }
        }

        if (requestCode == 2 && resultCode == Constants.GETAREACODE_PHONENO_RETURNCODE) {
            if (null != data) {
                String areaCode = data.getStringExtra("areaCode");
                String phoneNo = data.getStringExtra("phoneNo");
                shtRhsqPhoneTv.setText("(+" + areaCode + ")" + phoneNo);
            }
        }

        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data == null) {
                return;
            }
            // 得到图片的全路径
            Uri uri = data.getData();
            uriImg = uri;
            PicassoUtils.getinstance().loadNormalByUri(RHSQActivity.this, uriImg, shtRhsqPhotoIv);

            if (StringUtil.isNotEmpty(uriImg + "")) {
                uploadHeadPortrait(uriImg);
            }
        } else if (requestCode == PHOTO_REQUEST_CAMERA && resultCode == -1) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                if (tempFile.exists()) {
                    uriImg = Uri.fromFile(tempFile);
                    PicassoUtils.getinstance().loadNormalByUri(RHSQActivity.this, uriImg, shtRhsqPhotoIv);

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

                    shtRhsqPhotoIv.setImageDrawable(getResources().getDrawable(R.drawable.person_defaultheadportrait));
                }
            } else {
                Toast.makeText(RHSQActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
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
        Bitmap bitmap = compress.compressFromUri(RHSQActivity.this, options);
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
            loadingBar = LoadingBar.make(findViewById(R.id.activity_home_sht_rhsq_rootlayout), new CustomLoadingFactory());
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
    /*****************************************上传图片***************************************/

    /**
     * 设置民族
     */
    private void setMinzu() {
        initMinzuData();
        getMinzuPopupWindow();
    }


    /*********************************************选择民族*******************************************/
    List<String> minzus = new ArrayList<String>();

    private void initMinzuData() {
        minzus.add("汉族");
        minzus.add("壮族");
        minzus.add("满族");
        minzus.add("回族");
        minzus.add("苗族");
        minzus.add("维吾尔族");
        minzus.add("土家族");
        minzus.add("彝族");
        minzus.add("蒙古族");
        minzus.add("藏族");
        minzus.add("布依族");
        minzus.add("侗族");
        minzus.add("瑶族");
        minzus.add("朝鲜族");
        minzus.add("白族");
        minzus.add("哈尼族");
        minzus.add("哈萨克族");
        minzus.add("黎族");
        minzus.add("傣族");
        minzus.add("畲族");
        minzus.add("傈僳族");
        minzus.add("仡佬族");
        minzus.add("东乡族");
        minzus.add("高山族");
        minzus.add("拉祜族");
        minzus.add("水族");
        minzus.add("佤族");
        minzus.add("纳西族");
        minzus.add("羌族");
        minzus.add("土族");
        minzus.add("仫佬族");
        minzus.add("锡伯族");
        minzus.add("柯尔克孜族");
        minzus.add("达斡尔族");
        minzus.add("景颇族");
        minzus.add("毛南族");
        minzus.add("撒拉族");
        minzus.add("布朗族");
        minzus.add("塔吉克族");
        minzus.add("阿昌族");
        minzus.add("普米族");
        minzus.add("鄂温克族");
        minzus.add("怒族");
        minzus.add("京族");
        minzus.add("基诺族");
        minzus.add("德昂族");
        minzus.add("保安族");
        minzus.add("俄罗斯族");
        minzus.add("裕固族");
        minzus.add("乌兹别克族");
        minzus.add("门巴族");
        minzus.add("鄂伦春族");
        minzus.add("独龙族");
        minzus.add("塔塔尔族");
        minzus.add("赫哲族");
        minzus.add("珞巴族");
    }

    private PopupWindow minzuPopupWindow;

    public void getMinzuPopupWindow() {
        if (null != minzuPopupWindow) {
            //如果minzuPopupWindow正在显示，接下来隐藏
            if (minzuPopupWindow.isShowing()) {
                minzuPopupWindow.dismiss();
            } else {
                //产生背景变暗效果
                setBackgroundAlpha(0.3f);
                minzuPopupWindow.showAtLocation(this.findViewById(R.id.activity_home_sht_rhsq_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        } else {
            initMinzuPopupWindow();
        }
    }

    private void initMinzuPopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View minzuPopupWindow_view = inflater.inflate(R.layout.activity_home_shtsh_rhsq_minzu, null, false);
        minzuPopupWindow = new PopupWindow(minzuPopupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        minzuPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        minzuPopupWindow.setBackgroundDrawable(new PaintDrawable());
        minzuPopupWindow.setOutsideTouchable(true);
        minzuPopupWindow.setFocusable(true);
        minzuPopupWindow_view.setFocusable(true);
        minzuPopupWindow_view.setFocusableInTouchMode(true);

        shtRhsqMinzuTv.setText(minzus.get(0));

        TextView minzuConfirmTv = (TextView) minzuPopupWindow_view.findViewById(R.id.minzu_confirm_tv);
        final WheelView wheelView = (WheelView) minzuPopupWindow_view.findViewById(R.id.wheelview);
        wheelView.lists(minzus)
                .fontSize(35)
                .showCount(9)
                .selectTip("民族")
                .select(0)
                .listener(new WheelView.OnWheelViewItemSelectListener() {

                    @Override
                    public void onItemSelect(int index) {
                        System.out.println("wheelView.getSelectItem():" + wheelView.getSelectItem()
                                + "---index:" + index + "---minzus.get(index):" + minzus.get(index));
                        shtRhsqMinzuTv.setText(minzus.get(index));
                    }
                }).build();

        minzuConfirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minzuPopupWindow.dismiss();
            }
        });

        minzuPopupWindow_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    minzuPopupWindow.dismiss();
                    minzuPopupWindow = null;
                    return true;
                }
                return false;
            }
        });

        minzuPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
                minzuPopupWindow = null;
            }
        });


        setBackgroundAlpha(0.3f);
        minzuPopupWindow.showAtLocation(this.findViewById(R.id.activity_home_sht_rhsq_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /*********************************************选择民族*******************************************/


    private RegionRes.DataBean chosedJiguan;

    /**
     * 商会-成员信息信息接口
     * id 	否 	int 	成员id号 (商会成员列表 根据id 查会员信息 )
     * token 	否 	string 	token值 （申请人获取自己填写的资料）
     */
    private void getData() {
        if (StringUtil.isEmpty(SystemUtil.getInstance().getToken())) {
            ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
            return;
        }
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_home_sht_rhsq_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SHMANAGER_MEMBERINFO;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
//        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
        builder.add("token", SystemUtil.getInstance().getToken());
        Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
//        }
//        builder.add("id", toRHSQBean.getId() + "");
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
                                msg.what = Constants.WHAT_GETDATA;
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

    private String sexChosed = "1";//男"1" ，女 "2"

    private void setSexManChosed() {
        sexChosed = "1";
        shtRhsqSexManTv.setTextColor(getResources().getColor(R.color.black));
        shtRhsqSexLadyTv.setTextColor(getResources().getColor(R.color.textgray));
        shtRhsqSexManTv.setBackground(getResources().getDrawable(R.drawable.rectangle_gray2edge_style));
        shtRhsqSexLadyTv.setBackground(getResources().getDrawable(R.drawable.rectangle_graybg_style));
    }

    private void setSexLadyChosed() {
        sexChosed = "2";
        shtRhsqSexManTv.setTextColor(getResources().getColor(R.color.textgray));
        shtRhsqSexLadyTv.setTextColor(getResources().getColor(R.color.black));
        shtRhsqSexManTv.setBackground(getResources().getDrawable(R.drawable.rectangle_graybg_style));
        shtRhsqSexLadyTv.setBackground(getResources().getDrawable(R.drawable.rectangle_gray2edge_style));
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.sht_rhsq_birth_tv, R.id.sht_rhsq_jiguan_tv, R.id.sht_rhsq_minzu_tv, R.id.sht_rhsq_phone_tv, R.id.sht_rhsq_photo_iv, R.id.sht_rhsq_sex_man_tv, R.id.sht_rhsq_sex_lady_tv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                RHSQActivity.this.finish();
                break;
            case R.id.head_right_tv:
                SoftInputUtil.hideSoftInput(RHSQActivity.this, headRightTv);
                if (headRightTv.getText().equals("修改")) {
                    //popupwindow消失掉

                } else {
                    if (canCommit()) {
                        commit();
                    }
                }
                break;
            case R.id.sht_rhsq_birth_tv:
                DateTimepickDialogUtils mDTPicker = new DateTimepickDialogUtils(this);
                mDTPicker.setDateFormat("yyyy-MM-dd");
                mDTPicker.setmTheme(1);
                mDTPicker.dateTimePickDialog(shtRhsqBirthTv, DateTimepickDialogUtils.TYPE.DATE);
                break;
            case R.id.sht_rhsq_jiguan_tv:
                intent = new Intent(RHSQActivity.this, AscriptionActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.sht_rhsq_minzu_tv:
                setMinzu();
                break;
            case R.id.sht_rhsq_phone_tv:
                intent = new Intent(RHSQActivity.this, ManagerPhoneNoActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.sht_rhsq_photo_iv:
                getPopupWindow();
                break;
            case R.id.sht_rhsq_sex_man_tv:
                setSexManChosed();
                break;
            case R.id.sht_rhsq_sex_lady_tv:
                setSexLadyChosed();
                break;
        }
    }

}
