package rongshanghui.tastebychance.com.rongshanghui.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.mobileim.FeedbackAPI;
import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWChannel;
import com.alibaba.mobileim.YWConstants;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.WxLog;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.login.YWLoginCode;
import com.alibaba.mobileim.login.YWLoginState;
import com.alibaba.mobileim.utility.IMPrefsTools;
import com.alibaba.tcms.env.YWEnvType;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseTransparentThemeActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.common.Constant;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.demo.FragmentTabs;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.NotificationInitSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.UserProfileSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.bean.AlibabaUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventLoginSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventLoginSucToLoginAlibaba;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventRegisterSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventTaobaoUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.web.ShowWeb2Activity;
import rongshanghui.tastebychance.com.rongshanghui.common.web.ShowWebInfoActivity;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.AreaCodeActivity;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCodeInfo;
import rongshanghui.tastebychance.com.rongshanghui.login.bean.LoginRes;
import rongshanghui.tastebychance.com.rongshanghui.register.RegisterEntranceTypeActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.MD5Util;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.ScreenUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SharedPreferencesUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.TextClickable;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.util.network.MyCallBack;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 登录
 *
 * @author shenbh
 * @time 2017/12/4 09:56
 */
public class LoginActivity extends MyBaseTransparentThemeActivity {


    /*alibaba*/
    private static final int GUEST = 1;
    private static final String TAG = LoginActivity.class.getSimpleName();
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
    @BindView(R.id.Login_logo_iv)
    ImageView LoginLogoIv;
    @BindView(R.id.logiin_area_tv)
    TextView logiinAreaTv;
    @BindView(R.id.login_area_rl)
    RelativeLayout loginAreaRl;
    @BindView(R.id.login_getarea_tv)
    TextView getareaTv;
    @BindView(R.id.login_phoneno_edt)
    EditText loginPhonenoEdt;
    @BindView(R.id.login_verifycode_edt)
    EditText loginVerifycodeEdt;
    @BindView(R.id.login_verifycode_rl)
    RelativeLayout loginVerifycodeRl;
    @BindView(R.id.login_getverficode_tv)
    TextView getverficodeTv;
    @BindView(R.id.personalcenter_phoneno_cb)
    CheckBox personalcenterPhonenoCb;
    @BindView(R.id.login_yhxz_tv)
    TextView loginYhxzTv;
    @BindView(R.id.login_yhxz_ll)
    LinearLayout loginYhxzLl;
    @BindView(R.id.login_login_tv)
    TextView loginLoginTv;
    @BindView(R.id.login_register_tv)
    TextView loginRegisterTv;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    private LoginSampleHelper loginHelper;
    @BindView(R.id.login_progress)
    ProgressBar progressBar;

    private Handler handler = new Handler(Looper.getMainLooper());
    private ImageView lg;


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
                        case 1:
                            if (Constants.IS_DEVELOPING) {
                                ToastUtils.showOneToast(t.getApplicationContext(), (String) msg.obj);
                            }
                            break;
                        case 2:

                            break;
                        case 3:
                            ToastUtils.showOneToast(t.getApplicationContext(), (String) msg.obj);
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

    private MyHandler mHandler = new MyHandler(this);

    private String toActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null != intent) {
            toActivity = intent.getStringExtra("toActivity");
        }

        setTitle();
        init();
        myRegisterReceiver();
    }

    private void setTitle() {
        headCenterTitleTv.setText("单位入口");
        headRightTv.setText("前往个人入口");
        headRightTv.setVisibility(View.VISIBLE);
        headRightTv.setTextSize(12);
    }

    private void init() {
        //客户需知
        String khxzStr = "我已阅读并同意《用户须知》";
        SpannableString spannableInfo = new SpannableString(khxzStr);

        final Intent intent = new Intent(LoginActivity.this, ShowWebInfoActivity.class);
        WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
        wf.get().setTitle("用户须知");
        wf.get().setUrl(UrlManager.URL_WEB_USERNOTICE);
        intent.putExtra("showWebBean", wf.get());

        spannableInfo.setSpan(new TextClickable(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        }), (khxzStr.length() - 6), khxzStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginYhxzTv.setText(spannableInfo);
        loginYhxzTv.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        loginYhxzTv.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明


        updateLoginType();

        personalcenterPhonenoCb.setChecked(true);

        loginLoginTv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                validateData();
            }
        });
    }

    private void updateLoginType() {
        loginLoginTv.setVisibility(View.VISIBLE);
        if (headRightTv.getText().toString().contains("个人")) {//1：个人登录 2：单位登录
            personOrEnterpriseType = "2";
            loginRegisterTv.setVisibility(View.VISIBLE);
        } else {
            personOrEnterpriseType = "1";
            loginRegisterTv.setVisibility(View.GONE);
        }
    }

    private String selectedAreaCode = "86";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Constants.GETAREACODE_RETURNCODE) {
            if (null != data) {
                AreaCodeInfo selectedAreaData = (AreaCodeInfo) data.getSerializableExtra("selectedAreaData");
                if (logiinAreaTv != null) {
                    logiinAreaTv.setText("+" + selectedAreaData.getCode() + "(" + selectedAreaData.getName() + ")");
                    selectedAreaCode = selectedAreaData.getCode();
                }
            }
        }
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_login), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //采用okhttp3来进行网络请求
        String url = UrlManager.URL_GETVERIFYCODE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        /**
         * areacode 	是 	string 	区号
         mobile 	是 	string 	手机号
         type 	否 	int 	空 默认直接发送 1 登录 2 注册 3 更换手机号
         */
        RequestBody formBody = new FormBody.Builder()
                .add("areacode", selectedAreaCode)
                .add("mobile", loginPhonenoEdt.getText().toString().replaceAll(" ", ""))
                .add("type", "1")
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

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);

                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = INFO_WHAT;
                                msg.obj = "获取验证码成功";
                                myHandler.sendMessage(msg);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = 3;
                        msg.obj = resInfo.getError_message();
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 登录
     */
    private String personOrEnterpriseType = "2";

    private void login() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_login), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), "phoneno", loginPhonenoEdt.getText().toString().replaceAll(" ", ""));
        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), "areaCode", selectedAreaCode);

        /**
         areacode 	是 	string 	区号
         mobile 	是 	string 	手机号
         verify 	是 	string 	验证码
         type 	是 	int 	1：个人登录 2：单位登录
         */
        //采用okhttp3来进行网络请求
        String url = UrlManager.URL_VERIFYCODELOGIN;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("areacode", selectedAreaCode)
                .add("mobile", loginPhonenoEdt.getText().toString().replaceAll(" ", ""))
                .add("verify", loginVerifycodeEdt.getText().toString())
                .add("type", personOrEnterpriseType)
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
                                ToastUtils.showOneToast(MyApplication.getContext(), "登录成功");
                                SystemUtil.getInstance().clearData();

                                LoginRes loginRes = JSONObject.parseObject(resInfo.getData().toString(), LoginRes.class);
                                SystemUtil.getInstance().setToken(loginRes.getToken());
                                System.out.println("token = " + loginRes.getToken());
                                SystemUtil.getInstance().setUserInfo(loginRes.getUser_info());
                                SystemUtil.getInstance().setIsLogin(true);

                                SystemUtil.getInstance().saveIntToSP("registatus", loginRes.getUser_info().getRegi_status());

                                WeakReference<EventLoginSuc> wf = new WeakReference<EventLoginSuc>(new EventLoginSuc());
                                wf.get().setToActivity(toActivity);
//                                EventBusUtils.post(wf.get());
                                EventBusUtils.postSticky(wf.get());

                                WeakReference<EventLoginSucToLoginAlibaba> wf2 = new WeakReference<EventLoginSucToLoginAlibaba>(new EventLoginSucToLoginAlibaba());
                                wf2.get().setUserid(loginRes.getUser_info().getUser_login());
                                wf2.get().setUserid(MD5Util.md5Code(loginRes.getUser_info().getUser_login()));
                                wf2.get().setAppkey(Constants.APP_KEY);
                                EventBusUtils.post(wf2.get());

                                //保存到本地，供阿里百川使用
                                SystemUtil.getInstance().saveLoginInfoToLocal(loginRes.getUser_info().getUser_login(), MD5Util.md5Code(loginRes.getUser_info().getUser_login()), Constants.APP_KEY);

                                System.out.println("--------------------------------------alibaichuan-------------------------------------------");
                                System.out.println("userlogin = " + loginRes.getUser_info().getUser_login());
                                System.out.println("password = " + MD5Util.md5Code(loginRes.getUser_info().getUser_login()));
                                System.out.println("appkey = " + Constants.APP_KEY);

                                //阿里百川登录
                                loginAlibaba();
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = 3;
                        msg.obj = resInfo.getError_message();
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private boolean isNotEmpty() {
        if (StringUtil.isEmpty(logiinAreaTv.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "区号不能为空");
            return false;
        }
        if (StringUtil.isEmpty(loginPhonenoEdt.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "手机号码不能为空");
            return false;
        }
        if (StringUtil.isEmpty(loginVerifycodeEdt.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "验证码不能为空");
            return false;
        }
        return true;
    }

    private void validateData() {
        if (!isNotEmpty()) {
            return;
        }
        if (!personalcenterPhonenoCb.isChecked()) {
            ToastUtils.showOneToast(getApplicationContext(), "请勾选用户须知");
            return;
        }

        /*validateVerifyCode();*/
        login();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishThisActivity(@NonNull EventRegisterSuc eventRegisterSuc) {
        this.finish();
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


        myUnregisterReceiver();
    }

    /*alibaba*/
    public static final String AUTO_LOGIN_STATE_ACTION = "com.openim.autoLoginStateActionn";
    private BroadcastReceiver mAutoLoginStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final int state = intent.getIntExtra("state", -1);
            YWLog.i(TAG, "mAutoLoginStateReceiver, loginState = " + state);
            if (state == -1) {
                return;
            }
            handleAutoLoginState(state);
        }
    };

    private void startChattingActivity(String targetId) {
        startActivity(loginHelper.getIMKit().getChattingActivityIntent(targetId));
    }

    private void loginAlibaba() {
        loginHelper = LoginSampleHelper.getInstance();
        init(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID), Constants.APP_KEY);
        normalLogin();
    }

    private void normalLogin() {
        //判断当前网络状态，若当前无网络则提示用户无网络
        if (YWChannel.getInstance().getNetWorkState().isNetWorkNull()) {
            Toast.makeText(LoginActivity.this, "网络已断开，请稍后再试哦~", Toast.LENGTH_SHORT).show();
            return;
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(loginPhonenoEdt.getWindowToken(), 0);
        if (TextUtils.isEmpty(Constants.APP_KEY)) {
            Constants.APP_KEY = YWConstants.YWSDKAppKey;
        }
        init(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID), Constants.APP_KEY);
        progressBar.setVisibility(View.VISIBLE);
        loginHelper.login_Sample(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID), IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD), Constants.APP_KEY, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                SystemUtil.getInstance().saveLoginInfoToLocal(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID), IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD), Constants.APP_KEY);

                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                YWLog.i(TAG, "login success!");
                LoginActivity.this.finish();

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
                        SystemUtil.getInstance().saveLoginInfoToLocal(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID), IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD), Constants.APP_KEY);

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        YWLog.i(TAG, "login success!");
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        EventBusUtils.post(new EventTaobaoUser());
                    }

                    @Override
                    public void onError(Response response) {

                    }
                });
                /*progressBar.setVisibility(View.GONE);
                if (errorCode == YWLoginCode.LOGON_FAIL_INVALIDUSER) { //若用户不存在，则提示使用游客方式登陆
                    guest_login();
                } else {
                    YWLog.w(TAG, "登录失败，错误码：" + errorCode + "  错误信息：" + errorMessage);
                    IMNotificationUtils.getInstance().showToast(LoginActivity.this, errorMessage);
                }*/
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void taobaoUser(@NonNull EventTaobaoUser eventTaobaoUser) {
        SystemUtil.getInstance().taobaouser(1, new MyCallBack<AlibabaUser>() {
            @Override
            public void onLoadingBefore(Request request) {

            }

            @Override
            public void onSuccess(Response response, AlibabaUser result) {
                SystemUtil.getInstance().saveLoginInfoToLocal(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID), IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD), Constants.APP_KEY);

                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                YWLog.i(TAG, "login success!");
                LoginActivity.this.finish();
            }

            @Override
            public void onFailure(Request request, Exception e) {
//                EventBusUtils.post(new EventTaobaoUser());
            }

            @Override
            public void onError(Response response) {

            }
        });
    }

    private void init(String userId, String appKey) {
        //初始化imkit
        LoginSampleHelper.getInstance().initIMKit(userId, appKey);
        //自定义头像和昵称回调初始化(如果不需要自定义头像和昵称，则可以省去)
        UserProfileSampleHelper.initProfileCallback();
        //通知栏相关的初始化
        NotificationInitSampleHelper.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int loginState = LoginSampleHelper.getInstance().getAutoLoginState().getValue();
        handleAutoLoginState(loginState);
//        YWLog.i(TAG, "onResume, loginState = " + LoginSampleHelper.getInstance().getAutoLoginState().getValue());
        YWLog.i(TAG, "onResume, loginState = " + loginState);
    }

    private void myRegisterReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AUTO_LOGIN_STATE_ACTION);
        LocalBroadcastManager.getInstance(YWChannel.getApplication()).registerReceiver(mAutoLoginStateReceiver, filter);
    }

    private void myUnregisterReceiver() {
        LocalBroadcastManager.getInstance(YWChannel.getApplication()).unregisterReceiver(mAutoLoginStateReceiver);
    }

    private void handleAutoLoginState(int loginState) {
        if (loginState == YWLoginState.logining.getValue()) {
            if (progressBar.getVisibility() != View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }/*else if (loginState == YWLoginState.success.getValue()){
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(LoginActivity.this, FragmentTabs.class);
            LoginActivity.this.startActivity(intent);
            LoginActivity.this.finish();
        }*/ else {
            //当作失败处理
            progressBar.setVisibility(View.GONE);
        }
    }

    private IWxCallback mloginCallback = new IWxCallback() {
        @Override
        public void onSuccess(Object... result) {
            WxLog.d("test", "Feedback login success");

            Intent intent = FeedbackAPI.getFeedbackActivityIntent();
            if (intent != null) {
                startActivity(intent);
                LoginActivity.this.finish();
            }
        }

        @Override
        public void onError(int code, String info) {
            WxLog.d("test", "Feedback  login fail");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT);
                    finish();
                }
            });
        }

        @Override
        public void onProgress(int progress) {
        }
    };

   /*alibaba*/

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.login_getarea_tv, R.id.login_getverficode_tv, R.id.login_register_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                LoginActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (headRightTv.getText().toString().contains("个人")) {//1：个人登录 2：单位登录
                    headRightTv.setText("前往单位入口");
                    headCenterTitleTv.setText("个人入口");
                } else {
                    headRightTv.setText("前往个人入口");
                    headCenterTitleTv.setText("单位入口");
                }
                updateLoginType();
                break;
            case R.id.login_getarea_tv:
                startActivityForResult(new Intent(LoginActivity.this, AreaCodeActivity.class), 1);
                break;
            case R.id.login_getverficode_tv:
                SoftInputUtil.hideSoftInput(LoginActivity.this, getverficodeTv);
                if (StringUtil.isEmpty(logiinAreaTv.getText().toString())) {
                    ToastUtils.showOneToast(getApplicationContext(), "区号不能为空");
                    return;
                }
                if (StringUtil.isEmpty(loginPhonenoEdt.getText().toString())) {
                    ToastUtils.showOneToast(getApplicationContext(), "手机号码不能为空");
                    return;
                }
                getVerifyCode();
                SystemUtil.getInstance().countDownTime(getverficodeTv);
                break;
            case R.id.login_register_tv:
                startActivity(new Intent(LoginActivity.this, RegisterEntranceTypeActivity.class));
                break;
        }
    }

}