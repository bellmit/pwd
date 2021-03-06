package com.tastebychance.sonchance.homepage.myorder;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

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
import com.tastebychance.sonchance.homepage.afterpay.AfterPayActivity;
import com.tastebychance.sonchance.homepage.afterpay.bean.MobileInfo;
import com.tastebychance.sonchance.homepage.afterpay.bean.OrderAgainInfo;
import com.tastebychance.sonchance.homepage.myorder.adapter.MyPagerAdapter;
import com.tastebychance.sonchance.homepage.myorder.bean.MyOrderDetailInfo;
import com.tastebychance.sonchance.homepage.myorder.bean.MyOrderInfo;
import com.tastebychance.sonchance.homepage.order.bean.ShoppingcartBean;
import com.tastebychance.sonchance.homepage.pay.PayActivity;
import com.tastebychance.sonchance.homepage.toevaluate.ToEvaluateActivity;
import com.tastebychance.sonchance.homepage.toevaluate.bean.ButtonInfo;
import com.tastebychance.sonchance.util.CommomDialog;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.DensityUtils;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.util.commonpopupwindow.CommonPopupWindow;
import com.tastebychance.sonchance.view.CustomLoadingFactory;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ??????????????????
 *
 * @author shenbh
 * @date 2017/9/5
 */
public class MyOrderActivity extends MyBaseActivity {

    private static final int PAGER_NUM = 2;
    private TabHost mTabHost;
    private android.support.v4.view.ViewPager mViewpager;

    private List<MyOrderInfo> noFinishedList;
    private List<MyOrderInfo> historyList;


    private final String noFinish = "0,1,2";//???????????????
    private final String history = "3,4,5";//????????????

    private int currentPageIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_my_order);
        noFinishedList = new ArrayList<>();
        historyList = new ArrayList<>();
        showList = new ArrayList<>();

        SharedPreferencesUtils.setConfigBoolean(MyApplication.getContext(),Constants.TEMP,"isFromMyOrder",false);
        SharedPreferencesUtils.setConfigBoolean(MyApplication.getContext(),Constants.TEMP,"toMyOrder",false);

        setTitle();

        initTabHost(this);
        initPagerView(this);

        setListener();
    }

    /**
     * ????????????
     */
    private void setTitle() {
        //???????????????????????????????????????view????????????
/*        View view = (View) findViewById(R.id.view1);
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
            center_tv.setText("????????????");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    MyOrderActivity.this.finish();
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

    private void initTabHost(Context context){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        mTabHost = (TabHost) findViewById(R.id.tabhost);
        mTabHost.setLayoutParams(lp);
        mTabHost.setBackgroundColor(Color.WHITE);
        mTabHost.setup();

        View view1 = getLayoutInflater().inflate(R.layout.home_my_order_tab,null);
        TextView txt1 = (TextView) view1.findViewById(R.id.home_myorder_tv);
        ImageView img1 = (ImageView) view1.findViewById(R.id.home_myorder_iv);
        txt1.setText("???????????????");

        View view2 = getLayoutInflater().inflate(R.layout.home_my_order_tab,null);
        TextView txt2 = (TextView) view2.findViewById(R.id.home_myorder_tv);
        ImageView img2 = (ImageView) view2.findViewById(R.id.home_myorder_iv);
        txt2.setText("????????????");

        mTabHost.addTab(mTabHost.newTabSpec("A")
                .setIndicator(view1)
                .setContent(android.R.id.tabcontent));

        mTabHost.addTab(mTabHost.newTabSpec("B")
                .setIndicator(view2)
                .setContent(android.R.id.tabcontent));
        
        //?????????????????????????????????????????????
        mTabHost.setCurrentTab(0);
        //?????????Tab????????????????????????
        updateTab(mTabHost);
    }

    /**
     * ??????Tab????????????????????????????????????
     * @param mTabHost
     */
    private void updateTab(TabHost mTabHost) {
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            View view = mTabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.home_myorder_tv);
            tv.setTypeface(Typeface.SERIF,0);// ?????????????????????
            ImageView iv = (ImageView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.home_myorder_iv);
            if (mTabHost.getCurrentTab() == i){
                //??????
                view.setBackgroundColor(Color.WHITE);
                iv.setVisibility(View.VISIBLE);
            }else{
                //?????????
                view.setBackgroundColor(Color.WHITE);
                iv.setVisibility(View.INVISIBLE);
            }
        }
    }

    private List<View> viewList;
    private void initPagerView(Context context) {
        mViewpager = (android.support.v4.view.ViewPager) findViewById(R.id.viewpager);
        LayoutInflater lf = getLayoutInflater().from(context);
        View view1 = lf.inflate(R.layout.home_my_order_nofinish,null);
        View view2 = lf.inflate(R.layout.home_my_order_history,null);
        viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewList);
        mViewpager.setAdapter(myPagerAdapter);
        mViewpager.setOffscreenPageLimit(PAGER_NUM);

        initPagerItem(view1,noFinishedList);
    }

    private void setListener(){
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int currentIndex = mTabHost.getCurrentTab();
                mViewpager.setCurrentItem(currentIndex);


                mTabHost.setCurrentTabByTag(tabId);
                updateTab(mTabHost);
            }
        });

        /**
         * ViewPager??????????????????
         * */
        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int arg0) {
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageSelected(int position) {
                hightLightCurrentTabhost(position);
                currentPageIndex = position;
                getData();
            }

        });
    }

    private CommonAdapter<MyOrderInfo> myAdapter;
    private void initPagerItem(View childAt,List<MyOrderInfo> mDatas) {
       /* if (null == mDatas || mDatas.size() <= 0){
            return;
        }*/
        ListView list = (ListView) childAt.findViewById(R.id.pager_list);
        list.setAdapter(myAdapter = new CommonAdapter<MyOrderInfo>(
                getApplicationContext(),mDatas,R.layout.home_my_order_listitem
        ) {
            @Override
            protected void convert(ViewHolder viewHolder, MyOrderInfo item) {
                viewHolder.setText(R.id.textView1,"???????????????");
                viewHolder.setText(R.id.home_my_order_item_status_tv, StringUtil.statusToDescribe(item.getStatus()));
                viewHolder.setText(R.id.home_my_order_item_dishname_tv,item.getOrder_detail());
                viewHolder.setText(R.id.home_my_order_item_dishnum_tv,"???"+item.getCount()+"?????????");
                TextView home_my_order_item_subtotal_tv = viewHolder.getView(R.id.home_my_order_item_subtotal_tv);

                SpannableStringBuilder style = StringUtil.setTextSizeColor("??????:"+item.getDed_price()+"???"
                        , Color.BLACK ,"??????:".length(),("??????:"+item.getDed_price()).length(),22);
                home_my_order_item_subtotal_tv.setText(style);
                home_my_order_item_subtotal_tv.setTextColor(Color.BLACK);

                LinearLayout home_my_order_item_buttons_ll = viewHolder.getView(R.id.home_my_order_item_buttons_ll);
                home_my_order_item_buttons_ll.removeAllViews();
                //??????????????????
                final List<ButtonInfo> buttonInfos = item.getButton();
                if (null != buttonInfos){
                    LinearLayout.LayoutParams tv_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    tv_lp.width = DensityUtils.dp2px(MyOrderActivity.this,100);
//                    tv_lp.height = DensityUtils.dp2px(MyOrderActivity.this,40);
                    tv_lp.gravity = Gravity.CENTER;
                    tv_lp.rightMargin = 10;

                    System.out.println("viewHolder.getPosition() ================== " + viewHolder.getPosition());
                    for (int i = 0; i < buttonInfos.size(); i++) {
                        /*if (i == buttonInfos.size() - 1){
                            tv_lp.rightMargin = 0;
                        }*/

                        TextView tv = new TextView(MyOrderActivity.this);
                        tv.setCompoundDrawablePadding(DensityUtils.dp2px(MyOrderActivity.this,5));
                        tv.setGravity(Gravity.CENTER);

                        tv.setLayoutParams(tv_lp);
                        tv.setText(buttonInfos.get(i).getText());
                        if (buttonInfos.get(i).getAction_id() == 4){//?????????
                            tv.setBackground(MyOrderActivity.this.getResources().getDrawable(R.drawable.circle5_redbg_style));
                            tv.setTextColor(Color.WHITE);
                        }else if (buttonInfos.get(i).getAction_id() == 2){//????????????
                            tv.setBackground(MyOrderActivity.this.getResources().getDrawable(R.drawable.circle5_greenbg_style));
                            tv.setTextColor(Color.WHITE);
                        }else if (buttonInfos.get(i).getAction_id() == 5){//????????????
                            tv.setBackground(MyOrderActivity.this.getResources().getDrawable(R.drawable.circle5_rededge_style));
                            tv.setTextColor(Color.RED);
                        }else{
                            tv.setBackground(MyOrderActivity.this.getResources().getDrawable(R.drawable.circle5_grayedge_style));
                            tv.setTextColor(Color.BLACK);
                        }

                        home_my_order_item_buttons_ll.addView(tv);

                        btnClick(item,buttonInfos.get(i).getAction_id(),tv);
                    }
                }
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String order_sn = "";
                int currentItem = mViewpager.getCurrentItem();
                if (currentItem == 0){
                    order_sn = noFinishedList.get(position).getOrder_sn();
                }else{
                    order_sn = historyList.get(position).getOrder_sn();
                }
                //?????????????????????
                Intent intent = new Intent(MyOrderActivity.this,AfterPayActivity.class);
                intent.putExtra("order_sn",order_sn);
                SharedPreferencesUtils.setConfigBoolean(MyApplication.getContext(),Constants.TEMP,"isFromMyOrder",true);
                startActivity(intent);
                MyOrderActivity.this.finish();
            }
        });
    }

    protected void hightLightCurrentTabhost(int position) {
        mTabHost.setCurrentTab(position);
    }

    /**
     *???????????????actionId?????????????????????
     */
    private void btnClick(final MyOrderInfo item, final int actionId, TextView tv){
        if (actionId == 0){//?????????
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(MyOrderActivity.this,actionId+"",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MyOrderActivity.this, PayActivity.class);
                    intent.putExtra("order_sn",item.getOrder_sn());
                    startActivity(intent);
                    MyOrderActivity.this.finish();
                }
            });
        }else if (actionId == 1){//????????????
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(MyOrderActivity.this,actionId+"",Toast.LENGTH_SHORT).show();
                    contactSeller(item.getOrder_sn());
                }
            });
        }else if (actionId == 2){//????????????
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirm(item.getOrder_sn());
                }
            });
        }else if (actionId == 3){//????????????
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(MyOrderActivity.this,actionId+"",Toast.LENGTH_SHORT).show();

                    orderAgain(item.getOrder_sn());
                }
            });
        }else if (actionId == 4){//?????????
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyOrderActivity.this, ToEvaluateActivity.class);
                    intent.putExtra("order_sn",item.getOrder_sn());
                    startActivity(intent);
                }
            });
        }else if (actionId == 5){//????????????
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(MyOrderActivity.this,actionId+"",Toast.LENGTH_SHORT).show();
                    //?????????????????????????????????
                    comfirmCancelOrderPopupWindow(item.getOrder_sn());
                }
            });
        }
    }

    /**
     * ?????????????????????????????????
     */
    private CommonPopupWindow popupWindow;
    private void comfirmCancelOrderPopupWindow(final String order_sn) {
        popupWindow = new CommonPopupWindow.Builder(MyOrderActivity.this)
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
                                orderCancel(order_sn);
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

    private void getData() {
        if (Constants.IS_LOCALDATA){
            for (int i = 0; i < 3 ;i ++){
                WeakReference<MyOrderInfo> wf = new WeakReference<MyOrderInfo>(new MyOrderInfo());
                wf.get().setOrder_detail("??????????????????????????????????????????+??????");
                wf.get().setCount(1);
                wf.get().setStatus(0);

                WeakReference<ButtonInfo> wf2 = new WeakReference<ButtonInfo>(new ButtonInfo());
                wf2.get().setAction_id(0);
                wf2.get().setText("?????????");
                List<ButtonInfo> list = new ArrayList<>();
                list.add(wf2.get());

                wf.get().setButton(list);
                list = null;

                WeakReference<MyOrderDetailInfo> wf3 = new WeakReference<MyOrderDetailInfo>(new MyOrderDetailInfo());
                wf3.get().setId(22);
                wf3.get().setOrder_id(21);
                wf3.get().setUid(71);
                wf3.get().setDishes_id(58);
                wf3.get().setDishes_name("??????????????????????????????????????????+??????");
                wf3.get().setCount(1);
                wf3.get().setUnit_price(33.0f);
                wf3.get().setGive("");
                List<MyOrderDetailInfo> list1 = new ArrayList<>();
                list1.add(wf3.get());

                wf.get().setDetail_list(list1);
                list1 = null;

                noFinishedList.add(wf.get());
            }

            for (int i = 0; i < 3 ;i ++){
                WeakReference<MyOrderInfo> wf = new WeakReference<MyOrderInfo>(new MyOrderInfo());
                wf.get().setOrder_detail("??????????????????????????????????????????+??????");
                wf.get().setCount(1);
                wf.get().setStatus(0);

                List<ButtonInfo> list = new ArrayList<>();

                for (int j = 0; j <2 ; j++) {
                    WeakReference<ButtonInfo> wf2 = new WeakReference<ButtonInfo>(new ButtonInfo());
                    wf2.get().setAction_id(j);
                    if (j == 0){
                        wf2.get().setText("????????????");
                    }else{
                        wf2.get().setText("?????????");
                    }
                    list.add(wf2.get());
                }
                wf.get().setButton(list);
                list = null;

                WeakReference<MyOrderDetailInfo> wf3 = new WeakReference<MyOrderDetailInfo>(new MyOrderDetailInfo());
                wf3.get().setId(22);
                wf3.get().setOrder_id(21);
                wf3.get().setUid(71);
                wf3.get().setDishes_id(58);
                wf3.get().setDishes_name("??????????????????????????????????????????+??????");
                wf3.get().setCount(1);
                wf3.get().setUnit_price(33.0f);
                wf3.get().setGive("");
                List<MyOrderDetailInfo> list1 = new ArrayList<>();
                list1.add(wf3.get());

                wf.get().setDetail_list(list1);

                historyList.add(wf.get());
            }

            if (null != viewList && viewList.size() > 0){
                initPagerItem(viewList.get(0),noFinishedList);
            }
        }else{
            if (currentPageIndex == 0){
                getDataFromServer(noFinish);
            }else{
                getDataFromServer(history);
            }
        }

        if (null != myAdapter){
            myAdapter.notifyDataSetChanged();
        }
    }

    /**
     *
     * @param num 0,1 ????????????????????????0??????????????????1?????????????????? 2,3,4????????????????????????2?????????????????????3?????????????????????4?????????????????????
     */
    private List<MyOrderInfo> showList;
    private void getDataFromServer(final String num) {
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
        final String url = UrlManager.URL_HOME_GETORDERS;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("num",num).build();
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
                        if (Constants.IS_DEVELOPING) {
                            Message msg = new Message();
                            msg.what = INFO_WHAT;
                            msg.obj = GETDATA_SUCCESS;
                            myHandler.sendMessage(msg);
                        }

                        if (num.equals(noFinish)) {
                            noFinishedList = resInfo.getDataList(MyOrderInfo.class);
                            showList.clear();
                            showList.addAll(noFinishedList);
                        } else {
                            historyList = resInfo.getDataList(MyOrderInfo.class);
                            showList.clear();
                            showList.addAll(historyList);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null != viewList) {
                                    if (num.equals(noFinish)) {
                                        if (viewList.size() > 0) {
                                            initPagerItem(viewList.get(0), noFinishedList);
                                        }
                                    } else {
                                        if (viewList.size() > 1) {
                                            initPagerItem(viewList.get(1), historyList);
                                        }
                                    }

                                    if (null != myAdapter) {
                                        myAdapter.notifyDataSetChanged();
                                    }
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
                    CrashHandler.getInstance().handlerError("??????"+url+" ???????????????????????????");
                }
            }
        });
    }

    /**????????????
     * /api/UserApp/orderAgain
     * @param order_sn
     */
    private void orderAgain(String order_sn) {
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

                                startActivity(new Intent(MyOrderActivity.this, TabHostMainActivity.class));
                                MyOrderActivity.this.finish();
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
     * @param order_sn
     */
    private void confirm(String order_sn) {
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
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //??????UI??????
//                                center_tv.setText("?????????");
//                                stationFlag = ORDER_DONE;
//                                initObject();

                                //????????????????????????????????????????????????
                                getData();
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);

                        getData();
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
     * @param order_sn
     */
    private void contactSeller(String order_sn) {
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
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
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
                                    new CommomDialog(MyOrderActivity.this, R.style.dialog, "????????????" + mobileInfo.getMobile().toString() , new CommomDialog.OnCloseListener() {
                                        @Override
                                        public void onClick(Dialog dialog, boolean confirm) {
                                            if (confirm) {
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
     * @param order_sn
     */
    private void orderCancel(String order_sn) {
        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.root_layout),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

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

//                        List<OrderAgainInfo> againInfos   = resInfo.getDataList(OrderAgainInfo.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //?????????????????????
                                /*startActivity(new Intent(MyOrderActivity.this, TabHostMainActivity.class));
                                MyOrderActivity.this.finish();*/
                                getData();
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
                        getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("??????"+url+" ???????????????????????????");
                }
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getData();
    }
}
