package rongshanghui.tastebychance.com.rongshanghui.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

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
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.AlibabaUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventRegisterSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventTaobaoUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.AreaCodeActivity;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCodeInfo;
import rongshanghui.tastebychance.com.rongshanghui.login.bean.LoginRes;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.MD5Util;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.util.network.MyCallBack;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * ????????????RegisterBMZJActivity ?????????????????????-????????????
 * ?????????: shenbh Email:shenbh@qq.com
 * ??????????????? 2017/12/7 14:33
 * ????????????
 * ???????????????2017/12/7 14:33
 * ???????????????
 *
 * @version 1.0
 */
public class RegisterBMZJActivity extends MyBaseActivity {


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
    @BindView(R.id.register_bmzj_account_tv)
    TextView registerBmzjAccountTv;
    @BindView(R.id.register_bmzj_selectareacode_value_tv)
    TextView registerBmzjSelectareacodeValueTv;
    @BindView(R.id.register_bmzj_selectareacode_tv)
    TextView registerBmzjSelectareacodeTv;
    @BindView(R.id.register_bmzj_inputphonno_edt)
    EditText registerBmzjInputphonnoEdt;
    @BindView(R.id.register_bmzj_getverifycode_tv)
    TextView registerBmzjGetverifycodeTv;
    @BindView(R.id.register_bmzj_inputverficode_edt)
    EditText registerBmzjInputverficodeEdt;
    @BindView(R.id.activity_register_bmzj)
    LinearLayout activityRegisterBmzj;
    private String name = "";
    private String registerType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bmzj);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            name = getIntent().getStringExtra("name");
            registerType = getIntent().getStringExtra("registerType");
        }
        setTitle();

        registerBmzjAccountTv.setText("?????????????????????" + name);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == Constants.GETAREACODE_RETURNCODE) {
            if (null != data) {
                AreaCodeInfo selectedAreaData = (AreaCodeInfo) data.getSerializableExtra("selectedAreaData");
                if (selectedAreaData != null) {
                    registerBmzjSelectareacodeValueTv.setText("+" + selectedAreaData.getCode() + "(" + selectedAreaData.getName() + ")");
                    chosedAreaCode = selectedAreaData.getCode();
                }
            }
        }
    }

    /**
     * ??????/??????????????????
     * mobile 	??? 	string 	???????????????
     * area_code 	??? 	string 	??????
     * verify 	??? 	string 	?????????
     * name 	??? 	string 	????????????
     */
    private void register() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_register_bmzj), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //??????okhttp3?????????????????????
        final String url = UrlManager.URL_DEPAERRREGISTER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", registerBmzjInputphonnoEdt.getText().toString())
                .add("area_code", chosedAreaCode)
                .add("verify", registerBmzjInputverficodeEdt.getText().toString())
                .add("name", name)
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

                                SystemUtil.getInstance().saveLoginInfoToLocal(loginRes.getUser_info().getUser_login(), MD5Util.md5Code(loginRes.getUser_info().getUser_login()), Constants.APP_KEY);
                                SystemUtil.getInstance().taobaouser(1, new MyCallBack<AlibabaUser>() {
                                    @Override
                                    public void onLoadingBefore(Request request) {

                                    }

                                    @Override
                                    public void onSuccess(Response response, AlibabaUser result) {
                                        //??????????????????
                                        EventBusUtils.post(new EventRegisterSuc());
                                        SystemUtil.getInstance().alibabaLogin();
                                        RegisterBMZJActivity.this.finish();
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

    /**
     * areacode 	??? 	string 	??????
     * mobile 	??? 	string 	?????????
     * type 	??? 	int 	??? ?????????????????? 1 ?????? 2 ?????? 3 ???????????????
     */
    private void getVerifyCode() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_register_bmzj), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //??????okhttp3?????????????????????
        final String url = UrlManager.URL_GETVERIFYCODE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", registerBmzjInputphonnoEdt.getText().toString())
                .add("areacode", chosedAreaCode)
                .add("type", "2")
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

                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = 3;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
//??????????????????
                EventBusUtils.post(new EventRegisterSuc());
                RegisterBMZJActivity.this.finish();
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

    private String chosedAreaCode = "86";

    private boolean canRegister() {
        if (StringUtil.isEmpty(chosedAreaCode)) {
            ToastUtils.showOneToast(this, "??????????????????");
            return false;
        }
        if (StringUtil.isEmpty(registerBmzjInputphonnoEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "????????????????????????");
            return false;
        }
        if (StringUtil.isEmpty(registerBmzjInputverficodeEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "?????????????????????");
            return false;
        }
        return true;
    }


    private boolean canGetVerifyCode() {
        if (StringUtil.isEmpty(chosedAreaCode)) {
            ToastUtils.showOneToast(this, "??????????????????");
            return false;
        }
        if (StringUtil.isEmpty(registerBmzjInputphonnoEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "????????????????????????");
            return false;
        }
        return true;
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.register_bmzj_selectareacode_tv, R.id.register_bmzj_getverifycode_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                RegisterBMZJActivity.this.finish();
                break;
            case R.id.head_right_tv:
                SoftInputUtil.hideSoftInput(RegisterBMZJActivity.this, headRightTv);
                if (canRegister()) {
                    register();
                }
                break;
            case R.id.register_bmzj_selectareacode_tv:
                Intent intent = new Intent(RegisterBMZJActivity.this, AreaCodeActivity.class);
                startActivityForResult(intent, 5);
                break;
            case R.id.register_bmzj_getverifycode_tv:
                SoftInputUtil.hideSoftInput(RegisterBMZJActivity.this, registerBmzjGetverifycodeTv);
                if (canGetVerifyCode()) {
                    getVerifyCode();
                    SystemUtil.getInstance().countDownTime(registerBmzjGetverifycodeTv);
                }
                break;
        }
    }
}
