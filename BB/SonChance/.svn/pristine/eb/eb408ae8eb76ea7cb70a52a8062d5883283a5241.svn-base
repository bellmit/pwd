package com.tastebychance.sonchance.personal.balance;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.common.CommonAdapter;
import com.tastebychance.sonchance.common.ViewHolder;
import com.tastebychance.sonchance.personal.balance.bean.BalanceDetailInfo;
import com.tastebychance.sonchance.personal.bankcard.BankCardActivity;
import com.tastebychance.sonchance.personal.bankcard.BankCardAddActivity;
import com.tastebychance.sonchance.personal.bankcard.bean.BankCardInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.ImageDownLoad;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;
import com.tastebychance.sonchance.view.PullRefreshListView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BalanceDetailActivity extends MyBaseActivity implements PullRefreshListView.IXListViewListener{

    private PullRefreshListView pullrefreshlistview;
    private int pageIndex = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.WHAT_REFRESH:
                    pageIndex = 0;
                    adapter.notifyDataSetChanged();
                    onLoad();
                    break;
                case Constants.WHAT_LOADMORE:
                    ++pageIndex;
                    adapter.notifyDataSetChanged();
                    onLoad();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_balance_detail);

        bindViews();
        setTitle();

        mDatas = new ArrayList<BalanceDetailInfo>();
    }

    private void bindViews() {
        pullrefreshlistview = (PullRefreshListView) findViewById(R.id.pullrefreshlistview);
        pullrefreshlistview.setPullLoadEnable(false);
        pullrefreshlistview.setPullRefreshEnable(false);
        pullrefreshlistview.setXListViewListener(this);
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
        view.setLayoutParams(lp);
        System.out.println("statusHeight----------------------------------------- = " + statusHeight);*/

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null)
            center_tv.setText("账户余额明细");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BalanceDetailActivity.this.finish();
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

    private void onLoad(){
        pullrefreshlistview.stopRefresh();
        pullrefreshlistview.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        pullrefreshlistview.setRefreshTime(date);
    }

    private List<BalanceDetailInfo> mDatas;
    private void getData(){
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
        final String url =  UrlManager.URL_PERSON_RECHARGEDETAIL;
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
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        mDatas = resInfo.getDataList(BalanceDetailInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData(mDatas);
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

    private CommonAdapter<BalanceDetailInfo> adapter;
    private void initData(List<BalanceDetailInfo> mDatas) {
        pullrefreshlistview.setAdapter(adapter = new CommonAdapter<BalanceDetailInfo>(
                getApplicationContext(),mDatas,R.layout.person_balancedetail_listitem
        ) {
            @Override
            protected void convert(ViewHolder viewHolder, BalanceDetailInfo item) {
                if (item.getStatus() == 1){//0充值失败 1充值成功 2订单支付 3订单退款
                    viewHolder.setText(R.id.person_balancedetail_status_tv,"充值成功");
                }else if (item.getStatus() == 2){
                    viewHolder.setText(R.id.person_balancedetail_status_tv,"订单支付");
                }else if (item.getStatus() == 3){
                    viewHolder.setText(R.id.person_balancedetail_status_tv,"订单退款");
                }else{
                    viewHolder.setText(R.id.person_balancedetail_status_tv,"充值失败");
                }

                TextView person_balancedetail_value_tv = viewHolder.getView(R.id.person_balancedetail_value_tv);
                String value = item.getMoney()+"元";
                if (item.getStatus() == 1 || item.getStatus() == 2){// 1充值成功
                    person_balancedetail_value_tv.setText(StringUtil.setTextSizeColor(value, Color.RED,0,value.length(),17));
                }else{
//                    value = "-"+value;
                    person_balancedetail_value_tv.setText(StringUtil.setTextSizeColor(value, Color.BLACK,0,value.length(),17));
                }

                TextView person_balancedetail_time_tv = viewHolder.getView(R.id.person_balancedetail_time_tv);
                person_balancedetail_time_tv.setTextColor(getResources().getColor(R.color.gray));
                person_balancedetail_time_tv.setText(item.getCreate_time());

                TextView person_balancedetail_from_tv = viewHolder.getView(R.id.person_balancedetail_from_tv);
                person_balancedetail_from_tv.setTextColor(getResources().getColor(R.color.gray));
                if (item.getPay_type() == 1){
                    person_balancedetail_from_tv.setText("微信支付");
                }else if(item.getPay_type() == 2){
                    person_balancedetail_from_tv.setText("支付宝支付");
                }
            }
        });
    }

    /**
     * 本地构造银行卡列表信息
     */
    private void constructDataByLocal(){
        for (int i = 0; i < 5; i++) {
            WeakReference<BalanceDetailInfo> wf = new WeakReference<BalanceDetailInfo>(new BalanceDetailInfo());
            wf.get().setPay_type(1);
            wf.get().setCreate_time("2017-09-25 18:08");
            wf.get().setStatus(1);
            wf.get().setMoney(500.00f);
            if (i == 4){
                wf.get().setStatus(0);
                wf.get().setMoney(1000);
                wf.get().setPay_type(2);
            }
            mDatas.add(wf.get());
        }
        initData(mDatas);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.IS_LOCALDATA){
            constructDataByLocal();
        }else{
            getData();
        }
    }

    @Override
    public void onListViewRefresh() {
        pageIndex = 0;
        if (Constants.IS_LOCALDATA){
            constructDataByLocal();
        }else{
            getData();
        }
        onLoad();
    }

    @Override
    public void onListViewLoadMore() {
        ++ pageIndex ;
        if (Constants.IS_LOCALDATA){
            constructDataByLocal();
        }else{
            getData();
        }
        onLoad();
    }
}
