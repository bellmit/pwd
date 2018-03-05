package com.tastebychance.sonchance.personal.personalcenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.personal.bean.UserInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CountDownTimerUtil;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;
import com.tastebychance.sonchance.view.cleanableeditext.CleanableEditText;
import com.tastebychance.sonchance.view.cleanableeditext.TextWatcherCallBack;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 输入新手机号
 *
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class PersonalInformationNewPhoneNoActivity extends MyBaseActivity implements TextWatcherCallBack {
    private TextView person_information_error_tv;

    //输入新的手机号，获取验证码，输入验证码
            //    private EditText person_information_inputnewphoneno_edt;
    private CleanableEditText person_information_inputnewphoneno_edt;
    private TextView personal_information_getverifycode_tv;
    private EditText personal_information_inputverifycode_edt;

    private UserInfo userInfo;

    private final int ERROR_WHAT = 0;
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ERROR_WHAT:
                    showError((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information_newphoneno);

        userInfo = (UserInfo) SharedPreferencesUtils.queryForSharedToObject(MyApplication.getContext(),"userInfo");

        setTitle();
        bindViews();
        setListener();
    }

    private void bindViews() {
        person_information_error_tv = (TextView) findViewById(R.id.person_information_error_tv);
        person_information_inputnewphoneno_edt = (CleanableEditText) findViewById(R.id.person_information_inputnewphoneno_edt);
        personal_information_getverifycode_tv = (TextView) findViewById(R.id.personal_information_getverifycode_tv);
        personal_information_inputverifycode_edt = (EditText) findViewById(R.id.personal_information_inputverifycode_edt);

//        person_information_inputnewphoneno_edt.setText("151 1233 3634");
//        personal_information_inputverifycode_edt.setText("566523");
    }

    private void setListener() {
        person_information_inputnewphoneno_edt.setCallBack(this);

        personal_information_getverifycode_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person_information_error_tv.setVisibility(View.GONE);
            if (null != person_information_inputnewphoneno_edt){
                if (StringUtil.isEmpty(person_information_inputnewphoneno_edt.getText().toString().replace(" ",""))){
                    if (Constants.IS_DEVELOPING){
                        UiHelper.ShowOneToast(getApplicationContext(),"请输入新的手机号码!");
                    }
                    person_information_error_tv.setVisibility(View.VISIBLE);
                    person_information_error_tv.setText("请输入新的手机号码!");
                    return;
                }
                if (!StringUtil.isMobileNO(person_information_inputnewphoneno_edt.getText().toString().replace(" " ,""))){
                    if (Constants.IS_DEVELOPING){
                        UiHelper.ShowOneToast(getApplicationContext(),"您输入的手机号不正确，请重新输入!");
                    }
                    person_information_error_tv.setVisibility(View.VISIBLE);
                    person_information_error_tv.setText("您输入的手机号不正确，请重新输入!");
                    return;
                }
                if (null != userInfo){
                    if (userInfo.getMobile().equals(person_information_inputnewphoneno_edt.getText().toString().replace(" ",""))){
                        if (Constants.IS_DEVELOPING){
                            UiHelper.ShowOneToast(getApplicationContext(),"新旧号码不能一样,请输入新号码!");
                        }
                        person_information_error_tv.setVisibility(View.VISIBLE);
                        person_information_error_tv.setText("新旧号码不能一样,请输入新号码!");
                        return;
                    }
                }

                //获取验证码
                checkMobile();
            }
            }
        });
    }

    private void countDownTime(){
        //获取验证码倒计时
        CountDownTimerUtil countDownTimerUtil = new CountDownTimerUtil(personal_information_getverifycode_tv,60 * 1000,1000,
                StringUtil.setTextSizeColor("重新发送", Color.WHITE,0,"重新发送".length(),14),
            R.drawable.circle25_lightgreenbg_style,R.drawable.circle25_greenbg_style,null,null);
        countDownTimerUtil.start();
    }

    /**
     * 设置标题
     */
    private void setTitle() {
        //动态设置状态栏下方的控件（view）的高度
        View view = (View) findViewById(R.id.view1);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = statusHeight;
        view.setLayoutParams(lp);

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    PersonalInformationNewPhoneNoActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
        }
        if (center_tv != null){
            center_tv.setText("手机绑定");
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.VISIBLE);
            right_tv.setText("保存");
            right_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //上传新的手机号码
                    String verifyCode = personal_information_inputverifycode_edt.getText().toString();
                    if (StringUtil.isEmpty(verifyCode)){
                        UiHelper.ShowOneToast(getApplicationContext(),"验证码不能为空");
                        return;
                    }

                    uploadVerifyCode(verifyCode);
                }
            });
        }
    }

    /**
     * 手机去重等验证
     */
    private void checkMobile(){
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.content_order_form),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //给服务器发送手机号码，用于获取验证码
        String phoneNo = person_information_inputnewphoneno_edt.getText().toString().replace(" " ,"");

        //采用okhttp3来进行网络请求
        final String url =  UrlManager.URL_PERSON_CHECKMOBILE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", phoneNo)
                .add("token",token)
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
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                countDownTime();
                                getVerifyCode();
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

    /**
     * 获取验证码
     */
    private void getVerifyCode(){
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.content_order_form),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //给服务器发送手机号码，用于获取验证码
        String phoneNo = person_information_inputnewphoneno_edt.getText().toString().replace(" " ,"");

        //采用okhttp3来进行网络请求
        final String url =  UrlManager.URL_PERSON_CHECKMOBILE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", phoneNo)
                .add("token",token)
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
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

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

    /**
     * 上传验证码
     */
    private void uploadVerifyCode(String verfiCode){
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.content_order_form),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }


        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_PERSON_BINDMOBILE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("mobile",person_information_inputnewphoneno_edt.getText().toString().replace(" ",""))
                .add("verify",verfiCode)
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
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = "绑定新号码成功!";
                        myHandler.sendMessage(msg);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                PersonalInformationNewPhoneNoActivity.this.setResult(Constants.BIND_MOBILE_SUCCESS, intent);
                                PersonalInformationNewPhoneNoActivity.this.finish();
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
                    CrashHandler.getInstance().handlerError("处理"+url+" 返回的成功数据报错");
                }
            }
        });
    }

    private void showError(String errorStr) {
        if (null != person_information_error_tv){
            PersonalInformationNewPhoneNoActivity.this.person_information_error_tv.setVisibility(View.VISIBLE);
            PersonalInformationNewPhoneNoActivity.this.person_information_error_tv.setText(errorStr);
        }
        UiHelper.ShowOneToast(getApplicationContext(), errorStr);
    }

    @Override
    public void handleMoreTextChanged() {
        int lenth = person_information_inputnewphoneno_edt.getText().toString().length();
        if (lenth == 3 || lenth == 8){
            person_information_inputnewphoneno_edt.setText(person_information_inputnewphoneno_edt.getText() + " ");
            person_information_inputnewphoneno_edt.setSelection(person_information_inputnewphoneno_edt.getText().toString().length());
        }

        if (person_information_inputnewphoneno_edt.getText().toString().length() == 0){
            person_information_error_tv.setVisibility(View.GONE);
        }
    }
}
