package com.tastebychance.sonchance.homepage.afterpay.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.homepage.afterpay.AfterPayActivity;
import com.tastebychance.sonchance.homepage.orderform.bean.OrderInfo;
import com.tastebychance.sonchance.util.StringUtil;


/***
 * 订单明细-购物车内容
 *
 * @author shenbh
 * @date 2017/9/5
 */
public class AfterPayOrderInfoAdapter extends BaseAdapter {

    private AfterPayActivity activity;
    private SparseArray<OrderInfo> dataList;

    public AfterPayOrderInfoAdapter(AfterPayActivity activity

            , SparseArray<OrderInfo> dataList) {

        this.activity = activity;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.valueAt(position) ;
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
//            viewholder.order_form_couponicon_iv = (ImageView) view.findViewById(R.id.order_form_couponicon_iv);
            viewholder.order_form_dishname_tv = (TextView) view.findViewById(R.id.order_form_dishname_tv);
            viewholder.order_form_dishnum_tv = (TextView) view.findViewById(R.id.order_form_dishnum_tv) ;
            viewholder.order_form_dishprice_tv = (TextView) view.findViewById(R.id.order_form_dishprice_tv);
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }

       /* if (dataList.valueAt(position).getGive_num() != 1){
            viewholder.order_form_couponicon_iv.setVisibility(View.GONE);
        }else{
            viewholder.order_form_couponicon_iv.setVisibility(View.VISIBLE);
        }

        viewholder.order_form_dishname_tv.setText(dataList.valueAt(position).getName());
        viewholder.order_form_dishnum_tv.setText("x"+dataList.valueAt(position).getCount()+"");
        viewholder.order_form_dishprice_tv.setText(StringUtil.setMoneySize(dataList.valueAt(position).getPrice()+"",10,14));*/

        //TODO：
        //如果是赠送的则加中划线
//        viewholder.order_form_dishprice_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        return view;
    }

    class Viewholder {

        private ImageView order_form_couponicon_iv;
        private TextView order_form_dishname_tv,order_form_dishnum_tv,order_form_dishprice_tv;
    }

}