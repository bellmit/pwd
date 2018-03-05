package rongshanghui.tastebychance.com.rongshanghui.mime.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

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
import rongshanghui.tastebychance.com.rongshanghui.MyAppCompatActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.AreaCodeActivity;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCodeInfo;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：SettingPhoneBindActivity 我的-设置-手机绑定-绑定
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/8 18:31
 * 修改人：
 * 修改时间：2017/12/8 18:31
 * 修改备注：
 *
 * @version 1.0
 */
public class SettingPhoneBindActivity extends MyAppCompatActivity {

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
    @BindView(R.id.selectareacode_tv)
    TextView selectareacodeTv;
    @BindView(R.id.getverifycode_tv)
    TextView getverifycodeTv;
    @BindView(R.id.inputphonno_edt)
    EditText inputphonnoEdt;
    @BindView(R.id.inputverficode_edt)
    EditText inputverficodeEdt;
    @BindView(R.id.content_setting_phone_bind)
    RelativeLayout contentSettingPhoneBind;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_phone_bind);
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

        setTitle();
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("手机绑定");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("绑定");
        }
    }

    private String chosedAreaCode = null;

    private boolean canRegister() {
        if (StringUtil.isEmpty(chosedAreaCode)) {
            ToastUtils.showOneToast(this, "区号不能为空");
            return false;
        }
        if (StringUtil.isEmpty(inputphonnoEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "手机号码不能为空");
            return false;
        }
        if (StringUtil.isEmpty(inputverficodeEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "验证码不能为空");
            return false;
        }
        return true;
    }


    private boolean canGetVerifyCode() {
        if (StringUtil.
                isEmpty(chosedAreaCode)) {
            ToastUtils.showOneToast(this, "区号不能为空");
            return false;
        }
        if (StringUtil.isEmpty(inputphonnoEdt.getText().toString())) {
            ToastUtils.showOneToast(this, "手机号码不能为空");
            return false;
        }
        return true;
    }

    /**
     * areacode 	是 	string 	区号
     * mobile 	是 	string 	手机号
     * type 	否 	int 	空 默认直接发送 1 登录 2 注册 3 更换手机号
     */
    private void getVerifyCode() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_register_bmzj), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_GETVERIFYCODE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", inputphonnoEdt.getText().toString())
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

    /**
     * 用户手机绑定接口
     * token 	是 	string 	token值
     * area_code 	是 	int 	区号
     * mobile 	是 	string 	新手机号
     */
    private void register() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_register_bmzj), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SETTING_BINDMOBILE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", SystemUtil.getInstance().getToken())
                .add("mobile", inputphonnoEdt.getText().toString())
                .add("area_code", chosedAreaCode)
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
                                msg.what = ERROR_WHAT;
                                msg.obj = "绑定成功";
                                myHandler.sendMessage(msg);

                                //注册成功返回
                                Intent intent = new Intent();
                                sendBroadcast(intent, Constants.CHANGEACCOUNT_ACTION);
                                SettingPhoneBindActivity.this.finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == Constants.GETAREACODE_RETURNCODE) {
            if (null != data) {
                AreaCodeInfo selectedAreaData = (AreaCodeInfo) data.getSerializableExtra("selectedAreaData");
                if (selectedAreaData != null) {
                    selectareacodeTv.setText("+" + selectedAreaData.getCode() + "(" + selectedAreaData.getName() + ")");
                    chosedAreaCode = selectedAreaData.getCode();
                }
            }
        }
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.selectareacode_tv, R.id.getverifycode_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                SettingPhoneBindActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (canRegister()) {
                    register();
                }

                break;
            case R.id.selectareacode_tv:
                Intent intent = new Intent(SettingPhoneBindActivity.this, AreaCodeActivity.class);
                startActivityForResult(intent, 5);
                break;
            case R.id.getverifycode_tv:
                SoftInputUtil.hideSoftInput(SettingPhoneBindActivity.this, getverifycodeTv);
                if (canGetVerifyCode()) {
                    getVerifyCode();
                    SystemUtil.getInstance().countDownTime(getverifycodeTv);
                }
                break;
        }
    }
}
