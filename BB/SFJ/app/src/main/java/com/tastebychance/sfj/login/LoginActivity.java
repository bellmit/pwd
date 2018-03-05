package com.tastebychance.sfj.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.HostActivity;
import com.tastebychance.sfj.MyAppCompatActivity;
import com.tastebychance.sfj.MyApplication;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.bean.EventLogout;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.login.bean.LoginBean;
import com.tastebychance.sfj.login.forgetpwd.ForgetPwdActivity;
import com.tastebychance.sfj.util.AppManager;
import com.tastebychance.sfj.util.CrashHandler;
import com.tastebychance.sfj.util.EventBusUtils;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.ToastUtils;
import com.tastebychance.sfj.util.UrlManager;
import com.tastebychance.sfj.util.okgoutils.CommonOkGo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 登录
 *
 * @author shenbinghong
 * @time 2018/2/1 09:48
 * @path com.tastebychance.sfj.login.LoginActivity.java
 */
public class LoginActivity extends MyAppCompatActivity {

    @BindView(R.id.activity_login_logo_iv)
    ImageView activityLoginLogoIv;
    @BindView(R.id.activity_login_account_icon_iv)
    ImageView activityLoginAccountIconIv;
    @BindView(R.id.activity_login_account_edt)
    EditText activityLoginAccountEdt;
    @BindView(R.id.activity_login_input_ll)
    LinearLayout activityLoginInputLl;
    @BindView(R.id.activity_login_pwd_icon_iv)
    ImageView activityLoginPwdIconIv;
    @BindView(R.id.activity_login_pwd_edt)
    EditText activityLoginPwdEdt;
    @BindView(R.id.activity_login_pwd_ll)
    LinearLayout activityLoginPwdLl;
    @BindView(R.id.activity_login_rememberaccount_cb)
    CheckBox activityLoginRememberaccountCb;
    @BindView(R.id.activity_login_rememberaccount_tv)
    TextView activityLoginRememberaccountTv;
    @BindView(R.id.activity_login_forgetpwd_tv)
    TextView activityLoginForgetpwdTv;
    @BindView(R.id.activity_login_operation_rl)
    RelativeLayout activityLoginOperationRl;
    @BindView(R.id.activity_login_confirm_tv)
    TextView activityLoginConfirmTv;
    @BindView(R.id.activity_login_rootlayout)
    LinearLayout activityLoginRootlayout;
    @BindView(R.id.activity_login_rl)
    RelativeLayout activityLoginRl;


    private static class MyHandler<T extends Activity> extends MyBaseHandler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t) {
                try {
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:
                            LoginBean.DataBean loginBean = resInfo.getClass(LoginBean.DataBean.class);
                            SystemUtil.getInstance().saveObjectToSP(Constants.KEY_USERINFO, loginBean.getInfo());
                            SystemUtil.getInstance().saveStrToSP(Constants.KEY_TOKEN, loginBean.getToken());

                            SystemUtil.getInstance().saveBooleanToSP(Constants.KEY_LOGINSTATE, true);
                            t.startActivity(new Intent(t, HostActivity.class));
                            t.finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initListener() {
        activityLoginPwdEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!TextUtils.isEmpty(activityLoginAccountEdt.getText().toString()) && !TextUtils.isEmpty(activityLoginPwdEdt.getText().toString())) {
                    activityLoginConfirmTv.setBackground(getResources().getDrawable(R.drawable.circle5_darkbluebg_style));
                } else {
                    activityLoginConfirmTv.setBackground(getResources().getDrawable(R.drawable.circle5_graybg_style));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initView() {
        if (!TextUtils.isEmpty(SystemUtil.getInstance().getStrFromSP(Constants.KEY_ACCOUNT))) {
            activityLoginAccountEdt.setText(SystemUtil.getInstance().getStrFromSP(Constants.KEY_ACCOUNT));
        }

        activityLoginRememberaccountCb.setChecked(true);
    }

    private boolean canLogin() {
        if (!TextUtils.isEmpty(activityLoginAccountEdt.getText().toString()) && !TextUtils.isEmpty(activityLoginPwdEdt.getText().toString())) {
            return true;
        }

        ToastUtils.showOneToast(MyApplication.getContext(), Constants.LOGIN_NULL);
        return false;
    }

    /**
     * user_login	是	string	账户
     * user_pass	是	string	密码
     */
    private void login() {
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.activity_login_rootlayout));

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_LOGIN;
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("user_login", activityLoginAccountEdt.getText().toString());
        map.put("user_pass", activityLoginPwdEdt.getText().toString());
        CommonOkGo.getInstance().postByOkGo(url, map, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    CommonOkGo.getInstance().dialogCancel();
                    final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                    if (resInfo.getCode() == Constants.NET_RETURNCODE_SUC) {

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
                        msg.what = CommonOkGo.ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        CommonOkGo.getInstance().myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                CommonOkGo.getInstance().dialogCancel();
                super.onError(call, response, e);
            }
        });
    }

    @Override
    public void finish() {
        if (SystemUtil.getInstance().getBooleanFromSP(Constants.KEY_LOGOUT)) {
            SystemUtil.getInstance().saveBooleanToSP(Constants.KEY_LOGOUT, false);
            AppManager.getInstance().appExit(this);
        } else {
            super.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemUtil.getInstance().saveBooleanToSP(Constants.KEY_LOGOUT, false);
    }

    @OnClick({R.id.activity_login_rememberaccount_tv, R.id.activity_login_forgetpwd_tv, R.id.activity_login_confirm_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_login_rememberaccount_tv:
                activityLoginRememberaccountCb.setChecked(!activityLoginRememberaccountCb.isChecked());
                break;
            case R.id.activity_login_forgetpwd_tv:
                startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
                break;
            case R.id.activity_login_confirm_tv:

                if (canLogin()) {
                    login();
                }

                if (activityLoginRememberaccountCb.isChecked()) {
                    if (TextUtils.isEmpty(activityLoginAccountEdt.getText().toString())) {
                        return;
                    }
                    SystemUtil.getInstance().saveStrToSP(Constants.KEY_ACCOUNT, activityLoginAccountEdt.getText().toString());
                } else {
                    SystemUtil.getInstance().saveStrToSP(Constants.KEY_ACCOUNT, "");
                }
                break;
            default:
                break;
        }
    }

}