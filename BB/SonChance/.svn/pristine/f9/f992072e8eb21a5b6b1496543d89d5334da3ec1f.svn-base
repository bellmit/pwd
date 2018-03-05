package com.tastebychance.sonchance.homepage.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.tastebychance.sonchance.homepage.pay.adapter.PaymentAdapter;
import com.tastebychance.sonchance.homepage.pay.bean.GetPayInfo;
import com.tastebychance.sonchance.homepage.pay.bean.PayStatusInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CountDownTimerUtil;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.NoDoubleClickListener;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.TimeOrDateUtil;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 支付
 *
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class PayActivity extends MyBaseActivity{
    private TextView home_pay_lefttime_tv;
    private TextView home_pay_orderdetail_tv;
    private RelativeLayout homoe_pay_getmorepayment_rl;
    private TextView home_pay_confirmtopay_tv;

    private View home_pay_paymentchoosed;//
    private ImageView paymenttype_icon_iv;
    private TextView paymenttype_name_tv;//
    private CheckBox paymenttype_cb;//
//    private MyListView home_pay_payment_mlv;

    private LinearLayout payment_list_ll;
    private ImageView paymenttype_balance_icon_iv;
    private TextView paymenttype_balance_name_tv;
    private TextView paymenttype_balance_tv;
    private CheckBox paymenttype_banlance_cb;
    private ImageView paymenttype_wx_icon_iv;
    private TextView paymenttype_wx_name_tv;
    private CheckBox paymenttype_wx_cb;
    private ImageView paymenttype_zfb_icon_iv;
    private TextView paymenttype_zfb_name_tv;
    private CheckBox paymenttype_zfb_cb;
    private RelativeLayout paymenttype_balance_rl,paymenttype_wx_rl,paymenttype_zfb_rl;

    private String order_sn;//订单号
    private String dedPrice;//最终结算价格（从orderform接收的,用于初始化控件）

    private HashMap<Integer,Boolean> paymentCBHashMap = new HashMap<Integer,Boolean>();

    private IntentFilter intentFilter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_pay);

        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.PAY_SUCCESS_ACTION);
        intentFilter.addAction(Constants.PAY_CANCEL_ACTION);
        intentFilter.addAction(Constants.PAY_ERROR_ACTION);

        if (Constants.IS_DEVELOPING){
            System.out.println("payActivity------------------------onCreate()");
        }

        Intent intent = getIntent();
        if (intent != null) {
            order_sn = intent.getStringExtra("order_sn");
            System.out.println("payActivity接收到的order_sn---------------- = " + order_sn);
            dedPrice = intent.getStringExtra("dedPrice");
        }

        if (StringUtil.isEmpty(order_sn)){
            order_sn = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"order_sn");
            System.out.println("刷新界面订单号为空，重新获取订单号order_sn---------------------------= " + order_sn);
        }

        bindViews();
        setTitle();
        //设置默认支付方式
        setCBData(WX_PAY);
    }

    private void bindViews() {
        home_pay_lefttime_tv = (TextView) findViewById(R.id.home_pay_lefttime_tv);
        home_pay_orderdetail_tv = (TextView) findViewById(R.id.home_pay_orderdetail_tv);
        homoe_pay_getmorepayment_rl = (RelativeLayout) findViewById(R.id.homoe_pay_getmorepayment_rl);
        home_pay_confirmtopay_tv = (TextView) findViewById(R.id.home_pay_confirmtopay_tv);
        home_pay_paymentchoosed = findViewById(R.id.home_pay_paymentchoosed);
        paymenttype_icon_iv = (ImageView) findViewById(R.id.paymenttype_icon_iv);
        paymenttype_name_tv = (TextView) findViewById(R.id.paymenttype_name_tv);
        paymenttype_cb = (CheckBox) findViewById(R.id.paymenttype_cb);
//        home_pay_payment_mlv = (MyListView) findViewById(R.id.home_pay_payment_mlv);

        payment_list_ll = (LinearLayout) findViewById(R.id.payment_list_ll);
        paymenttype_balance_icon_iv = (ImageView) findViewById(R.id.paymenttype_balance_icon_iv);
        paymenttype_balance_name_tv = (TextView) findViewById(R.id.paymenttype_balance_name_tv);
        paymenttype_balance_tv = (TextView) findViewById(R.id.paymenttype_balance_tv);
        paymenttype_banlance_cb = (CheckBox) findViewById(R.id.paymenttype_banlance_cb);
        paymenttype_wx_icon_iv = (ImageView) findViewById(R.id.paymenttype_wx_icon_iv);
        paymenttype_wx_name_tv = (TextView) findViewById(R.id.paymenttype_wx_name_tv);
        paymenttype_wx_cb = (CheckBox) findViewById(R.id.paymenttype_wx_cb);
        paymenttype_zfb_icon_iv = (ImageView) findViewById(R.id.paymenttype_zfb_icon_iv);
        paymenttype_zfb_name_tv = (TextView) findViewById(R.id.paymenttype_zfb_name_tv);
        paymenttype_zfb_cb = (CheckBox) findViewById(R.id.paymenttype_zfb_cb);
        paymenttype_balance_rl = (RelativeLayout) findViewById(R.id.paymenttype_balance_rl);
        paymenttype_wx_rl = (RelativeLayout) findViewById(R.id.paymenttype_wx_rl);
        paymenttype_zfb_rl = (RelativeLayout) findViewById(R.id.paymenttype_zfb_rl);

        home_pay_paymentchoosed.setVisibility(View.VISIBLE);
        payment_list_ll.setVisibility(View.GONE);


        home_pay_confirmtopay_tv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                pay();//确认支付
            }
        });
    }

    /**
     * 设置标题
     */
    private void setTitle() {
        //动态设置状态栏下方的控件（view）的高度
       /* View view = (View) findViewById(R.id.view1);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = statusHeight;
        view.setLayoutParams(lp);*/

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        TextView subTitle = (TextView) findViewById(R.id.head_center_subtitle);
        //重新设置view的高度

        if (center_tv != null) {
            center_tv.setText("在线支付");
        }
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    notifyToCloseBlankTOPayActivity();

                    PayActivity.this.finish();
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
     * 从服务器获取初始数据
     */
    private GetPayInfo getPayInfo;
    private void getData(){
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.root_layout),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_PAYMONEY;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token).add("order_sn",order_sn).build();
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

                        getPayInfo = JSONObject.parseObject(resInfo.getData().toString(), GetPayInfo.class);
                        System.out.println("getPayInfo = " + getPayInfo);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData(getPayInfo);
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
     * 初始化界面数据
     * @param getPayInfo
     */
    private CountDownTimerUtil countDownTimerUtil;
    private void initData(GetPayInfo getPayInfo) {

        long leftTime = 15 * 60 * 1000L;
        if (null != getPayInfo){
            leftTime = getPayInfo.getTime();
        }

        System.out.println("leftTime--------------------------------- = " + leftTime);
        if (countDownTimerUtil != null ){
            countDownTimerUtil.cancel();
        }

        //支付剩余时间倒计时
        countDownTimerUtil = new CountDownTimerUtil(home_pay_lefttime_tv, leftTime, 1000,
                StringUtil.setTextSizeColor("订单取消", Color.BLACK,0,"订单取消".length(),14)
                , 0 ,0 ,TimeOrDateUtil.MM_SS , new CountDownTimerUtil.OnFinishListener() {
            @Override
            public void onFinishListener() {
//                Toast.makeText(PayActivity.this,"倒计时结束",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PayActivity.this,AfterPayActivity.class);
                intent.putExtra("order_sn",order_sn);
                startActivity(intent);
                PayActivity.this.finish();
            }
        });
        countDownTimerUtil.start();
//        home_pay_lefttime_tv.setText(getPayInfo.getTime());

        if (null != getPayInfo){
            dedPrice = getPayInfo.getOrder().getDed_price()+"";
        }
        //确认支付
        home_pay_confirmtopay_tv.setText("确认支付￥"+dedPrice);
    }

    private List<PaymentInfo> paymentInfos ;
    private final int BALANCE_PAY = 3;
    private final int WX_PAY = 1;
    private final int ZFB_PAY =2;

    /**
     * 订单详情
     * @param view
     */
    public void orderDetailClick(View view){
        Intent intent = new Intent(PayActivity.this,PayDetailActivity.class);
        intent.putExtra("getpayinfo",getPayInfo);
        startActivity(intent);
    }

    /**
     * 获取更多支付方式
     * @param view
     */
    private PaymentAdapter adapter;
    public void getMorePaymentClick(final View view){
        home_pay_paymentchoosed.setVisibility(View.GONE);
        homoe_pay_getmorepayment_rl.setVisibility(View.GONE);
        payment_list_ll.setVisibility(View.VISIBLE);

        if (null != getPayInfo){
            paymenttype_balance_tv.setText(StringUtil.setTextSizeColor("可用余额：￥" + getPayInfo.getMoney(), Color.RED, "可用余额：".length(), ("可用余额：￥" + getPayInfo.getMoney()).length(), 13));
        }
    }

    /**
     * 选择余额支付方式
     * @param view
     */
    public void balanceClick(View view){
        if (null == getPayInfo){
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (null != getPayInfo){
            float money = getPayInfo.getMoney();//余额
            float dedPrice =getPayInfo.getOrder().getDed_price();//需要支付的金额
            if (dedPrice > money){
                paymenttype_banlance_cb.setChecked(false);
                Toast.makeText(PayActivity.this,"余额不足",Toast.LENGTH_SHORT).show();
            }else{
                setCBData(BALANCE_PAY);
            }
        }else{
            CrashHandler.getInstance().handlerError("异常：待支付界面，getPayInfo为null");
        }
    }

    /**
     * 选择微信支付方式
     * @param view
     * @return
     */
    public void wxClick(View view){
        setCBData(WX_PAY);
    }

    /**
     * 选择支付宝支付方式
     * @param view
     */
    public void  zfbClick(View view){
        setCBData(ZFB_PAY);
    }

    private void setCBData(int paytype) {
        if (Constants.IS_DEVELOPING){
            System.out.println("payActivity中支付方式paytype ---------------= " + paytype);
        }
        if (null == paymentCBHashMap){
            paymentCBHashMap = new HashMap<Integer, Boolean>();
        }
        paymentCBHashMap.put(paytype, true);
        if (paytype == BALANCE_PAY) {
            paymenttype_banlance_cb.setChecked(true);
            paymenttype_wx_cb.setChecked(false);
            paymenttype_zfb_cb.setChecked(false);

            paymentCBHashMap.put(WX_PAY, false);
            paymentCBHashMap.put(ZFB_PAY, false);

        } else if (paytype == WX_PAY) {
            paymenttype_banlance_cb.setChecked(false);
            paymenttype_wx_cb.setChecked(true);
            paymenttype_zfb_cb.setChecked(false);

            paymentCBHashMap.put(BALANCE_PAY, false);
            paymentCBHashMap.put(ZFB_PAY, false);
        } else if (paytype == ZFB_PAY) {
            paymenttype_banlance_cb.setChecked(false);
            paymenttype_wx_cb.setChecked(false);
            paymenttype_zfb_cb.setChecked(true);

            paymentCBHashMap.put(BALANCE_PAY, false);
            paymentCBHashMap.put(WX_PAY, false);
        }
    }

    private String prepayId;
    private String payType = BALANCE_PAY+"";
    private void pay(){
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.root_layout),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        if (null == paymentCBHashMap){
            setCBData(WX_PAY);
        }

        if (Constants.IS_DEVELOPING){
            System.out.println("payActivity中支付方式打印如下-------------------------");
            System.out.println("paymentCBHashMap.get(WX_PAY) = " + paymentCBHashMap.get(WX_PAY));
            System.out.println("paymentCBHashMap.get(ZFB_PAY) = " + paymentCBHashMap.get(ZFB_PAY));
            System.out.println("paymentCBHashMap.get(BALANCE_PAY) = " + paymentCBHashMap.get(BALANCE_PAY));
        }

        if (paymentCBHashMap.get(WX_PAY)){
            payType = WX_PAY +"";
        }else if (paymentCBHashMap.get(ZFB_PAY)){
            payType = ZFB_PAY +"";
        }else{
            payType = BALANCE_PAY +"";
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_PAYBUTTON;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("pay_type",payType)
                .add("order_sn",order_sn).build();
        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"order_sn",order_sn);

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
                    if (Constants.IS_DEVELOPING){
                        Log.i(Constants.TAG, str);
                        System.out.println("str ----------------------------------------------------= " + str);
                    }

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS && null != resInfo.getData()) {


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String order_sn = (String) JSONObject.parseObject(resInfo.getData().toString()).get("order_sn");

                                Intent intent = new Intent(PayActivity.this, AfterPayActivity.class);
                                intent.putExtra("order_sn", order_sn);
                                startActivity(intent);

                                PayActivity.this.finish();
                            }
                        });
                    } else {
                        if (StringUtil.isNotEmpty(resInfo.getError_message())) {
                            Message msg = new Message();
                            msg.what = ERROR_WHAT;
                            msg.obj = resInfo.getError_message();
                            myHandler.sendMessage(msg);
                        }
                    }

                    String tokenId = (String) JSONObject.parseObject(str).get("token_id");
                    String services = (String) JSONObject.parseObject(str).get("services");
                    System.out.println("tokenId = " + tokenId);
                    System.out.println("services = " + services);

//                    prepayId = JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject(str).get("token_id").toString()).get("pay_info").toString()).get("prepayid").toString();

//                    String tokenId = JSONObject.parseObject(JSONObject.parseObject(str).get("token_id").toString()).get("token_id").toString().toString();
                    wftPay(tokenId);
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理"+url+" 返回的成功数据报错");
                }
            }
        });
    }

    private void wftPay(String tokenId){
        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"payFrom","PayActivity");

        RequestMsg msg = new RequestMsg();
        msg.setTokenId(tokenId);
        if (Constants.IS_DEVELOPING) {
            System.out.println("msg.getTokenId() --------------------------------= " + msg.getTokenId());
            System.out.println("选择类型是： --------------------------------" + payType);
            Log.i("tag", "typetag-->" + payType);
        }
        if (payType.equals(WX_PAY+"")) {
            //微信
            msg.setTradeType(MainApplication.WX_APP_TYPE);
            msg.setAppId(Constants.WXAPPID);//wxd3a1cdf74d0c41b3
            if (Constants.IS_DEVELOPING){
                System.out.println("msg.getTradeType() -------------------------------- = " + msg.getTradeType());
                System.out.println("msg.getAppId() --------------------------------= " + msg.getAppId());
            }
            PayPlugin.unifiedAppPay(PayActivity.this, msg);
        } else {
            //支付宝
            msg.setTradeType(MainApplication.PAY_NEW_ZFB_WAP);
            PayPlugin.unifiedH5Pay(PayActivity.this, msg);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*-----------------------------------------威富通支付----------------------------------------------------------------------------------*/

    @Override
    protected void onStart() {
        if (Constants.IS_DEVELOPING){
            System.out.println("payActivity------------------------onStart()");
        }
        if (null != myBrodcastReceiver){
            registerReceiver(myBrodcastReceiver,intentFilter);
        }

        super.onStart();
    }

    @Override
    protected void onPause() {
        if (Constants.IS_DEVELOPING){
            System.out.println("payActivity------------------------onPause()");
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (Constants.IS_DEVELOPING){
            System.out.println("payActivity------------------------onDestory()");
        }
        super.onDestroy();
        if (null != myBrodcastReceiver){
            unregisterReceiver(myBrodcastReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.IS_DEVELOPING){
            System.out.println("payActivity------------------------onResume()");
        }

        PayStatusInfo payStatusInfo  = (PayStatusInfo) SharedPreferencesUtils.queryForSharedToObject(MyApplication.getContext(),order_sn);
        if (Constants.IS_DEVELOPING){
            System.out.println("PayActivity----------------------------------------------------"+getTaskId());
        }

        getData();

        if (null != payStatusInfo
                && StringUtil.isNotEmpty(payStatusInfo.getOrder_sn())
                && order_sn.equals(payStatusInfo.getOrder_sn())
                && payStatusInfo.isPayed()){

            if (Constants.IS_DEVELOPING){
                System.out.println("检测到当前订单支付成功，进行跳转 ---------------= " + "检测到当前订单支付成功，进行跳转");
            }
            Intent intent = new Intent(PayActivity.this,AfterPayActivity.class);
            intent.putExtra("order_sn",order_sn);
            startActivity(intent);
            PayActivity.this.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            notifyToCloseBlankTOPayActivity();

            PayActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void notifyToCloseBlankTOPayActivity(){
        Intent intent = new Intent();
        intent.setAction(Constants.PAYACTIVITY_FINISH_ACTION);
        sendBroadcast(intent);
    }

    BroadcastReceiver myBrodcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String order_sn;
                if (null != intent && StringUtil.isNotEmpty(intent.getStringExtra("order_sn"))) {
                    order_sn = intent.getStringExtra("order_sn");
                    System.out.println("PayActivity从WXPayEntryActivity中获取到order_sn-------------- = " + order_sn);
                }else{
                    order_sn = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"order_sn");
                    System.out.println("PayActivity从SharedPreference中获取到order_sn ----------------= " + order_sn);
                }

                if (intent.getAction().equals(Constants.PAY_SUCCESS_ACTION) || intent.getAction().equals(Constants.PAY_ERROR_ACTION)){
                    /*Intent intent1 = new Intent(PayActivity.this,AfterPayActivity.class);
                    System.out.println("PayActivity支付成功后的订单号order_sn --------------------= " + order_sn);
                    intent1.putExtra("order_sn",order_sn);
                    startActivity(intent1);
                    PayActivity.this.finish();*/
                    System.out.println("支付成功or错误，进行支付查询-------------------");
                    checkPayStatus();
                }else if(intent.getAction().equals(Constants.PAY_CANCEL_ACTION)){
                    System.out.println("PayActivity 取消支付payActivity界面---------------------------------- = ");
                    /*Intent intent1 = new Intent(PayActivity.this,PayActivity.class);
                    intent1.putExtra("order_sn",order_sn);
                    startActivity(intent1);*/
                    checkPayStatus();
                    /*PayActivity.this.finish();*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void checkPayStatus() {
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.root_layout),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_ORDERQUERY;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final String order_sn = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"order_sn");
        String payFrom = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"payFrom");
        String orderQueryType = "1";//1 普通订单 2 充值订单
        if (StringUtil.isNotEmpty(payFrom) && "BalanceRechargeActivity".equals(payFrom)){
            orderQueryType = "2";
        }else{
            orderQueryType = "1";
        }

        if (Constants.IS_DEVELOPING){
            System.out.println("WXPayEntryActivity支付回调,查询支付情况，order_sn -------------------------= " + order_sn);
        }
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("order_sn",order_sn)
                .add("type",orderQueryType)
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

                        try {
                            String result_code = (String) JSONObject.parseObject(resInfo.getData().toString()).get("result_code");
                            if (result_code.equals("0")) {
                                if (Constants.IS_DEVELOPING) {
                                    System.out.println("WXPayEntryActivity支付成功-------------------- 返回order_sn:" + order_sn);
                                }

                            } else if (result_code.equals("1")) {
                                if (Constants.IS_DEVELOPING) {
                                    System.out.println("WXPayEntryActivity支付失败-------------------- 返回order_sn:" + order_sn);
                                }
                            }

                            toAfterPay(order_sn);
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (Constants.IS_DEVELOPING){
                                        System.out.println("WXPayEntryActivity支付后，插入本地数据库成功 ----------------------返回order_sn:"+order_sn);
                                    }
//                                    payReturn(order_sn);
//                                    getData();
                                toAfterPay(order_sn);

                                }
                            });
                            e.printStackTrace();
                        }

                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = "支付成功";
                        myHandler.sendMessage(msg);
                    } else {
                        toAfterPay(order_sn);

                        getData();

/*                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    private void toAfterPay(String order_sn){
        Intent intent1 = new Intent(PayActivity.this,AfterPayActivity.class);
        System.out.println("PayActivity支付成功后的订单号order_sn --------------------= " + order_sn);
        intent1.putExtra("order_sn",order_sn);
        startActivity(intent1);
        PayActivity.this.finish();
    }

    /**
     * 支付方式bean
     */
    public class PaymentInfo{
//        private String icon;
        private int icon;
        private String name;
        private int paytype;
        private float money;

/*        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }*/

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPaytype() {
            return paytype;
        }

        public void setPaytype(int paytype) {
            this.paytype = paytype;
        }

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }
    }
}
