package com.tastebychance.sonchance.personal.locate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.personal.locate.GoodsReceiptAddressManagerActivity;
import com.tastebychance.sonchance.personal.locate.bean.GoodsReceiptInfo;

import java.util.List;

/**
 * Created by shenbh on 2017/8/23.
 */

public class GoodsReceiptAddressManagerAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<GoodsReceiptInfo> list;
    private GoodsReceiptAddressManagerActivity activity;

    public GoodsReceiptAddressManagerAdapter(Context context, List<GoodsReceiptInfo> list) {
        this.activity = (GoodsReceiptAddressManagerActivity) context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.person_goodsreceiptaddress_item, null);
            viewHolder = new ViewHolder();
            viewHolder.person_goodsreceiptaddress_listitem_default_tv = (TextView) convertView.findViewById(R.id.person_goodsreceiptaddress_listitem_default_tv);
            viewHolder.person_goodsreceiptaddress_listitem_address_tv = (TextView) convertView.findViewById(R.id.person_goodsreceiptaddress_listitem_address_tv);
            viewHolder.person_goodsreceiptaddress_listitem_username_tv = (TextView) convertView.findViewById(R.id.person_goodsreceiptaddress_listitem_username_tv);
            viewHolder.person_goodsreceiptaddress_listitem_tel_tv = (TextView) convertView.findViewById(R.id.person_goodsreceiptaddress_listitem_tel_tv);
            viewHolder.person_goodsreceiptaddress_listitem_edt_ll = (LinearLayout) convertView.findViewById(R.id.person_goodsreceiptaddress_listitem_edt_ll);

            viewHolder.delete = (TextView) convertView.findViewById(R.id.delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list.get(position).getIs_check().equals("1")){
            viewHolder.person_goodsreceiptaddress_listitem_default_tv.setVisibility(View.VISIBLE);
        }else{
            viewHolder.person_goodsreceiptaddress_listitem_default_tv.setVisibility(View.GONE);
        }
        viewHolder.person_goodsreceiptaddress_listitem_address_tv.setText(list.get(position).getAddress_detail());
        viewHolder.person_goodsreceiptaddress_listitem_username_tv.setText(list.get(position).getUsername());
        viewHolder.person_goodsreceiptaddress_listitem_tel_tv.setText(list.get(position).getTel());
        viewHolder.person_goodsreceiptaddress_listitem_edt_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.intentToAddressEditActivity(list.get(position));
            }
        });

        //删除
        final int pos = position;
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.deleteAddress(pos);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView person_goodsreceiptaddress_listitem_default_tv;
        private TextView person_goodsreceiptaddress_listitem_address_tv;
        private TextView person_goodsreceiptaddress_listitem_username_tv;
        private TextView person_goodsreceiptaddress_listitem_tel_tv;
        private LinearLayout person_goodsreceiptaddress_listitem_edt_ll;

        private TextView delete;
    }
}
