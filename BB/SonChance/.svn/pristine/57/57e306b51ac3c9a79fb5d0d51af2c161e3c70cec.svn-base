package com.tastebychance.sonchance.personal.bankcard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
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
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.common.CommonAdapter;
import com.tastebychance.sonchance.common.ViewHolder;
import com.tastebychance.sonchance.homepage.afterpay.AfterPayActivity;
import com.tastebychance.sonchance.personal.bankcard.adapter.BankCardAdapter;
import com.tastebychance.sonchance.personal.bankcard.bean.BankCardInfo;
import com.tastebychance.sonchance.personal.bankcard.bean.FindBankInfo;
import com.tastebychance.sonchance.personal.locate.GoodsReceiptAddressManagerActivity;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.ImageDownLoad;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.util.commonpopupwindow.CommonPopupWindow;
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

/**
 * 类描述：BankCardActivity 银行卡列表界面
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/9/5 17:27
 * 修改人：
 * 修改时间：2017/9/5 17:27
 * 修改备注：
 * @version 1.0
 */
public class BankCardActivity extends MyBaseActivity implements PullRefreshListView.IXListViewListener{

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
        setContentView(R.layout.personal_bank_card);

        bindViews();
        setTitle();

        mDatas = new ArrayList<BankCardInfo>();
    }

    private void bindViews() {
        pullrefreshlistview = (PullRefreshListView) findViewById(R.id.pullrefreshlistview);
        pullrefreshlistview.setPullLoadEnable(true);
        pullrefreshlistview.setXListViewListener(this);
    }

    /**
     * 设置标题
     */
    private void setTitle() {
        //动态设置状态栏下方的控件（view）的高度
//        View view = (View) findViewById(R.id.view1);
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
//        lp.height = statusHeight;
//        view.setLayoutParams(lp);
//        System.out.println("statusHeight----------------------------------------- = " + statusHeight);

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null)
            center_tv.setText("银行卡");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BankCardActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.VISIBLE);
            right_btn.setBackground(getResources().getDrawable(R.drawable.person_goodsreceipt_add));
            right_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //添加银行卡
                    Intent intent = new Intent(BankCardActivity.this, BankCardAddActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.GONE);
        }
    }

    private List<BankCardInfo> mDatas;
    private void getData(){

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url =  UrlManager.URL_PERSON_BANKLIST;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token).build();
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
                        msg.what = INFO_WHAT;
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        mDatas = resInfo.getDataList(BankCardInfo.class);

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

    private CommonAdapter<BankCardInfo> adapter;
    private void initData(List<BankCardInfo> mDatas) {
        pullrefreshlistview.setAdapter(adapter = new CommonAdapter<BankCardInfo>(
                getApplicationContext(),mDatas,R.layout.person_bankcard_listitem
        ) {
            @Override
            protected void convert(ViewHolder viewHolder, final BankCardInfo item) {
                viewHolder.setText(R.id.person_bank_number_tv,item.getBank_number());
                viewHolder.setText(R.id.person_bank_type_tv,item.getBank_type());
                ImageView bankIV = viewHolder.getView(R.id.person_bank_icon_iv);
                ImageDownLoad.getDownLoadSmallImg(UrlManager.REQUESTBANKIMGURL + item.getLetter(),bankIV);

                TextView person_bank_unbind_tv = viewHolder.getView(R.id.person_bank_unbind_tv);//解除绑定
                person_bank_unbind_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //弹框提示是否解除绑定

                        comfirmCancelOrderPopupWindow(item.getId()+"");
                    }
                });
            }
        });
    }

    /**
     * 弹出框“解除绑定”
     */
    private CommonPopupWindow popupWindow;
    private void comfirmCancelOrderPopupWindow(final String id) {
        popupWindow = new CommonPopupWindow.Builder(BankCardActivity.this)
                //设置Popupwindow布局
                .setView(R.layout.person_bindbank_unbindbankpopupwindow)
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
                                unBindBankCard(id);
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
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM,0,0);
    }

    // 设置透明度
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        this.getWindow().setAttributes(layoutParams);
    }

    /**解除绑定银行卡
     * /api/UserApp/deleteBank
     */
    private void unBindBankCard(String cardId){
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_PERSON_DELETEBANK;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("id",cardId).build();
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
                                Toast.makeText(BankCardActivity.this, "解除成功", Toast.LENGTH_SHORT).show();
                                update();
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

    private void onLoad(){
        pullrefreshlistview.stopRefresh();
        pullrefreshlistview.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        pullrefreshlistview.setRefreshTime(date);
    }

    /**
     * 本地构造银行卡列表信息
     */
    private void constructDataByLocal(){
        for (int i = 0; i < 5; i++) {
            WeakReference<BankCardInfo> wf = new WeakReference<BankCardInfo>(new BankCardInfo());
            wf.get().setId(12);
            wf.get().setUser_name("朱金榜");
            wf.get().setUser_number("350322199410273016");
            wf.get().setBank_name("ICBC中国工商银行");
            wf.get().setBank_type("信用卡");
            wf.get().setUid(59);
            wf.get().setMobile("18159412205");
            wf.get().setLetter("ICBC");
            wf.get().setBank_number("6212261405004600748");
            wf.get().setCreate_time(new Date());
            mDatas.add(wf.get());
        }
        initData(mDatas);
    }

    private void update(){
        if (Constants.IS_LOCALDATA){
            constructDataByLocal();
        }else{
            getData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onListViewRefresh() {
        pageIndex = 0;
        update();
        onLoad();
    }

    @Override
    public void onListViewLoadMore() {
        ++ pageIndex ;
        update();
        onLoad();
    }
}
