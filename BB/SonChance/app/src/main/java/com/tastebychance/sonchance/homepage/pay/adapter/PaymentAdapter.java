package com.tastebychance.sonchance.homepage.pay.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.homepage.pay.PayActivity;
import com.tastebychance.sonchance.util.StringUtil;

import java.util.List;


/**
 * 类描述：PaymentAdapter 支付方式
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/9/26 17:13
 * 修改人：
 * 修改时间：2017/9/26 17:13
 * 修改备注：
 *
 * @version 1.0
 */
public class PaymentAdapter extends BaseAdapter {

    private PayActivity activity;
    private List<PayActivity.PaymentInfo> dataList;
    private int temp = -1;

    public PaymentAdapter(PayActivity activity, List<PayActivity.PaymentInfo> dataList) {
        this.activity = activity;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final Viewholder viewholder;
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.home_pay_paymentlistitem, null);
            viewholder = new Viewholder();
            viewholder.paymenttype_icon_iv = (ImageView) view.findViewById(R.id.paymenttype_icon_iv);
            viewholder.paymenttype_name_tv = (TextView) view.findViewById(R.id.paymenttype_name_tv);
            viewholder.paymenttype_balance_tv = (TextView) view.findViewById(R.id.paymenttype_balance_tv);
            viewholder.paymenttype_cb = (CheckBox) view.findViewById(R.id.paymenttype_cb);
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }

        viewholder.paymenttype_icon_iv.setImageDrawable(activity.getResources().getDrawable(dataList.get(position).getIcon()));
        viewholder.paymenttype_name_tv.setText(dataList.get(position).getName());
        if (dataList.get(position).getPaytype() == 0) {
            viewholder.paymenttype_balance_tv.setText(StringUtil.setTextSizeColor("可用余额：￥" + dataList.get(position).getMoney(), Color.RED, "可用余额：".length(), ("可用余额：￥" + dataList.get(position).getMoney()).length(), 13));
        }

        viewholder.paymenttype_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){//实现checkbox的单选功能，同样适用于radiobutton
                    if (temp != -1){
                        //找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉
                        CheckBox tempCheckBox = (CheckBox) activity.findViewById(temp);
                        if (tempCheckBox != null){
                            tempCheckBox.setChecked(false);
                        }
                    }
                    temp = buttonView.getId();
                }
            }
        });
        if (position == temp){//比对position和当前的temp是否一致
            viewholder.paymenttype_cb.setChecked(true);
        }else{
            viewholder.paymenttype_cb.setChecked(false);
        }
        return view;
    }

    public static class Viewholder {
        public ImageView paymenttype_icon_iv;
        public TextView paymenttype_name_tv, paymenttype_balance_tv;
        public CheckBox paymenttype_cb;
    }
}