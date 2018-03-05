package com.tastebychance.sonchance.personal.balance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.personal.PersonalActivity;
import com.tastebychance.sonchance.personal.balance.bean.BalanceInfo;
import com.tastebychance.sonchance.personal.bean.UserInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BalanceActivity extends MyBaseActivity{

    private TextView balance_value_tv;//账户余额
    private UserInfo userInfo;

    private IntentFilter intentfilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_balance);

        intentfilter = new IntentFilter();
        intentfilter.addAction(Constants.RECHARGE_CANCEL_ACTION);
        intentfilter.addAction(Constants.RECHARGE_ERROR_ACTION);
        intentfilter.addAction(Constants.RECHARGE_SUCCESS_ACTION);

        userInfo = (UserInfo) SharedPreferencesUtils.queryForSharedToObject(MyApplication.getContext(),"userInfo");

        bindViews();
        setTitle();
    }

    private void bindViews() {
        balance_value_tv = (TextView) findViewById(R.id.balance_value_tv);
        if (null != userInfo){
            balance_value_tv.setText(userInfo.getMoney()+"");
        }
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
            center_tv.setText("账户余额");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Constants.BALANCE_FINISH_ACTION);
                    sendBroadcast(intent);
                    BalanceActivity.this.finish();
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
        final String url = UrlManager.URL_PERSON_USERMONEY;
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
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = UPDATE_SUCCESS;
                        myHandler.sendMessage(msg);


                        final BalanceInfo balanceInfo = JSONObject.parseObject(resInfo.getData().toString(), BalanceInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null != balance_value_tv) {
                                    balance_value_tv.setText(balanceInfo.getMoney() + "");
                                }
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

    BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                String action = intent.getAction();
                if (action.equals(Constants.RECHARGE_CANCEL_ACTION) || action.equals(Constants.RECHARGE_ERROR_ACTION) || action.equals(Constants.RECHARGE_SUCCESS_ACTION)){
                    BalanceActivity.this.finish();
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
            Intent intent = new Intent();
            intent.setAction(Constants.BALANCE_FINISH_ACTION);
            sendBroadcast(intent);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (null != myBroadcastReceiver){
            registerReceiver(myBroadcastReceiver,intentfilter);
        }

        checkPayStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != myBroadcastReceiver){
            unregisterReceiver(myBroadcastReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("BalanceActivity----------------------------------------------------"+getTaskId());
        getData();
    }

    private void checkPayStatus() {
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
        final String url = UrlManager.URL_ORDERQUERY;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final String order_sn = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"order_sn");

        if (Constants.IS_DEVELOPING){
            System.out.println("WXPayEntryActivity支付回调,查询支付情况，order_sn -------------------------= " + order_sn);
        }
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("order_sn",order_sn)
                .add("type","2") //1 普通订单 2 充值订单
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
            }
        });
    }

    /**
     * 查看明细
     * @param view
     */
    public void balanceDetailClick(View view){
        startActivity(new Intent(BalanceActivity.this,BalanceDetailActivity.class));
    }

    //账户充值
    public void rechargeClick(View view){
//        SystemUtil.getInstance().makeToastInfo(BalanceActivity.this);
        startActivity(new Intent(BalanceActivity.this,BalanceRechargeActivity.class));
    }
}
