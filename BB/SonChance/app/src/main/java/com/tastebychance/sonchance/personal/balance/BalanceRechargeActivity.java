package com.tastebychance.sonchance.personal.balance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.switfpass.pay.MainApplication;
import com.switfpass.pay.activity.PayPlugin;
import com.switfpass.pay.bean.RequestMsg;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.homepage.afterpay.AfterPayActivity;
import com.tastebychance.sonchance.homepage.pay.PayActivity;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.NoDoubleClickListener;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 项目名称：SonChance
 * 类描述：账户充值
 * 创建人：Administrator
 * 创建时间： 2017/10/9 10:33
 * 修改人：Administrator
 * 修改时间：2017/10/9 10:33
 * 修改备注：
 */

public class BalanceRechargeActivity extends MyBaseActivity implements View.OnClickListener {

    private RelativeLayout content_order_form;
    private TextView person_balancerecharge_torecharge_tv;
    private TextView person_balancerecharge_1000_tv;
    private TextView person_balancerecharge_500_tv;
    private TextView person_balancerecharge_300_tv;
    private TextView person_balancerecharge_100_tv;
    private TextView person_balancerecharge_50_tv;
    private ImageView person_balancerecharge_paymenttype_zfb_iv;
    private TextView person_balancerecharge_paymenttype_zfb_tv;
    private CheckBox person_balancerecharge_paymenttype_zfb_cb;
    private ImageView person_balancerecharge_paymenttype_wx_iv;
    private TextView person_balancerecharge_paymenttype_wx_tv;
    private CheckBox person_balancerecharge_paymenttype_wx_cb;

    private String choosedMoney;

    private HashMap<Integer, Boolean> paymentCBHashMap;

    private IntentFilter intentFilter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentCBHashMap = new HashMap<>();
        setContentView(R.layout.person_balancerecharge);

        bindViews();
        resetStyle();

        setListener();
        setTitle();

        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.RECHARGE_SUCCESS_ACTION);
        intentFilter.addAction(Constants.RECHARGE_ERROR_ACTION);
        intentFilter.addAction(Constants.RECHARGE_CANCEL_ACTION);
    }

    private void bindViews() {
        content_order_form = (RelativeLayout) findViewById(R.id.content_order_form);
        person_balancerecharge_torecharge_tv = (TextView) findViewById(R.id.person_balancerecharge_torecharge_tv);
        person_balancerecharge_1000_tv = (TextView) findViewById(R.id.person_balancerecharge_1000_tv);
        person_balancerecharge_500_tv = (TextView) findViewById(R.id.person_balancerecharge_500_tv);
        person_balancerecharge_300_tv = (TextView) findViewById(R.id.person_balancerecharge_300_tv);
        person_balancerecharge_100_tv = (TextView) findViewById(R.id.person_balancerecharge_100_tv);
        person_balancerecharge_50_tv = (TextView) findViewById(R.id.person_balancerecharge_50_tv);
        person_balancerecharge_paymenttype_zfb_iv = (ImageView) findViewById(R.id.person_balancerecharge_paymenttype_zfb_iv);
        person_balancerecharge_paymenttype_zfb_tv = (TextView) findViewById(R.id.person_balancerecharge_paymenttype_zfb_tv);
        person_balancerecharge_paymenttype_zfb_cb = (CheckBox) findViewById(R.id.person_balancerecharge_paymenttype_zfb_cb);
        person_balancerecharge_paymenttype_wx_iv = (ImageView) findViewById(R.id.person_balancerecharge_paymenttype_wx_iv);
        person_balancerecharge_paymenttype_wx_tv = (TextView) findViewById(R.id.person_balancerecharge_paymenttype_wx_tv);
        person_balancerecharge_paymenttype_wx_cb = (CheckBox) findViewById(R.id.person_balancerecharge_paymenttype_wx_cb);

        paymentCBHashMap.put(R.id.person_balancerecharge_paymenttype_zfb_cb, false);
        paymentCBHashMap.put(R.id.person_balancerecharge_paymenttype_wx_cb, true);

        setPaymentCb();
    }

    private void setListener() {
        person_balancerecharge_1000_tv.setOnClickListener(this);
        person_balancerecharge_500_tv.setOnClickListener(this);
        person_balancerecharge_300_tv.setOnClickListener(this);
        person_balancerecharge_100_tv.setOnClickListener(this);
        person_balancerecharge_50_tv.setOnClickListener(this);
        person_balancerecharge_torecharge_tv.setOnClickListener(
                new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        toRecharge();
                    }
                });

        person_balancerecharge_paymenttype_zfb_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    paymentCBHashMap.put(R.id.person_balancerecharge_paymenttype_zfb_cb, true);
                    paymentCBHashMap.put(R.id.person_balancerecharge_paymenttype_wx_cb, false);
                } else {
                    paymentCBHashMap.put(R.id.person_balancerecharge_paymenttype_zfb_cb, false);
                }

                setPaymentCb();
            }
        });

        person_balancerecharge_paymenttype_wx_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    paymentCBHashMap.put(R.id.person_balancerecharge_paymenttype_wx_cb, true);
                    paymentCBHashMap.put(R.id.person_balancerecharge_paymenttype_zfb_cb, false);
                } else {
                    paymentCBHashMap.put(R.id.person_balancerecharge_paymenttype_wx_cb, false);
                }

                setPaymentCb();
            }
        });
    }

    private void setPaymentCb() {
        person_balancerecharge_paymenttype_zfb_cb.setChecked(paymentCBHashMap.get(R.id.person_balancerecharge_paymenttype_zfb_cb));
        person_balancerecharge_paymenttype_wx_cb.setChecked(paymentCBHashMap.get(R.id.person_balancerecharge_paymenttype_wx_cb));
    }

    /**
     * 设置标题
     */
    private void setTitle() {
        //动态设置状态栏下方的控件（view）的高度
/*        View view = (View) findViewById(R.id.view1);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = statusHeight;
        view.setLayoutParams(lp);
        System.out.println("statusHeight----------------------------------------- = " + statusHeight);*/

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null)
            center_tv.setText("账户充值");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BalanceRechargeActivity.this.finish();
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

    private void initData(ArrayList<String> buttons) {
        if (null == buttons && buttons.size() <= 0) {
            return;
        }
        if (buttons.size() > 0) {
            person_balancerecharge_1000_tv.setText(buttons.get(0) + "元");
        }
        if (buttons.size() > 1) {
            person_balancerecharge_500_tv.setText(buttons.get(1) + "元");
        }
        if (buttons.size() > 2) {
            person_balancerecharge_300_tv.setText(buttons.get(2) + "元");
        }
        if (buttons.size() > 3) {
            person_balancerecharge_100_tv.setText(buttons.get(3) + "元");
        }
        if (buttons.size() > 4) {
            person_balancerecharge_50_tv.setText(buttons.get(4) + "元");
        }
    }

    /**
     * 去充值
     */
    private String payType = "2";//选择支付方式 1 微信支付 2支付宝支付 3账户余额支付

    public void toRecharge() {
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.content_order_form),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        if (paymentCBHashMap.get(R.id.person_balancerecharge_paymenttype_wx_cb)) {
            payType = "1";
        }
        if (paymentCBHashMap.get(R.id.person_balancerecharge_paymenttype_zfb_cb)) {
            payType = "2";
        }

        if (StringUtil.isEmpty(choosedMoney)) {
            UiHelper.ShowOneToast(BalanceRechargeActivity.this, "请选择要充值的金额");
            return;
        }
        System.out.println("choosedMoney = " + choosedMoney);

        //TODO:充值接口
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_PERSON_RECHARGE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("money", choosedMoney)
                .add("pay_type", payType).build();
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
                    if (Constants.IS_DEVELOPING) {
                        Log.i(Constants.TAG, str);
                        System.out.println("充值str ----------------------------------------------------= " + str);
                    }

                    String tokenId = (String) JSONObject.parseObject(str).get("token_id");
                    String services = (String) JSONObject.parseObject(str).get("services");
                    String order_sn = (String) JSONObject.parseObject(str).get("order_sn");
                    SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"order_sn",order_sn);
                    System.out.println("充值tokenId --------------------------= " + tokenId);
                    System.out.println("services = " + services);

//                    prepayId = JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject(str).get("token_id").toString()).get("pay_info").toString()).get("prepayid").toString();

//                    String tokenId = JSONObject.parseObject(JSONObject.parseObject(str).get("token_id").toString()).get("token_id").toString().toString();
                    wftPay(tokenId);
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    private final int BALANCE_PAY = 3;
    private final int WX_PAY = 1;
    private final int ZFB_PAY = 2;

    private void wftPay(String tokenId) {
        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"payFrom","BalanceRechargeActivity");

        RequestMsg msg = new RequestMsg();
        msg.setTokenId(tokenId);
        if (Constants.IS_DEVELOPING) {
            System.out.println("msg.getTokenId() --------------------------------= " + msg.getTokenId());
            System.out.println("选择类型是： --------------------------------" + payType);
            Log.i("tag", "typetag-->" + payType);
        }
        if (payType.equals(WX_PAY + "")) {
            //微信
            msg.setTradeType(MainApplication.WX_APP_TYPE);
            msg.setAppId(Constants.WXAPPID);//wxd3a1cdf74d0c41b3
            if (Constants.IS_DEVELOPING){
                System.out.println("msg.getTradeType() -------------------------------- = " + msg.getTradeType());
                System.out.println("msg.getAppId() --------------------------------= " + msg.getAppId());
            }
            PayPlugin.unifiedAppPay(BalanceRechargeActivity.this, msg);
        } else {
            //支付宝
            msg.setTradeType(MainApplication.PAY_NEW_ZFB_WAP);
            PayPlugin.unifiedH5Pay(BalanceRechargeActivity.this, msg);
        }
    }

    /**
     * 获取按钮上的金额数值
     * URL_PERSON_RECHARGEMONEY
     */
    private void getData() {
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
        final String url = UrlManager.URL_PERSON_RECHARGEMONEY;
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

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                        final ArrayList<String> buttons = (ArrayList<String>) resInfo.getDataList(String.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData(buttons);
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

    private void resetStyle() {
        person_balancerecharge_1000_tv.setBackground(getResources().getDrawable(R.drawable.circle5_grayedge_style));
        person_balancerecharge_500_tv.setBackground(getResources().getDrawable(R.drawable.circle5_grayedge_style));
        person_balancerecharge_300_tv.setBackground(getResources().getDrawable(R.drawable.circle5_grayedge_style));
        person_balancerecharge_100_tv.setBackground(getResources().getDrawable(R.drawable.circle5_grayedge_style));
        person_balancerecharge_50_tv.setBackground(getResources().getDrawable(R.drawable.circle5_grayedge_style));

        person_balancerecharge_1000_tv.setTextColor(Color.BLACK);
        person_balancerecharge_500_tv.setTextColor(Color.BLACK);
        person_balancerecharge_300_tv.setTextColor(Color.BLACK);
        person_balancerecharge_100_tv.setTextColor(Color.BLACK);
        person_balancerecharge_50_tv.setTextColor(Color.BLACK);
    }

    BroadcastReceiver myBrodcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BalanceRechargeActivity.this.finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("BalanceRechargeActivity----------------------------------------------------"+getTaskId());

        getData();
        if (myBrodcastReceiver != null){
            registerReceiver(myBrodcastReceiver,intentFilter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != myBrodcastReceiver){
            unregisterReceiver(myBrodcastReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_balancerecharge_1000_tv:
                resetStyle();
                person_balancerecharge_1000_tv.setTextColor(Color.WHITE);
                person_balancerecharge_1000_tv.setBackground(getResources().getDrawable(R.drawable.circle5_redbg_style));
                choosedMoney = person_balancerecharge_1000_tv.getText().toString().replace("元", "");
                break;
            case R.id.person_balancerecharge_500_tv:
                resetStyle();
                person_balancerecharge_500_tv.setTextColor(Color.WHITE);
                person_balancerecharge_500_tv.setBackground(getResources().getDrawable(R.drawable.circle5_redbg_style));
                choosedMoney = person_balancerecharge_500_tv.getText().toString().replace("元", "");
                break;
            case R.id.person_balancerecharge_300_tv:
                resetStyle();
                person_balancerecharge_300_tv.setTextColor(Color.WHITE);
                person_balancerecharge_300_tv.setBackground(getResources().getDrawable(R.drawable.circle5_redbg_style));
                choosedMoney = person_balancerecharge_300_tv.getText().toString().replace("元", "");
                break;
            case R.id.person_balancerecharge_100_tv:
                resetStyle();
                person_balancerecharge_100_tv.setTextColor(Color.WHITE);
                person_balancerecharge_100_tv.setBackground(getResources().getDrawable(R.drawable.circle5_redbg_style));
                choosedMoney = person_balancerecharge_100_tv.getText().toString().replace("元", "");
                break;
            case R.id.person_balancerecharge_50_tv:
                resetStyle();
                person_balancerecharge_50_tv.setTextColor(Color.WHITE);
                person_balancerecharge_50_tv.setBackground(getResources().getDrawable(R.drawable.circle5_redbg_style));
                choosedMoney = person_balancerecharge_50_tv.getText().toString().replace("元", "");
                break;
        }
    }
}
