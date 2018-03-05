package com.tastebychance.sonchance.homepage.orderform.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.common.CommonAdapter;
import com.tastebychance.sonchance.common.ViewHolder;

import java.util.List;


/***
 * “订单配送至”界面的选择时间控件gridview的适配器
 */
public class ReceiptTimeGridAdapter<T> extends CommonAdapter<T> {
/*    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;*/

    public ReceiptTimeGridAdapter(Context mContext, List<T> mDatas, int mItemLayoutId) {
        super(mContext, mDatas, mItemLayoutId);
/*        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = mItemLayoutId;*/
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        View view = super.getView(position, convertView, parent);
        if (position == setSelectedPosition) {
            view.setBackgroundResource(R.drawable.choosetime_gv_chosed);
        } else {
            view.setBackgroundResource(R.drawable.choosetime_gv_unchosed);
        }

        convert(viewHolder, getItem(position));
        return view;
    }

    @Override
    protected void convert(ViewHolder viewHolder, T item) {

    }

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

    private int setSelectedPosition = 0;

    public void setSetSelectedPosition(int position) {
        setSelectedPosition = position;
    }
}