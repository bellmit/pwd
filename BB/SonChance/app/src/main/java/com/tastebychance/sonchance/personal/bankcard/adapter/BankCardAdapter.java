package com.tastebychance.sonchance.personal.bankcard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.personal.bankcard.BankCardActivity;
import com.tastebychance.sonchance.personal.bankcard.bean.BankCardInfo;

import java.util.List;

/**
 * 优惠券adapter
 * Created by shenbh on 2017/8/29.
 */

public class BankCardAdapter extends BaseAdapter {

    private BankCardActivity activity;
    private List<BankCardInfo> list;

    public BankCardAdapter(Context context, List<BankCardInfo> list) {
        this.activity = (BankCardActivity) context;
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.person_bankcard_listitem, null);
            viewHolder = new ViewHolder();
            viewHolder.person_bank_icon_iv = (ImageView) convertView.findViewById(R.id.person_bank_icon_iv);
            viewHolder.person_bank_name_tv = (TextView) convertView.findViewById(R.id.person_bank_name_tv);
            viewHolder.person_bank_type_tv = (TextView) convertView.findViewById(R.id.person_bank_type_tv);
            viewHolder.person_bank_number_tv = (TextView) convertView.findViewById(R.id.person_bank_number_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {
        private ImageView person_bank_icon_iv;
        private TextView person_bank_name_tv,person_bank_type_tv,person_bank_number_tv;
    }
}
