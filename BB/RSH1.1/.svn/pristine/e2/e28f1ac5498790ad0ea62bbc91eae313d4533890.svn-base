package rongshanghui.tastebychance.com.rongshanghui.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
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
import com.dyhdyh.widget.loading.bar.LoadingBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.ref.WeakReference;

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
import rongshanghui.tastebychance.com.rongshanghui.bean.EventRegisterSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.web.ShowWeb2Activity;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.AreaCodeActivity;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCodeInfo;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.SharedPreferencesUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.TextClickable;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

//import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCode2Info;

/**
 * 类描述：RegisterValidatePhoneNoActivity 手机验证
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/23 18:20
 * 修改人：
 * 修改时间：2017/11/23 18:20
 * 修改备注：
 *
 * @version 1.0
 */
public class RegisterValidatePhoneNoActivity extends MyBaseTransparentThemeActivity {


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
                            Intent intent = null;
                            if (StringUtil.isNotEmpty(((RegisterValidatePhoneNoActivity) t).registerType)) {
                                if (((RegisterValidatePhoneNoActivity) t).registerType.equals(Constants.REGISTERENTRANCETYPE_JG)) {
                                    intent = new Intent(t, RegisterJGActivity.class);
                                } else if (((RegisterValidatePhoneNoActivity) t).registerType.equals(Constants.REGISTERENTRANCETYPE_XX)
                                        || ((RegisterValidatePhoneNoActivity) t).registerType.equals(Constants.REGISTERENTRANCETYPE_YY)
                                        || ((RegisterValidatePhoneNoActivity) t).registerType.equals(Constants.REGISTERENTRANCETYPE_QT)) {

                                    intent = new Intent(t, RegisterXXYYQTActivity.class);
                                } else if (((RegisterValidatePhoneNoActivity) t).registerType.equals(Constants.REGISTERENTRANCETYPE_SH)
                                        || ((RegisterValidatePhoneNoActivity) t).registerType.equals(Constants.REGISTERENTRANCETYPE_QY)) {
                                    intent = new Intent(t, RegisterSHQYActivity.class);
                                } else if (((RegisterValidatePhoneNoActivity) t).registerType.equals(Constants.REGISTERENTRANCETYPE_BM)
                                        || ((RegisterValidatePhoneNoActivity) t).registerType.equals(Constants.REGISTERENTRANCETYPE_ZJ)) {
                                    intent = new Intent(t, RegisterBMZJEntranceActivity.class);
                                }
                            }

                            //保存手机号码和区号
                            SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), "phoneno", ((RegisterValidatePhoneNoActivity) t).loginPhonenoEdt.getText().toString().replaceAll(" ", ""));
                            SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), "areaCode", ((RegisterValidatePhoneNoActivity) t).selectedAreaCode);

                            if (null != intent) {
                                intent.putExtra("registerType", ((RegisterValidatePhoneNoActivity) t).registerType);
                                ((RegisterValidatePhoneNoActivity) t).startActivity(intent);
                            }
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
    @BindView(R.id.logiin_area_tv)
    TextView logiinAreaTv;
    @BindView(R.id.login_getarea_tv)
    TextView loginGetareaTv;
    @BindView(R.id.login_area_rl)
    RelativeLayout loginAreaRl;
    @BindView(R.id.login_phoneno_edt)
    EditText loginPhonenoEdt;
    @BindView(R.id.login_verifycode_edt)
    EditText loginVerifycodeEdt;
    @BindView(R.id.login_getverficode_tv)
    TextView loginGetverficodeTv;
    @BindView(R.id.login_verifycode_rl)
    RelativeLayout loginVerifycodeRl;
    @BindView(R.id.personalcenter_phoneno_cb)
    CheckBox personalcenterPhonenoCb;
    @BindView(R.id.login_yhxz_tv)
    TextView loginYhxzTv;
    @BindView(R.id.login_yhxz_ll)
    LinearLayout loginYhxzLl;
    @BindView(R.id.login_singlelogin_tv)
    TextView loginSingleloginTv;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;

    private String registerType = Constants.REGISTERENTRANCETYPE_SH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_validate_phone_no);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            registerType = getIntent().getStringExtra("registerType");
        }
        init();

        setTitle();
    }

    private void setTitle() {
        headCenterTitleTv.setText("手机验证");
    }

    private void init() {
        //客户需知
        String khxzStr = "我已阅读并同意《用户须知》";
        SpannableString spannableInfo = new SpannableString(khxzStr);
        final Intent intent = new Intent(RegisterValidatePhoneNoActivity.this, ShowWeb2Activity.class);
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

        personalcenterPhonenoCb.setChecked(true);

       /* if (selectedAreaCode.equals("86")) {

            //监听号码输入
            loginPhonenoEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    Editable editable = loginPhonenoEdt.getText();
                    int len = editable.length();

                    String tempPhoneNo = editable.toString().replace(" ", "");
                    if (tempPhoneNo.length() >= 11 && !StringUtil.isMobileNO(tempPhoneNo)) {
                        Toast.makeText(RegisterValidatePhoneNoActivity.this, "手机号码格式不正确！", Toast.LENGTH_SHORT).show();
                    }

                    if (count == 1) {
                        int lenth = charSequence.toString().length();
                        if (lenth == 3 || lenth == 8) {
                            loginPhonenoEdt.setText(charSequence + " ");
                            loginPhonenoEdt.setSelection(loginPhonenoEdt.getText().toString().length());
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }*/

        loginSingleloginTv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                next();
            }
        });
    }

    /**
     * area_code 	是 	string 	区号
     * mobile 	是 	string 	手机号
     * verify 	是 	string 	验证码
     */
    private void check() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_login), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_CHECKVERIFY;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("area_code", selectedAreaCode)
                .add("mobile", loginPhonenoEdt.getText().toString().replaceAll(" ", ""))
                .add("verify", loginVerifycodeEdt.getText().toString())
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
                                Message msg = new Message();
                                msg.what = 2;
                                msg.obj = resInfo;
                                mHandler.sendMessage(msg);
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

    private String selectedAreaCode = "86";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Constants.GETAREACODE_RETURNCODE) {
            if (null != data) {
                AreaCodeInfo selectedAreaData = (AreaCodeInfo) data.getSerializableExtra("selectedAreaData");
                if (logiinAreaTv != null) {
                    logiinAreaTv.setText("+" + selectedAreaData.getCode() + "(" + selectedAreaData.getName() + ")");
                    selectedAreaCode = selectedAreaData.getCode();
                }
            }
        }
    }

    private void getVerifyCode() {

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
                .add("type", "2")
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);

                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isPhoneCanUse = true;
                                ToastUtils.showOneToast(getApplicationContext(), "验证码发送成功");
                            }
                        });
                    } else {
                        isPhoneCanUse = false;

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

    private boolean isPhoneCanUse = true;

    private void next() {
        if (!isNotEmpty()) {
            return;
        }

        if (!isPhoneCanUse) {
            ToastUtils.showOneToast(getApplicationContext(), "该手机号不可用,请核查后再注册");
            return;
        }

        if (!personalcenterPhonenoCb.isChecked()) {
            ToastUtils.showOneToast(getApplicationContext(), "请勾选用户须知");
            return;
        }

        check();
    }

   /* BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RegisterValidatePhoneNoActivity.this.finish();
        }
    };*/

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
        /*if (null != broadcastReceiver){
            unregisterReceiver(broadcastReceiver);
        }*/

        EventBusUtils.unregister(this);
    }

    @OnClick({R.id.head_left_iv, R.id.login_getarea_tv, R.id.login_getverficode_tv})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.head_left_iv:
                RegisterValidatePhoneNoActivity.this.finish();
                break;
            case R.id.login_getarea_tv:
                intent = new Intent(RegisterValidatePhoneNoActivity.this, AreaCodeActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.login_getverficode_tv:
                SoftInputUtil.hideSoftInput(RegisterValidatePhoneNoActivity.this, loginGetverficodeTv);
                if (StringUtil.isEmpty(logiinAreaTv.getText().toString())) {
                    ToastUtils.showOneToast(getApplicationContext(), "区号不能为空");
                    return;
                }
                if (StringUtil.isEmpty(loginPhonenoEdt.getText().toString())) {
                    ToastUtils.showOneToast(getApplicationContext(), "手机号码不能为空");
                    return;
                }
                getVerifyCode();
                SystemUtil.getInstance().countDownTime(loginGetverficodeTv);
                break;
        }
    }

}
