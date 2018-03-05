package com.tastebychance.sonchance.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
//    PayActivity pmyid = new PayActivity();
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private RelativeLayout mLayout;

    private TextView tvOrderNo, tvOrderTime, tvMoney;
    private IWXAPI api;

    public final int ERROR_WHAT = 0;//错误提示
    public final int INFO_WHAT = 1;//消息提示
    public String UPDATE_SUCCESS = "更新成功";
    public String GETDATA_SUCCESS = "请求数据成功";
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR_WHAT:
                    UiHelper.ShowOneToast(getApplicationContext(), (String) msg.obj);
                    break;
                case INFO_WHAT:
                    if (Constants.IS_DEVELOPING) {
                        UiHelper.ShowOneToast(getApplicationContext(), (String) msg.obj);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_results);
        api = WXAPIFactory.createWXAPI(this, Constants.WXAPPID);//appid需换成商户自己开放平台appid
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            // resp.errCode == -1 原因：支付错误,可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
            // resp.errCode == -2 原因 用户取消,无需处理。发生场景：用户不支付了，点击取消，返回APP

            if (resp.errCode == 0) // 支付成功
            {
                String order_sn = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"order_sn");
                payReturn(order_sn);
            } else if (resp.errCode == -2) {
                Toast.makeText(this, getString(R.string.action_cancel) + resp.errCode + "test", Toast.LENGTH_SHORT).show();

                payCancel();
            } else if (resp.errCode == -1){
                Toast.makeText(this, "支付错误："+resp.errCode+";可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等", Toast.LENGTH_SHORT).show();

                payError();
            }
        }
    }

    /**
     * 支付错误
     */
    private void payError() {
        String payFrom = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"payFrom");
        String order_sn = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"order_sn");

        Intent intent = new Intent();
        if (StringUtil.isEmpty(payFrom) || "PayActivity".equals(payFrom)){
            if (Constants.IS_DEVELOPING){
                System.out.println("WXPayEntryActivity支付错误-----------------------PayActivity,order_sn:"+order_sn);
            }
            intent.setAction(Constants.PAY_ERROR_ACTION);
        }else{
            if (Constants.IS_DEVELOPING){
                System.out.println("WXPayEntryActivity支付错误-----------------------BalanceActivity,order_sn:"+order_sn);
            }
            intent.setAction(Constants.RECHARGE_ERROR_ACTION);
        }
        sendBroadcast(intent);

        SystemUtil.getInstance().setPayStatus(order_sn,false);
        WXPayEntryActivity.this.finish();
    }

    /**
     * 取消支付
     */
    private void payCancel() {
        String payFrom = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"payFrom");
        String order_sn = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"order_sn");

        Intent intent = new Intent();
        if (StringUtil.isEmpty(payFrom) || "PayActivity".equals(payFrom)){
            if (Constants.IS_DEVELOPING){
                System.out.println("WXPayEntryActivity取消支付-----------------------PayActivity,order_sn:"+order_sn);
            }
            intent.setAction(Constants.PAY_CANCEL_ACTION);
        }else{
            if (Constants.IS_DEVELOPING){
                System.out.println("WXPayEntryActivity取消支付-----------------------BalanceActivity,order_sn:"+order_sn);
            }
            intent.setAction(Constants.RECHARGE_CANCEL_ACTION);
        }
        sendBroadcast(intent);

        SystemUtil.getInstance().setPayStatus(order_sn,false);

        WXPayEntryActivity.this.finish();
    }

    private void payReturn(String order_sn){
        String payFrom = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"payFrom");
        Intent intent = new Intent();
        if (StringUtil.isEmpty(payFrom) || "PayActivity".equals(payFrom)){
            if (Constants.IS_DEVELOPING){
                System.out.println("WXPayEntryActivity支付成功-----------------------PayActivity,返回order_sn:"+order_sn);
            }
            intent.setAction(Constants.PAY_SUCCESS_ACTION);
        }else{
            if (Constants.IS_DEVELOPING){
                System.out.println("WXPayEntryActivity支付成功-----------------------BalanceActivity,返回order_sn:"+order_sn);
            }
            intent.setAction(Constants.RECHARGE_SUCCESS_ACTION);
        }
        intent.putExtra("order_sn",order_sn);
        sendBroadcast(intent);

        SystemUtil.getInstance().setPayStatus(order_sn,true);

        WXPayEntryActivity.this.finish();
    }
}
