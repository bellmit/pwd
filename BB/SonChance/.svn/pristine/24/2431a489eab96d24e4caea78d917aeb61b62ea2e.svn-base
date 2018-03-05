package com.tastebychance.sonchance.homepage.pay;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.common.CommonAdapter;
import com.tastebychance.sonchance.common.ViewHolder;
import com.tastebychance.sonchance.homepage.orderform.bean.GiveDetailInfo;
import com.tastebychance.sonchance.homepage.pay.bean.GetPayInfo;
import com.tastebychance.sonchance.homepage.pay.bean.OrderDetailInfo;
import com.tastebychance.sonchance.util.StringUtil;

import java.util.List;

public class PayDetailActivity extends MyBaseActivity{

    private RelativeLayout content_order_form;
    private TextView home_pay_detail_userinfo_tv;
    private TextView home_pay_detail_address_tv;
    private com.tastebychance.sonchance.view.MyListView afterpay_dishes_mlv;

    private GetPayInfo getPayInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_pay_detail);
        Intent intent = getIntent();
        getPayInfo = (GetPayInfo) intent.getSerializableExtra("getpayinfo");

        bindViews();
        initData();
    }

    private void bindViews() {
        content_order_form = (RelativeLayout) findViewById(R.id.content_order_form);
        home_pay_detail_userinfo_tv = (TextView) findViewById(R.id.home_pay_detail_userinfo_tv);
        home_pay_detail_address_tv = (TextView) findViewById(R.id.home_pay_detail_address_tv);
        afterpay_dishes_mlv = (com.tastebychance.sonchance.view.MyListView) findViewById(R.id.afterpay_dishes_mlv);
    }

    private CommonAdapter<OrderDetailInfo> orderDetailInfoCommonAdapter;
    private void initData(){
        if (getPayInfo != null){
            com.tastebychance.sonchance.homepage.pay.bean.OrderInfo orderInfo = getPayInfo.getOrder();
            home_pay_detail_userinfo_tv.setText(orderInfo.getReceived_username() +" "+ orderInfo.getReceived_tel());
            home_pay_detail_address_tv.setText(orderInfo.getReceived_village() +" "+orderInfo.getReceived_address());

            //订单明细-购物车
            if (null == getPayInfo.getOrder_detail() || getPayInfo.getOrder_detail().size() <= 0) {
                return;
            }
            List<OrderDetailInfo> orderDetailInfos = getPayInfo.getOrder_detail();
            afterpay_dishes_mlv.setAdapter(orderDetailInfoCommonAdapter = new CommonAdapter<OrderDetailInfo>(
                    getApplicationContext(),orderDetailInfos,R.layout.home_pay_detail_listitem
            ) {
                @Override
                protected void convert(ViewHolder viewHolder, OrderDetailInfo item) {
                    TextView home_pay_detail_name_tv = viewHolder.getView(R.id.home_pay_detail_name_tv);
                    home_pay_detail_name_tv.setText(item.getDishes_name() + "x"+ item.getCount());
                    home_pay_detail_name_tv.setTextColor(Color.WHITE);

                    //订单明细-优惠情况
                    LinearLayout home_pay_detail_give_ll = viewHolder.getView(R.id.home_pay_detail_give_ll);
                    ImageView home_pay_detail_give_iv = viewHolder.getView(R.id.home_pay_detail_give_iv);
                    TextView home_pay_detail_givedishname_tv = viewHolder.getView(R.id.home_pay_detail_givedishname_tv);
                    try {

                        //返回是对象的处理
                        if(null == item.getGive() || StringUtil.isEmpty(item.getGive().toString())){
                            home_pay_detail_give_ll.setVisibility(View.GONE);
                        }else{
                            GiveDetailInfo giveDetailInfo = JSONObject.parseObject(item.getGive().toString(),GiveDetailInfo.class);
                            if (StringUtil.isEmpty(giveDetailInfo.getDishes_name())){
                                home_pay_detail_give_ll.setVisibility(View.GONE);
                            }else{
                                home_pay_detail_give_ll.setVisibility(View.VISIBLE);
                                home_pay_detail_give_iv.setImageDrawable(getResources().getDrawable(R.drawable.order_form_sendicon));
                                home_pay_detail_givedishname_tv.setText(giveDetailInfo.getDishes_name() + " x"+giveDetailInfo.getNum());
                                home_pay_detail_givedishname_tv.setTextColor(Color.WHITE);
                            }
                        }
                    } catch (Exception e){
                        //返回是集合的处理
                        if (null == item.getGive() || item.getGiveList(GiveDetailInfo.class).size() <= 0){
                            home_pay_detail_give_ll.setVisibility(View.GONE);
                        }else{
                            GiveDetailInfo giveDetailInfo = JSONObject.parseObject(item.getGive().toString(),GiveDetailInfo.class);
                            home_pay_detail_give_ll.setVisibility(View.VISIBLE);
                            home_pay_detail_give_iv.setImageDrawable(getResources().getDrawable(R.drawable.order_form_sendicon));
                            home_pay_detail_givedishname_tv.setText(giveDetailInfo.getDishes_name() + " x"+giveDetailInfo.getNum());
                            home_pay_detail_givedishname_tv.setTextColor(Color.WHITE);
                        }
                    }
                }
            });
        }
    }

    public void confirmClick(View view){
        PayDetailActivity.this.finish();
    }
}
