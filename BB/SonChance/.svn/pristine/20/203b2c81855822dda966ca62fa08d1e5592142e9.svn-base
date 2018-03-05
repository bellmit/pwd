package com.tastebychance.sonchance.personal.bankcard;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.personal.bankcard.bean.FindBankInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CountDownTimerUtil;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.ImageDownLoad;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * 类描述：BankCardAddActivity 添加银行卡界面
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/9/5 16:48
 * 修改人：
 * 修改时间：2017/9/5 16:48
 * 修改备注：
 * @version 1.0
 */
public class BankCardAddActivity extends MyBaseActivity {

    private RelativeLayout content_order_form;
    private EditText person_bank_name_edt;
    private EditText person_bank_idcard_edt;//身份证号码
    private EditText person_bank_cardno_edt;
    private ImageView person_bank_bankname_iv;
    private EditText person_bank_phone_edt;
    private EditText person_bank_verifycode_edt;
    private TextView person_bank_verifycode_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_bank_card_add);

        bindViews();
        setTitle();

        setListener();
    }


    private void bindViews() {

        content_order_form = (RelativeLayout) findViewById(R.id.content_order_form);
        person_bank_name_edt = (EditText) findViewById(R.id.person_bank_name_edt);
        person_bank_idcard_edt = (EditText) findViewById(R.id.person_bank_idcard_edt);
        person_bank_cardno_edt = (EditText) findViewById(R.id.person_bank_cardno_edt);
        person_bank_bankname_iv = (ImageView) findViewById(R.id.person_bank_bankname_iv);
        person_bank_phone_edt = (EditText) findViewById(R.id.person_bank_phone_edt);
        person_bank_verifycode_edt = (EditText) findViewById(R.id.person_bank_verifycode_edt);
        person_bank_verifycode_tv = (TextView) findViewById(R.id.person_bank_verifycode_tv);

 /*       person_bank_name_edt.setText("冯明枫");
        person_bank_idcard_edt.setText("350121199405017280");
        person_bank_cardno_edt.setText("6232111820005830849");
        person_bank_phone_edt.setText("18030045724");*/
    }

    private void setListener() {
        person_bank_verifycode_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtil.isEmpty(person_bank_phone_edt.getText().toString())){
                    UiHelper.ShowOneToast(getApplicationContext(),"请输入手机号!");
                    return;
                }
                if (!StringUtil.isMobileNO(person_bank_phone_edt.getText().toString())){
                    UiHelper.ShowOneToast(getApplicationContext(),"您输入的手机号格式不正确!");
                    return;
                }
                sendVerifyCodeClick();

                countDownTime();
            }
        });

        person_bank_cardno_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //失去焦点的是获取开户行信息
                if (!hasFocus) {
                    if (StringUtil.isEmpty(person_bank_cardno_edt.getText().toString())) {
                        UiHelper.ShowOneToast(getApplicationContext(), "请输入银行卡号码!");
                        return;
                    } else {
                        if (!StringUtil.isBankCard(person_bank_cardno_edt.getText().toString())) {
                            UiHelper.ShowOneToast(getApplicationContext(), "银行卡号码格式不正确!");
                            return;
                        }
                    }
                    //获取开户行名称
                    findBank(person_bank_cardno_edt.getText().toString());
                }
            }
        });

        //内容变化的时候清空开户行信息
        person_bank_cardno_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bank_name = null;
                bank_type = null;
                letter  = null;
//              person_bank_bankname_iv.setBackground(getResources().getDrawable(R.drawable.whitebg_round));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void countDownTime(){
        //获取验证码倒计时
        CountDownTimerUtil countDownTimerUtil = new CountDownTimerUtil(person_bank_verifycode_tv,60 * 1000,1000,
                StringUtil.setTextSizeColor("重新发送", Color.WHITE,0,"重新发送".length(),14),
                R.drawable.circle25_lightgreenbg_style,R.drawable.circle25_greenbg_style,null,null);
        countDownTimerUtil.start();
    }

    /**
     * 设置标题
     */
    private void setTitle() {
        //动态设置状态栏下方的控件（view）的高度
        /*View view = (View) findViewById(R.id.view1);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = statusHeight;
        view.setLayoutParams(lp);*/

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null)
            center_tv.setText("添加银行卡");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BankCardAddActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.GONE);
        }
    }

    /**
     * 银行卡查询接口
     */
    private String letter;//银行字母代号
    private String bank_name;//银行全称
    private String bank_type;//银行卡类型
    private void findBank(String bankCardNo) {
        //取到已经保存的token（登录后的信息）
        SystemUtil.getInstance().setToken("eyJ1aWQiOiI3MSIsImV4cCI6MTUwNzg1ODExNCwic2lnbmF0dXJlIjoiM2I0MTM0OWZmZWU5YjJlMTJmZGZmMDVmMDYwYjRmYjQifQ");
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url =   UrlManager.URL_PERSON_FINDBANK;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("bank_code",bankCardNo).build();
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
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = "查询到开户行信息";
                        myHandler.sendMessage(msg);

                        final FindBankInfo findBankInfo = JSONObject.parseObject(resInfo.getData().toString(), FindBankInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageDownLoad.getDownLoadSmallImg(UrlManager.REQUESTBANKIMGURL + findBankInfo.getLetter(), person_bank_bankname_iv);
                                letter = findBankInfo.getLetter();
                                bank_name = findBankInfo.getBank_name();
                                bank_type = findBankInfo.getCardType();
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

    /**
     * 发送验证码
     *
     */
    public void sendVerifyCodeClick() {
        String phoneNo = person_bank_phone_edt.getText().toString();
        if (StringUtil.isEmpty(phoneNo)){
            UiHelper.ShowOneToast(getApplicationContext(),"请填写手机号");
            return;
        }

        //采用okhttp3来进行网络请求
        String url =   UrlManager.URL_SENDVIRIFY;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("mobile", phoneNo).build();
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
                }
            }
        });
    }

    /**
     * 立即申请
     *
     * @param view
     */
    public void applyClick(View view) {
        if (StringUtil.isEmpty(person_bank_name_edt.getText().toString())){
            UiHelper.ShowOneToast(getApplicationContext(),"请输入姓名!");
            return;
        }
        if(StringUtil.isEmpty(person_bank_idcard_edt.getText().toString())){
            UiHelper.ShowOneToast(getApplicationContext(),"请输入您的身份证号码!");
            return;
        }else{
            if(!StringUtil.isPid(person_bank_idcard_edt.getText().toString())){
                UiHelper.ShowOneToast(getApplicationContext(),"身份证号码格式不正确!");
                return;
            }
        }
        if(StringUtil.isEmpty(person_bank_cardno_edt.getText().toString())){
            UiHelper.ShowOneToast(getApplicationContext(),"请输入银行卡号码!");
            return;
        }else{
            if (!StringUtil.isBankCard(person_bank_cardno_edt.getText().toString())){
                UiHelper.ShowOneToast(getApplicationContext(),"银行卡号码格式不正确!");
                return;
            }
        }
        if( StringUtil.isEmpty(bank_name) || StringUtil.isEmpty(bank_type) || StringUtil.isEmpty(letter)){
            UiHelper.ShowOneToast(getApplicationContext(),"未查询到开户行,请检查您的银行卡号是否正确!");
            return;
        }
        if(StringUtil.isEmpty(person_bank_phone_edt.getText().toString())){
            UiHelper.ShowOneToast(getApplicationContext(),"请输入手机号!");
            return;
        }
        if (!StringUtil.isMobileNO(person_bank_phone_edt.getText().toString())){
            UiHelper.ShowOneToast(getApplicationContext(),"您输入的手机号格式不正确!");
            return;
        }
        if(StringUtil.isEmpty(person_bank_verifycode_edt.getText().toString())){
            UiHelper.ShowOneToast(getApplicationContext(),"请输入验证码");
            return ;
        }
        bindBankCard();
    }

    /**
     * 用户绑定银行卡
     *
     * user_name	是	string	真实姓名
     token	是	string	token值
     user_number	是	string	身份证号码
     bank_number	是	string	银行卡号
     bank_name	是	string	银行名称
     mobile	是	string	手机号码
     */
    private void bindBankCard(){
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        String url =  UrlManager.URL_PERSON_BINDBANK;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("user_name",person_bank_name_edt.getText().toString())
                .add("user_number",person_bank_idcard_edt.getText().toString())
                .add("bank_number",person_bank_cardno_edt.getText().toString())
                .add("bank_name",bank_name)
                .add("mobile",person_bank_phone_edt.getText().toString())
                .add("bank_type",bank_type)
                .add("letter",letter)
                .add("verify",person_bank_verifycode_edt.getText().toString())
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
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {

    //                AfterPayInfo afterPayInfo = JSONObject.parseObject(resInfo.getData().toString(),AfterPayInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = ERROR_WHAT;
                                msg.obj = "绑定银行卡成功!";
                                myHandler.sendMessage(msg);

                                BankCardAddActivity.this.finish();
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
                }
            }

        });
    }
}
