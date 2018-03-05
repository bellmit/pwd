package com.tastebychance.sonchance.homepage.afterpay;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.TabHostMainActivity;
import com.tastebychance.sonchance.common.CommonAdapter;
import com.tastebychance.sonchance.common.ViewHolder;
import com.tastebychance.sonchance.homepage.ShoppCartInstance;
import com.tastebychance.sonchance.homepage.afterpay.bean.AfterPayInfo;
import com.tastebychance.sonchance.homepage.afterpay.bean.MobileInfo;
import com.tastebychance.sonchance.homepage.afterpay.bean.OrderAgainInfo;
import com.tastebychance.sonchance.homepage.afterpay.bean.OrderDetailInfo;
import com.tastebychance.sonchance.homepage.myorder.MyOrderActivity;
import com.tastebychance.sonchance.homepage.order.bean.ShoppingcartBean;
import com.tastebychance.sonchance.homepage.orderform.bean.GiveDetailInfo;
import com.tastebychance.sonchance.homepage.pay.PayActivity;
import com.tastebychance.sonchance.homepage.toevaluate.ToEvaluateActivity;
import com.tastebychance.sonchance.homepage.toevaluate.bean.ButtonInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.DensityUtils;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.util.CommomDialog;
import com.tastebychance.sonchance.util.commonpopupwindow.CommonPopupWindow;
import com.tastebychance.sonchance.view.CustomLoadingFactory;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 订单详情：支付完成后界面
 * Created by shenbh on 2017/8/30.
 */

public class AfterPayActivity extends MyBaseActivity implements View.OnClickListener{

    //状态：支付成功、等待送达、订单已完成
    private TextView afterpay_station_tv;
    //支付成功
    private LinearLayout afterpay_paysuccess_ll;
    private TextView afterpay_connectionseller_tv;//联系商户
    private TextView afterpay_confirmreceive_tv;//等待送达
    //订单已完成
    private LinearLayout afterpay_orderformdone_ll;
    private TextView afterpay_onceagain_tv;//再来一单
    private TextView afterpay_toevaluate_tv;//去评价
    //订单明细-购物车内容
    private com.tastebychance.sonchance.view.MyListView afterpay_dishes_mlv;
    //订单明细-券、赠送内容
    private com.tastebychance.sonchance.view.MyListView afterpay_coupondish_mlv;
    //小计数额 ￥96
    private TextView afterpay_subtotal_tv;
    //订单信息-配送地址内容
    private TextView afterpay_address_tv;
    //订单信息-联系人信息
    private TextView afterpay_userinfo_tv;
    //订单信息-订单号内容
    private TextView afterpay_ordernumber_tv;
    //订单信息-下单时间
    private TextView afterpay_ordertime_tv;

    //已评价后-我的评价
    private RelativeLayout afterpay_evaluate_rl;

//    private final String PAY_SUCCESS = "paySuccess";//支付成功
//    private final String WAIT_SERVICE = "waitService";//等待送达
//    private final String ORDER_DONE = "orderformDone";//订单已完成
//    private final String EVALUATED = "evaluated";//已经评价
//    private String stationFlag = PAY_SUCCESS;

    private String order_sn;//订单号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_orderform_afterpay);
        bindViews();
        Intent intent = getIntent();
        if (intent != null) {
            order_sn = intent.getStringExtra("order_sn");
        }

        setListener();
//        initObject();

        getTrasport();//配送页面

        setTitle();
    }

    /**
     * 设置标题
     */
    private TextView center_tv;
    private void setTitle() {
        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null) {
//            if (stationFlag.equals(PAY_SUCCESS) || stationFlag.equals(WAIT_SERVICE)) {
            if (null != afterPayInfo){
                if (afterPayInfo.getStatus() == 0 || afterPayInfo.getStatus() == 1) {//订单状态 0:等待支付 1：等待送达 2：订单已完成 3：订单已评价 4：订单已取消
                    center_tv.setText("预计11:52前送达");
                } else if(afterPayInfo.getStatus() == 2){
                    center_tv.setText("订单详情");
                }
            }
        }
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    intentToTabHostMain();
                    AfterPayActivity.this.finish();
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

    private void intentToTabHostMain(){
        Intent intent = new Intent(AfterPayActivity.this, TabHostMainActivity.class);
        startActivity(intent);
    }

    private void bindViews() {
        afterpay_station_tv = (TextView) findViewById(R.id.afterpay_station_tv);
        afterpay_paysuccess_ll = (LinearLayout) findViewById(R.id.afterpay_paysuccess_ll);
        afterpay_connectionseller_tv = (TextView) findViewById(R.id.afterpay_connectionseller_tv);
        afterpay_confirmreceive_tv = (TextView) findViewById(R.id.afterpay_confirmreceive_tv);
        afterpay_orderformdone_ll = (LinearLayout) findViewById(R.id.afterpay_orderformdone_ll);
        afterpay_onceagain_tv = (TextView) findViewById(R.id.afterpay_onceagain_tv);
        afterpay_toevaluate_tv = (TextView) findViewById(R.id.afterpay_toevaluate_tv);
        afterpay_dishes_mlv = (com.tastebychance.sonchance.view.MyListView) findViewById(R.id.afterpay_dishes_mlv);
        afterpay_coupondish_mlv = (com.tastebychance.sonchance.view.MyListView) findViewById(R.id.afterpay_coupondish_mlv);
        afterpay_subtotal_tv = (TextView) findViewById(R.id.afterpay_subtotal_tv);
        afterpay_address_tv = (TextView) findViewById(R.id.afterpay_couponnum_tv);
        afterpay_userinfo_tv = (TextView) findViewById(R.id.afterpay_userinfo_tv);
        afterpay_ordernumber_tv = (TextView) findViewById(R.id.afterpay_ordernumber_tv);
        afterpay_ordertime_tv = (TextView) findViewById(R.id.afterpay_ordertime_tv);
        afterpay_evaluate_rl = (RelativeLayout) findViewById(R.id.afterpay_evaluate_rl);
    }

    private void setListener() {
        afterpay_connectionseller_tv.setOnClickListener(this);
        afterpay_confirmreceive_tv.setOnClickListener(this);
        afterpay_onceagain_tv.setOnClickListener(this);
        afterpay_toevaluate_tv.setOnClickListener(this);
        afterpay_evaluate_rl.setOnClickListener(this);
    }

  /*   private void initObject() {

       afterpay_evaluate_rl.setVisibility(View.GONE);
        switch (stationFlag){
            case PAY_SUCCESS:
//                initPaySuccess();
//                break;
            case WAIT_SERVICE:
                initWaitService();
                break;
            case ORDER_DONE:
                initOrderDone();
//                break;
            case EVALUATED:
                initOrderDone();

//                evaluated();
                break;
        }*/


       /* if (stationFlag.equals(EVALUATED)) {//已评价，显示我的评价控件
            afterpay_evaluate_rl.setVisibility(View.VISIBLE);
        } else {
            afterpay_evaluate_rl.setVisibility(View.GONE);
        }

    }*/

/*    private void initPaySuccess(){
        afterpay_paysuccess_ll.setVisibility(View.VISIBLE);
        afterpay_orderformdone_ll.setVisibility(View.GONE);

        afterpay_connectionseller_tv.setVisibility(View.VISIBLE);
        afterpay_confirmreceive_tv.setVisibility(View.GONE);

        afterpay_station_tv.setText("支付成功");
    }

    private void initWaitService(){
        afterpay_paysuccess_ll.setVisibility(View.VISIBLE);
        afterpay_orderformdone_ll.setVisibility(View.GONE);

        afterpay_connectionseller_tv.setVisibility(View.VISIBLE);
        afterpay_confirmreceive_tv.setVisibility(View.VISIBLE);

        afterpay_station_tv.setText("等待送达");
    }

    private void initOrderDone(){
        afterpay_paysuccess_ll.setVisibility(View.GONE);
        afterpay_orderformdone_ll.setVisibility(View.VISIBLE);

        afterpay_onceagain_tv.setVisibility(View.VISIBLE);
        afterpay_toevaluate_tv.setVisibility(View.VISIBLE);
        afterpay_station_tv.setText("订单已完成");
    }

    private void evaluated(){
        afterpay_paysuccess_ll.setVisibility(View.GONE);
        afterpay_orderformdone_ll.setVisibility(View.VISIBLE);

        afterpay_onceagain_tv.setVisibility(View.VISIBLE);
        afterpay_toevaluate_tv.setVisibility(View.GONE);
        afterpay_station_tv.setText("订单已完成");

        afterpay_evaluate_rl.setVisibility(View.VISIBLE);
    }*/

    /**
     * 配送页面接口
     */
    private AfterPayInfo afterPayInfo;
    private void getTrasport() {
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
        final String url =  UrlManager.URL_ORDERFORM_TRASPORT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token).add("order_sn",order_sn).build();
//        RequestBody formBody = new FormBody.Builder().add("token", token).add("order_sn","o2017091914123257193").build();
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
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        afterPayInfo = JSONObject.parseObject(resInfo.getData().toString(), AfterPayInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData(afterPayInfo);
//                                stationFlag = WAIT_SERVICE;
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
     * 确认收货
     */
    private void confirm() {
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
        final String url = UrlManager.URL_CONFIRM;
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
                String str = response.body().string();
                Log.i(Constants.TAG, str);

                try {
                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if(resInfo.getResult() == Constants.RESULT_SUCCESS){
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //确认收货成功后请求数据来刷新界面
                                getTrasport();
                            }
                        });
                    }else{
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);

                        getTrasport();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理"+url+" 返回的成功数据报错");
                }
            }

        });
    }

    /**联系商家
     * /api/UserApp/tel
     */
    private void contactSeller() {
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
        final String url = UrlManager.URL_TEL;
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

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if(resInfo.getResult() == Constants.RESULT_SUCCESS){
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        final MobileInfo mobileInfo = JSONObject.parseObject(resInfo.getData().toString(), MobileInfo.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null != mobileInfo && StringUtil.isNotEmpty(mobileInfo.getMobile().toString())) {
                                    //弹出框提示是否拨打电话
                                    new CommomDialog(AfterPayActivity.this,R.style.dialog, "是否拨打" + mobileInfo.getMobile().toString() ,new CommomDialog.OnCloseListener(){
                                        @Override
                                        public void onClick(Dialog dialog, boolean confirm) {
                                            if (confirm){
                                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileInfo.getMobile().toString())));
                                                dialog.dismiss();
                                            }
                                        }
                                    }).setTitle("提示").show();
                                } else {
                                    Message msg = new Message();
                                    msg.what = ERROR_WHAT;
                                    msg.obj = "商家未留电话,请走意见反馈渠道!";
                                    myHandler.sendMessage(msg);
                                }
                            }
                        });
                    }else{
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


    /**再来一单
     * /api/UserApp/orderAgain
     */
    private void orderAgain() {
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
        final String url = UrlManager.URL_ORDERAGAIN;
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

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        final List<OrderAgainInfo> againInfos = resInfo.getDataList(OrderAgainInfo.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //塞到购物车中
                                for (OrderAgainInfo orderAginInfo : againInfos) {
                                    WeakReference<ShoppingcartBean> wf = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());
                                    wf.get().setDishname(orderAginInfo.getDishes_name());
                                    wf.get().setNum(orderAginInfo.getCount());
                                    wf.get().setPrice(orderAginInfo.getUnit_price());
                                    wf.get().setId(orderAginInfo.getDishes_id());
                                    ShoppCartInstance.getInstance().add(wf.get());
                                }

                                intentToTabHostMain();
                                AfterPayActivity.this.finish();
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

    /**取消订单
     * /api/UserApp/cancel
     */
    private void orderCancel() {
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
        final String url = UrlManager.URL_ORDERCANCEL;
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

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //取消订单成功后
                                intentToTabHostMain();
                                AfterPayActivity.this.finish();
                            }
                        });
                    } else {
                        //取消订单失败
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                        //返回上一级
//                        AfterPayActivity.this.finish();

                        getTrasport();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理"+url+" 返回的成功数据报错");
                }
            }

        });
    }

    private CommonAdapter<OrderDetailInfo> orderDetailInfoCommonAdapter;
    private void initData(AfterPayInfo afterPayInfo) {
        if (null == afterPayInfo){
            return;
        }

        //标题
        if (center_tv != null) {
//            if (stationFlag.equals(PAY_SUCCESS) || stationFlag.equals(WAIT_SERVICE)) {

            if (afterPayInfo.getStatus() == 0 || afterPayInfo.getStatus() == 1) {//订单状态 0:等待支付 1：等待送达 2：订单已完成 3：订单已评价 4：订单已取消
                if (StringUtil.isNotEmpty(afterPayInfo.getSend_time())) {
                    center_tv.setText("预计" + afterPayInfo.getSend_time() + "前送达");
                } else {
                    center_tv.setText("等待送达");
                }
            } else {
                center_tv.setText("订单详情");
            }
        }

        //订单状态 0:等待支付 1：已支付 2：已配送 3：订单已完成 4：订单已评价 5：订单已取消
        afterpay_station_tv.setText(StringUtil.statusToDescribe(afterPayInfo.getStatus()));

        //订单明细-购物车
        if (null == afterPayInfo.getOrder_detail() || afterPayInfo.getOrder_detail().size() <= 0) {
            return;
        }
        List<OrderDetailInfo> orderDetailInfos = afterPayInfo.getOrder_detail();
        afterpay_dishes_mlv.setAdapter(orderDetailInfoCommonAdapter = new CommonAdapter<OrderDetailInfo>(
                getApplicationContext(),orderDetailInfos,R.layout.home_orderform_disheslistitem
        ) {
            @Override
            protected void convert(ViewHolder viewHolder, OrderDetailInfo item) {
                viewHolder.setText(R.id.order_form_dishname_tv,item.getDishes_name());
                viewHolder.setText(R.id.order_form_dishnum_tv,"x"+ item.getCount());
                viewHolder.setText(R.id.order_form_dishprice_tv,StringUtil.setMoneySize(item.getUnit_price()+"",10,14).toString());

                //订单明细-优惠情况
                LinearLayout order_form_give_ll = viewHolder.getView(R.id.order_form_give_ll);
                ImageView order_form_give_iv = viewHolder.getView(R.id.order_form_give_iv);
                TextView order_form_givedishprice_tv = viewHolder.getView(R.id.order_form_givedishprice_tv);
                 try {

                    //返回是对象的处理
                    if(null == item.getGive() || StringUtil.isEmpty(item.getGive().toString())){
                        order_form_give_ll.setVisibility(View.GONE);
                    }else{
                        GiveDetailInfo giveDetailInfo = JSONObject.parseObject(item.getGive().toString(),GiveDetailInfo.class);
                        if (StringUtil.isEmpty(giveDetailInfo.getDishes_name())){
                            order_form_give_ll.setVisibility(View.GONE);
                        }else{
                            order_form_give_ll.setVisibility(View.VISIBLE);
                            order_form_give_iv.setImageDrawable(getResources().getDrawable(R.drawable.order_form_sendicon));
                            viewHolder.setText(R.id.order_form_givedishname_tv,giveDetailInfo.getDishes_name());
                            viewHolder.setText(R.id.order_form_givedishnum_tv,"x"+giveDetailInfo.getNum()+"");
                            order_form_givedishprice_tv.setText(StringUtil.setMoneySize(giveDetailInfo.getSave_price()+"",10,14).toString());
                            order_form_givedishprice_tv.setTextColor(Color.BLACK);
                            //赠送的or券送的则加中划线
                            order_form_givedishprice_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        }
                    }
                } catch (Exception e){
                    //返回是集合的处理
                    if (null == item.getGive() || item.getGiveList(GiveDetailInfo.class).size() <= 0){
                        order_form_give_ll.setVisibility(View.GONE);
                    }else{
                        GiveDetailInfo giveDetailInfo = JSONObject.parseObject(item.getGive().toString(),GiveDetailInfo.class);
                        order_form_give_ll.setVisibility(View.VISIBLE);
                        order_form_give_iv.setImageDrawable(getResources().getDrawable(R.drawable.order_form_sendicon));
                        viewHolder.setText(R.id.order_form_givedishname_tv,giveDetailInfo.getDishes_name());
                        viewHolder.setText(R.id.order_form_givedishnum_tv,"x"+giveDetailInfo.getNum()+"");
                        order_form_givedishprice_tv.setText(StringUtil.setMoneySize(giveDetailInfo.getSave_price()+"",10,14));
                        order_form_givedishprice_tv.setTextColor(Color.BLACK);
                        //赠送的or券送的则加中划线
                        order_form_givedishprice_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            }
        });

        //小计
        afterpay_subtotal_tv.setText("￥"+afterPayInfo.getDed_price());

        //配送地址
        afterpay_address_tv.setText(afterPayInfo.getReceived_address());

        //姓名，联系电话
        afterpay_userinfo_tv.setText(afterPayInfo.getReceived_username()+afterPayInfo.getReceived_tel());

        //订单号
        afterpay_ordernumber_tv.setText(afterPayInfo.getOrder_sn());
        //下单时间
        afterpay_ordertime_tv.setText(afterPayInfo.getCreate_time());

        //动态生成几个状态按钮
        initButtons(afterPayInfo);
    }


    /**
     * 初始化按钮控件
     *
     * 动态生成按钮控件
     * @param afterPayInfo
     */
    private void initButtons(AfterPayInfo afterPayInfo) {
        List<ButtonInfo> buttonInfos = afterPayInfo.getButton();
        if (null != buttonInfos && buttonInfos.size() > 0){
            int size = buttonInfos.size();

        }

        afterpay_orderformdone_ll.removeAllViews();
        //动态生成按钮
        if (null != buttonInfos){
            LinearLayout.LayoutParams tv_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tv_lp.width = DensityUtils.dp2px(AfterPayActivity.this,100);
//            tv_lp.height = DensityUtils.dp2px(AfterPayActivity.this,40);
            tv_lp.gravity = Gravity.CENTER;
            tv_lp.rightMargin = 10;

            for (int i = 0; i < buttonInfos.size(); i++) {
                        /*if (i == buttonInfos.size() - 1){
                            tv_lp.rightMargin = 0;
                        }*/

                TextView tv = new TextView(AfterPayActivity.this);
                tv.setCompoundDrawablePadding(DensityUtils.dp2px(AfterPayActivity.this,5));
                tv.setGravity(Gravity.CENTER);

                tv.setLayoutParams(tv_lp);
                tv.setText(buttonInfos.get(i).getText());
                if (buttonInfos.get(i).getAction_id() == 4){//去评价
                    tv.setBackground(AfterPayActivity.this.getResources().getDrawable(R.drawable.circle5_redbg_style));
                    tv.setTextColor(Color.WHITE);
                }else if (buttonInfos.get(i).getAction_id() == 2){//确认收货
                    tv.setBackground(AfterPayActivity.this.getResources().getDrawable(R.drawable.circle5_greenbg_style));
                    tv.setTextColor(Color.WHITE);
                }else if (buttonInfos.get(i).getAction_id() == 5){//取消订单
                    tv.setBackground(AfterPayActivity.this.getResources().getDrawable(R.drawable.circle5_rededge_style));
                    tv.setTextColor(Color.RED);
                }else{
                    tv.setBackground(AfterPayActivity.this.getResources().getDrawable(R.drawable.circle5_grayedge_style));
                    tv.setTextColor(Color.BLACK);
                }

                afterpay_orderformdone_ll.addView(tv);

                btnClick(afterPayInfo,buttonInfos.get(i).getAction_id(),tv);
            }
        }
    }

    /**
     *根据不同的actionId执行不同的动作
     */
    private void btnClick(final AfterPayInfo item, final int actionId, TextView tv){
        if (actionId == 0){//去支付
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(AfterPayActivity.this,actionId+"",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AfterPayActivity.this, PayActivity.class);
                    intent.putExtra("order_sn",item.getOrder_sn());
                    startActivity(intent);
                    AfterPayActivity.this.finish();
                }
            });
        }else if (actionId == 1){//联系商家
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(AfterPayActivity.this,actionId+"",Toast.LENGTH_SHORT).show();
                    contactSeller();
                }
            });
        }else if (actionId == 2){//确认收货
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirm();
                }
            });
        }else if (actionId == 3){//再来一单
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(AfterPayActivity.this,actionId+"",Toast.LENGTH_SHORT).show();

                    orderAgain();
                }
            });
        }else if (actionId == 4){//去评价
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AfterPayActivity.this, ToEvaluateActivity.class);
                    intent.putExtra("order_sn",afterPayInfo.getOrder_sn());
                    startActivity(intent);
                }
            });
        }else if (actionId == 5){//订单取消
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(AfterPayActivity.this,actionId+"",Toast.LENGTH_SHORT).show();
                    //弹出框“确认取消订单”
                    comfirmCancelOrderPopupWindow();

                }
            });
        }
    }

    /**
     * 弹出框“确认取消订单”
     */
    private CommonPopupWindow popupWindow;
    private void comfirmCancelOrderPopupWindow() {
        popupWindow = new CommonPopupWindow.Builder(AfterPayActivity.this)
                //设置Popupwindow布局
                .setView(R.layout.home_afterpay_cancelorderpopupwindow)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色,取值范围0.0f-1.0f 值越小越暗 1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface(){

                    @Override
                    public void getChildView(View view, int layoutResId) {
                        TextView afterpay_confirmcancelorder_tv = (TextView)view.findViewById(R.id.afterpay_confirmcancelorder_tv);
                        TextView afterpay_cancelorder_cancel_tv = (TextView) view.findViewById(R.id.afterpay_cancelorder_cancel_tv);
                        afterpay_confirmcancelorder_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                orderCancel();
                                if (popupWindow != null){
                                    popupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                            }
                        });

                        afterpay_cancelorder_cancel_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow != null){
                                    popupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                            }
                        });
                    }
                })
                //设置外部是否可以点击，默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        setBackgroundAlpha(0.3f);
        //弹出popupwindow
        popupWindow.showAtLocation(findViewById(android.R.id.content),Gravity.BOTTOM,0,0);
    }

    // 设置透明度
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        this.getWindow().setAttributes(layoutParams);
    }

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

        if (Constants.IS_DEVELOPING){
            System.out.println("WXPayEntryActivity支付回调,查询支付情况，order_sn -------------------------= " + order_sn);
        }
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("order_sn",order_sn)
                .add("type","1")
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
 /*           case R.id.afterpay_connectionseller_tv://联系商户

                break;
            case R.id.afterpay_confirmreceive_tv://确认收货
                confirm();
                break;
            case R.id.afterpay_onceagain_tv://再来一单

                break;
            case R.id.afterpay_toevaluate_tv://去评价
                Intent intent = new Intent(AfterPayActivity.this, ToEvaluateActivity.class);
                intent.putExtra("order_id",afterPayInfo.getOrder_detail().get(0).getDishes_id());
                startActivity(intent);
                break;*/
            case R.id.afterpay_evaluate_rl://我的评价
                break;
            default:
                break;
        }
    }

   /* @Override
    public void finish() {
        startActivity(new Intent(AfterPayActivity.this, TabHostMainActivity.class));
//        AfterPayActivity.this.finish();
        super.finish();
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            intentToTabHostMain();
            AfterPayActivity.this.finish();
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPayStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("After----------------------------------------------------"+getTaskId());
    }

    @Override
    public void finish() {
        SharedPreferencesUtils.setConfigBoolean(MyApplication.getContext(),Constants.TEMP,"toMyOrder",true);
        super.finish();
    }
}
