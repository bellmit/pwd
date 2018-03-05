package com.tastebychance.sonchance.homepage.order.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.homepage.bean.HomePageListRes;
import com.tastebychance.sonchance.homepage.order.OrderActivity;
import com.tastebychance.sonchance.homepage.order.bean.EvaluateInfo;

/**
 * 评论列表数据展示（全部评论、好评、差评）
 *
 * @author shenbh
 *         <p>
 *         2017年8月11日
 */
public class EvaluateListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<EvaluateInfo> list;
    private OrderActivity orderActivity;

    public EvaluateListViewAdapter(Context context, List<EvaluateInfo> list) {
        this.orderActivity = (OrderActivity) context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.home_order_evaluate_listviewitem, null);
            viewHolder = new ViewHolder();

            viewHolder.home_order_evaluate_listviewitem_mobile_tv = (TextView) convertView.findViewById(R.id.home_order_evaluate_listviewitem_mobile_tv);
            viewHolder.home_order_evaluate_listviewitem_content_tv = (TextView) convertView.findViewById(R.id.home_order_evaluate_listviewitem_content_tv);
            viewHolder.home_order_evaluate_listviewitem_grade_tv = (TextView) convertView.findViewById(R.id.home_order_evaluate_listviewitem_grade_tv);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EvaluateInfo bean = list.get(position);
        viewHolder.home_order_evaluate_listviewitem_mobile_tv.setText(bean.getMobile()+":");
        viewHolder.home_order_evaluate_listviewitem_content_tv.setText(bean.getContent());
        viewHolder.home_order_evaluate_listviewitem_grade_tv.setText(bean.getGrade()+"");

        return convertView;
    }

    class ViewHolder {
        TextView home_order_evaluate_listviewitem_mobile_tv;
        TextView home_order_evaluate_listviewitem_content_tv;
        TextView home_order_evaluate_listviewitem_grade_tv;

    }
}
