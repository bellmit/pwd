package com.tastebychance.sonchance.homepage.orderform.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.homepage.orderform.OrderFormActivity;
import com.tastebychance.sonchance.homepage.orderform.bean.GiveDetailInfo;
import com.tastebychance.sonchance.homepage.orderform.bean.OrderDetailInfo;
import com.tastebychance.sonchance.util.StringUtil;

import java.util.List;


/***
 * “订单配送至”界面的菜品list的adapter
 */
public class OrderInfoAdapter extends BaseAdapter {

    private OrderFormActivity activity;
    private List<OrderDetailInfo> dataList;

    public OrderInfoAdapter(OrderFormActivity activity

            , List<OrderDetailInfo> dataList) {

        this.activity = activity;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final Viewholder viewholder;
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.home_orderform_disheslistitem, null);
            viewholder = new Viewholder();
            viewholder.order_form_dishname_tv = (TextView) view.findViewById(R.id.order_form_dishname_tv);
            viewholder.order_form_dishnum_tv = (TextView) view.findViewById(R.id.order_form_dishnum_tv) ;
            viewholder.order_form_dishprice_tv = (TextView) view.findViewById(R.id.order_form_dishprice_tv);

            viewholder.order_form_give_ll = (LinearLayout) view.findViewById(R.id.order_form_give_ll);
            viewholder.order_form_give_iv = (ImageView) view.findViewById(R.id.order_form_give_iv);
            viewholder.order_form_givedishname_tv = (TextView) view.findViewById(R.id.order_form_givedishname_tv);
            viewholder.order_form_givedishnum_tv = (TextView) view.findViewById(R.id.order_form_givedishnum_tv);
            viewholder.order_form_givedishprice_tv = (TextView) view.findViewById(R.id.order_form_givedishprice_tv);
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }

        /*List<GiveDetailInfo> list = JSON.parseArray(dataList.get(position).getGive().toString(), GiveDetailInfo.class);
        GiveDetailInfo giveDetailInfo = null;
        if (null != list && list.size() > 0){
            giveDetailInfo = list.get(0);
        }
        System.out.println("giveDetailInfo = " + giveDetailInfo);
        */

        try {
            //返回是对象的处理
            if(null == dataList.get(position).getGive() || StringUtil.isEmpty(dataList.get(position).getGive().toString())){
                viewholder.order_form_give_ll.setVisibility(View.GONE);
            }else{
                GiveDetailInfo giveDetailInfo = JSONObject.parseObject(dataList.get(position).getGive().toString(),GiveDetailInfo.class);
                if (StringUtil.isEmpty(giveDetailInfo.getDishes_name())){
                    viewholder.order_form_give_ll.setVisibility(View.GONE);
                }else{
                    viewholder.order_form_give_ll.setVisibility(View.VISIBLE);
                    viewholder.order_form_give_iv.setImageDrawable(activity.getResources().getDrawable(R.drawable.order_form_sendicon));
                    viewholder.order_form_givedishname_tv.setText(giveDetailInfo.getDishes_name());
                    viewholder.order_form_givedishnum_tv.setText("x"+giveDetailInfo.getNum()+"");
                    viewholder.order_form_givedishprice_tv.setText(StringUtil.setMoneySize(giveDetailInfo.getSave_price()+"",10,14));
                    //赠送的or券送的则加中划线
                    viewholder.order_form_givedishprice_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        } catch (Exception e){
            //返回是集合的处理
            if (null == dataList.get(position).getGive() || ((JSONArray)dataList.get(position).getGive()).size() <= 0){
                viewholder.order_form_give_ll.setVisibility(View.GONE);
            }else{
                GiveDetailInfo giveDetailInfo = JSONObject.parseObject(dataList.get(position).getGive().toString(),GiveDetailInfo.class);
                viewholder.order_form_give_ll.setVisibility(View.VISIBLE);
                viewholder.order_form_give_iv.setImageDrawable(activity.getResources().getDrawable(R.drawable.order_form_sendicon));
                viewholder.order_form_givedishname_tv.setText(giveDetailInfo.getDishes_name());
                viewholder.order_form_givedishnum_tv.setText("x"+giveDetailInfo.getNum()+"");
                viewholder.order_form_givedishprice_tv.setText(StringUtil.setMoneySize(giveDetailInfo.getSave_price()+"",10,14));
                //赠送的or券送的则加中划线
                viewholder.order_form_givedishprice_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

        viewholder.order_form_dishname_tv.setText(dataList.get(position).getDishes_name());
        viewholder.order_form_dishnum_tv.setText("x"+dataList.get(position).getCount()+"");
        viewholder.order_form_dishprice_tv.setText(StringUtil.setMoneySize(dataList.get(position).getPrice()+"",10,14));
        return view;
    }

    class Viewholder {

        private TextView order_form_dishname_tv,order_form_dishnum_tv,order_form_dishprice_tv;

        //赠送or券
        private LinearLayout order_form_give_ll;
        private ImageView order_form_give_iv;
        private TextView order_form_givedishname_tv,order_form_givedishnum_tv,order_form_givedishprice_tv;
    }

}