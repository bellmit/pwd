package com.tastebychance.sonchance.personal.ordercoupon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.personal.ordercoupon.PersonCouponActivity;
import com.tastebychance.sonchance.personal.ordercoupon.bean.CouponInfo;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.TimeOrDateUtil;

import java.util.Date;
import java.util.List;

/**
 * 优惠券adapter
 * Created by shenbh on 2017/8/29.
 */

public class PersonCouponAdapter extends BaseAdapter {

    private PersonCouponActivity activity;
    private List<CouponInfo> list;
    private String flag = "";

    public PersonCouponAdapter(Context context, String flag, List<CouponInfo> list) {
        this.activity = (PersonCouponActivity) context;
        this.flag = flag;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position) == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.person_coupon_listitem, null);
            viewHolder = new ViewHolder();
            viewHolder.person_coupon_listitem_endtime_tv = (TextView) convertView.findViewById(R.id.person_coupon_listitem_endtime_tv);
            viewHolder.person_coupon_listitem_imediatlyuse_tv = (TextView) convertView.findViewById(R.id.person_coupon_listitem_imediatlyuse_tv);
            viewHolder.person_coupon_listitem_money_tv = (TextView) convertView.findViewById(R.id.person_coupon_listitem_money_tv);
            viewHolder.person_coupon_listitem_packagename_tv = (TextView) convertView.findViewById(R.id.person_coupon_listitem_packagename_tv);
            viewHolder.person_coupon_listitem_remaintime_tv = (TextView) convertView.findViewById(R.id.person_coupon_listitem_remaintime_tv);
            viewHolder.person_coupon_listitem_tellimit_tv = (TextView) convertView.findViewById(R.id.person_coupon_listitem_tellimit_tv);
            viewHolder.person_coupon_listitem_usecondition_tv = (TextView) convertView.findViewById(R.id.person_coupon_listitem_usecondition_tv);
            viewHolder.person_coupon_listitem_userange_tv = (TextView) convertView.findViewById(R.id.person_coupon_listitem_userange_tv);

            viewHolder.person_coupon_listitem_ll = (LinearLayout) convertView.findViewById(R.id.person_coupon_listitem_ll);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list.get(position).getIs_used() == 0) {
//            Date endDate = list.get(position).getEnd_time();
//            if (null != endDate && new Date().before(endDate)) {
            viewHolder.person_coupon_listitem_money_tv.setText(StringUtil.setMoneySizeAndColor(list.get(position).getDiscount() + "", Color.RED, 13, 31));
            viewHolder.person_coupon_listitem_usecondition_tv.setText("满" + list.get(position).getNeed_num() + "元可用");
//            viewHolder.person_coupon_listitem_packagename_tv.setText(list.get(position).get); ;
            viewHolder.person_coupon_listitem_endtime_tv.setText(TimeOrDateUtil.formatDate(list.get(position).getEnd_time(), TimeOrDateUtil.YYYY_MM_DD) + "到期");
//            viewHolder.person_coupon_listitem_tellimit_tv.setText(list.get(position).get); ;


//                int remainTime = TimeOrDateUtil.calculateDays(endDate, new Date());
            int remainTime = list.get(position).getLast_day();
            if (remainTime == 1) {
                viewHolder.person_coupon_listitem_remaintime_tv.setText("限今日");
            } else {
                viewHolder.person_coupon_listitem_remaintime_tv.setText("剩 " + remainTime + " 日");
            }

            //开始使用时间
            Date startDate = list.get(position).getBegin_time();


            //样式：从配送页进来的，判断不可用的设置为灰色。从我的界面进来的直接显示所有未使用的（不变灰）
            if (StringUtil.isNotEmpty(flag) && flag.equals("OrderFormActivity")) {//从配送页进来
                if (list.get(position).getCan_use() == 0) {//0不可用
                    viewHolder.person_coupon_listitem_money_tv.setText(StringUtil.setMoneySizeAndColor(list.get(position).getDiscount() + "", Color.GRAY, 13, 31));
                    viewHolder.person_coupon_listitem_remaintime_tv.setText("不可用");
                    viewHolder.person_coupon_listitem_remaintime_tv.setTextColor(Color.GRAY);
                    viewHolder.person_coupon_listitem_imediatlyuse_tv.setTextColor(Color.GRAY);
                    viewHolder.person_coupon_listitem_imediatlyuse_tv.setBackground(activity.getResources().getDrawable(R.drawable.circle25_grayedge_style));
                    viewHolder.person_coupon_listitem_imediatlyuse_tv.setEnabled(false);
                }else{
                    viewHolder.person_coupon_listitem_imediatlyuse_tv.setEnabled(true);
                }
            }

            viewHolder.person_coupon_listitem_userange_tv.setText(list.get(position).getFanwei());

            viewHolder.person_coupon_listitem_imediatlyuse_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = null;
                    if (StringUtil.isEmpty(flag)) {
                        /*intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);*/
                    } else if (flag.equals("OrderFormActivity")) {
                        intent = new Intent();
                        intent.putExtra("coupon_id", list.get(position).getId() + "");//返回选中的购物券id
                        activity.setResult(1000, intent);
                    }

                    activity.finish();
                }
            });

            /*viewHolder.person_coupon_listitem_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    if (StringUtil.isEmpty(flag)){

                    }else if(flag.equals("OrderFormActivity")){
                        intent = new Intent();
                        intent.putExtra("coupon_id", list.get(position).getId() + "");//返回选中的购物券id
                        activity.setResult(1000, intent);

                        activity.finish();
                    }
                }
            });*/
//            }
        }


        return convertView;
    }

    class ViewHolder {
        private TextView person_coupon_listitem_money_tv;
        private TextView person_coupon_listitem_usecondition_tv;
        private TextView person_coupon_listitem_packagename_tv;
        private TextView person_coupon_listitem_endtime_tv;
        private TextView person_coupon_listitem_tellimit_tv;
        private TextView person_coupon_listitem_remaintime_tv;
        private TextView person_coupon_listitem_imediatlyuse_tv;
        private TextView person_coupon_listitem_userange_tv;

        private LinearLayout person_coupon_listitem_ll;
    }
}
