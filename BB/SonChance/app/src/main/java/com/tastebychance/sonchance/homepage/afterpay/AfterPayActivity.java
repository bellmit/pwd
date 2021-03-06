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
 * ????????????????????????????????????
 * Created by shenbh on 2017/8/30.
 */

public class AfterPayActivity extends MyBaseActivity implements View.OnClickListener{

    //??????????????????????????????????????????????????????
    private TextView afterpay_station_tv;
    //????????????
    private LinearLayout afterpay_paysuccess_ll;
    private TextView afterpay_connectionseller_tv;//????????????
    private TextView afterpay_confirmreceive_tv;//????????????
    //???????????????
    private LinearLayout afterpay_orderformdone_ll;
    private TextView afterpay_onceagain_tv;//????????????
    private TextView afterpay_toevaluate_tv;//?????????
    //????????????-???????????????
    private com.tastebychance.sonchance.view.MyListView afterpay_dishes_mlv;
    //????????????-??????????????????
    private com.tastebychance.sonchance.view.MyListView afterpay_coupondish_mlv;
    //???????????? ???96
    private TextView afterpay_subtotal_tv;
    //????????????-??????????????????
    private TextView afterpay_address_tv;
    //????????????-???????????????
    private TextView afterpay_userinfo_tv;
    //????????????-???????????????
    private TextView afterpay_ordernumber_tv;
    //????????????-????????????
    private TextView afterpay_ordertime_tv;

    //????????????-????????????
    private RelativeLayout afterpay_evaluate_rl;

//    private final String PAY_SUCCESS = "paySuccess";//????????????
//    private final String WAIT_SERVICE = "waitService";//????????????
//    private final String ORDER_DONE = "orderformDone";//???????????????
//    private final String EVALUATED = "evaluated";//????????????
//    private String stationFlag = PAY_SUCCESS;

    private String order_sn;//?????????
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

        getTrasport();//????????????

        setTitle();
    }

    /**
     * ????????????
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
                if (afterPayInfo.getStatus() == 0 || afterPayInfo.getStatus() == 1) {//???????????? 0:???????????? 1??????????????? 2?????????????????? 3?????????????????? 4??????????????????
                    center_tv.setText("??????11:52?????????");
                } else if(afterPayInfo.getStatus() == 2){
                    center_tv.setText("????????????");
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


       /* if (stationFlag.equals(EVALUATED)) {//????????????????????????????????????
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

        afterpay_station_tv.setText("????????????");
    }

    private void initWaitService(){
        afterpay_paysuccess_ll.setVisibility(View.VISIBLE);
        afterpay_orderformdone_ll.setVisibility(View.GONE);

        afterpay_connectionseller_tv.setVisibility(View.VISIBLE);
        afterpay_confirmreceive_tv.setVisibility(View.VISIBLE);

        afterpay_station_tv.setText("????????????");
    }

    private void initOrderDone(){
        afterpay_paysuccess_ll.setVisibility(View.GONE);
        afterpay_orderformdone_ll.setVisibility(View.VISIBLE);

        afterpay_onceagain_tv.setVisibility(View.VISIBLE);
        afterpay_toevaluate_tv.setVisibility(View.VISIBLE);
        afterpay_station_tv.setText("???????????????");
    }

    private void evaluated(){
        afterpay_paysuccess_ll.setVisibility(View.GONE);
        afterpay_orderformdone_ll.setVisibility(View.VISIBLE);

        afterpay_onceagain_tv.setVisibility(View.VISIBLE);
        afterpay_toevaluate_tv.setVisibility(View.GONE);
        afterpay_station_tv.setText("???????????????");

        afterpay_evaluate_rl.setVisibility(View.VISIBLE);
    }*/

    /**
     * ??????????????????
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

        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
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
                    CrashHandler.getInstance().handlerError("??????"+url+" ???????????????????????????");
                }
            }
        });
    }

    /**
     * ????????????
     */
    private void confirm() {
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.root_layout),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
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
                                //????????????????????????????????????????????????
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
                    CrashHandler.getInstance().handlerError("??????"+url+" ???????????????????????????");
                }
            }

        });
    }

    /**????????????
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

        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
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
                                    //?????????????????????????????????
                                    new CommomDialog(AfterPayActivity.this,R.style.dialog, "????????????" + mobileInfo.getMobile().toString() ,new CommomDialog.OnCloseListener(){
                                        @Override
                                        public void onClick(Dialog dialog, boolean confirm) {
                                            if (confirm){
                                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileInfo.getMobile().toString())));
                                                dialog.dismiss();
                                            }
                                        }
                                    }).setTitle("??????").show();
                                } else {
                                    Message msg = new Message();
                                    msg.what = ERROR_WHAT;
                                    msg.obj = "??????????????????,????????????????????????!";
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
                    CrashHandler.getInstance().handlerError("??????"+url+" ???????????????????????????");
                }
            }

        });
    }


    /**????????????
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

        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
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
                                //??????????????????
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
                    CrashHandler.getInstance().handlerError("??????"+url+" ???????????????????????????");
                }
            }

        });
    }

    /**????????????
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

        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
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
                                //?????????????????????
                                intentToTabHostMain();
                                AfterPayActivity.this.finish();
                            }
                        });
                    } else {
                        //??????????????????
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                        //???????????????
//                        AfterPayActivity.this.finish();

                        getTrasport();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("??????"+url+" ???????????????????????????");
                }
            }

        });
    }

    private CommonAdapter<OrderDetailInfo> orderDetailInfoCommonAdapter;
    private void initData(AfterPayInfo afterPayInfo) {
        if (null == afterPayInfo){
            return;
        }

        //??????
        if (center_tv != null) {
//            if (stationFlag.equals(PAY_SUCCESS) || stationFlag.equals(WAIT_SERVICE)) {

            if (afterPayInfo.getStatus() == 0 || afterPayInfo.getStatus() == 1) {//???????????? 0:???????????? 1??????????????? 2?????????????????? 3?????????????????? 4??????????????????
                if (StringUtil.isNotEmpty(afterPayInfo.getSend_time())) {
                    center_tv.setText("??????" + afterPayInfo.getSend_time() + "?????????");
                } else {
                    center_tv.setText("????????????");
                }
            } else {
                center_tv.setText("????????????");
            }
        }

        //???????????? 0:???????????? 1???????????? 2???????????? 3?????????????????? 4?????????????????? 5??????????????????
        afterpay_station_tv.setText(StringUtil.statusToDescribe(afterPayInfo.getStatus()));

        //????????????-?????????
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

                //????????????-????????????
                LinearLayout order_form_give_ll = viewHolder.getView(R.id.order_form_give_ll);
                ImageView order_form_give_iv = viewHolder.getView(R.id.order_form_give_iv);
                TextView order_form_givedishprice_tv = viewHolder.getView(R.id.order_form_givedishprice_tv);
                 try {

                    //????????????????????????
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
                            //?????????or????????????????????????
                            order_form_givedishprice_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        }
                    }
                } catch (Exception e){
                    //????????????????????????
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
                        //?????????or????????????????????????
                        order_form_givedishprice_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            }
        });

        //??????
        afterpay_subtotal_tv.setText("???"+afterPayInfo.getDed_price());

        //????????????
        afterpay_address_tv.setText(afterPayInfo.getReceived_address());

        //?????????????????????
        afterpay_userinfo_tv.setText(afterPayInfo.getReceived_username()+afterPayInfo.getReceived_tel());

        //?????????
        afterpay_ordernumber_tv.setText(afterPayInfo.getOrder_sn());
        //????????????
        afterpay_ordertime_tv.setText(afterPayInfo.getCreate_time());

        //??????????????????????????????
        initButtons(afterPayInfo);
    }


    /**
     * ?????????????????????
     *
     * ????????????????????????
     * @param afterPayInfo
     */
    private void initButtons(AfterPayInfo afterPayInfo) {
        List<ButtonInfo> buttonInfos = afterPayInfo.getButton();
        if (null != buttonInfos && buttonInfos.size() > 0){
            int size = buttonInfos.size();

        }

        afterpay_orderformdone_ll.removeAllViews();
        //??????????????????
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
                if (buttonInfos.get(i).getAction_id() == 4){//?????????
                    tv.setBackground(AfterPayActivity.this.getResources().getDrawable(R.drawable.circle5_redbg_style));
                    tv.setTextColor(Color.WHITE);
                }else if (buttonInfos.get(i).getAction_id() == 2){//????????????
                    tv.setBackground(AfterPayActivity.this.getResources().getDrawable(R.drawable.circle5_greenbg_style));
                    tv.setTextColor(Color.WHITE);
                }else if (buttonInfos.get(i).getAction_id() == 5){//????????????
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
     *???????????????actionId?????????????????????
     */
    private void btnClick(final AfterPayInfo item, final int actionId, TextView tv){
        if (actionId == 0){//?????????
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
        }else if (actionId == 1){//????????????
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(AfterPayActivity.this,actionId+"",Toast.LENGTH_SHORT).show();
                    contactSeller();
                }
            });
        }else if (actionId == 2){//????????????
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirm();
                }
            });
        }else if (actionId == 3){//????????????
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(AfterPayActivity.this,actionId+"",Toast.LENGTH_SHORT).show();

                    orderAgain();
                }
            });
        }else if (actionId == 4){//?????????
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AfterPayActivity.this, ToEvaluateActivity.class);
                    intent.putExtra("order_sn",afterPayInfo.getOrder_sn());
                    startActivity(intent);
                }
            });
        }else if (actionId == 5){//????????????
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(AfterPayActivity.this,actionId+"",Toast.LENGTH_SHORT).show();
                    //?????????????????????????????????
                    comfirmCancelOrderPopupWindow();

                }
            });
        }
    }

    /**
     * ?????????????????????????????????
     */
    private CommonPopupWindow popupWindow;
    private void comfirmCancelOrderPopupWindow() {
        popupWindow = new CommonPopupWindow.Builder(AfterPayActivity.this)
                //??????Popupwindow??????
                .setView(R.layout.home_afterpay_cancelorderpopupwindow)
                //????????????
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                //????????????
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //??????????????????,????????????0.0f-1.0f ??????????????? 1.0f?????????
//                .setBackGroundLevel(0.3f)
                //??????popupwindow??????View???????????????
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
                //??????????????????????????????????????????true
                .setOutsideTouchable(true)
                //????????????
                .create();
        setBackgroundAlpha(0.3f);
        //??????popupwindow
        popupWindow.showAtLocation(findViewById(android.R.id.content),Gravity.BOTTOM,0,0);
    }

    // ???????????????
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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

        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
        final String url = UrlManager.URL_ORDERQUERY;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final String order_sn = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"order_sn");

        if (Constants.IS_DEVELOPING){
            System.out.println("WXPayEntryActivity????????????,?????????????????????order_sn -------------------------= " + order_sn);
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
 /*           case R.id.afterpay_connectionseller_tv://????????????

                break;
            case R.id.afterpay_confirmreceive_tv://????????????
                confirm();
                break;
            case R.id.afterpay_onceagain_tv://????????????

                break;
            case R.id.afterpay_toevaluate_tv://?????????
                Intent intent = new Intent(AfterPayActivity.this, ToEvaluateActivity.class);
                intent.putExtra("order_id",afterPayInfo.getOrder_detail().get(0).getDishes_id());
                startActivity(intent);
                break;*/
            case R.id.afterpay_evaluate_rl://????????????
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
