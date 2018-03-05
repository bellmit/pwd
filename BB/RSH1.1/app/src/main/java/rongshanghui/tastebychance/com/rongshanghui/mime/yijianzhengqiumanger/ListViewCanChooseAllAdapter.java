package rongshanghui.tastebychance.com.rongshanghui.mime.yijianzhengqiumanger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.mime.yijianzhengqiumanger.bean.YJZQManagerBean;

/**
 * Created by shenbinghong on 2018/1/24.
 */

public class ListViewCanChooseAllAdapter extends BaseAdapter {
    private Context mContext;

    private List<YJZQManagerBean.DataBean> mDatas;

    private LayoutInflater mInflater;

    private boolean flage = false;

    public boolean isFlage() {
        return flage;
    }

    public void setFlage(boolean flage) {
        this.flage = flage;
    }

    public ListViewCanChooseAllAdapter(Context mContext, List<YJZQManagerBean.DataBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;

        mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_canchooceall_list, null);
            holder = new ViewHolder();
            holder.cbRl = (RelativeLayout) convertView.findViewById(R.id.cb_rl);
            holder.cb = (CheckBox) convertView.findViewById(R.id.item_yjzqmanager_cb);
            holder.titleTv = (TextView) convertView.findViewById(R.id.item_yjzqmanager_title_tv);
            holder.pageViewTv = (TextView) convertView.findViewById(R.id.item_yjzqmanager_pagerview_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final YJZQManagerBean.DataBean dataBean = mDatas.get(position);
        if (dataBean != null) {
            holder.titleTv.setText(dataBean.getPost_title());
            holder.pageViewTv.setText(dataBean.getPost_hits());


            // 根据isSelected来设置checkbox的显示状况
            if (flage) {
                holder.cbRl.setVisibility(View.VISIBLE);
            } else {
                holder.cbRl.setVisibility(View.GONE);
            }

            holder.cb.setChecked(dataBean.isChoosed());

            //注意这里设置的不是onCheckedChangListener，还是值得思考一下的
            holder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataBean.isChoosed()) {
                        dataBean.setChoosed(false);
                    } else {
                        dataBean.setChoosed(true);
                    }

                    notifyDataSetChanged();
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        public RelativeLayout cbRl;
        public CheckBox cb;
        public TextView titleTv;
        public TextView pageViewTv;
    }
}
