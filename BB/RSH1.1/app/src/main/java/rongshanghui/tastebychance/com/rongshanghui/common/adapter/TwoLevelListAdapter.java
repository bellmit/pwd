package rongshanghui.tastebychance.com.rongshanghui.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.home.rsnews.bean.RSNewslvRes;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;

/**
 * 项目名称：ListViewFirstItemDemo
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/16 14:24
 * 修改人：Administrator
 * 修改时间：2017/11/16 14:24
 * 修改备注：
 */

public class TwoLevelListAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private ArrayList<HashMap<String, Object>> items;
    private Context context;

    public TwoLevelListAdapter(Context context, ArrayList<HashMap<String, Object>> items) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        HashMap<String, Object> map = items.get(i);
        return map.get(Constants.DATA);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        GroupViewHolder groupViewHolder;
        ItemViewHolder itemViewHolder;
        View groupView = view;
        View itemView = view;

        int type = getItemViewType(i);
        switch (type) {
            case Constants.GROUP:
                if (null == groupView) {
                    groupView = inflater.inflate(R.layout.item_twolevellist_group, null);
                    groupViewHolder = new GroupViewHolder();
                    groupViewHolder.twolevelListHeadIv = (ImageView) groupView.findViewById(R.id.hdly_child1__bg_iv);
                    groupViewHolder.twolevelListHeadTv = (TextView) groupView.findViewById(R.id.twolevellist_head_tv);

                    groupView.setTag(groupViewHolder);
                } else {
                    groupViewHolder = (GroupViewHolder) groupView.getTag();
                }
                view = groupView;

                RSNewslvRes.DataBeanX.TopBean topBean = (RSNewslvRes.DataBeanX.TopBean) getItem(i);
                groupViewHolder.twolevelListHeadTv.setText(topBean.getPost_title());
                PicassoUtils.getinstance().loadNormalByPath(context, topBean.getM_post_image1(), groupViewHolder.twolevelListHeadIv);
                break;
            case Constants.ITEM:
                if (null == itemView) {
                    itemView = inflater.inflate(R.layout.item_twolevellist_child, null);
                    itemViewHolder = new ItemViewHolder();

                    itemViewHolder.twolevellistChildPicIv = (ImageView) itemView.findViewById(R.id.item_yjzq_hf_img_iv);
                    itemViewHolder.twolevellistChildTitleTv = (TextView) itemView.findViewById(R.id.twolevellist_child_title_tv);
                    itemViewHolder.twolevellistPagerviewTv = (TextView) itemView.findViewById(R.id.twolevellist_child_pagerview_tv);
                    itemViewHolder.twolevellistSourceTv = (TextView) itemView.findViewById(R.id.twolevellist_child_source_tv);

                    itemView.setTag(itemViewHolder);
                } else {
                    itemViewHolder = (ItemViewHolder) itemView.getTag();
                }
                view = itemView;

                RSNewslvRes.DataBeanX.ListBean.DataBean dataBean = (RSNewslvRes.DataBeanX.ListBean.DataBean) getItem(i);
                PicassoUtils.getinstance().loadNormalByPath(context, dataBean.getM_post_image1(), itemViewHolder.twolevellistChildPicIv);
                itemViewHolder.twolevellistChildTitleTv.setText(dataBean.getPost_title());
                itemViewHolder.twolevellistPagerviewTv.setText(dataBean.getPost_hits() + "人");
                itemViewHolder.twolevellistSourceTv.setText(dataBean.getUnit_name());
                break;
        }

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        HashMap<String, Object> map = items.get(position);
        return (int) map.get(Constants.TYPE);
    }

    @Override
    public int getViewTypeCount() {
        return Constants.VIEW_TYPE_COUNT;
    }

    class GroupViewHolder {
        private ImageView twolevelListHeadIv;
        private TextView twolevelListHeadTv;
    }

    class ItemViewHolder {
        private ImageView twolevellistChildPicIv;
        private TextView twolevellistChildTitleTv;
        private TextView twolevellistPagerviewTv;
        private TextView twolevellistSourceTv;
    }
}
