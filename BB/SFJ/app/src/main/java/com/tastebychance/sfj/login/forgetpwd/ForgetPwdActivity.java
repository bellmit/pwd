package com.tastebychance.sfj.login.forgetpwd;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyAppCompatActivity;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.util.CrashHandler;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.ToastUtils;
import com.tastebychance.sfj.util.UrlManager;
import com.tastebychance.sfj.util.Validator;
import com.tastebychance.sfj.util.okgoutils.CommonOkGo;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 忘记密码
 *
 * @author shenbinghong
 * @time 2018/1/31 20:38
 * @path com.tastebychance.sfj.login.forgetpwd.ForgetPwdActivity.java
 */
public class ForgetPwdActivity extends MyAppCompatActivity {

    @BindView(R.id.mine_topblank_iv)
    View mineTopblankIv;
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
    @BindView(R.id.activity_forgetpwd_phoneno_edt)
    EditText activityForgetpwdPhonenoEdt;
    @BindView(R.id.activity_forgetpwd_verifycode_edt)
    EditText activityForgetpwdVerifycodeEdt;
    @BindView(R.id.activity_forgetpwd_getverifycode_tv)
    TextView activityForgetpwdGetverifycodeTv;
    @BindView(R.id.activity_forgetpwd_account_edt)
    EditText activityForgetpwdAccountEdt;
    @BindView(R.id.activity_forgetpwd_resetpwd_edt)
    EditText activityForgetpwdResetpwdEdt;
    @BindView(R.id.activity_forgetpwd_confirmpwd_edt)
    EditText activityForgetpwdConfirmpwdEdt;
    @BindView(R.id.activity_forgetpwd_confirm_tv)
    TextView activityForgetpwdConfirmTv;
    @BindView(R.id.activity_forgetpwd_callservice_tv)
    TextView activityForgetpwdCallserviceTv;
    @BindView(R.id.activity_forgetpwd_rootlayout)
    LinearLayout activityForgetpwdRootlayout;

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
                            ToastUtils.showOneToast(t, Constants.GET_VERIFYCODE_SUC);
                            break;
                        case Constants.WHAT_POSTDATA:
                            ToastUtils.showOneToast(t, Constants.MODIFY_SUCCES);
                            t.finish();
                            break;
                        default:break;
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
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);

        setTitle();
        setListener();
    }

    private void setListener() {
        //监听号码输入
        activityForgetpwdPhonenoEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Editable editable = activityForgetpwdPhonenoEdt.getText();
                int len = editable.length();

                String tempPhoneNo = editable.toString().replace(" ", "");
                if (tempPhoneNo.length() >= 11 && !Validator.isMobile(tempPhoneNo)) {
                    Toast.makeText(ForgetPwdActivity.this, Constants.VALIDATOR_MOBILE, Toast.LENGTH_SHORT).show();
                }

                if (count == 1) {
                    int lenth = charSequence.toString().length();
                    if (lenth == 3 || lenth == 8) {
                        activityForgetpwdPhonenoEdt.setText(charSequence + " ");
                        activityForgetpwdPhonenoEdt.setSelection(activityForgetpwdPhonenoEdt.getText().toString().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        activityForgetpwdPhonenoEdt.addTextChangedListener(new MyTextWatch());
        activityForgetpwdVerifycodeEdt.addTextChangedListener(new MyTextWatch());
        activityForgetpwdAccountEdt.addTextChangedListener(new MyTextWatch());
        activityForgetpwdResetpwdEdt.addTextChangedListener(new MyTextWatch());
        activityForgetpwdConfirmpwdEdt.addTextChangedListener(new MyTextWatch());
    }

    private class MyTextWatch implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (checkInputNull()) {
                activityForgetpwdConfirmTv.setBackground(getResources().getDrawable(R.drawable.circle5_darkbluebg_style));
            } else {
                activityForgetpwdConfirmTv.setBackground(getResources().getDrawable(R.drawable.circle5_graybg_style));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void getVerifyCode() {
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.activity_forgetpwd_rootlayout));

        final String url = UrlManager.URL_GETVERIFYCODE;
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("mobile", activityForgetpwdPhonenoEdt.getText().toString().replaceAll(" ", ""));
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

    private void setTitle() {
        headCenterTitleTv.setText("忘记密码");
    }

    private boolean canConfirm() {
        if (!checkInputNull()) {
            ToastUtils.showOneToast(ForgetPwdActivity.this, Constants.INPUT_HAS_EMPTY);
            return false;
        }

        if (!validator()) {
            return false;
        }

        if (!activityForgetpwdResetpwdEdt.getText().toString().equals(activityForgetpwdConfirmpwdEdt.getText().toString())) {
            ToastUtils.showOneToast(ForgetPwdActivity.this, Constants.PWD_NOTEQUAL);
            return false;
        }
        return true;
    }

    private boolean validator() {
        if (!Validator.isMobile(activityForgetpwdPhonenoEdt.getText().toString().replaceAll(" ", ""))) {
            ToastUtils.showOneToast(getApplicationContext(), Constants.VALIDATOR_MOBILE);
            return false;
        }
        return true;
    }

    private boolean checkInputNull() {
        if (TextUtils.isEmpty(activityForgetpwdPhonenoEdt.getText().toString().replaceAll(" ", ""))
                || TextUtils.isEmpty(activityForgetpwdVerifycodeEdt.getText().toString())
                || TextUtils.isEmpty(activityForgetpwdAccountEdt.getText().toString())
                || TextUtils.isEmpty(activityForgetpwdResetpwdEdt.getText().toString())
                || TextUtils.isEmpty(activityForgetpwdConfirmpwdEdt.getText().toString())) {
            return false;
        }
        return true;
    }

    /**
     * mobile	是	string	手机号
     * verify	是	string	验证码
     * user_login	是	string	账户
     * new_pass	是	string	新密码
     * re_pass	是	string	重复新密码
     */
    private void confirm() {
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.activity_forgetpwd_rootlayout));

        final String url = UrlManager.URL_FORGETPASS;
        Map<String, String> map = new HashMap<String, String>(5);
        map.put("mobile", activityForgetpwdPhonenoEdt.getText().toString().replaceAll(" ", ""));
        map.put("verify", activityForgetpwdVerifycodeEdt.getText().toString());
        map.put("user_login", activityForgetpwdAccountEdt.getText().toString());
        map.put("new_pass", activityForgetpwdResetpwdEdt.getText().toString());
        map.put("re_pass", activityForgetpwdConfirmpwdEdt.getText().toString());
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
                                msg.what = Constants.WHAT_POSTDATA;
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

    @OnClick({R.id.head_left_iv, R.id.activity_forgetpwd_getverifycode_tv, R.id.activity_forgetpwd_confirm_tv, R.id.activity_forgetpwd_callservice_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                finish();
                break;
            case R.id.activity_forgetpwd_getverifycode_tv:
                if (TextUtils.isEmpty(activityForgetpwdPhonenoEdt.getText().toString())) {
                    ToastUtils.showOneToast(ForgetPwdActivity.this, Constants.INPUT_PHONE);
                    return;
                }
                getVerifyCode();
                SystemUtil.getInstance().countDownTime(activityForgetpwdGetverifycodeTv);
                break;
            case R.id.activity_forgetpwd_confirm_tv:
                if (canConfirm()) {
                    confirm();
                }
                break;
            case R.id.activity_forgetpwd_callservice_tv:
                SystemUtil.getInstance().contactUs(ForgetPwdActivity.this, "0591-88888888");
                break;
            default:break;
        }
    }
}
